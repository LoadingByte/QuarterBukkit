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

import java.util.logging.Level;
import com.quartercode.quarterbukkit.QuarterBukkit;

/**
 * A Class Template meant for pointing to a Class in the net.minecraft.server Package<br>
 * Automatically deals with package versioning<br>
 * The empty constructor can be used to initialize this Class using the current Class name
 */

public class NMSClassTemplate extends ClassTemplate<Object> {

    /**
     * Initializes this NMS Class Template using the NMS Class name the same as this Class<br>
     * If this Class is called 'Packet', it will point to Class 'net.minecraft.server.[version].Packet'<br>
     * A leading 'NMS' or trailing 'Ref' is omitted from the Class name, avoiding the Class showing up in the imports<br>
     * 
     * This constructor should and can only be called by an extension of this Class
     */
    protected NMSClassTemplate() {

        setNMSClass(getClass().getSimpleName());
    }

    /**
     * Initializes this NMS Class Template pointing to the class name specified
     * 
     * @param className in the NMS package
     */
    public NMSClassTemplate(String className) {

        setNMSClass(className);
    }

    /**
     * Initializes this net.minecraft.server Class Template to represent the NMS Class name specified
     * 
     * @param className to represent
     */
    @SuppressWarnings ({ "rawtypes", "unchecked" })
    protected void setNMSClass(String className) {

        Class clazz = BukkitServer.getNMSClass(className);
        if (clazz == null) {
            QuarterBukkit.getPlugin().getLogger().log(Level.WARNING, "Failed to find a valid class for name = {0}!", className);
        }
        setClass(clazz);
    }

    /**
     * Creates a new NMS Class Template for the net.minecraft.server Class name specified
     * 
     * @param className of the class in the NMS package
     * @return new Class Template
     */
    public static NMSClassTemplate create(String className) {

        return new NMSClassTemplate(className);
    }
}
