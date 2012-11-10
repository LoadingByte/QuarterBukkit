
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.command.Command;

/**
 * The NoPermissionException represents an exception caused by not enough permissions.
 */
public class NoCommandPermissionException extends NoPermissionException {

    private static final long serialVersionUID = 357704312885525729L;

    private final Command     command;

    /**
     * Creates an NoPermissionException filled with a {@link CommandSender} causer, the permission and the tried command.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     * @param command The {@link Command} which the {@link CommandSender} tried to execute.
     */
    public NoCommandPermissionException(final Plugin plugin, final String permission, final CommandSender causer, final Command command) {

        super(plugin, permission, causer);
        this.command = command;
    }

    /**
     * Creates an NoPermissionException filled with a {@link CommandSender} causer, the permission, the tried command and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     * @param command The {@link Command} which the {@link CommandSender} tried to execute.
     * @param message The information message.
     */
    public NoCommandPermissionException(final Plugin plugin, final String permission, final CommandSender causer, final Command command, final String message) {

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
