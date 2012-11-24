
package com.quartercode.quarterbukkit.api.thread;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Class for representing an invoke-ready method.
 */
public class PreparedMethod {

    private final Method   method;
    private final Object   object;
    private final Object[] parameters;

    /**
     * Creates an invoke-ready method.
     * 
     * @param method The reflection-{@link Method}.
     * @param object The call {@link Object} (null for static access).
     * @param parameters The call parameters as an {@link Object}-array.
     */
    public PreparedMethod(final Method method, final Object object, final Object... parameters) {

        this.method = method;
        this.object = object;
        this.parameters = parameters;
    }

    /**
     * Returns the reflection-{@link Method}.
     * 
     * @return The reflection-{@link Method}.
     */
    public Method getMethod() {

        return method;
    }

    /**
     * Returns the call {@link Object}.
     * 
     * @return The call {@link Object}.
     */
    public Object getObject() {

        return object;
    }

    /**
     * Returns the call parameters as an {@link Object}-array.
     * 
     * @return The call parameters as an {@link Object}-array.
     */
    public Object[] getParameters() {

        return parameters;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (method == null ? 0 : method.hashCode());
        result = prime * result + (object == null ? 0 : object.hashCode());
        result = prime * result + Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PreparedMethod other = (PreparedMethod) obj;
        if (method == null) {
            if (other.method != null) {
                return false;
            }
        } else if (!method.equals(other.method)) {
            return false;
        }
        if (object == null) {
            if (other.object != null) {
                return false;
            }
        } else if (!object.equals(other.object)) {
            return false;
        }
        if (!Arrays.equals(parameters, other.parameters)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [method=" + method + ", object=" + object + ", parameters=" + Arrays.toString(parameters) + "]";
    }

}
