
package com.quartercode.quarterbukkit.api.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public class ReflectionUtil {

    /**
     * a Reflection helper class
     * 
     * Creating Javadoc later!
     */

    /**
     * get NMS class.
     * 
     * @param name The name of the class.
     * @param args The arguments to initalize the class.
     * @return The NMS class as Object.
     */

    public static Object getNMSClass(String name, Object... args) {

        try {
            Class<?> c = Class.forName(ReflectionUtil.getPackageName() + "." + name);
            int params = 0;
            if (args != null) {
                params = args.length;
            }
            for (Constructor<?> co : c.getConstructors()) {
                if (co.getParameterTypes().length == params) {
                    return co.newInstance(args);
                }
            }
        }
        catch (Exception e) {

        }

        return null;
    }

    /**
     * get NMS exact class.
     * 
     * @param name The name of the class.
     * @param args The arguments to initalize the class.
     * @return The NMS class as Object.
     */
    public static Class<?> getNMSClassExact(String name, Object... args) {

        Class<?> c;
        try {
            c = Class.forName(ReflectionUtil.getPackageName() + "." + name);
        }
        catch (Exception e) {
            return null;
        }
        return c;
    }
    
    /**
     * get Method of a class.
     * 
     * @param name
     * @param c
     * @param params
     * @return
     */
    public static Method getMethod(String name, Class<?> c, int params) {

        for (Method m : c.getMethods()) {
            if (m.getName().equals(name) && m.getParameterTypes().length == params) {
                return m;
            }
        }
        return null;
    }

    public static Method getMethod(String name, Class<?> c) {

        for (Method m : c.getMethods()) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    public static Field getField(String name, Class<?> c) {

        for (Field f : c.getFields()) {
            if (f.getName().equals(name)) {
                return f;
            }
        }
        return null;
    }

    public static void setPublicValue(Object instance, String fieldName, Object value) {

        Field field;

        try {

            field = instance.getClass().getField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPrivateValue(Object instance, String fieldName, Object value) {

        Field field;

        try {

            field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPackageName() {

        return "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
}