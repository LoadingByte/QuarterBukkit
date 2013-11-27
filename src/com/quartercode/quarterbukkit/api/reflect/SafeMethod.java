
package com.quartercode.quarterbukkit.api.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import com.quartercode.quarterbukkit.QuarterBukkit;

/**
 * 
 * i will add the JavaDoc later!
 * 
 */

public class SafeMethod<T> implements MethodAccessor<T> {

    private Method     method;
    private Class<?>[] params;
    private boolean    isStatic;

    public SafeMethod() {

    }

    public SafeMethod(Method method) {

        setMethod(method);
    }

    public SafeMethod(Class<?> coreClass, String methodname, Class<?>... params) {

        try {
            Method method = coreClass.getDeclaredMethod(methodname, params);
            setMethod(method);
        }
        catch (NoSuchMethodException e) {
            QuarterBukkit.getPlugin().getLogger().log(Level.WARNING, "No such method: " + methodname + "!");
        }
    }

    protected void setMethod(Method method) {

        if (method == null) {
            QuarterBukkit.getPlugin().getLogger().log(Level.WARNING, "Cannot create a SafeMethod!");
        }
        if (!method.isAccessible()) {
            method.setAccessible(true);
        }
        this.method = method;
        this.params = method.getParameterTypes();
        this.isStatic = Modifier.isStatic(method.getModifiers());
    }

    @SuppressWarnings ("unchecked")
    @Override
    public T invoke(Object instance, Object... args) {

        if (this.method != null) {

            if (instance == null && !isStatic) {
                throw new UnsupportedOperationException("Non-static methods require a valid instance passed in!");
            }

            if (args.length != this.params.length) {
                throw new UnsupportedOperationException("Not enough arguments!");
            }

            try {
                return (T) this.method.invoke(instance, args);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
