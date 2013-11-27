
package com.quartercode.quarterbukkit.api.reflect;

/**
 * Defines the methods to access a certain field
 */

public interface FieldAccessor<T> {

    /**
     * Sets the value of a field of an instance
     * 
     * @param instance to set the field in
     * @param value to set to
     * @return True if setting was successful, False if not
     */
    boolean set(Object instance, T value);

    /**
     * Gets the value of a field from an instance
     * 
     * @param instance to get from
     * @return value of the field in the instance
     */
    T get(Object instance);

}
