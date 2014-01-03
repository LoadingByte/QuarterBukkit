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

package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

/**
 * This is the base class for the exception-event-system.
 */
public class GameException extends Event {

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {

        return handlers;
    }

    private final Plugin plugin;
    private String       message;

    /**
     * Creates a new empty {@link GameException} with a {@link Plugin}.
     * 
     * @param plugin The causing {@link Plugin}.
     */
    public GameException(final Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Creates a new {@link GameException} with a {@link Plugin} and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param message The information as a human-readable message.
     */
    public GameException(final Plugin plugin, final String message) {

        this.plugin = plugin;
        this.message = message;
    }

    /**
     * Returns the causing {@link Plugin};
     * 
     * @return The causing {@link Plugin};
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * Returns the informational message.
     * 
     * @return The informational message.
     */
    public String getMessage() {

        return message;
    }

    @Override
    public HandlerList getHandlers() {

        return handlers;
    }

}
