
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.hcl.Config;

/**
 * The NoConfigDatatypeFoundException occurres if a datatype which is used in an HDL-decument wasn't found.
 */
public class NoConfigDatatypeFoundException extends GameException {

    private static final long serialVersionUID = -5612668120947291786L;

    private final Config      config;
    private final String      datatype;

    /**
     * Creates a new NoConfigDatatypeFoundException filled with the causing config and the datatype which wasn't found.
     * 
     * @param plugin The causing plugin.
     * @param config The causing config.
     * @param datatype The datatype which wasn't found.
     */
    public NoConfigDatatypeFoundException(final Plugin plugin, final Config config, final String datatype) {

        super(plugin);
        this.config = config;
        this.datatype = datatype;
    }

    /**
     * Creates a new NoConfigDatatypeFoundException filled with the causing config, the datatype which wasn't found and an informational message.
     * 
     * @param plugin The causing plugin.
     * @param config The causing config.
     * @param datatype The datatype which wasn't found.
     * @param message The informational message.
     */
    public NoConfigDatatypeFoundException(final Plugin plugin, final Config config, final String datatype, final String message) {

        super(plugin, message);
        this.config = config;
        this.datatype = datatype;
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
     * Returns the datatype which wasn't found.
     * 
     * @return The datatype which wasn't found.
     */
    public String getDatatype() {

        return datatype;
    }

}
