
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.hcl.Config;

/**
 * The ConfigSaveException is thrown if an error occurred while saving a config.
 * This may be an IOException or something like that.
 */
public class ConfigSaveException extends GameException {

    private static final long serialVersionUID = 3431422109096318544L;

    private final Config      config;
    private final Exception   cause;

    /**
     * Creates a new ConfigSaveException filled with the causing config and the causing exception.
     * 
     * @param plugin The causing plugin.
     * @param config The causing config.
     * @param cause The causing exception.
     */
    public ConfigSaveException(final Plugin plugin, final Config config, final Exception cause) {

        super(plugin);
        this.config = config;
        this.cause = cause;
    }

    /**
     * Creates a new ConfigSaveException filled with the causing config, the causing exception and an informational message.
     * 
     * @param plugin The causing plugin.
     * @param config The causing config.
     * @param cause The causing exception.
     * @param message The informational message.
     */
    public ConfigSaveException(final Plugin plugin, final Config config, final Exception cause, final String message) {

        super(plugin, message);
        this.config = config;
        this.cause = cause;
    }

    /**
     * Returns the causing config.
     * 
     * @return The causing config.
     */
    public Config getConfig() {

        return config;
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
