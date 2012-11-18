
package com.quartercode.quarterbukkit.api.hcl.type;

import java.math.BigInteger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.DatatypeEntry;

public class DatatypeInteger extends DatatypeEntry<BigInteger> {

    private static final String[] formatStrings  = { " ", "," };
    private static final String[] allowedStrings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "%" };

    /**
     * Creates a new empty integer datatype object.
     * 
     * @param config The {@link Config}.
     * @param name The name of the entry.
     */
    public DatatypeInteger(final Config config, final String name) {

        super(config, name);
    }

    /**
     * Stores a new value as a {@link Byte}.
     * 
     * @param value The new value as a {@link Byte}.
     */
    public void set(final byte value) {

        set(new BigInteger(String.valueOf(value)));
    }

    /**
     * Stores a new value as a {@link Short}.
     * 
     * @param value The new value as a {@link Short}.
     */
    public void set(final short value) {

        set(new BigInteger(String.valueOf(value)));
    }

    /**
     * Stores a new value as an {@link Integer}.
     * 
     * @param value The new value as an {@link Integer}.
     */
    public void set(final int value) {

        set(new BigInteger(String.valueOf(value)));
    }

    /**
     * Stores a new value as a {@link Long}.
     * 
     * @param value The new value as a {@link Long}.
     */
    public void set(final long value) {

        set(new BigInteger(String.valueOf(value)));
    }

    /**
     * Returns the stored value as a {@link Byte}.
     * 
     * @return The stored value as a {@link Byte}.
     */
    public byte getByte() {

        return get().byteValue();
    }

    /**
     * Returns the stored value as a {@link Short}.
     * 
     * @return The stored value as a {@link Short}.
     */
    public short getShort() {

        return get().shortValue();
    }

    /**
     * Returns the stored value as a {@link Integer}.
     * 
     * @return The stored value as a {@link Integer}.
     */
    public int getInteger() {

        return get().intValue();
    }

    /**
     * Returns the stored value as a {@link Long}.
     * 
     * @return The stored value as a {@link Long}.
     */
    public long getLong() {

        return get().longValue();
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
