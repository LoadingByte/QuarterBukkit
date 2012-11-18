
package com.quartercode.quarterbukkit.api.hcl.type;

import java.math.BigDecimal;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.DatatypeEntry;

public class DatatypeNumber extends DatatypeEntry<BigDecimal> {

    private static final String[] formatStrings  = { " ", "," };
    private static final String[] allowedStrings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "%", "." };

    /**
     * Creates a new empty number datatype object.
     * 
     * @param config The {@link Config}.
     * @param name The name of the entry.
     */
    public DatatypeNumber(final Config config, final String name) {

        super(config, name);
    }

    /**
     * Stores a new value as a {@link Float}.
     * 
     * @param value The new value as a {@link Float}.
     */
    public void set(final float value) {

        set(new BigDecimal(value));
    }

    /**
     * Stores a new value as a {@link Double}.
     * 
     * @param value The new value as a {@link Double}.
     */
    public void set(final double value) {

        set(new BigDecimal(value));
    }

    /**
     * Returns the stored value as a {@link Float}.
     * 
     * @return The stored value as a {@link Float}.
     */
    public float getFloat() {

        return get().floatValue();
    }

    /**
     * Returns the stored value as a {@link Double}.
     * 
     * @return The stored value as a {@link Double}.
     */
    public double getDouble() {

        return get().doubleValue();
    }

    @Override
    public boolean isValid(String value) {

        for (final String formatString : DatatypeNumber.formatStrings) {
            value = value.replaceAll(formatString, "");
        }

        for (final String allowedString : DatatypeNumber.allowedStrings) {
            value = value.replaceAll(allowedString, "");
        }

        return value.isEmpty();
    }

    @Override
    public String getString() {

        return String.valueOf(value);
    }

    @Override
    public void setString(String value) {

        if (value == null) {
            throw new IllegalArgumentException("Null is not allowed as value");
        }

        for (final String formatString : DatatypeNumber.formatStrings) {
            value = value.replaceAll(formatString, "");
        }

        try {
            final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
            scriptEngine.eval("var result = " + value);
            set(new BigDecimal(String.valueOf(scriptEngine.get("result"))));
        }
        catch (final ScriptException e) {
            throw new IllegalArgumentException("The math string " + value + " is not parsable. Please inform the plugin author " + QuarterBukkit.getPlugin().getDescription().getAuthors().get(0));
        }
    }

}
