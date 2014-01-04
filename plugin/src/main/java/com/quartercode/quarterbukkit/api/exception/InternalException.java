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

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * The InternalException occurres if something goes wrong deep inside a plugin.
 */
public class InternalException extends GameException {

    private final Throwable     cause;
    private final CommandSender causer;

    /**
     * Creates an InternalException filled with the cause as a {@link Throwable} for the error.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     */
    public InternalException(Plugin plugin, Throwable cause) {

        super(plugin);
        this.cause = cause;
        causer = null;
    }

    /**
     * Creates a InternalException filled with the cause as a {@link Throwable} for the error and the {@link CommandSender} who may caused the error.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param causer The {@link CommandSender} who caused the error.
     */
    public InternalException(Plugin plugin, Throwable cause, CommandSender causer) {

        super(plugin);
        this.cause = cause;
        this.causer = causer;
    }

    /**
     * Creates a InternalException filled with the cause as an {@link Throwable} for the error and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param message The informational message.
     */
    public InternalException(Plugin plugin, Throwable cause, String message) {

        super(plugin, message);
        this.cause = cause;
        causer = null;
    }

    /**
     * Creates a InternalException filled with the cause as a {@link Throwable} for the error and the {@link CommandSender} who may caused the error an an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param causer The {@link CommandSender} who caused the error.
     * @param message The informational message.
     */
    public InternalException(Plugin plugin, Throwable cause, CommandSender causer, String message) {

        super(plugin, message);
        this.cause = cause;
        this.causer = causer;
    }

    /**
     * Returns the cause for the error.
     * 
     * @return The cause for the error as an {@link Throwable}.
     */
    public Throwable getCause() {

        return cause;
    }

    /**
     * Returns the {@link CommandSender} causer.
     * 
     * @return The causer who hasn't enough permissions.
     */
    public CommandSender getCauser() {

        return causer;
    }

}
