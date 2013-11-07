
package com.quartercode.quarterbukkit.api.reflect;

public interface MethodAccessor<T> {

    T invoke(Object instance, Object... args);

}
