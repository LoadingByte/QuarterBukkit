
package com.quartercode.quarterbukkit.api.hcl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.exception.ConfigLoadException;
import com.quartercode.quarterbukkit.api.exception.ConfigSaveException;
import com.quartercode.quarterbukkit.api.hcl.type.DatatypeBoolean;
import com.quartercode.quarterbukkit.api.hcl.type.DatatypeInteger;
import com.quartercode.quarterbukkit.api.hcl.type.DatatypeNumber;
import com.quartercode.quarterbukkit.api.hcl.type.DatatypeString;

/**
 * Class for representing a HCL config.
 */
public class Config implements ParentEntry {

    private static void fillStandardCustomTypes(final Config config) {

        config.registerCustomType("Integer", DatatypeInteger.class);
        config.registerCustomType("Number", DatatypeNumber.class);
        config.registerCustomType("Boolean", DatatypeBoolean.class);
        config.registerCustomType("String", DatatypeString.class);
    }

    private final Plugin                             plugin;

    private ConfigParser                             parser                  = new DefaultConfigParser();
    private Map<String, Class<? extends CustomType>> customTypes             = new HashMap<String, Class<? extends CustomType>>();
    private final List<ConfigEntry>                  firstLevelConfigEntries = new ArrayList<ConfigEntry>();

    /**
     * Creates a new empty HCL config.
     * 
     * @param plugin The {@link Plugin}.
     */
    public Config(final Plugin plugin) {

        this.plugin = plugin;

        fillStandardCustomTypes(this);
    }

    /**
     * Creates a new HCL config and fills it with some {@link CustomType}s.
     * 
     * @param plugin The {@link Plugin}.
     * @param customTypes The {@link CustomType}s.
     */
    public Config(final Plugin plugin, final Map<String, Class<? extends CustomType>> customTypes) {

        this.plugin = plugin;
        this.customTypes = customTypes;

        fillStandardCustomTypes(this);
    }

    /**
     * Creates a new HCL config and sets the {@link CustomType}.
     * 
     * @param plugin The {@link Plugin}.
     * @param parser The {@link ConfigParser}.
     */
    public Config(final Plugin plugin, final ConfigParser parser) {

        this.plugin = plugin;
        this.parser = parser;

        fillStandardCustomTypes(this);
    }

    /**
     * Creates a new HCL config, fills it with some {@link CustomType}s and sets the {@link ConfigParser}.
     * 
     * @param plugin The {@link Plugin}.
     * @param customTypes The {@link CustomType}s.
     * @param parser The {@link ConfigParser}.
     */
    public Config(final Plugin plugin, final Map<String, Class<? extends CustomType>> customTypes, final ConfigParser parser) {

        this.plugin = plugin;
        this.customTypes = customTypes;
        this.parser = parser;

        fillStandardCustomTypes(this);
    }

    /**
     * Returns the plugin.
     * 
     * @return The plugin.
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * returns the registered {@link CustomType}s.
     * 
     * @return The registered {@link CustomType}s.
     */
    public Map<String, Class<? extends CustomType>> getCustomTypes() {

        return Collections.unmodifiableMap(customTypes);
    }

    /**
     * Registers a {@link CustomType}.
     * 
     * @param name The name of the {@link CustomType} in HDL.
     * @param customType The {@link CustomType}-{@link Class}.
     */
    public void registerCustomType(final String name, final Class<? extends CustomType> customType) {

        if (customTypes.containsKey(name)) {
            throw new IllegalStateException("Name" + customType.getName() + " already registered");
        } else if (customTypes.containsValue(customType)) {
            throw new IllegalStateException("Class " + name + " already registered");
        }

        customTypes.put(name, customType);
    }

    /**
     * Unregisters a {@link CustomType}.
     * 
     * @param name The name of the {@link CustomType} in HDL.
     */
    public void unregisterCustomType(final String name) {

        if (!customTypes.containsKey(name)) {
            throw new IllegalStateException("Name " + name + " not registered");
        }

        customTypes.remove(name);
    }

    /**
     * Unregisters a {@link CustomType}.
     * 
     * @param customType The {@link CustomType}-{@link Class}.
     */
    public void unregisterCustomType(final Class<? extends CustomType> customType) {

        if (!customTypes.containsValue(customType)) {
            throw new IllegalStateException("Name " + customType.getName() + " not registered");
        }

        String removalKey = null;
        for (final Entry<String, Class<? extends CustomType>> entry : customTypes.entrySet()) {
            if (customType.equals(entry.getValue())) {
                removalKey = entry.getKey();
                break;
            }
        }

        if (removalKey != null) {
            customTypes.remove(removalKey);
        }
    }

    /**
     * returns the current {@link ConfigParser}.
     * 
     * @return The current {@link ConfigParser}.
     */
    public ConfigParser getParser() {

        return parser;
    }

