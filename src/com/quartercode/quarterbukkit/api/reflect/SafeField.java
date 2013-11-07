
package com.quartercode.quarterbukkit.api.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public class SafeField<T> implements FieldAccessor<T> {

    private Field field;

    public SafeField(Field field) {

        if (!field.isAccessible()) {
            try {
                field.setAccessible(true);
            }
            catch (SecurityException ex) {
                Bukkit.getLogger().log(Level.WARNING, "Failed to make field: {0} accessible!", field.getName());
                ex.printStackTrace();
            }
        }
        this.field = field;
    }

    public SafeField(Class<?> instance, String fieldName) {

        try {
            new SafeField<T>(instance.getDeclaredField(fieldName));
        }
        catch (NoSuchFieldException e) {
            Bukkit.getLogger().log(Level.WARNING, "Field: {0} does not exist!", fieldName);
            e.printStackTrace();
        }
    }

    @Override
    public T get(Object instance) {

        if (this.field == null) {
            return null;
        }

        try {
            @SuppressWarnings ("unchecked")
            T value = (T) this.field.get(instance);
            return value;
        }
        catch (IllegalAccessException e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to get the value of field: {0}.", this.field.getName());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean set(Object instance, Object value) {

        if (this.field == null) {
            return false;
        }

        try {
            this.field.set(instance, value);
            return true;
        }
        catch (IllegalAccessException e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to set field: {0}.", this.field.getName());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public T transfer(Object from, Object to) {

        if (this.field == null) {
            return null;
        }

        set(to, get(from));

        return get(to);
    }

    @Override
    public String toString() {

        StringBuilder toString = new StringBuilder(50);
        int modifiers = this.field.getModifiers();
        if (Modifier.isPublic(modifiers)) {
            toString.append("public ");
        } else if (Modifier.isPrivate(modifiers)) {
            toString.append("private ");
        } else if (Modifier.isProtected(modifiers)) {
            toString.append("protected ");
        }
        if (Modifier.isStatic(modifiers)) {
            toString.append("static ");
        }
        toString.append(this.field.getName());
        return toString.append(" ").append(this.field.getName()).toString();
    }
}
