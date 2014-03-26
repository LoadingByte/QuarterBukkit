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

package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.Bukkit;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.FileUtils;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InstallException;
import com.quartercode.quarterbukkit.api.query.FilesQuery;
import com.quartercode.quarterbukkit.api.query.FilesQuery.ProjectFile;
import com.quartercode.quarterbukkit.api.query.FilesQuery.VersionParser;
import com.quartercode.quarterbukkit.api.query.QueryException;

/**
 * This class is for updating the QuarterBukkit plugin.
 */
public class QuarterBukkitUpdater {

    private static final int PROJECT_ID = 47006;

    /**
     * Creates a new QuarterBukkit updater.
     */
    public QuarterBukkitUpdater() {

    }

    /**
     * Checks for a new version of QuarterBukkit and executes the update if necessary.
     * 
     * @return True if an update was executed successfully.
     */
    public boolean checkAndUpdate() {

        QuarterBukkit plugin = QuarterBukkit.getPlugin();

        try {
            // ----- Version Check -----

            plugin.getLogger().info("Querying server mods api ...");

            // Get latest version
            List<ProjectFile> availableFiles = new FilesQuery(PROJECT_ID, new VersionParser() {

                @Override
                public String parseVersion(ProjectFile file) {

                    return file.getName().replace("QuarterBukkit ", "");
                }
            }).execute();
            if (availableFiles.isEmpty()) {
                // No file available
                return false;
            }

            ProjectFile latestFile = availableFiles.get(availableFiles.size() - 1);
            if (latestFile.getVersion().equals(plugin.getDescription().getVersion())) {
                // No update required (latest version already installed)
                return false;
            }

            plugin.getLogger().info("Found a new version of " + plugin.getName() + ": " + latestFile.getVersion());

            // ----- Download and Installation -----

            plugin.getLogger().info("Installing " + plugin.getName() + " " + latestFile.getVersion());

            // Variables
            File pluginsDir = plugin.getDataFolder().getParentFile();
            File pluginJar = new File(pluginsDir, File.separator + plugin.getName() + ".jar");

            // Disable plugin
            Bukkit.getPluginManager().disablePlugin(plugin);

            // Download zip
            File zip = new File(pluginsDir, latestFile.getFileName());
            FileUtils.download(latestFile.getLocation().toURL(), zip);

            // Unzip zip
            File unzipDir = new File(zip.getParent(), zip.getName() + "_extract");
            FileUtils.unzip(zip, unzipDir);
            FileUtils.delete(zip);

            // Get inner directory
            File innerUnzipDir = unzipDir.listFiles()[0];

            // Overwrite current plugin jar
            FileUtils.copy(new File(innerUnzipDir, pluginJar.getName()), pluginJar);

            // Delete temporary unzip dir
            FileUtils.delete(unzipDir);

            // Load plugin from file
            try {
                Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(pluginJar));
            } catch (Exception e) {
                ExceptionHandler.exception(new InstallException(plugin, e, "Error while reloading the plugin with the new jar"));
                return false;
            }

            plugin.getLogger().info("Successfully updated " + plugin.getName() + " to " + latestFile.getVersion() + "!");
            return true;
        } catch (QueryException e) {
            ExceptionHandler.exception(new InstallException(plugin, e, "Error while querying the server mods api: " + e.getType()));
        } catch (IOException e) {
            ExceptionHandler.exception(new InstallException(plugin, e, "Error while doing some file operations"));
        }

        return false;
    }

}
