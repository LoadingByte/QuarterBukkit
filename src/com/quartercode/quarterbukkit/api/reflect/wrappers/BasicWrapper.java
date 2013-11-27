
package com.quartercode.quarterbukkit.api.reflect.wrappers;

public class BasicWrapper {

    /**
     * ===========================================
     * ===== Required for internal processes =====
     * ===========================================
     */

    protected Object handle;

    protected void setHandle(Object handle) {

        if (handle == null) {
            throw new UnsupportedOperationException("Cannot set handle to null!");
        }
        this.handle = handle;
    }

    public Object getHandle() {

        return handle;
    }
}
