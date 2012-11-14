
package com.quartercode.quarterbukkit.api.hcl.type;

import java.math.BigDecimal;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.Datatype;

public class DatatypeNumber extends Datatype<BigDecimal> {

    private static final String[] formatStrings  = { " ", "," };
    private static final String[] allowedStrings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "+", "-", "*", "/", "%", "." };

    /**
     * Creates a new empty number datatype object.
     * 
     * @param config The {@link Config}.
     */
    public DatatypeNumber(final Config config) {

        super(config);
    }

    /**
     * Creates a new number datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link Float}.
     */
    public DatatypeNumber(final Config config, final float value) {

        super(config, new BigDecimal(String.valueOf(value)));
    }

    /**
     * Creates a new number datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link Double}.
     */
    public DatatypeNumber(final Config config, final double value) {

        super(config, new BigDecimal(String.valueOf(value)));
    }

    /**
     * Creates a new number datatype object and fills it with a value.
     * 
     * @param config The {@link Config}.
     * @param value The value as a {@link BigDecimal}.
     */
    public DatatypeNumber(final Config config, final BigDecimal value) {

        super(config, value);
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
