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
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.FileUtils;
import com.quartercode.quarterbukkit.api.Updater;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InstallException;

/**
 * This class is for checking the QuarterBukkit-version and updating the plugin.
 */
public class QuarterBukkitUpdater extends Updater {

    /**
     * Creates a new QuarterBukkit updater.
     * 
     * @param plugin The {@link QuarterBukkit}-{@link Plugin}.
     */
    public QuarterBukkitUpdater(Plugin plugin) {

        super(plugin, plugin, "quarterbukkit");
    }

    @Override
    protected String parseVersion(String title) {

        return title.replaceAll("QuarterBukkit ", "");
    }

    @Override
    protected void doInstall(File downloadedFile, CommandSender causer) throws IOException {

        File pluginJar = new File("plugins" + File.separator + getUpdatePlugin().getName() + ".jar");

        // Unzip the new plugin jar from the downloaded file
        File unzipDir = new File(downloadedFile.getParent(), getUpdatePlugin().getName() + "_extract");
        FileUtils.unzip(downloadedFile, unzipDir);
        FileUtils.delete(downloadedFile);
        File unzipInnerDir = unzipDir.listFiles()[0];
        FileUtils.delete(pluginJar);
        FileUtils.copy(new File(unzipInnerDir, pluginJar.getName()), pluginJar);
        FileUtils.delete(unzipDir);

        // Reload the plugin
        try {
            Bukkit.getPluginManager().disablePlugin(getUpdatePlugin());
            Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(pluginJar));
        }
        catch (Exception e) {
            ExceptionHandler.exception(new InstallException(getPlugin(), this, e, "Error while reloading new plugin jar"));
        }

        getPlugin().getLogger().info("Successfully updated " + getUpdatePlugin().getName() + "!");
    }

}
