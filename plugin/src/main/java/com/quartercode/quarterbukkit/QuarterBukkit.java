/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;
import com.quartercode.quarterbukkit.util.Config;
import com.quartercode.quarterbukkit.util.CustomEventListener;
import com.quartercode.quarterbukkit.util.QuarterBukkitExceptionListener;
import com.quartercode.quarterbukkit.util.QuarterBukkitUpdater;

/**
 * The main class of the QuarterBukkit API.
 * This is for easy API loading.
 */
public class QuarterBukkit extends JavaPlugin {

    private static QuarterBukkit plugin;

    /**
     * Returns the current QuarterBukkit {@link Plugin} instance.
     *
     * @return The current QuarterBukkit instance.
     */
    public static QuarterBukkit getPlugin() {

        return plugin;
    }

    /**
     * Returns the {@link Logger} provided by the current QuarterBukkit {@link Plugin} instance.
     *
     * @return The current QuarterBukkit logger.
     */
    public static Logger getLog() {

        return plugin.getLogger();
    }

    private Config      config;
    private MetricsLite metrics;

    /**
     * The default constructor for Bukkit.
     */
    public QuarterBukkit() {

        if (plugin == null) {
            plugin = this;
        } else {
            throw new IllegalStateException("Plugin already initalized");
        }
    }

    @Override
    public void onEnable() {

        // Internal exceptions
        Bukkit.getPluginManager().registerEvents(new QuarterBukkitExceptionListener(), plugin);

        // Config
        config = new Config(new File(getDataFolder(), "config.yml"));

        // Autoupdate of the API plugin
        if (config.getBoolean("autoupdate")) {
            QuarterBukkitUpdater updater = new QuarterBukkitUpdater();
            getLogger().info("Checking for a new version and updating " + getName() + " ...");
            if (updater.checkAndUpdate()) {
                // Stop here if the updater succeeded (it reloads the plugin)
                return;
            }
        }

        // Enable MetricsLite
        getLogger().info("Enabling MetricsLite ...");
        try {
            metrics = new MetricsLite(this);
            metrics.enable();
        } catch (IOException e) {
            getLogger().severe("An error occurred while enabling MetricsLite (" + e + ")");
        }

        // Custom events
        Bukkit.getPluginManager().registerEvents(new CustomEventListener(), this);
    }

    @Override
    public void onDisable() {

        if (metrics != null) {
            // Disable MetricsLite
            getLogger().info("Disabling MetricsLite ...");
            try {
                metrics.disable();
            } catch (IOException e) {
                getLogger().severe("An error occurred while disabling MetricsLite (" + e + ")");
            }
        }
    }

    /**
     * Returns the internal config of QuarterBukkit.
     *
     * @return The {@link Config} object QuarterBukkit uses for its configuration.
     */
    @Override
    public Config getConfig() {

        return config;
    }

}
