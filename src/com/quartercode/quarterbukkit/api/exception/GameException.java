
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;

/**
 * This is the bukkit base class for the exception-system.
 */
public class GameException {

    private final Plugin plugin;
    private String       message;

    /**
     * Creates a new empty {@link GameException} with a {@link Plugin}.
     * 
     * @param plugin The causing {@link Plugin}.
     */
    public GameException(final Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Creates a new {@link GameException} with a {@link Plugin} and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param message The information as a human-readable message.
     */
    public GameException(final Plugin plugin, final String message) {

        this.plugin = plugin;
        this.message = message;
    }

    /**
     * Returns the causing {@link Plugin};
     * 
     * @return The causing {@link Plugin};
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * Returns the informational message.
     * 
     * @return The informational message.
     */
    public String getMessage() {

        return message;
    }

}
