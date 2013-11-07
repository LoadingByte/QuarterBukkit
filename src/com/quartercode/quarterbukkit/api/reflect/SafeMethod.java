
package com.quartercode.quarterbukkit.api.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SafeMethod<T> implements MethodAccessor<T> {

    private Method     method;
    @SuppressWarnings ("unused")
    private Class<?>[] params;

    public SafeMethod(Method method) {

        if (method == null) {
            throw new UnsupportedOperationException("Cannot create a new safe-method with a null-constructor!");
        }
        this.method = method;
        this.params = method.getParameterTypes();

    }

    public SafeMethod(Class<?> instance, String methodName, Class<?>[] params) {

        init(instance, methodName, params);
    }

    public SafeMethod(Object instance, String methodName, Class<?>[] params) {

        init(instance.getClass(), methodName, params);
    }

    protected void init(Class<?> instance, String methodName, Class<?>[] params) {

        try {
            method = instance.getDeclaredMethod(methodName, params);
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public String getName() {

        return this.method != null ? this.method.getName() : null;
    }

    @SuppressWarnings ("unchecked")
    @Override
    public T invoke(Object instance, Object... args) {

        try {
            if (this.method != null) {
                return (T) this.method.invoke(instance, args);
            }
            return null;
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
