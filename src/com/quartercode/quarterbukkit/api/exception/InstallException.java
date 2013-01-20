
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.Updater;

/**
 * The InstallException occurres if something goes wrong while installing a plugin via {@link Updater}.
 */
public class InstallException extends GameException {

    private static final long   serialVersionUID = 3944156504914815607L;

    private final Exception     cause;
    private final CommandSender causer;

    /**
     * Creates a InstallException filled with a cause as an {@link Exception} for the error.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     */
    public InstallException(final Plugin plugin, final Exception cause) {

        super(plugin);
        this.cause = cause;
        causer = null;
    }

    /**
     * Creates a InstallException filled with a cause as an {@link Exception} for the error.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param causer The {@link CommandSender} who caused the exception.
     */
    public InstallException(final Plugin plugin, final Exception cause, final CommandSender causer) {

        super(plugin);
        this.cause = cause;
        this.causer = causer;
    }

    /**
     * Creates a InstallException filled with a cause as an {@link Exception} for the error and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param message The informational message.
     */
    public InstallException(final Plugin plugin, final Exception cause, final String message) {

        super(plugin, message);
        this.cause = cause;
        causer = null;
    }

    /**
     * Creates a InstallException filled with a cause as an {@link Exception} for the error and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param causer The {@link CommandSender} who caused the exception.
     * @param message The informational message.
     */
    public InstallException(final Plugin plugin, final Exception cause, final CommandSender causer, final String message) {

        super(plugin, message);
        this.cause = cause;
        this.causer = causer;
    }

    /**
     * Returns the cause for the error.
     * 
     * @return The cause for the error as an {@link Exception}.
     */
    @Override
    public Exception getCause() {

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
