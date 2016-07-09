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
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.MathUtil;

/**
 * This class is for implementing an easy to use schedule-task fir the Bukkit-Scheduler.
 */
public abstract class ScheduleTask implements Runnable {

    private final Plugin plugin;
    private int          bukkitTaskId;

    /**
     * Creates a schedule task with a custom {@link Plugin}. This is recommended!
     *
     * @param plugin The custom plugin.
     */
    public ScheduleTask(Plugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Returns if the ScheduleTask is running.
     * Check this before run() or cancel()!
     *
     * @return If the ScheduleTask is running.
     */
    public boolean isRunning() {

        return bukkitTaskId > 0;
    }

    /**
     * Adds the schedule task to a {@link Collection} (this may be a {@link ScheduleGroup}).
     *
     * @param collection The {@link Collection} to add the task.
     * @return This schedule task.
     */
    public ScheduleTask add(Collection<ScheduleTask> collection) {

        collection.add(this);
        return this;
    }

    private void checkRunning() {

        if (isRunning()) {
            throw new IllegalStateException("ScheduleTask already running (id " + bukkitTaskId + ")");
        }
    }

    /**
     * Runs the scheduler once with a delay. You have to cancel it after running.
     *
     * @param sync Should the scheduler runs synced with the Bukkit-Main-{@link Thread}. Async tasks are deprecated!
     * @param delay The delay in ticks.
     * @return This schedule task.
     */
    public ScheduleTask run(boolean sync, long delay) {

        checkRunning();

        if (sync) {
            bukkitTaskId = Bukkit.getScheduler().runTaskLater(plugin, this, MathUtil.getTicks(delay)).getTaskId();
        } else {
            bukkitTaskId = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this, MathUtil.getTicks(delay)).getTaskId();
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
    public ScheduleTask run(boolean sync, long delay, long period) {

        checkRunning();

        if (sync) {
            bukkitTaskId = Bukkit.getScheduler().runTaskTimer(plugin, this, MathUtil.getTicks(delay), MathUtil.getTicks(period)).getTaskId();
        } else {
            bukkitTaskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, MathUtil.getTicks(delay), MathUtil.getTicks(period)).getTaskId();
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

        Bukkit.getScheduler().cancelTask(bukkitTaskId);
        bukkitTaskId = -1;

        return this;
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this, new String[] { "plugin" });
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj, new String[] { "plugin" });
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
