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
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.ModificationRule;
import com.quartercode.quarterbukkit.api.objectsystem.Source;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;

/**
 * An object system runner takes an {@link ActiveObjectSystem} and a bunch of {@link Renderer}s and then simulates and displays the system using those renderers.
 */
public class ObjectSystemRunner {

    private static final Timer       UPDATE_TIMER = new Timer("Timer-ObjectSystemRunner-Update", true);

    private final Plugin             plugin;
    private final ActiveObjectSystem objectSystem;

    private final List<Renderer<?>>  renderers;
    private final long               timeResolution;
    private final boolean            stopWhenNoObjects;

    private TimerTask                updateTask;

    private long                     lastUpdateTime;                                                   // nanoseconds

    /**
     * Creates a new object system runner using the given parameters.
     * Note that this constructor is not considered public API and should therefore be avoided.
     * Try to use the {@link ObjectSystemRunnerBuilder} instead.
     *
     * @param plugin The plugin that is used to call some required Bukkit methods.
     *        It hosts the new runner.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     * @param renderers The {@link Renderer}s that simulate and display the objects of the active system.
     *
     * @param stopWhenNoObjects Whether the runner should stop if no more objects are stored in the given active system.
     *        This is useful for systems with a few manually spawned objects that expire after some time.
     */
    protected ObjectSystemRunner(Plugin plugin, ActiveObjectSystem objectSystem, List<Renderer<?>> renderers, long timeResolution, boolean stopWhenNoObjects) {

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

            updateTask = new TimerTask() {

                @Override
                public void run() {

                    // If the plugin got disabled, stop this runner
                    if (!plugin.isEnabled()) {
                        setRunning(false);
                        return;
                    }

                    long currentTime = System.nanoTime() / 1000 / 1000; // convert from ns to ms
                    final long dt = lastUpdateTime == -1 ? 0 : currentTime - lastUpdateTime;
                    lastUpdateTime = currentTime;

                    Bukkit.getScheduler().callSyncMethod(plugin, new Callable<Void>() {

                        @Override
                        public Void call() throws Exception {

                            updateRecursively(dt, objectSystem);

                            // Stop if "stopWhenNoObjects" is enabled and no objects are found
                            if (stopWhenNoObjects && !checkNotEmptyRecursively(objectSystem)) {
                                setRunning(false);
                            }

                            return null;
                        }

                    });
                }

            };
            UPDATE_TIMER.scheduleAtFixedRate(updateTask, 0, timeResolution);
        } else if (!running && isRunning()) {
            updateTask.cancel();
            updateTask = null;
        }
    }

    private void updateRecursively(long dt, ActiveObjectSystem system) {

        // Increment object system lifetime
        system.incrementLifetime(dt);

        // Apply modification rules
        for (BaseObject object : concurrentIterable(system.getObjects())) {
            for (Trait trait : concurrentIterable(object.getTraits())) {
                for (ModificationRule<?, ?> modificationRule : system.getDefinition().getModificationRules()) {
                    tryApplyModificationRule(dt, modificationRule, trait);
                }
            }
        }

        // Spawn new objects
        for (Source source : system.getDefinition().getSources()) {
            source.update(plugin, system, dt);
        }

        // Apply renderers
        for (Renderer<?> renderer : renderers) {
            for (BaseObject object : concurrentIterable(system.getObjects())) {
                tryApplyRenderer(dt, renderer, object);
            }
        }

        // Update all nested object systems, if there are any
        for (BaseObject object : system.getObjects()) {
            if (object.hasTrait(ActiveObjectSystem.class)) {
                updateRecursively(dt, object.getTrait(ActiveObjectSystem.class));
            }
        }
    }

    /*
     * Returns whether the given active system or any of its children contains at least one non-subsystem object.
     */
    private boolean checkNotEmptyRecursively(ActiveObjectSystem system) {

        for (BaseObject object : system.getObjects()) {
            if (!object.hasTrait(ActiveObjectSystem.class) || checkNotEmptyRecursively(object.getTrait(ActiveObjectSystem.class))) {
                return true;
            }
        }

        return false;
    }

    private <E> Iterable<E> concurrentIterable(Collection<E> collection) {

        return new ArrayList<>(collection);
    }

    @SuppressWarnings ("unchecked")
    private <T extends Trait> void tryApplyModificationRule(long dt, ModificationRule<T, ?> modificationRule, Trait trait) {

        if (modificationRule.getTraitType().isInstance(trait)) {
            modificationRule.apply(dt, (T) trait);
        }
    }

    @SuppressWarnings ("unchecked")
    private <O extends BaseObject> void tryApplyRenderer(long dt, Renderer<O> renderer, BaseObject object) {

        if (renderer.getObjectType().isInstance(object)) {
            renderer.render(plugin, dt, (O) object);
        }
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
