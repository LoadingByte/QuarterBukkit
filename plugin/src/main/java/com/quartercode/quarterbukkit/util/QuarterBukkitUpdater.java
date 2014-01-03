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
    public QuarterBukkitUpdater(final Plugin plugin) {

        super(plugin, plugin, "quarterbukkit");
    }

    @Override
    protected String parseVersion(final String title) {

        return title.replaceAll("QuarterBukkit ", "");
    }

    @Override
    protected boolean doInstall(final File downloadedFile, final CommandSender causer) throws IOException {

        extract(downloadedFile, "QuarterBukkit.jar", new File("plugins", "QuarterBukkit.jar"));
        downloadedFile.delete();

        try {
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File("plugins", "QuarterBukkit.jar")));
            return true;
        }
        catch (final Exception e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, "Error while reloading"));
        }

        plugin.getLogger().info("Successfull updated QuarterBukkit!");

        return false;
    }

}