    /**
     * Sets the current {@link ConfigParser}.
     * 
     * @param parser The {@link ConfigParser}.
     */
    public void setParser(final ConfigParser parser) {

        this.parser = parser;
    }

    /**
     * Returns all config entries on the first level.
     * 
     * @return All config entries on the first level.
     */
    @Override
    public List<ConfigEntry> getChildren() {

        return firstLevelConfigEntries;
    }

    /**
     * Returns all loaded config entries.
     * 
     * @return All loaded config entries.
     */
    public List<ConfigEntry> getAllChildren() {

        final List<ConfigEntry> configEntries = new ArrayList<ConfigEntry>();
        for (final ConfigEntry configEntry : firstLevelConfigEntries) {
            configEntries.add(configEntry);

            if (configEntry instanceof ParentEntry) {
                configEntries.addAll(getAllChildren((ParentEntry) configEntry));
            }
        }

        return configEntries;
    }

    /**
     * Returns all children entries of a {@link ParentEntry}.
     * 
     * @param parentEntry The {@link ParentEntry} to check.
     * @return All loaded config entries.
     */
    public List<ConfigEntry> getAllChildren(final ParentEntry parentEntry) {

        final List<ConfigEntry> configEntries = new ArrayList<ConfigEntry>();
        for (final ConfigEntry configEntry : parentEntry.getChildren()) {
            configEntries.add(configEntry);

            if (configEntry instanceof ParentEntry) {
                configEntries.addAll(getAllChildren((ParentEntry) configEntry));
            }
        }

        return configEntries;
    }

    /**
     * Returns the {@link ConfigEntry} matching to a path.
     * 
     * @param path The path.
     * @return The {@link ConfigEntry}.
     */
    public ConfigEntry getChild(String path) {

        if (path.contains("\\.")) {
            String[] names = path.split("\\.");
            ParentEntry currentEntry = this;
            for (String name : names) {
                ConfigEntry configEntry = getChild(currentEntry, name);

                if (currentEntry instanceof ParentEntry) {
                    currentEntry = (ParentEntry) configEntry;
                } else if (configEntry.getName().equalsIgnoreCase(names[names.length])) {
                    return configEntry;
                } else {
                    break;
                }
            }
        } else {
            for (ConfigEntry configEntry : firstLevelConfigEntries) {
                if (configEntry.getName().equalsIgnoreCase(path)) {
                    return configEntry;
                }
            }
        }

        throw new IllegalStateException("The path " + path + " wasn't found");
    }

    /**
     * Returns is a {@link ParentEntry} contains a direct child with a name.
     * 
     * @param parentEntry The {@link ParentEntry}.
     * @param name The name as a {@link String}.
     * @return If the {@link ParentEntry} contains a direct child with the name.
     */
    public ConfigEntry getChild(ParentEntry parentEntry, String name) {

        for (ConfigEntry configEntry : parentEntry.getChildren()) {
            if (configEntry.getName().equalsIgnoreCase(name)) {
                return configEntry;
            }
        }

        return null;
    }

    @Override
    public void addChild(final ConfigEntry configEntry) {

        if (firstLevelConfigEntries.contains(configEntry) || getChild(this, configEntry.getName()) != null) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " already exists");
        }

        firstLevelConfigEntries.add(configEntry);
    }

    @Override
    public void removeChild(final ConfigEntry configEntry) {

        if (!firstLevelConfigEntries.contains(configEntry) || getChild(this, configEntry.getName()) == null) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " not exists");
        }

        firstLevelConfigEntries.remove(configEntry);
    }

    /**
     * Loads a config from a {@link String}.
     * 
     * @param string The config save {@link String}.
     */
    public void load(final String string) {

        parser.insert(this, string);
    }

    /**
     * Loads a config from a {@link File}.
     * 
     * @param file The config save {@link File}.
     */
    public void load(final File file) {

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            final FileChannel fileChannel = inputStream.getChannel();
            final MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            load(Charset.defaultCharset().decode(byteBuffer).toString());
        }
        catch (final IOException e) {
            QuarterBukkit.exception(new ConfigLoadException(plugin, this, e, "Error while reading config file"));
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (final IOException e) {
                    QuarterBukkit.exception(new ConfigLoadException(plugin, this, e, "Error while closing file stream"));
                }
            }
        }
    }

    /**
     * Saves the config into a {@link String} and returns it.
     * 
     * @return The saved config.
     */
    public String save() {

        return parser.get(this);
    }

    /**
     * Saves the config into a {@link File}.
     * 
     * @param file The config save {@link File}.
     */
    public void save(final File file) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            final String config = save();
            config.replaceAll("\n", System.getProperty("line.separator"));
            writer.write(config);
        }
        catch (final IOException e) {
            QuarterBukkit.exception(new ConfigSaveException(plugin, this, e, "Error while saving config file"));
        }
        finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (final IOException e) {
                    QuarterBukkit.exception(new ConfigSaveException(plugin, this, e, "Error while closing file writer"));
                }
            }
        }
    }

}
