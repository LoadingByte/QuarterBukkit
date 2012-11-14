
package com.quartercode.quarterbukkit.api.hcl;


/**
 * Class for representing an entry in a HCL config.
 */
public abstract class ConfigEntry {

    private final Config config;

    /**
     * Creates a new ConfigEntry and set the config.
     * 
     * @param config The config.
     */
    public ConfigEntry(final Config config) {

        this.config = config;
    }

    /**
     * Returns the config.
     * 
     * @return The config.
     */
    public Config getConfig() {

        return config;
    }

}
