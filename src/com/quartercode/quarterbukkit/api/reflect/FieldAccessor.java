
package com.quartercode.quarterbukkit.api.reflect;

public abstract interface FieldAccessor<T> {

    T get(Object instance);

    boolean set(Object instance, T value);

    T transfer(Object from, Object to);

}
