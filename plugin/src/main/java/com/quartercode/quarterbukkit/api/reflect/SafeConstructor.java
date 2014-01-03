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

package com.quartercode.quarterbukkit.api.reflect;

import java.lang.reflect.Constructor;

/**
 * A safe version of the Constructor
 * 
 * @param <T> type of Class to construct
 */
public class SafeConstructor<T> {

    private Constructor<T> constructor;

    /**
     * Set the Constructor
     * 
     * @param type
     * @param args
     */
    public SafeConstructor(Class<T> type, Class<?>... args) {

        try {
            constructor = type.getConstructor(args);
            constructor.setAccessible(true);
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the Constructor
     * 
     * @param constructor to set
     */
    public SafeConstructor(Constructor<T> constructor) {

        this.constructor = constructor;
    }

    /**
     * Checks whether this Constructor is in a valid state<br>
     * Only if this return true can this Constructor be used without problems
     * 
     * @return True if this constructor is valid, False if not
     */
    public boolean isValid() {

        return constructor != null;
    }

    /**
     * Constructs a new Instance
     * 
     * @param parameters to use for this Constructor
     * @return A constructed type
     * @throws RuntimeException if something went wrong while constructing
     */
    public T newInstance(Object... parameters) {

        try {
            return constructor.newInstance(parameters);
        }
        catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
