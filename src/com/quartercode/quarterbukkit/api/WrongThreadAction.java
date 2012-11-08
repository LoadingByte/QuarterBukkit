
package com.quartercode.quarterbukkit.api;

import java.lang.reflect.Method;

/**
 * This enum defines the actions what to do if a Bukkit-API-Method is called in the wrong thread.
 */
public enum WrongThreadAction {

    /**
     * Throw an {@link Exception}.
     */
    EXCEPTION,

    /**
     * Insert the {@link Method} in a list and wait for the main bukkit {@link Thread}.
     */
    WAIT,

    /**
     * Ignores the {@link Thread}-problem. Not recommended!
     */
    IGNORE;

}
