
package com.quartercode.quarterbukkit.api.hcl.type;

import java.util.Collections;
import java.util.List;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.ConfigEntry;
import com.quartercode.quarterbukkit.api.hcl.ParentEntry;

/**
 * Class for representing a HCL config list with {@code -}.
 */
public class ConfigList extends ConfigEntry implements ParentEntry {

    private List<ConfigEntry> children;

    /**
     * Creates a new HCL config list and sets the name and the type definition.
     * 
     * @param config The config.
     * @param name The name of the entry.
     */
    public ConfigList(final Config config, final String name) {

        super(config, name);
    }

    @Override
    public List<ConfigEntry> getChildren() {

        return Collections.unmodifiableList(children);
    }

    @Override
    public void addChild(final ConfigEntry configEntry) {

        if (children.contains(configEntry) || config.getChild(this, configEntry.getName()) != null) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " already exists");
        }

        children.add(configEntry);
    }

    @Override
    public void removeChild(final ConfigEntry configEntry) {

        if (!children.contains(configEntry) || config.getChild(this, configEntry.getName()) == null) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " not exists");
        }

        children.remove(configEntry);
    }

}
