
package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionManager;
import com.quartercode.quarterbukkit.api.exception.GameException;

public class Config extends YamlConfiguration {

    private final Plugin plugin;
    private final File   file;

    public Config(final Plugin plugin, final File file) {

        this.plugin = plugin;
        this.file = file;

        if (file.exists()) {
            load(file);
        }

        setDefaults();
        save(file);
    }

    @Override
    public void load(final File file) {

        try {
            super.load(file);
        }
        catch (final IOException e) {
            ExceptionManager.exception(new GameException(plugin, "Can't load QuarterBukkit config (" + e + ")"));
        }
        catch (final InvalidConfigurationException e) {
            ExceptionManager.exception(new GameException(plugin, "Can't load QuarterBukkit config (" + e + ")"));
        }
    }

    @Override
    public void save(final File file) {

        try {
            super.save(file);
        }
        catch (final IOException e) {
            ExceptionManager.exception(new GameException(plugin, "Can't save QuarterBukkit config (" + e + ")"));
        }
    }

    public void save() {

        save(file);
    }

    private void setDefaults() {

        addDefaultGV("autoupdate", true);
    }

    private void addDefaultGV(final String path, final Object value) {

        if (!contains(path)) {
            set(path, value);
        }
    }

}
