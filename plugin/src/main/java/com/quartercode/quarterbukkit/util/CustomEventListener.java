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
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.event.RedstoneToggleEvent;

public class CustomEventListener implements Listener {

    public CustomEventListener(Plugin plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRedstoneBlockChange(BlockRedstoneEvent event) {

        boolean powered = event.getNewCurrent() > 0 && event.getOldCurrent() < 1;

        for (BlockFace face : new BlockFace[] { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN }) {
            if (event.getBlock().getRelative(face).getType() != Material.AIR) {
                Bukkit.getPluginManager().callEvent(new RedstoneToggleEvent(event.getBlock().getRelative(face), powered));
            }
        }
    }

}
