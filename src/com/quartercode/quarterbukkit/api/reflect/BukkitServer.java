
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
