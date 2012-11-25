
package com.quartercode.quarterbukkit.api.scheduler;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.MathUtil;

/**
 * This class is for implementing an easy to use schedule-task fir the Bukkit-Scheduler.
 */
public abstract class ScheduleTask implements Runnable {

    private final Plugin plugin;
    private int          id = -1;

    /**
     * Creates a schedule task with a custom {@link Plugin}. This is recommended!
     * 
     * @param plugin The custom plugin.
     */
    public ScheduleTask(final Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Returns if the ScheduleTask is running.
     * Check this before run() or cancel()!
     * 
     * @return If the ScheduleTask is running.
     */
    public boolean isRunning() {

        return id >= 0;
    }

    /**
     * Adds the schedule task to a {@link Collection} (this may be a {@link ScheduleGroup}).
     * 
     * @param collection The {@link Collection} to add the task.
     * @return This schedule task.
     */
    public ScheduleTask add(final Collection<ScheduleTask> collection) {

        collection.add(this);
        return this;
    }

    private void checkId() {

        if (isRunning()) {
            throw new IllegalStateException("ScheduleTask already running (id " + id + ")");
        }
    }

    /**
     * Runs the scheduler once with a delay. You have to cancel it after running.
     * 
     * @param sync Should the scheduler runs synced with the Bukkit-Main-{@link Thread}.
     * @param delay The delay in ticks.
     * @return This schedule task.
     */
    public ScheduleTask run(final boolean sync, final long delay) {

        checkId();

        if (sync) {
            id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, this, MathUtil.getTicks(delay));
        } else {
            id = Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, this, MathUtil.getTicks(delay));
        }

        return this;
    }

    /**
     * Runs the scheduler repeating with a delay until it's cancelled.
     * 
     * @param sync Should the scheduler runs synced with the Bukkit-Main-{@link Thread}.
     * @param delay The delay in ticks.
     * @param period The delay between two repeatings in ticks.
     * @return This schedule task.
     */
    public ScheduleTask run(final boolean sync, final long delay, final long period) {

        checkId();

        if (sync) {
            id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, MathUtil.getTicks(delay), MathUtil.getTicks(period));
        } else {
            id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, this, MathUtil.getTicks(delay), MathUtil.getTicks(period));
        }

        return this;
    }

    /**
     * Cancels this schedule task.
     * 
     * @return This schedule task.
     */
    public ScheduleTask cancel() {

        if (!isRunning()) {
            throw new IllegalStateException("ScheduleTask isn't running");
        }

        Bukkit.getScheduler().cancelTask(id);
        id = -1;

        return this;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + (plugin == null ? 0 : plugin.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ScheduleTask other = (ScheduleTask) obj;
        if (id != other.id) {
            return false;
        }
        if (plugin == null) {
            if (other.plugin != null) {
                return false;
            }
        } else if (!plugin.equals(other.plugin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [plugin=" + plugin + ", id=" + id + "]";
    }

}
