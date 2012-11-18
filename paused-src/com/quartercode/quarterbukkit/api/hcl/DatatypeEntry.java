
package com.quartercode.quarterbukkit.api.hcl;

/**
 * This interface is for representing the (primitive) datatypes. Create a constructor which needs a value. Null is not allowed.
 * 
 * @param <T> The java-type.
 */
public abstract class DatatypeEntry<T> extends ConfigEntry implements CustomType {

    protected T value;

    /**
     * Creates a new empty datatype object.
     * 
     * @param config The {@link Config}.
     * @param name The name of the entry.
     */
    protected DatatypeEntry(final Config config, final String name) {

        super(config, name);
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

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatatypeEntry<?> other = (DatatypeEntry<?>) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [value=" + value + "]";
    }

}
