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

package com.quartercode.quarterbukkit.api.event;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * The redstone toggle event is fired when a {@link Block} gets powered or unpowered.
 */
public class RedstoneToggleEvent extends BlockEvent {

    private static HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {

        return handlers;
    }

    private final boolean powered;

    /**
     * Creates a new redstone toggle event which describes that the power on the given {@link Block} has been toggled on or off.
     * 
     * @param block The {@link Block} which got powered or unpowered.
     * @param powered True if the given {@link Block} got powered, false if it got unpowered.
     */
    public RedstoneToggleEvent(Block block, boolean powered) {

        super(block);

        this.powered = powered;
    }

    /**
     * Returns true if the set {@link Block} got powered or false if it got unpowered.
     * 
     * @return If the set {@link Block} you can retreive with {@link #getBlock()} is powered.
     */
    public boolean isPowered() {

        return powered;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;
    }

}
