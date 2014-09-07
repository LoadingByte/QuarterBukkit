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

package com.quartercode.quarterbukkit.api.objectsystem.object;

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.physics.StandalonePhysicsObject;

/**
 * A {@link StandalonePhysicsObject} that represents a minecraft particle which is spawned at the different positions of the object.
 * The goal is to support all possible particles.
 */
public class ParticleObject extends StandalonePhysicsObject {

    private boolean speedBasedFrequency = true;

    /**
     * Creates a new particle object that never expires, is located at the origin of its {@link ActiveObjectSystem} and doesn't move initially.
     */
    public ParticleObject() {

        super();
    }

    /**
     * Creates a new particle object that never expires, is located at the given position and doesn't move initially.
     * 
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public ParticleObject(Vector position) {

        super(position);
    }

    /**
     * Creates a new particle object that never expires, is located at the given position and initially moves with the given velocity.
     * 
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public ParticleObject(Vector position, Vector velocity) {

        super(position, velocity);
    }

    /**
     * Creates a new particle object that is removed after the given amount of updates, is located at the origin of its {@link ActiveObjectSystem} and
     * doesn't move initially.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     */
    public ParticleObject(int expirationTime) {

        super(expirationTime);
    }

    /**
     * Creates a new particle object that is removed after the given amount of updates, is located at the given position and doesn't move initially.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public ParticleObject(int expirationTime, Vector position) {

        super(expirationTime, position);
    }

    /**
     * Creates a new particle object that is removed after the given amount of updates, is located at the given position and initially moves
     * with the given velocity.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public ParticleObject(int expirationTime, Vector position, Vector velocity) {

        super(expirationTime, position, velocity);
    }

    // TODO: Write the particle api

    /**
     * Returns whether the speed based frequency adjustment mode is enabled.
     * If it is, particle objects with a low velocity will spawn less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     * 
     * @return Whether speed based frequency is enabled.
     */
    public boolean hasSpeedBasedFrequency() {

        return speedBasedFrequency;
    }

    /**
     * Sets whether the speed based frequency adjustment mode is enabled.
     * If it is, particle objects with a low velocity will spawn less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     * 
     * @param speedBasedFrequency Whether speed based frequency should be enabled.
     * @return This object.
     */
    public ParticleObject setSpeedBasedFrequency(boolean speedBasedFrequency) {

        this.speedBasedFrequency = speedBasedFrequency;
        return this;
    }

    @Override
    public ParticleObject clone() {

        return (ParticleObject) super.clone();
    }

}
