
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.Updater;

/**
 * The InstallException occurres if something goes wrong while installing a plugin via {@link Updater}.
 */
public class InstallException extends GameException {

    private static final long serialVersionUID = 3944156504914815607L;

    private final Exception   cause;

    /**
     * Creates a InstallException filled with a cause as an {@link Exception} for the error.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     */
    public InstallException(final Plugin plugin, final Exception cause) {

        super(plugin);
        this.cause = cause;
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
    }

    /**
     * Returns the cause for the error.
     * 
     * @return The cause for the error as {@link Exception}.
     */
    @Override
    public Exception getCause() {

        return cause;
    }

}
