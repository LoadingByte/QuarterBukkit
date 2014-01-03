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

package com.quartercode.quarterbukkit.api.scheduler;

import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import com.quartercode.quarterbukkit.api.MathUtil;

/**
 * This class is for implementing an easy to use schedule-task fir the Bukkit-Scheduler.
 */
public abstract class ScheduleTask implements Runnable {

    private final Plugin plugin;
    private BukkitTask   bukkitTask;

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

        return bukkitTask != null;
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

    private void checkRunning() {

        if (isRunning()) {
            throw new IllegalStateException("ScheduleTask already running (id " + bukkitTask.getTaskId() + ")");
        }
    }

    /**
     * Runs the scheduler once with a delay. You have to cancel it after running.
     * 
     * @param sync Should the scheduler runs synced with the Bukkit-Main-{@link Thread}. Async tasks are deprecated!
     * @param delay The delay in ticks.
     * @return This schedule task.
     */
    public ScheduleTask run(final boolean sync, final long delay) {

        checkRunning();

        if (sync) {
            bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, this, MathUtil.getTicks(delay));
        } else {
            bukkitTask = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this, MathUtil.getTicks(delay));
        }

        return this;
    }

    /**
     * Runs the scheduler repeating with a delay until it's cancelled.
     * 
     * @param sync Should the scheduler runs synced with the Bukkit-Main-{@link Thread}. Async tasks are deprecated!
     * @param delay The delay in ticks.
     * @param period The delay between two repeatings in ticks.
     * @return This schedule task.
     */
    public ScheduleTask run(final boolean sync, final long delay, final long period) {

        checkRunning();

        if (sync) {
            bukkitTask = Bukkit.getScheduler().runTaskTimer(plugin, this, MathUtil.getTicks(delay), MathUtil.getTicks(period));
        } else {
            bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, MathUtil.getTicks(delay), MathUtil.getTicks(period));
        }

        return this;
    }

    /**
     * Runs the scheduler once with a delay. You have to cancel it after running if you want to reuse it.
     * 
     * @param delay The delay in ticks.
     * @return This schedule task.
     * 
     * @deprecated This method is deprecated. Use {@link ScheduleTask#run(boolean, long)} instead.
     */
    @Deprecated
    public ScheduleTask run(final long delay) {

        run(true, delay);

        return this;
    }

    /**
     * Runs the scheduler repeating with a delay until it's cancelled.
     * 
     * @param delay The delay in ticks.
     * @param period The delay between two repeatings in ticks.
     * @return This schedule task.
     * 
     * @deprecated This method is deprecated. Use {@link ScheduleTask#run(boolean, long, long)} instead.
     */
    @Deprecated
    public ScheduleTask run(final long delay, final long period) {

        run(true, delay, period);

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

        Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
        bukkitTask = null;

        return this;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + bukkitTask.getTaskId();
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
        if (bukkitTask != other.bukkitTask) {
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

        return getClass().getName() + " [plugin=" + plugin + ", id=" + bukkitTask.getTaskId() + "]";
    }

}
