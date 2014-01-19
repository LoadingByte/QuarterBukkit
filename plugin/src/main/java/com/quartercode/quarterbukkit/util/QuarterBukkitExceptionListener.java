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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.InstallException;
import com.quartercode.quarterbukkit.api.exception.InternalException;

public class QuarterBukkitExceptionListener implements Listener {

    private final Plugin plugin;

    public QuarterBukkitExceptionListener(Plugin plugin) {

        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void installException(InstallException exception) {

        if (exception.getCauser() != null) {
            exception.getCauser().sendMessage(ChatColor.RED + "Can't update " + plugin.getName() + ": " + exception.getMessage());
            if (exception.getCause() != null) {
                exception.getCauser().sendMessage(ChatColor.RED + "Reason: " + exception.getCause().toString());
            }
        } else {
            plugin.getLogger().warning("Can't update " + plugin.getName() + ": " + exception.getMessage());
            if (exception.getCause() != null) {
                plugin.getLogger().warning("Reason: " + exception.getCause().toString());
            }
        }
    }

    @EventHandler
    public void internalException(InternalException exception) {

        exception.getCause().printStackTrace();
    }

}
