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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import com.quartercode.quarterbukkit.QuarterBukkit;

/**
 * Uses reflection to transfer/copy all the fields of a class
 */

public class ClassTemplate<T> {

    private Class<T>           type;
    private List<SafeField<?>> fields;

    /**
     * Initializes a new ClassTemplate not pointing to any Class<br>
     * Call setClass to define the Class to use
     */
    public ClassTemplate() {

    }

    /**
     * Initializes a new ClassTemplate pointing to the Class specified
     * 
     * @param type of Class
     */
    public ClassTemplate(Class<T> type) {

        setClass(type);
    }

    /**
     * Initializes this Class Template to represent the Class and fields of the type specified
     * 
     * @param type to set the Class to
     */
    protected void setClass(Class<T> type) {

        this.type = type;
    }

    /**
     * Gets all the fields declared in this Class
     * 
     * @return Declared fields
     */
    public List<SafeField<?>> getFields() {

        if (type == null) {
            return Collections.emptyList();
        }
        if (fields == null) {
            fields = populateFieldList(new ArrayList<SafeField<?>>(), type);
        }
        return Collections.unmodifiableList(fields);
    }

    private static List<SafeField<?>> populateFieldList(List<SafeField<?>> fields, Class<?> clazz) {

        if (clazz == null) {
            return fields;
        }
        Field[] declared = clazz.getDeclaredFields();
        ArrayList<SafeField<?>> newFields = new ArrayList<SafeField<?>>(declared.length);
        for (Field field : declared) {
            if (!Modifier.isStatic(field.getModifiers())) {
                newFields.add(new SafeField<Object>(field));
            }
        }
        fields.addAll(0, newFields);
        return populateFieldList(fields, clazz.getSuperclass());
    }

    /**
     * Gets a new instance of this Class, using the empty constructor
     * 
     * @return A new instance, or null if an error occurred/empty constructor is not available
     * @throws IllegalStateException if this ClassTemplate has no (valid) Class set
     */
    public T newInstance() {

        if (this.type == null) {
            throw new IllegalStateException("Class not set.");
        }

        try {
            return getType().newInstance();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the Class type represented by this Template
     * 
     * @return Class type
     */
    public Class<T> getType() {

        return this.type;
    }

    /**
     * Attempts to create a new template for the class at the path specified.
     * If the class is not found, a proper warning is printed.
     * 
     * @param path to the class
     * @return a new template, or null if the template could not be made
     */
    public static ClassTemplate<?> create(String path) {

        Class<?> type = BukkitServer.getClass(path);
        if (type == null) {
            QuarterBukkit.getPlugin().getLogger().log(Level.WARNING, "Class not found: '" + path + "'");
        }
        return create(type);
    }

    /**
     * Attempts to create a new template for the class of the class instance specified<br>
     * If something fails, an empty instance is returned
     * 
     * @param value of the class to create the template for
     * @return a new template
     */
    @SuppressWarnings ("unchecked")
    public static <T> ClassTemplate<T> create(T value) {

        return create((Class<T>) value.getClass());
    }

    /**
     * Attempts to create a new template for the class specified<br>
     * If something fails, an empty instance is returned
     * 
     * @param clazz to create
     * @return a new template
     */
    public static <T> ClassTemplate<T> create(Class<T> clazz) {

        return new ClassTemplate<T>(clazz);
    }

    /**
     * Attempts to create a new template for the class specified<br>
     * If something fails, an empty instance is returned
     * 
     * @param clazz to create
     * @return a new template
     */
    public boolean isAssignableFrom(Class<?> clazz) {

        return this.getType().isAssignableFrom(clazz);
    }

    /**
     * Checks whether a given object is an instance of the class represented by this Template
     * 
     * @param object to check
     * @return True if the object is an instance, False if not
     */
    public boolean isInstance(Object object) {

        return this.getType().isInstance(object);
    }

    /**
     * Attempts to find the method by name
     * 
     * @param methodname of the method
     * @param params of the method
     * @return method
     */
    public <K> MethodAccessor<K> getMethod(String methodname, Class<?>... params) {

        return new SafeMethod<K>(this.getType(), methodname, params);
    }

    /**
     * Attempts to find the field by name
     * 
     * @param fieldName of the field
     * @return field
     */
    public <K> FieldAccessor<K> getField(String fieldName) {

        return new SafeField<K>(getType(), fieldName);
    }

    /**
     * Attempts to find the constructor for this Class using the parameter types
     * 
     * @param params of the constructor
     * @return Constructor
     */
    public SafeConstructor<T> getConstructor(Class<?>... params) {

        return new SafeConstructor<T>(this.getType(), params);
    }

    /**
     * Gets a statically declared field value
     * 
     * @param name of the static field
     * @return Static field value
     */
    public <K> K getStaticFieldValue(String name) {

        return SafeField.get(getType(), name);
    }

    /**
     * Sets a statically declared field value
     * 
     * @param name of the static field
     * @param value to set the static field to
     */
    public <K> void setStaticFieldValue(String name, K value) {

        SafeField.setStatic(getType(), name, value);
    }
}
