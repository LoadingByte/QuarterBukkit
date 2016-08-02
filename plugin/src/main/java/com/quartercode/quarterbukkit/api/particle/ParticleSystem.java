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

package com.quartercode.quarterbukkit.api.particle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;

/**
 * This class represents a complex particle system using firework particles.
 * The system can also get customized with custom renderers and spawners.
 * All parameters can be changed in real time between to runs.
 *
 * @deprecated The particle API was replaced by the more flexible object system API.
 *             See the wiki for more information on that new API.
 */
@Deprecated
public class ParticleSystem {

    private List<ParticleDescription> descriptions = new ArrayList<>();
    private Location                  location;
    private int                       runs         = 1;
    private int                       rate         = 0;
    private Vector                    animation    = new Vector();

    private ParticleSpawner           spawner      = new DefaultParticleSpawner();
    private ScheduleTask              scheduleTask;

    /**
     * Creates a new empty particle system.
     */
    public ParticleSystem() {

    }

    /**
     * Creates a new particle system and sets the start {@link Location}.
     *
     * @param location The start {@link Location}.
     */
    public ParticleSystem(Location location) {

        this.location = location;
    }

    /**
     * Creates a new particle system and sets the {@link ParticleDescription}s as an array/vararg.
     *
     * @param descriptions The {@link ParticleDescription}s as an array/vararg.
     */
    public ParticleSystem(ParticleDescription... descriptions) {

        this.descriptions = Arrays.asList(descriptions);
    }

    /**
     * Creates a new particle system and sets the {@link ParticleDescription}s as a {@link List}.
     *
     * @param descriptions The {@link ParticleDescription}s as a {@link List}.
     */
    public ParticleSystem(List<ParticleDescription> descriptions) {

        this.descriptions = descriptions;
    }

    /**
     * Creates a new particle system and sets the start {@link Location} and the {@link ParticleDescription}s as an array/vararg.
     *
     * @param location The start {@link Location}.
     * @param descriptions The {@link ParticleDescription}s as an array/vararg.
     */
    public ParticleSystem(Location location, ParticleDescription... descriptions) {

        this.location = location;
        this.descriptions = Arrays.asList(descriptions);
    }

    /**
     * Creates a new particle system and sets the start {@link Location} and the {@link ParticleDescription}s as a {@link List}.
     *
     * @param location The start {@link Location}.
     * @param descriptions The {@link ParticleDescription}s as a {@link List}.
     */
    public ParticleSystem(Location location, List<ParticleDescription> descriptions) {

        this.location = location;
        this.descriptions = descriptions;
    }

    /**
     * Returns the {@link ParticleDescription}s.
     *
     * @return The {@link ParticleDescription}s.
     */
    public List<ParticleDescription> getDescriptions() {

        return Collections.unmodifiableList(descriptions);
    }

    /**
     * Sets the {@link ParticleDescription}s as an array/vararg.
     *
     * @param descriptions The {@link ParticleDescription}s as an array/vararg.
     * @return This particle system.
     */
    public ParticleSystem setDescriptions(ParticleDescription... descriptions) {

        this.descriptions = Arrays.asList(descriptions);
        return this;
    }

    /**
     * Sets the {@link ParticleDescription}s as a {@link List}.
     *
     * @param descriptions The {@link ParticleDescription}s as a {@link List}.
     * @return This particle system.
     */
    public ParticleSystem setDescriptions(List<ParticleDescription> descriptions) {

        this.descriptions = descriptions;
        return this;
    }

    /**
     * Adds a {@link ParticleDescription}.
     *
     * @param description The {@link ParticleDescription} to add.
     * @return This particle system.
     */
    public ParticleSystem addDescription(ParticleDescription description) {

        descriptions.add(description);
        return this;
    }

    /**
     * Removes a {@link ParticleDescription}.
     *
     * @param description The {@link ParticleDescription} to remove.
     * @return This particle system.
     */
    public ParticleSystem removeDescription(ParticleDescription description) {

        descriptions.remove(description);
        return this;
    }

