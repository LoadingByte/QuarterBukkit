/*
 * This file is part of QuarterBukkit-Integration.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Integration is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Integration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Integration. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.FileUtils;
import com.quartercode.quarterbukkit.api.query.FilesQuery;
import com.quartercode.quarterbukkit.api.query.FilesQuery.ProjectFile;
import com.quartercode.quarterbukkit.api.query.FilesQuery.VersionParser;
import com.quartercode.quarterbukkit.api.query.QueryException;

/**
 * This class is used for integrating QuarterBukkit into a {@link Plugin}.
 */
public class QuarterBukkitIntegration {

    private static final String PLUGIN_NAME = "QuarterBukkit-Plugin";
    private static final int    PROJECT_ID  = 47006;

    /**
     * Call this method in onEnable() for integrating QuarterBukkit into your plugin.
     * It creates a config where the user has to turn a value to "Yes" for the actual installation.
     * The class notfies him on the console and every time an op joins to the server.
     *
     * @param plugin The {@link Plugin} which tries to integrate QuarterBukkit.
     * @return True if QuarterBukkit can be used after the call, false if not.
     */
    public static boolean integrate(final Plugin plugin) {

        File pluginDir = plugin.getDataFolder().getParentFile();

        if (!Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME)) {
            // This is the installation confirmation file
            File autoinstallConfigFile = new File(pluginDir + "/" + PLUGIN_NAME + "/" + "autoinstall.yml");

            try {
                // If no installation confirmation file exists yet, create a new one
                if (!autoinstallConfigFile.exists()) {
                    autoinstallConfigFile.getParentFile().mkdirs();
                    autoinstallConfigFile.createNewFile();
                }

                // Read the installation confirmation file
                YamlConfiguration autoinstallConfig = YamlConfiguration.loadConfiguration(autoinstallConfigFile);
                List<String> requiringPlugins = autoinstallConfig.getStringList(PLUGIN_NAME + "-requiring-plugins");

                // If the plugin which is currently integrating is present in the file, it must have written its name into it last time this method was called
                // Therefore, we can assume that the user restarted the server after reviewing the info message
                // That means that we are free to install the main plugin
                if (requiringPlugins.contains(plugin.getName())) {
                    autoinstallConfigFile.delete();
                    install(pluginDir);
                    return true;
                }
                // Otherwise, this is the first time the user starts up the server with a QuarterBukkit-requiring plugin
                // In that case, add the plugin's name to the file and wait for a restart
                else {
                    requiringPlugins.add(plugin.getName());
                    autoinstallConfig.set(PLUGIN_NAME + "-requiring-plugins", requiringPlugins);
                    autoinstallConfig.save(autoinstallConfigFile);
                }

                // Notify the user of the need to install QuarterBukkit and wait until restart
                // Schedule with a non-bukkit timer because the integrating plugin might become disabled
                new Timer().schedule(new TimerTask() {

                    @Override
                    public void run() {

                        Bukkit.broadcastMessage(ChatColor.RED + "" + plugin.getName() + " requires " + PLUGIN_NAME + ". Please " + ChatColor.DARK_AQUA + "restart" + ChatColor.RED + " the server to automatically install it.");
                    }

                }, 0, 5 * 1000);
            } catch (UnknownHostException e) {
                Bukkit.getLogger().warning("Can't connect to dev.bukkit.org for installing " + PLUGIN_NAME + "!");
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred while installing " + PLUGIN_NAME, e);
            }

            return false;
        } else {
            return true;
        }
    }

    private static void install(File pluginDir) throws QueryException, IOException, InvalidPluginException, InvalidDescriptionException {

        // ----- Get Latest Version -----

        Bukkit.getLogger().info("===============[ " + PLUGIN_NAME + " Installation ]===============");

        Bukkit.getLogger().info("Querying server mods api ...");

        // Get latest version
        List<ProjectFile> availableFiles = new FilesQuery(PROJECT_ID, new VersionParser() {

            @Override
            public String parseVersion(ProjectFile file) {

                return file.getName().replace("QuarterBukkit ", "");
            }

        }).execute();
        if (availableFiles.isEmpty()) {
            // No file available
            return;
        }
        ProjectFile latestFile = availableFiles.get(availableFiles.size() - 1);

        Bukkit.getLogger().info("Found the latest version of " + PLUGIN_NAME + ": " + latestFile.getVersion());

        // ----- Download and Installation -----

        Bukkit.getLogger().info("Installing " + PLUGIN_NAME + " " + latestFile.getVersion());

        // Download zip
        File zip = new File(pluginDir, latestFile.getFileName());
        FileUtils.download(latestFile.getLocation().toURL(), zip);

        // Unzip zip
        File unzipDir = new File(zip.getParent(), zip.getName() + "_extract");
        FileUtils.unzip(zip, unzipDir);
        FileUtils.delete(zip);

        // Get inner directory
        File innerUnzipDir = unzipDir.listFiles()[0];

        // Overwrite current plugin jar
        File pluginJar = new File(pluginDir, PLUGIN_NAME + ".jar");
        FileUtils.copy(new File(innerUnzipDir, pluginJar.getName()), pluginJar);

        // Delete temporary unzip dir
        FileUtils.delete(unzipDir);

        // Load plugin from file
        Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(pluginJar));

        Bukkit.getLogger().info("Successfully installed " + PLUGIN_NAME + " " + latestFile.getVersion() + "!");
    }

    private QuarterBukkitIntegration() {

    }

}
