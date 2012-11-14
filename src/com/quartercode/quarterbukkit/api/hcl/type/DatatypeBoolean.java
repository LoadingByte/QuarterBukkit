
package com.quartercode.quarterbukkit.api.hcl.type;

import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.Datatype;

public class DatatypeBoolean extends Datatype<Boolean> {

    /**
     * Creates a new empty boolean datatype object.
     * 
     * @param config The {@link Config}.
     */
    public DatatypeBoolean(final Config config) {

        super(config);
    }

    /**
     * Creates a new boolean datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link Boolean}.
     */
    public DatatypeBoolean(final Config config, final boolean value) {

        super(config, value);
    }

    @Override
    public boolean isValid(final String value) {

        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    @Override
    public String getString() {

        return String.valueOf(get());
    }

    @Override
    public void setString(final String value) {

        set(Boolean.parseBoolean(value));
    }

}
