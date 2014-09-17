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
import com.quartercode.quarterbukkit.api.objectsystem.DefaultBaseObject;

/**
 * A standalone implementation of {@link PhysicsObject} that just stores the data in member variables and applies the velocity every tick.
 * It extends the {@link DefaultBaseObject} class for inheriting the basic object functionality.
 * 
 * @see PhysicsObject
 */
public class StandalonePhysicsObject extends DefaultBaseObject implements PhysicsObject {

    private Vector position;
    private Vector velocity;

    /**
     * Creates a new standalone physics object that never expires, is located at the origin of its {@link ActiveObjectSystem} and doesn't move initially.
     */
    public StandalonePhysicsObject() {

        this(new Vector());
    }

    /**
     * Creates a new standalone physics object that never expires, is located at the given position and doesn't move initially.
     * 
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public StandalonePhysicsObject(Vector position) {

        this(position, new Vector());
    }

    /**
     * Creates a new standalone physics object that never expires, is located at the given position and initially moves with the given velocity.
     * 
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public StandalonePhysicsObject(Vector position, Vector velocity) {

        this.position = position;
        this.velocity = velocity;
    }

    /**
     * Creates a new standalone physics object that is removed after the given amount of updates, is located at the origin of its {@link ActiveObjectSystem} and
     * doesn't move initially.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     */
    public StandalonePhysicsObject(int expirationTime) {

        this();

        setExpirationTime(expirationTime);
    }

    /**
     * Creates a new standalone physics object that is removed after the given amount of updates, is located at the given position and doesn't move initially.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public StandalonePhysicsObject(int expirationTime, Vector position) {

        this(position);

        setExpirationTime(expirationTime);
    }

    /**
     * Creates a new standalone physics object that is removed after the given amount of updates, is located at the given position and initially moves
     * with the given velocity.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public StandalonePhysicsObject(int expirationTime, Vector position, Vector velocity) {

        this(position, velocity);

        setExpirationTime(expirationTime);
    }

    @Override
    public Vector getPosition() {

        return position.clone();
    }

    @Override
    public void setPosition(Vector position) {

        this.position = position.clone();
    }

    @Override
    public Vector getVelocity() {

        return velocity.clone();
    }

    @Override
    public void setVelocity(Vector velocity) {

        this.velocity = velocity.clone();
    }

    @Override
    public StandalonePhysicsObject clone() {

        StandalonePhysicsObject clone = (StandalonePhysicsObject) super.clone();

        // Deep cloning
        clone.position = position.clone();
        clone.velocity = velocity.clone();

        return clone;
    }

}
