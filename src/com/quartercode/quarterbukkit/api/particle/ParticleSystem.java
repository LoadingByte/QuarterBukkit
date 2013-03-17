
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
 * The system can also get customized with sutom renderers and spawners.
 * All parameters can be changed in real time between to runs.
 */
public class ParticleSystem {

    private List<ParticleDescription> descriptions = new ArrayList<ParticleDescription>();
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
    public ParticleSystem(final Location location) {

        this.location = location;
    }

    /**
     * Creates a new particle system and sets the {@link ParticleDescription}s as an array/vararg.
     * 
     * @param descriptions The {@link ParticleDescription}s as an array/vararg.
     */
    public ParticleSystem(final ParticleDescription... descriptions) {

        this.descriptions = Arrays.asList(descriptions);
    }

    /**
     * Creates a new particle system and sets the {@link ParticleDescription}s as a {@link List}.
     * 
     * @param descriptions The {@link ParticleDescription}s as a {@link List}.
     */
    public ParticleSystem(final List<ParticleDescription> descriptions) {

        this.descriptions = descriptions;
    }

    /**
     * Creates a new particle system and sets the start {@link Location} and the {@link ParticleDescription}s as an array/vararg.
     * 
     * @param location The start {@link Location}.
     * @param descriptions The {@link ParticleDescription}s as an array/vararg.
     */
    public ParticleSystem(final Location location, final ParticleDescription... descriptions) {

        this.location = location;
        this.descriptions = Arrays.asList(descriptions);
    }

    /**
     * Creates a new particle system and sets the start {@link Location} and the {@link ParticleDescription}s as a {@link List}.
     * 
     * @param location The start {@link Location}.
     * @param descriptions The {@link ParticleDescription}s as a {@link List}.
     */
    public ParticleSystem(final Location location, final List<ParticleDescription> descriptions) {

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
    public ParticleSystem setDescriptions(final ParticleDescription... descriptions) {

        this.descriptions = Arrays.asList(descriptions);
        return this;
    }

    /**
     * Sets the {@link ParticleDescription}s as a {@link List}.
     * 
     * @param descriptions The {@link ParticleDescription}s as a {@link List}.
     * @return This particle system.
     */
    public ParticleSystem setDescriptions(final List<ParticleDescription> descriptions) {

        this.descriptions = descriptions;
        return this;
    }

    /**
     * Adds a {@link ParticleDescription}.
     * 
     * @param description The {@link ParticleDescription} to add.
     * @return This particle system.
     */
    public ParticleSystem addDescription(final ParticleDescription description) {

        descriptions.add(description);
        return this;
    }

    /**
     * Removes a {@link ParticleDescription}.
     * 
     * @param description The {@link ParticleDescription} to remove.
     * @return This particle system.
     */
    public ParticleSystem removeDescription(final ParticleDescription description) {

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
    public ParticleSystem setLocation(final Location location) {

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
    public ParticleSystem setRuns(final int runs) {

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
    public ParticleSystem setRate(final int rate) {

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
    public ParticleSystem setAnimation(final Vector animation) {

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
    public ParticleSystem setSpawner(final ParticleSpawner spawner) {

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
     * Cancels the animation if it's running.
     * 
     * @return This particle system.
     */
    public ParticleSystem cancel() {

        if (isRunning()) {
            scheduleTask.cancel();
            scheduleTask = null;
        }

        return this;
    }

    /**
     * Starts the animation and stops the current one if there's already one running.
     * 
     * @param plugin The {@link Plugin} to bind the {@link ScheduleTask} and exception-handling on.
     * @return This particle system.
     */
    public ParticleSystem start(final Plugin plugin) {

        if (isRunning()) {
            cancel();
        }

        final AtomicInteger runCounter = new AtomicInteger();
        final AtomicReference<Location> playLocation = new AtomicReference<Location>(location.clone());
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
     * Executes the particle animation; may override in subclass for more customization.
     * 
     * @param plugin The {@link Plugin} to bind the {@link ScheduleTask} and exception-handling on.
     * @param location The current particle source.
     */
    protected void execute(final Plugin plugin, final Location location) {

        spawner.spawn(plugin, descriptions, location);
    }

    /**
     * Gets called when the animation ends.
     * 
     * @param plugin The {@link Plugin} to bind the {@link ScheduleTask} and exception-handling on.
     * @param location The current particle source.
     */
    protected void end(final Plugin plugin, final Location location) {

    }

}
