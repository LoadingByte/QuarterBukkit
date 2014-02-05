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
 * The InstallException occurres if something goes wrong while installing a plugin, e.g. over an updater.
 */
public class InstallException extends GameException {

    private final Throwable     cause;
    private final CommandSender causer;

    /**
     * Creates an InstallException filled with the cause for the error that occurred.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     */
    public InstallException(Plugin plugin, Throwable cause) {

        super(plugin);

        this.cause = cause;
        causer = null;
    }

    /**
     * Creates an InstallException filled with the cause for the error that occurred and the {@link CommandSender} which invoked the update.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param causer The {@link CommandSender} which invoked the update.
     */
    public InstallException(Plugin plugin, Throwable cause, CommandSender causer) {

        super(plugin);

        this.cause = cause;
        this.causer = causer;
    }

    /**
     * Creates an InstallException filled with the cause for the error that occurred and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param message The informational message.
     */
    public InstallException(Plugin plugin, Throwable cause, String message) {

        super(plugin, message);
        this.cause = cause;
        causer = null;
    }

    /**
     * Creates an InstallException filled with the cause for the error that occurred, the {@link CommandSender} which invoked the update an an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param causer The {@link CommandSender} which invoked the update.
     * @param message The informational message.
     */
    public InstallException(Plugin plugin, Throwable cause, CommandSender causer, String message) {

        super(plugin, message);
        this.cause = cause;
        this.causer = causer;
    }

    /**
     * Returns the cause for the error that occurred.
     * 
     * @return The cause for the error.
     */
    public Throwable getCause() {

        return cause;
    }

    /**
     * Returns the {@link CommandSender} which invoked the updater and indirectly caused the exception.
     * 
     * @return The causer which invoked the updater.
     */
    public CommandSender getCauser() {

        return causer;
    }

}
