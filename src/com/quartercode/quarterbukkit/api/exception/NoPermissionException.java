
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * The NoPermissionException represents an exception caused by not enough permissions.
 */
public class NoPermissionException extends GameException {

    private static final long   serialVersionUID = 3304922513580160938L;

    private final String        permission;
    private final CommandSender causer;

    /**
     * Creates an NoPermissionException filled with a {@link CommandSender} causer and the permission.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     */
    public NoPermissionException(final Plugin plugin, final String permission, final CommandSender causer) {

        super(plugin);
        this.permission = permission;
        this.causer = causer;
    }

    /**
     * Creates an NoPermissionException filled with a {@link CommandSender} causer, the permission and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     * @param message The information message.
     */
    public NoPermissionException(final Plugin plugin, final String permission, final CommandSender causer, final String message) {

        super(plugin, message);
        this.permission = permission;
        this.causer = causer;
    }

    /**
     * Returns the permission which the causer doesn't have.
     * 
     * @return The permission.
     */
    public String getPermission() {

        return permission;
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
