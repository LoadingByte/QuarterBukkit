
package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.exception.GameException;

public class Config extends YamlConfiguration {

    private Plugin plugin;
    private File   file;

    public Config(Plugin plugin, File file) {

        this.plugin = plugin;
        this.file = file;

        if (file.exists()) {
            load(file);
        }

        setDefaults();
        save(file);
    }

    @Override
    public void load(File file) {

        try {
            super.load(file);
        }
        catch (IOException e) {
            QuarterBukkit.exception(new GameException(plugin, "Can't load QuarterBukkit config"));
        }
        catch (InvalidConfigurationException e) {
            QuarterBukkit.exception(new GameException(plugin, "Can't load QuarterBukkit config"));
        }
    }

    public void save(File file) {

        try {
            super.save(file);
        }
        catch (IOException e) {
            QuarterBukkit.exception(new GameException(plugin, "Can't save QuarterBukkit config"));
        }
    }

    public void save() {

        save(file);
    }

    private void setDefaults() {

        addDefaultGV("autoupdate", true);
    }

    private void addDefaultGV(String path, Object value) {

        if (!contains(path)) {
            set(path, value);
        }
    }

}
