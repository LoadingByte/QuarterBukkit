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

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;

/**
 * A {@link Trait} that adds physics to {@link BaseObject objects} so that they can be moved through the world.
 * It stores a position {@link Vector} which is relative to the origin of the {@link ActiveObjectSystem} it is used in, as well as a velocity vector
 * which defines the movement of the object.
 * An implementation of this interface can either be a {@link StandalonePhysicsObject standalone physics engine} or a delegation to the Bukkit physics engine (e.g. using an entity).
 * Note that it is generally assumed that the position vector changes over time if the velocity vector doesn't have a length of zero.<br>
 * <br>
 * <b>Trait dependencies:</b> <i>none</i>
 *
 * @see Trait
 */
public abstract class PhysicsTrait extends Trait {

    /**
     * Returns the current position of the object relative to the origin of the {@link ActiveObjectSystem} it is used in as a {@link Vector}.
     * Note that this vector changes over time if the object's velocity vector doesn't have a length of zero.
     *
     * @return The current position vector of the object.
     */
    public abstract Vector getPosition();

    /**
     * Changes the current position {@link Vector} of the object relative to the origin of the {@link ActiveObjectSystem} it is used.
     * Note that the newly set vector will change over time if the object's velocity vector doesn't have a length of zero.
     *
     * @param position The new position vector of the object.
     * @return This object.
     */
    public abstract PhysicsTrait setPosition(Vector position);

    /**
     * Returns the current velocity of the object as a {@link Vector}.
     * The unit of this vector is m/s. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s.
     * Note that this velocity vector is applied to the position vector of the object every update.
     *
     * @return The current velocity vector of the object in m/s.
     */
    public abstract Vector getVelocity();

    /**
     * Changes the current velocity {@link Vector} of the object.
     * The unit of this vector is m/s. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s.
     * Note that this velocity vector is applied to the position vector of the object every update.
     *
     * @param velocity The new velocity vector of the object in m/s.
     * @return This object.
     */
    public abstract PhysicsTrait setVelocity(Vector velocity);

    /**
     * Adds the given {@link Vector} to the velocity vector of the object. This method is quite useful for accelerating or decelerating objects.
     * Internally, this is just a shortcut for <code>physics.{@link #setVelocity(Vector) setVelocity}(physics.{@link #getVelocity() getVelocity}().add(velocityChange))</code>.
     * The unit of the input vector is m/s. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s.
     * Note that the overall velocity vector is applied to the position vector of the object every update.
     *
     * @param velocityChange The new velocity vector of the object in m/s.
     * @return This object.
     */
    public PhysicsTrait addVelocity(Vector velocityChange) {

        setVelocity(getVelocity().add(velocityChange));
        return this;
    }

}
