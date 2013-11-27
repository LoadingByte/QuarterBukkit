
package com.quartercode.quarterbukkit.api.reflect.wrappers;

import com.quartercode.quarterbukkit.api.reflect.refs.IntHashMapRef;

public class IntHashMap<T> extends BasicWrapper {

    /**
     * ===========================================
     * ===== Required for internal processes =====
     * ===========================================
     */

    public IntHashMap() {

        setHandle(IntHashMapRef.constructor.newInstance());
    }

    public IntHashMap(Object handle) {

        setHandle(handle);
    }

    @SuppressWarnings ("unchecked")
    public T get(int key) {

        return (T) IntHashMapRef.get.invoke(handle, key);
    }

    public boolean contains(int key) {

        return IntHashMapRef.contains.invoke(handle, key);
    }

    @SuppressWarnings ("unchecked")
    public T remove(int key) {

        return (T) IntHashMapRef.remove.invoke(handle, key);
    }

    public void put(int key, Object value) {

        IntHashMapRef.put.invoke(handle, key, value);
    }

    public void clear() {

        IntHashMapRef.clear.invoke(handle);
    }
}
