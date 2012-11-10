
package com.quartercode.quarterbukkit.api.command;

import org.bukkit.command.CommandSender;

/**
 * This class represents a typed command with different components.
 */
public class Command {

    private final CommandSender sender;
    private final String        globalLabel;
    private final String        label;
    private final String[]      arguments;

    /**
     * Creates a new command representing object.
     * 
     * @param sender The {@link CommandSender} of the command.
     * @param globalLabel The global label of the command.
     * @param label The secondary label of the command.
     * @param arguments The parsed arguments of the command as a {@link String}-array.
     */
    public Command(final CommandSender sender, final String globalLabel, final String label, final String... arguments) {

        this.sender = sender;
        this.globalLabel = globalLabel;
        this.label = label;
        this.arguments = arguments;
    }

    /**
     * Returns the {@link CommandSender} of the command.
     * 
     * @return The {@link CommandSender}.
     */
    public CommandSender getSender() {

        return sender;
    }

    /**
     * Returns the global label of the command.
     * If you type in /command help, command will be the global label.
     * 
     * @return The global label of the command.
     */
    public String getGlobalLabel() {

        return globalLabel;
    }

    /**
     * Returns the secondary label of the command.
     * If you type in /command help, help will be the secondary label.
     * 
     * @return The secondary label of the command.
     */
    public String getLabel() {

        return label;
    }

    /**
     * Returns the parsed arguments of the command as a {@link String}-array.
     * If you type in /command help 2, the array will only contains [2].
     * 
     * @return The parsed arguments of the command.
     */
    public String[] getArguments() {

        return arguments;
    }

}