    /**
     * Returns the start {@link Location}.
     *
     * @return The start {@link Location}.
     */
    public Location getLocation() {

        return location;
    }

    /**
     * Sets the start {@link Location}.
     *
     * @param location The start {@link Location}.
     * @return This particle system.
     */
    public ParticleSystem setLocation(Location location) {

        this.location = location;
        return this;
    }

    /**
     * Returns the count of runs the particle system should emit particles.
     *
     * @return The count of runs the particle system should emit particles.
     */
    public int getRuns() {

        return runs;
    }

    /**
     * Sets the count of runs the particle system should emit particles. Set to -1 for endless runs.
     *
     * @param runs The count of runs the particle system should emit particles. Set to -1 for endless runs.
     * @return This particle system.
     */
    public ParticleSystem setRuns(int runs) {

        this.runs = runs;
        return this;
    }

    /**
     * Returns the delay between two runs.
     *
     * @return The delay between two runs.
     */
    public int getRate() {

        return rate;
    }

    /**
     * Sets the delay between two runs.
     *
     * @param rate The delay between two runs.
     * @return This particle system.
     */
    public ParticleSystem setRate(int rate) {

        this.rate = rate;
        return this;
    }

    /**
     * Returns the movement direction of the spawner as a {@link Vector}.
     *
     * @return The movement direction of the spawner as a {@link Vector}.
     */
    public Vector getAnimation() {

        return animation;
    }

    /**
     * Sets the movement direction of the spawner as a {@link Vector}.
     *
     * @param animation The movement direction of the spawner as a {@link Vector}.
     * @return This particle system.
     */
    public ParticleSystem setAnimation(Vector animation) {

        this.animation = animation;
        return this;
    }

    /**
     * Returns the {@link ParticleSpawner} (default {@link DefaultParticleSpawner}).
     *
     * @return The {@link ParticleSpawner}.
     */
    public ParticleSpawner getSpawner() {

        return spawner;
    }

    /**
     * Sets the {@link ParticleSpawner}.
     *
     * @param spawner The {@link ParticleSpawner}.
     * @return This particle system.
     */
    public ParticleSystem setSpawner(ParticleSpawner spawner) {

        this.spawner = spawner;
        return this;
    }

    /**
     * Returns if the particle system is running.
     *
     * @return If the particle system is running.
     */
    public boolean isRunning() {

        return scheduleTask != null && scheduleTask.isRunning();
    }

    /**
     * Starts the animation and stops the current one if there's already one running.
     *
     * @param plugin The {@link Plugin} to bind the {@link ScheduleTask} and exception-handling on.
     * @return This particle system.
     */
    public ParticleSystem start(final Plugin plugin) {

        if (isRunning()) {
            stop();
        }

        final AtomicInteger runCounter = new AtomicInteger();
        final AtomicReference<Location> playLocation = new AtomicReference<>(location.clone());
        scheduleTask = new ScheduleTask(plugin) {

            @Override
            public void run() {

                execute(plugin, playLocation.get());
                playLocation.get().add(animation);

                if (rate >= 0) {
                    runCounter.incrementAndGet();
                    if (runCounter.get() >= runs) {
                        end(plugin, playLocation.get());
                        cancel();
                    }
                }
            }
        }.run(true, 0, rate);

        return this;
    }

    /**
     * Stops the animation if it's running.
     *
     * @return This particle system.
     */
    public ParticleSystem stop() {

        if (isRunning()) {
            scheduleTask.cancel();
            scheduleTask = null;
        }

        return this;
    }

    /**
     * Executes the particle animation.
     * This method can be overriden in subclasses for more customization.
     *
     * @param plugin The {@link Plugin} to bind the {@link ScheduleTask} and exception-handling on.
     * @param location The current particle source.
     */
    protected void execute(Plugin plugin, Location location) {

        spawner.spawn(plugin, descriptions, location);
    }

    /**
     * Is called when the animation ends.
     *
     * @param plugin The {@link Plugin} to bind the {@link ScheduleTask} and exception-handling on.
     * @param location The current particle source.
     */
    protected void end(Plugin plugin, Location location) {

    }

}
