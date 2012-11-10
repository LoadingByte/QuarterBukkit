
package com.quartercode.quarterbukkit.api;

/**
 * A math helper class.
 * You can convert values with this Utility-Class.
 */
public class MathUtil {

    /**
     * Convert milliseconds to minecraft-ticks.
     * 
     * @param millis The milliseconds.
     * @return The minecraft-ticks.
     */
    public static long getTicks(final long millis) {

        return (long) (millis / 50D);
    }

    /**
     * Convert minecraft-ticks to milliseconds.
     * 
     * @param ticks The minecraft-ticks
     * @return The milliseconds
     */
    public static long getMillis(final long ticks) {

        return (long) (ticks * 50D);
    }

    private MathUtil() {

    }

}
