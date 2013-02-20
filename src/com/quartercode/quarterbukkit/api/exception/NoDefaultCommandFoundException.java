
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
    public NoDefaultCommandFoundException(final Plugin plugin, final Command command, final CommandExecutor commandExecutor) {

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
    public NoDefaultCommandFoundException(final Plugin plugin, final Command command, final CommandExecutor commandExecutor, final String message) {

        super(plugin, command, commandExecutor, message);
    }

}
