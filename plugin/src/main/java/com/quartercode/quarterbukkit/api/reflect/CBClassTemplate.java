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

import java.util.logging.Level;
import com.quartercode.quarterbukkit.QuarterBukkit;

/**
 * A Class Template meant for pointing to a Class in the org.bukkit.craftbukkit Package<br>
 * Automatically deals with package versioning<br>
 * The empty constructor can be used to initialize this Class using the current Class name
 */

public class CBClassTemplate extends ClassTemplate<Object> {

    /**
     * Initializes this CB Class Template using the CB Class name the same as this Class<br>
     * If this Class is called 'CraftServer', it will point to Class 'org.bukkit.craftbukkit.[version].CraftServer'<br>
     * A leading 'CB' or trailing 'Ref' is omitted from the Class name, avoiding the Class showing up in the imports<br>
     * 
     * This constructor should and can only be called by an extension of this Class
     */
    public CBClassTemplate() {

        setCBClass(getClass().getSimpleName());
    }

    /**
     * Initializes this CB Class Template pointing to the class name specified
     * 
     * @param className in the CB package
     */
    public CBClassTemplate(String className) {

        setCBClass(className);
    }

    /**
     * Initializes this org.bukkit.craftbukkit Class Template to represent the CB Class name specified
     * 
     * @param className to represent
     */
    @SuppressWarnings ({ "rawtypes", "unchecked" })
    protected void setCBClass(String className) {

        Class clazz = BukkitServer.getCBClass(className);
        if (clazz == null) {
            QuarterBukkit.getPlugin().getLogger().log(Level.WARNING, "Failed to find a valid class for name: " + className + "!");
        }
        setClass(clazz);
    }

    /**
     * Creates a new CB Class Template for the org.bukkit.craftbukkit Class name specified
     * 
     * @param className of the class in the CB package
     * @return new Class Template
     */
    public static CBClassTemplate create(String className) {

        return new CBClassTemplate(className);
    }
}
