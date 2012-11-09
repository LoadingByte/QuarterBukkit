
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

        return (long) ((double) millis / 50D);
    }

    /**
     * Convert minecraft-ticks to milliseconds.
     * 
     * @param ticks The minecraft-ticks
     * @return The milliseconds
     */
    public long getMillis(long ticks) {

        return (long) ((double) ticks * 50D);
    }

    private MathUtil() {

    }

}
