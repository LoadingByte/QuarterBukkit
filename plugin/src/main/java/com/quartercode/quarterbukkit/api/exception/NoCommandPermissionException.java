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
import com.quartercode.quarterbukkit.api.command.Command;

/**
 * The NoPermissionException represents an exception caused by not enough permissions.
 */
public class NoCommandPermissionException extends NoPermissionException {

    private final Command command;

    /**
     * Creates an NoCommandPermissionException filled with a {@link CommandSender} causer, the permission and the tried command.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     * @param command The {@link Command} which the {@link CommandSender} tried to execute.
     */
    public NoCommandPermissionException(Plugin plugin, String permission, CommandSender causer, Command command) {

        super(plugin, permission, causer);
        this.command = command;
    }

    /**
     * Creates an NoCommandPermissionException filled with a {@link CommandSender} causer, the permission, the tried command and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     * @param command The {@link Command} which the {@link CommandSender} tried to execute.
     * @param message The information message.
     */
    public NoCommandPermissionException(Plugin plugin, String permission, CommandSender causer, Command command, String message) {

        super(plugin, permission, causer, message);
        this.command = command;
    }

    /**
     * Returns the tried {@link Command}.
     * 
     * @return The {@link Command} which the {@link CommandSender} tried to execute.
     */
    public Command getCommand() {

        return command;
    }

}
