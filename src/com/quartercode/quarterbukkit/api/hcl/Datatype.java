
package com.quartercode.quarterbukkit.api.hcl;


/**
 * This interface is for representing the (primitive) datatypes. Create a constructor which needs a value. Null is not allowed.
 * 
 * @param <T> The java-type.
 */
public abstract class Datatype<T> extends ConfigEntry implements CustomType {

    protected T value;

    /**
     * Creates a new empty datatype object.
     * 
     * @param config The {@link Config}.
     */
    protected Datatype(final Config config) {

        super(config);
    }

    /**
     * Creates a new datatype object with an initial value.
     * 
     * @param config The {@link Config}.
     * @param value The initial value.
     */
    protected Datatype(final Config config, final T value) {

        super(config);
        set(value);
    }

    /**
     * Returns the stored value.
     * 
     * @return The stored value.
     */
    public T get() {

        return value;
    }

    /**
     * Stores a new value.
     * 
     * @param value The new value.
     */
    public void set(final T value) {

        this.value = value;
    }

    /**
     * Returns if a value is valid for this datatype.
     * 
     * @param value The value to check.
     * @return If the value is valid.
     */
    public abstract boolean isValid(String value);

    /**
     * Returns the stored value as a {@link String}.
     * 
     * @return The value as a {@link String}.
     */
    public abstract String getString();

    /**
     * Sets the stored value out of a {@link String}.
     * 
     * @param value The value as a {@link String}.
     */
    public abstract void setString(String value);

    /**
     * Returns the stored value as a {@link String}.
     * 
     * @return The value as a {@link String}.
     */
    @Override
    public String toString() {

        return getString();
    }

}
