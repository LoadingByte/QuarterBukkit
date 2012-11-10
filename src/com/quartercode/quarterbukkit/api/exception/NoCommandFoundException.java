
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.command.Command;
import com.quartercode.quarterbukkit.api.command.CommandExecutor;

/**
 * The NoCommandFoundException represents an exception caused by a not registered command.
 */
public class NoCommandFoundException extends QuarterBukkitException {

    private static final long     serialVersionUID = 3629005568232667285L;

    private final Command         command;
    private final CommandExecutor commandExecutor;

    /**
     * Creates a NoCommandFoundException filled with a {@link Command} and a {@link CommandExecutor}.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param command The not registered {@link Command}.
     * @param commandExecutor The {@link CommandExecutor} which should execute the {@link Command}.
     */
    public NoCommandFoundException(final Plugin plugin, final Command command, final CommandExecutor commandExecutor) {

        super(plugin);
        this.command = command;
        this.commandExecutor = commandExecutor;
    }

    /**
     * Creates a NoCommandFoundException filled with a {@link Command}, a {@link CommandExecutor} and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param command The not registered {@link Command}.
     * @param commandExecutor The {@link CommandExecutor} which should execute the {@link Command}.
     * @param message The information message.
     */
    public NoCommandFoundException(final Plugin plugin, final Command command, final CommandExecutor commandExecutor, final String message) {

        super(plugin, message);
        this.command = command;
        this.commandExecutor = commandExecutor;
    }

    /**
     * Returns the not registered {@link Command}.
     * 
     * @return The not existing {@link Command}.
     */
    public Command getCommand() {

        return command;
    }

    /**
     * Returns the {@link CommandExecutor} which should execute the {@link Command}.
     * 
     * @return The {@link CommandExecutor}.
     */
    public CommandExecutor getCommandExecutor() {

        return commandExecutor;
    }

}
