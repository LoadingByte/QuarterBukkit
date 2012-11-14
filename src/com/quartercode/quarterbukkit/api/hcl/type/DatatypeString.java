
package com.quartercode.quarterbukkit.api.hcl.type;

import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.Datatype;

public class DatatypeString extends Datatype<String> {

    /**
     * Creates a new empty string datatype object.
     * 
     * @param config The {@link Config}.
     */
    public DatatypeString(final Config config) {

        super(config);
    }

    /**
     * Creates a new string datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as {@link String}.
     */
    public DatatypeString(final Config config, final String value) {

        super(config, value);
    }

    @Override
    public boolean isValid(final String value) {

        return true;
    }

    @Override
    public String getString() {

        return get();
    }

    @Override
    public void setString(final String string) {

        set(string);
    }

}
