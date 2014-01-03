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

import org.bukkit.Bukkit;

/**
 * This class meant the Reference to the Bukkit Version paths.
 * It is used for Reflection System.
 */

public class BukkitServer {

    /**
     * The NMS Path, version already right!
     */
    public static final String NMS_ROOT = getNMSPackageName();

    /**
     * The Craftbukkit Path, version already right!
     */
    public static final String CB_ROOT  = getOBCPackageName();

    private static String getNMSPackageName() {

        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    private static String getOBCPackageName() {

        return "org.bukkit.craftbukkit" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    /**
     * Represents Class.forName();
     * 
     * @param path to the class.
     * @return The Class.
     */
    public static Class<?> getClass(String path) {

        try {
            return Class.forName(path);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Return the NMS Class.
     * 
     * @param className where Located the class.
     * @return the Class.
     */
    public static Class<?> getNMSClass(String className) {

        return getClass(BukkitServer.NMS_ROOT + "." + className);
    }

    /**
     * Return CB Class
     * 
     * @param className where Located the class.
     * @return the Class.
     */
    public static Class<?> getCBClass(String className) {

        return getClass(BukkitServer.CB_ROOT + "." + className);
    }
}
