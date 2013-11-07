
package com.quartercode.quarterbukkit.api.reflect;

import java.util.logging.Level;
import org.bukkit.Bukkit;

public class NMSClassTemplate extends ClassTemplate<Object> {

    public NMSClassTemplate() {

        setNMSClass(getClass().getSimpleName());
    }

    public NMSClassTemplate(String className) {

        setNMSClass(className);
    }

    @SuppressWarnings ("unchecked")
    protected void setNMSClass(String className) {

        setClass((Class<Object>) BukkitServer.getNMSClass(className));
        if (getType() == null) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to find a valid class for name = {0}.", className);
        }
    }

    public static NMSClassTemplate create(String className) {

        return new NMSClassTemplate(className);
    }
}