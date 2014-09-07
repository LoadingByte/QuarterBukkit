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

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.DefaultBaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;

/**
 * An object that represents a regular bukkit {@link Entity} and makes it controllable for an {@link ActiveObjectSystem}.
 * All calls to {@link PhysicsObject} methods are forwarded to the equivalent methods of the entity object.
 * 
 * @see Entity
 */
public class RealEntityObject extends DefaultBaseObject implements PhysicsObject {

    private final ActiveObjectSystem referenceSystem;
    private final Entity             entity;

    /**
     * Creates a new real entity object that never expires for the given {@link Entity}.
     * Note that a reference to the object's {@link ActiveObjectSystem} must be provided for position vector calculations.
     * 
     * @param referenceSystem The active system the new object is used in.
     * @param entity The entity that should be made controllable for the active system.
     */
    public RealEntityObject(ActiveObjectSystem referenceSystem, Entity entity) {

        Validate.notNull(entity, "Cannot use null entity for real entity object");

        this.referenceSystem = referenceSystem;
        this.entity = entity;
    }

    /**
     * Creates a new real entity object that expires and is removed after the given amount of updates for the given {@link Entity}.
     * Note that a reference to the object's {@link ActiveObjectSystem} must be provided for position vector calculations.
     * 
     * @param referenceSystem The active system the new object is used in.
     * @param entity The entity that should be made controllable for the active system.
     * @param expirationTime The amount of updates after which the object expires and is removed.
     */
    public RealEntityObject(ActiveObjectSystem referenceSystem, Entity entity, int expirationTime) {

        this(referenceSystem, entity);

        setExpirationTime(expirationTime);
    }

    /**
     * Returns the {@link ActiveObjectSystem} that uses the real entity object.
     * This reference is required because the position vector must be transformed to be relative to the system's origin vector.
     * 
     * @return The active system the object is used in.
     */
    public ActiveObjectSystem getReferenceSystem() {

        return referenceSystem;
    }

    /**
     * Returns the bukkit {@link Entity} that made controllable by the object.
     * All calls to the physics methods are forwarded to that entity.
     * 
     * @return The bukkit entity that is represented by the object.
     */
    public Entity getEntity() {

        return entity;
    }

    @Override
    public Vector getPosition() {

        return entity.getLocation().clone().subtract(referenceSystem.getOrigin()).toVector();
    }

    @Override
    public void setPosition(Vector position) {

        Location oldLocation = entity.getLocation();
        entity.teleport(position.toLocation(oldLocation.getWorld(), oldLocation.getYaw(), oldLocation.getPitch()).add(referenceSystem.getOrigin()));
    }

    @Override
    public Vector getVelocity() {

        return entity.getVelocity().clone();
    }

    @Override
    public void setVelocity(Vector velocity) {

        entity.setVelocity(velocity.clone());
    }

    @Override
    public RealEntityObject clone() {

        return (RealEntityObject) super.clone();
    }

}
