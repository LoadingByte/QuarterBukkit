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

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandExecutor;

/**
 * The NoCommandFoundException represents an exception caused by a not registered default command.
 */
public class NoDefaultCommandFoundException extends NoCommandFoundException {

    /**
     * Creates a NoDefaultCommandFoundException filled with a {@link Command} and a {@link CommandExecutor}.
     *
     * @param plugin The causing {@link Plugin}.
     * @param command The not registered {@link Command}.
     * @param commandExecutor The {@link CommandExecutor} which should execute the {@link Command}.
     */
    public NoDefaultCommandFoundException(Plugin plugin, Command command, CommandExecutor commandExecutor) {

        super(plugin, command, commandExecutor);
    }

    /**
     * Creates a NoDefaultCommandFoundException filled with a {@link Command}, a {@link CommandExecutor} and an informational message.
     *
     * @param plugin The causing {@link Plugin}.
     * @param command The not registered {@link Command}.
     * @param commandExecutor The {@link CommandExecutor} which should execute the {@link Command}.
     * @param message The information message.
     */
    public NoDefaultCommandFoundException(Plugin plugin, Command command, CommandExecutor commandExecutor, String message) {

        super(plugin, command, commandExecutor, message);
    }

}
