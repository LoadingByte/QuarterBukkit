
package com.quartercode.quarterbukkit.api;

/**
 * A math helper class.
 */
public class MathUtil {

    /**
     * Convert milliseconds to minecraft-ticks.
     * 
     * @param millis The milliseconds.
     * @return The minecraft-ticks.
     */
    public long getTicks(long millis) {

        return millis / 50;
    }

    /**
     * Convert minecraft-ticks to milliseconds.
     * 
     * @param ticks The minecraft-ticks
     * @return The milliseconds
     */
    public long getMillis(long ticks) {

        return ticks * 50;
    }

    private MathUtil() {

    }

}
