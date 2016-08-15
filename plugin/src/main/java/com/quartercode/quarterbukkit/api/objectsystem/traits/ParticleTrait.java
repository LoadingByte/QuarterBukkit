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

package com.quartercode.quarterbukkit.api.objectsystem.traits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang.Validate;
import com.quartercode.quarterbukkit.api.fx.particle.ParticleDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;
import com.quartercode.quarterbukkit.api.objectsystem.TraitDependencies;

/**
 * A {@link Trait} that makes its {@link BaseObject object} spawn Minecraft particle wherever it currently is.
 * The goal is to support all possible particles.<br>
 * <br>
 * <b>Trait dependencies:</b> {@link PhysicsTrait}
 *
 * @see Trait
 */
@TraitDependencies (PhysicsTrait.class)
public class ParticleTrait extends Trait {

    private final Collection<ParticleDefinition> particles           = new ArrayList<>();
    private boolean                              speedBasedFrequency = true;

    /**
     * Creates a new particle trait which doesn't have any {@link ParticleDefinition}s yet.
     */
    public ParticleTrait() {

    }

    /**
     * Creates a new particle trait with the given {@link ParticleDefinition}s that define some more particles that should be spawned.
     * Those particles will be spawned each time the object system is updated.
     *
     * @param particles The particle definitions that should be used.
     */
    public ParticleTrait(ParticleDefinition... particles) {

        addParticles(particles);
    }

    /**
     * Creates a new particle trait with the given {@link ParticleDefinition}s that define some more particles that should be spawned.
     * Those particles will be spawned each time the object system is updated.
     *
     * @param particles The particle definitions that should be used.
     */
    public ParticleTrait(Collection<ParticleDefinition> particles) {

        addParticles(particles);
    }

    /**
     * Returns the {@link ParticleDefinition}s that define the particles that should be spawned.
     * All particles will be spawned each time the object system is updated.
     *
     * @return The particle definitions that are spawned to display the object.
     */
    public Collection<ParticleDefinition> getParticles() {

        return Collections.unmodifiableCollection(particles);
    }

    /**
     * Adds the given {@link ParticleDefinition}s that define some more particles that should be spawned.
     * The new particles and the already existing ones particles will be spawned each time the object system is updated.
     *
     * @param particles The particle definitions that should be added.
     * @return This object.
     */
    public ParticleTrait addParticles(ParticleDefinition... particles) {

        addParticles(Arrays.asList(particles));
        return this;
    }

    /**
     * Adds the given {@link ParticleDefinition}s that define some more particles that should be spawned.
     * The new particles and the already existing ones particles will be spawned each time the object system is updated.
     *
     * @param particles The particle definitions that should be added.
     * @return This object.
     */
    public ParticleTrait addParticles(Collection<ParticleDefinition> particles) {

        Validate.noNullElements(particles, "Cannot add null particle definitions to particle trait");
        this.particles.addAll(particles);
        return this;
    }

    /**
     * Removes the given {@link ParticleDefinition}s so they will no longer be spawned.
     * The removed effects will no longer be spawned each time the object system is updated.
     *
     * @param particles The particle definitions that should be removed.
     * @return This object.
     */
    public ParticleTrait removeParticles(ParticleDefinition... particles) {

        removeParticles(Arrays.asList(particles));
        return this;
    }

    /**
     * Removes the given {@link ParticleDefinition}s so they will no longer be spawned.
     * The removed effects will no longer be spawned each time the object system is updated.
     *
     * @param particles The particle definitions that should be removed.
     * @return This object.
     */
    public ParticleTrait removeParticles(Collection<ParticleDefinition> particles) {

        this.particles.removeAll(particles);
        return this;
    }

    /**
     * Returns whether the speed based frequency adjustment mode is enabled.
     * If it is, objects with a low velocity will spawn particles less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     *
     * @return Whether speed based frequency is enabled.
     */
    public boolean hasSpeedBasedFrequency() {

        return speedBasedFrequency;
    }

    /**
     * Sets whether the speed based frequency adjustment mode is enabled.
     * If it is, objects with a low velocity will spawn particles less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     *
     * @param speedBasedFrequency Whether speed based frequency should be enabled.
     * @return This object.
     */
    public ParticleTrait setSpeedBasedFrequency(boolean speedBasedFrequency) {

        this.speedBasedFrequency = speedBasedFrequency;
        return this;
    }

}
