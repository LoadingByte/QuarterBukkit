/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * This file is part of QuarterBukkit.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit. If not, see <http://www.gnu.org/licenses/>.
 */

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
