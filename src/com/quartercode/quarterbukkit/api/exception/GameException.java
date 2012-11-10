
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;

/**
 * QuarterBukkitExceptions represents not so bad exceptions, they are primary e.g. for printing information messages.
 * They are exceptions like the {@link NoPermissionException}.
 */
public class GameException extends Exception {

    private static final long serialVersionUID = 7755838223142991751L;

    private final Plugin      plugin;

    /**
     * Creates a new empty QuarterBukkitException with a {@link Plugin}.
     * 
     * @param plugin The causing {@link Plugin}.
     */
    public GameException(final Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Creates a new QuarterBukkitException with a {@link Plugin} and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param message The information as a human-readable message.
     */
    public GameException(final Plugin plugin, final String message) {

        super(message);
        this.plugin = plugin;
    }

    /**
     * Returns the causing {@link Plugin};
     * 
     * @return The causing {@link Plugin};
     */
    public Plugin getPlugin() {

        return plugin;
    }

}
