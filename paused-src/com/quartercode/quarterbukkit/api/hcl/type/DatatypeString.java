
package com.quartercode.quarterbukkit.api.hcl.type;

import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.DatatypeEntry;

public class DatatypeString extends DatatypeEntry<String> {

    /**
     * Creates a new empty string datatype object.
     * 
     * @param config The {@link Config}.
     * @param name The name of the entry.
     */
    public DatatypeString(final Config config, final String name) {

        super(config, name);
    }

    /**
     * Stores a new value as a {@link Byte}-array.
     * 
     * @param value The new value as a {@link Byte}-array.
     */
    public void set(final byte[] value) {

        set(new String(value));
    }

    /**
     * Stores a new value as a {@link Character}-array.
     * 
     * @param value The new value as a {@link Character}-array.
     */
    public void set(final char[] value) {

        set(new String(value));
    }

    /**
     * Stores a new value as a {@link StringBuffer}.
     * 
     * @param value The new value as a {@link StringBuffer}.
     */
    public void set(final StringBuffer value) {

        set(new String(value));
    }

    /**
     * Stores a new value as a {@link StringBuilder}.
     * 
     * @param value The new value as a {@link StringBuilder}.
     */
    public void set(final StringBuilder value) {

        set(new String(value));
    }

    /**
     * Returns the stored value as a {@link Byte}-array.
     * 
     * @return The stored value as a {@link Byte}-array.
     */
    public byte[] getBytes() {

        return get().getBytes();
    }

    /**
     * Returns the stored value as a {@link Character}-array.
     * 
     * @return The stored value as a {@link Character}-array.
     */
    public char[] getByte() {

        return get().toCharArray();
    }

    /**
     * Returns the stored value as a {@link StringBuffer}.
     * 
     * @return The stored value as a {@link StringBuffer}.
     */
    public StringBuffer getStringBuffer() {

        return new StringBuffer(get());
    }

    /**
     * Returns the stored value as a {@link StringBuilder}.
     * 
     * @return The stored value as a {@link StringBuilder}.
     */
    public StringBuilder getStringBuilder() {

        return new StringBuilder(get());
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
