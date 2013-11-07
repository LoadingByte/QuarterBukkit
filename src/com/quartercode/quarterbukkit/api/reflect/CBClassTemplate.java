
package com.quartercode.quarterbukkit.api.reflect;

import java.util.logging.Level;
import org.bukkit.Bukkit;

public class CBClassTemplate extends ClassTemplate<Object> {

    public CBClassTemplate() {

        setCBClass(getClass().getSimpleName());
    }

    public CBClassTemplate(String className) {

        setCBClass(className);
    }

    @SuppressWarnings ("unchecked")
    protected void setCBClass(String className) {

        setClass((Class<Object>) BukkitServer.getCBClass(className));
        if (getType() == null) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to find a valid class; name = {0}.", className);
        }
    }

    public static CBClassTemplate create(String className) {

        return new CBClassTemplate(className);
    }
}
