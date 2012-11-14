
package com.quartercode.quarterbukkit.api.hcl.type;

import java.math.BigInteger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.Datatype;

public class DatatypeInteger extends Datatype<BigInteger> {

    private static final String[] formatStrings  = { " ", "," };
    private static final String[] allowedStrings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "%" };

    /**
     * Creates a new empty integer datatype object.
     * 
     * @param config The {@link Config}.
     */
    public DatatypeInteger(final Config config) {

        super(config);
    }

    /**
     * Creates a new integer datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link Byte}.
     */
    public DatatypeInteger(final Config config, final byte value) {

        super(config, new BigInteger(String.valueOf(value)));
    }

    /**
     * Creates a new integer datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link Short}.
     */
    public DatatypeInteger(final Config config, final short value) {

        super(config, new BigInteger(String.valueOf(value)));
    }

    /**
     * Creates a new integer datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as an {@link Integer}.
     */
    public DatatypeInteger(final Config config, final int value) {

        super(config, new BigInteger(String.valueOf(value)));
    }

    /**
     * Creates a new integer datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link Long}.
     */
    public DatatypeInteger(final Config config, final long value) {

        super(config, new BigInteger(String.valueOf(value)));
    }

    /**
     * Creates a new integer datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link BigInteger}.
     */
    public DatatypeInteger(final Config config, final BigInteger value) {

        super(config, value);
    }

    @Override
    public boolean isValid(String value) {

        for (final String formatString : DatatypeInteger.formatStrings) {
            value = value.replaceAll(formatString, "");
        }

        for (final String allowedString : DatatypeInteger.allowedStrings) {
            value = value.replaceAll(allowedString, "");
        }

        return value.isEmpty();
    }

    @Override
    public String getString() {

        return String.valueOf(get());
    }

    @Override
    public void setString(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Null is not allowed as value");
        }

        for (final String formatString : DatatypeInteger.formatStrings) {
            value = value.replaceAll(formatString, "");
        }

        try {
            final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
            scriptEngine.eval("var result = " + value);
            set(new BigInteger(String.valueOf(scriptEngine.get("result"))));
        }
        catch (final ScriptException e) {
            throw new IllegalArgumentException("The math string " + value + " is not parsable. Please inform the plugin author " + QuarterBukkit.getPlugin().getDescription().getAuthors().get(0));
        }
    }

}
