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

package com.quartercode.quarterbukkit.api.objectsystem.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Behavior;

/**
 * An object system runner takes an {@link ActiveObjectSystem} and a bunch of {@link Renderer}s and then simulates and displays the system using those renderers.
 */
public class ObjectSystemRunner {

    private final Plugin             plugin;
    private final ActiveObjectSystem objectSystem;

    private final List<Renderer>     renderers;
    private final long               timeResolution;
    private final boolean            stopWhenNoObjects;

    private BukkitTask               updateTask;

    private long                     lastUpdateTime;   // milliseconds

    /**
     * Creates a new object system runner using the given parameters.
     * Note that this constructor is not considered public API and should therefore be avoided.
     * Try to use the {@link ObjectSystemRunnerBuilder} instead.
     *
     * @param plugin The plugin that is used to call some required Bukkit methods.
     *        It hosts the new runner.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     * @param renderers The {@link Renderer}s that simulate and display the objects of the active system.
     * @param timeResolution The time between to object system updates in milliseconds.
     * @param stopWhenNoObjects Whether the runner should stop if no more objects are stored in the given active system.
     *        This is useful for systems with a few manually spawned objects that expire after some time.
     */
    protected ObjectSystemRunner(Plugin plugin, ActiveObjectSystem objectSystem, List<Renderer> renderers, long timeResolution, boolean stopWhenNoObjects) {

        this.plugin = plugin;
        this.objectSystem = objectSystem;

        this.renderers = new ArrayList<>(renderers);
        this.timeResolution = timeResolution;
        this.stopWhenNoObjects = stopWhenNoObjects;
    }

    /**
     * Returns whether the object system runner is currently running and simulating its {@link ActiveObjectSystem} using its {@link Renderer}s.
     * This state can be changed with {@link #setRunning(boolean)}.
     *
     * @return Whether the runner is running.
     */
    public boolean isRunning() {

        return updateTask != null;
    }

    /**
     * Starts or stops the object system runner simulating its {@link ActiveObjectSystem} using its {@link Renderer}s.
     *
     * @param running Whether the should be running.
     */
    public void setRunning(boolean running) {

        if (running && !isRunning()) {
            lastUpdateTime = -1;

            new BukkitRunnable() {

                @Override
                public void run() {

                    // Although we know that 50ms should have elapsed since the last call, we measure elapsed time anyway since lag could influence the scheduler stability
                    long currentTime = System.nanoTime() / 1000 / 1000; // convert from ns to ms

                    if (lastUpdateTime == -1) {
                        // If this is the first run, initialize "lastUpdateTime" and update the object system with a dt of 0
                        lastUpdateTime = currentTime;
                        performUpdate(0);
                    } else {
                        // Otherwise, fill up "lastUpdateTime" with as many "timeResolution"s as we can so that it doesn't exceed currentTime, and update with a dt of "timeResolution" each time
                        while (lastUpdateTime < currentTime - timeResolution) {
                            lastUpdateTime += timeResolution;
                            performUpdate(timeResolution);
                        }
                    }
                }

            }.runTaskTimer(plugin, 0, 1);
        } else if (!running && isRunning()) {
            updateTask.cancel();
            updateTask = null;
        }
    }

    private void performUpdate(long dt) {

        updateRecursively(dt, objectSystem);

        // Stop if "stopWhenNoObjects" is enabled and no objects are found
        if (stopWhenNoObjects && !checkNotEmptyRecursively(objectSystem)) {
            setRunning(false);
        }
    }

    private void updateRecursively(long dt, ActiveObjectSystem system) {

        // Increment object system lifetime
        system.incrementLifetime(dt);

        // Apply global behaviors
        for (Behavior<ActiveObjectSystem> behavior : system.getDefinition().getBehaviors()) {
            behavior.behave(dt, system);
        }

        // Apply renderers
        for (Renderer renderer : renderers) {
            for (BaseObject object : new ArrayList<>(system.getObjects())) {
                renderer.render(plugin, dt, object);
            }
        }

        // Update all nested object systems, if there are any
        for (BaseObject object : system.getObjects()) {
            object.get(ActiveObjectSystem.class).ifPresent(aos -> updateRecursively(dt, aos));
        }
    }

    /*
     * Returns whether the given active system or any of its children contains at least one non-subsystem object.
     */
    private boolean checkNotEmptyRecursively(ActiveObjectSystem system) {

        for (BaseObject object : system.getObjects()) {
            Optional<ActiveObjectSystem> subSystem = object.get(ActiveObjectSystem.class);
            if (!subSystem.isPresent() || checkNotEmptyRecursively(subSystem.get())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
