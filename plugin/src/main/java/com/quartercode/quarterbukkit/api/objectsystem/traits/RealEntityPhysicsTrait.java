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

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;

/**
 * A {@link PhysicsTrait} that uses the position and velocity of a real Bukkit {@link Entity} and thereby makes it controllable for an {@link ActiveObjectSystem}.
 * All calls to the physics methods are forwarded to the equivalent methods on the entity object.<br>
 * <br>
 * <b>Trait dependencies:</b> <i>none</i>
 *
 * @see PhysicsTrait
 * @see Entity
 */
public class RealEntityPhysicsTrait extends PhysicsTrait {

    private final Entity entity;

    /**
     * Creates a new real entity trait for the given {@link Entity}.
     *
     * @param entity The entity that should be made controllable for the active system.
     */
    public RealEntityPhysicsTrait(Entity entity) {

        Validate.notNull(entity, "Cannot use null entity for real entity trait");

        this.entity = entity;
    }

    /**
     * Returns the Bukkit {@link Entity} that is made controllable through this trait.
     * All calls to the physics methods are forwarded to that entity.
     *
     * @return The Bukkit entity that is represented by the trait and thus the entire object.
     */
    public Entity getEntity() {

        return entity;
    }

    @Override
    public Vector getPosition() {

        return entity.getLocation().clone().subtract(getObject().getSystem().getOrigin()).toVector();
    }

    @Override
    public RealEntityPhysicsTrait setPosition(Vector position) {

        Location oldLocation = entity.getLocation();
        entity.teleport(position.toLocation(oldLocation.getWorld(), oldLocation.getYaw(), oldLocation.getPitch()).add(getObject().getSystem().getOrigin()));

        return this;
    }

    @Override
    public Vector getVelocity() {

        return entity.getVelocity().clone();
    }

    @Override
    public RealEntityPhysicsTrait setVelocity(Vector velocity) {

        entity.setVelocity(velocity.clone());
        return this;
    }

}
