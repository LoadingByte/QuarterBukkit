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

import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.exception.InstallException;
import com.quartercode.quarterbukkit.api.exception.InternalException;

public class QuarterBukkitExceptionListener implements Listener {

    @EventHandler
    public void installException(InstallException exception) {

        if (exception.getPlugin().equals(QuarterBukkit.getPlugin())) {
            Plugin plugin = exception.getPlugin();

            if (exception.getCauser() != null) {
                exception.getCauser().sendMessage(ChatColor.RED + "Can't update " + plugin.getName() + ": " + exception.getMessage());
                if (exception.getCause() != null) {
                    exception.getCauser().sendMessage(ChatColor.RED + "Reason: " + exception.getCause().toString());
                }
            } else {
                plugin.getLogger().warning("Can't update " + plugin.getName() + ": " + exception.getMessage());
                if (exception.getCause() != null) {
                    plugin.getLogger().log(Level.WARNING, "Reason:", exception.getCause());
                }
            }
        }
    }

    @EventHandler
    public void internalException(InternalException exception) {

        QuarterBukkit.getLog().log(Level.SEVERE, "The plugin " + exception.getPlugin().getName() + " caused an internal exception", exception.getCause());
    }

}
