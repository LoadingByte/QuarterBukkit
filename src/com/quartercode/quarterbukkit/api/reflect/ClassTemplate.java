
package com.quartercode.quarterbukkit.api.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public class ClassTemplate<T> {

    private Class<T>           type;
    private List<SafeField<?>> fields;

    public ClassTemplate() {

    }

    public ClassTemplate(Class<T> clazz) {

        setClass(clazz);
    }

    protected void setClass(Class<T> clazz) {

        this.type = clazz;
    }

    public T newInstance() {

        if (this.type == null) {
            throw new IllegalStateException("Class was not found or is not set");
        }
        try {
            return this.type.newInstance();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static ClassTemplate<?> create(String path) {

        Class<?> type = BukkitServer.getClass(path);
        if (type == null) {
            Bukkit.getLogger().log(Level.WARNING, "Class: {0} not found!", path);
        }
        return create(type);
    }

    public List<SafeField<?>> getFields() {

        if (this.fields == null) {
            return Collections.emptyList();
        }
        if (this.fields == null) {
            this.fields = populateList(this.fields, this.type);
        }
        return this.fields;
    }

    private List<SafeField<?>> populateList(List<SafeField<?>> fieldList, Class<?> clazz) {

        if (clazz == null) {
            return this.fields;
        }
        Field[] fields = clazz.getDeclaredFields();
        List<SafeField<?>> newList = new ArrayList<SafeField<?>>(fields.length);

        for (Field field : fields) {
            newList.add(new SafeField<Object>(field));
        }

        fieldList.addAll(0, newList);

        return populateList(fieldList, clazz.getSuperclass());
    }

    public boolean isAssignableFrom(Class<?> clazz) {

        return this.type.isAssignableFrom(clazz);
    }

    public Class<T> getType() {

        return this.type;
    }

    public <K> SafeMethod<K> getMethod(String name, Class<?>... parameterTypes) {

        return new SafeMethod<K>(this.getType(), name, parameterTypes);
    }

    public <K> SafeField<K> getField(String fieldName) {

        return new SafeField<>(this.getType(), fieldName);
    }

    @SuppressWarnings ("unchecked")
    public static <T> ClassTemplate<T> create(T value) {

        return create((Class<T>) value.getClass());
    }

    public static <T> ClassTemplate<T> create(Class<T> clazz) {

        return new ClassTemplate<T>(clazz);
    }
}
