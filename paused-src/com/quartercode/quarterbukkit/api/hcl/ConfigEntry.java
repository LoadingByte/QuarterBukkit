
package com.quartercode.quarterbukkit.api.hcl;

/**
 * Class for representing an entry in a HCL config.
 */
public abstract class ConfigEntry {

    protected final Config config;
    protected final String name;

    /**
     * Creates a new ConfigEntry and set the config.
     * 
     * @param config The config.
     * @param name The name of the entry.
     */
    public ConfigEntry(final Config config, final String name) {

        this.config = config;
        this.name = name;
    }

    /**
     * Returns the config.
     * 
     * @return The config.
     */
    public Config getConfig() {

        return config;
    }

    /**
     * Returns the name of the entry.
     * 
     * @return The name of the entry.
     */
    public String getName() {

        return name;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (config == null ? 0 : config.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfigEntry other = (ConfigEntry) obj;
        if (config == null) {
            if (other.config != null) {
                return false;
            }
        } else if (!config.equals(other.config)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [config=" + config + ", name=" + name + "]";
    }

}
