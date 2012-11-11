
package com.quartercode.quarterbukkit.api.command;

/**
 * Interface for CommandHandlers which executes commands.
 */
public interface CommandHandler {

    /**
     * Returns a {@link CommandInfo}-object which contains some information about the {@link CommandHandler}.
     * 
     * @return The information-object as {@link CommandInfo}.
     */
    public CommandInfo getInfo();

    /**
     * Executes a {@link Command}.
     * 
     * @param command The {@link Command} to execute.
     */
    public void execute(Command command);

}
