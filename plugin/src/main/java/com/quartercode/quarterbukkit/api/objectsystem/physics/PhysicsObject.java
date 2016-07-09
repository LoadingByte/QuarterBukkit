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

package com.quartercode.quarterbukkit.api.objectsystem.physics;

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;

/**
 * A physics object that can be moved through the world.
 * It stores a position {@link Vector} which is relative to the origin of the {@link ActiveObjectSystem} it is used in, as well as a velocity vector
 * which defines the movement of the object.
 * An implementation of this interface can either be a standalone physics engine or a delegation to the bukkit physics engine (e.g. using an entity).
 * Note that the position vector changes over time if the velocity vector doesn't have a length of zero.
 *
 * @see BaseObject
 * @see VelocityModificationRule
 */
public interface PhysicsObject extends BaseObject {

    /**
     * Returns the current position of the physics object relative to the origin of the {@link ActiveObjectSystem} it is used in as a {@link Vector}.
     * Note that this vector changes over time if the object's velocity vector doesn't have a length of zero.
     *
     * @return The current position vector of the object.
     */
    public Vector getPosition();

    /**
     * Changes the current position {@link Vector} of the physics object relative to the origin of the {@link ActiveObjectSystem} it is used.
     * Note that the newly set vector will change over time if the object's velocity vector doesn't have a length of zero.
     *
     * @param position The new position vector of the object.
     */
    public void setPosition(Vector position);

    /**
     * Returns the current velocity of the physics object as a {@link Vector}.
     * Note that this vector is added to the position vector of the object every tick.
     *
     * @return The current velocity vector of the object.
     */
    public Vector getVelocity();

    /**
     * Changes the current velocity {@link Vector} of the physics object.
     * Note that the newly set vector will be added to the position vector of the object every tick.
     *
     * @param velocity The new velocity vector of the object.
     */
    public void setVelocity(Vector velocity);

}
