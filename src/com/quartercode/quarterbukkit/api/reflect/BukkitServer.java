
package com.quartercode.quarterbukkit.api.reflect;

import org.bukkit.Bukkit;

public class BukkitServer {

    private static String getNMSPackageName() {

        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    private static String getOBCPackageName() {

        return "org.bukkit.craftbukkit" + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static Class<?> getClass(String path) {

        try {
            return Class.forName(path);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String className) {

        return getClass(getNMSPackageName() + "." + className);
    }

    public static Class<?> getCBClass(String className) {

        return getClass(getOBCPackageName() + "." + className);
    }
}
