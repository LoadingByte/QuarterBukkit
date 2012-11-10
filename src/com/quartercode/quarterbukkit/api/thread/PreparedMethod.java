
package com.quartercode.quarterbukkit.api.thread;

import java.lang.reflect.Method;

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

}
