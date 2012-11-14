
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.Datatype;

/**
 * The ConfigParseException occurres if a value from the config which should be parsed isn't valid.
 */
public class ConfigParseException extends GameException {

    private static final long serialVersionUID = 1027631888510952838L;

    private final Config      config;
    private final String      value;
    private final Datatype<?> datatype;

    /**
     * Creates a new ConfigParseException filled with the causing config, the unparsable value and the datatype.
     * 
     * @param plugin The causing plugin.
     * @param config The causing config.
     * @param value The unparsable value.
     * @param datatype The datatype.
     */
    public ConfigParseException(final Plugin plugin, final Config config, final String value, final Datatype<?> datatype) {

        super(plugin);
        this.config = config;
        this.value = value;
        this.datatype = datatype;
    }

    /**
     * Creates a new ConfigParseException filled with the causing config, the unparsable value, the datatype and an informational message.
     * 
     * @param plugin The causing plugin.
     * @param config The causing config.
     * @param value The unparsable value.
     * @param datatype The datatype.
     * @param message The informational message.
     */
    public ConfigParseException(final Plugin plugin, final Config config, final String value, final Datatype<?> datatype, final String message) {

        super(plugin, message);
        this.config = config;
        this.value = value;
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
     * Returns the unparsable value.
     * 
     * @return The unparsable value.
     */
    public String getValue() {

        return value;
    }

    /**
     * Returns the datatype.
     * 
     * @return The datatype.
     */
    public Datatype<?> getDatatype() {

        return datatype;
    }

}
