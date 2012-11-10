
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;

/**
 * Class for handling {@link GameException} caused by a {@link Plugin}.
 * Those represents not so bad exceptions, they are primary e.g. for printing information messages.
 */
public abstract class ExceptionHandler {

    private final Plugin plugin;

    /**
     * Creates a new ExceptionHandler binded to a {@link Plugin}.
     * 
     * @param plugin The binding plugin.
     */
    public ExceptionHandler(final Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Returns the {@link Plugin} the ExceptionHandler is binded to.
     * 
     * @return The binding plugin.
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * Handle an {@link GameException} caused by a {@link Plugin}.
     * 
     * @param exception The {@link GameException} to handle.
     */
    public abstract void handle(GameException exception);

}
