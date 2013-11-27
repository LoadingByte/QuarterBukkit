
package com.quartercode.quarterbukkit.api.reflect;

/**
 * Defines the methods to access a certain method
 */

public interface MethodAccessor<T> {

    /**
     * Executes the method
     * 
     * @param instance of the class the method is in, use null if it is a static method
     * @param args to use for the method
     * @return A possible returned value from the method, is always null if the method is a void
     */
    T invoke(Object instance, Object... args);

}
