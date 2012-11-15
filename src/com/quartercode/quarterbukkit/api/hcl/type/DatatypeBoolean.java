
package com.quartercode.quarterbukkit.api.hcl.type;

import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.DatatypeEntry;

public class DatatypeBoolean extends DatatypeEntry<Boolean> {

    /**
     * Creates a new empty boolean datatype object.
     * 
     * @param config The {@link Config}.
     * @param name The name of the entry.
     */
    public DatatypeBoolean(final Config config, final String name) {

        super(config, name);
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
