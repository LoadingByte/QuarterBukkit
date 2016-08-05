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

/**
 * A standalone implementation of {@link PhysicsTrait} that just stores the data in member variables and applies the velocity every update.<br>
 * <br>
 * <b>Trait dependencies:</b> <i>none</i>
 *
 * @see PhysicsTrait
 */
public class StandalonePhysicsTrait extends PhysicsTrait {

    private Vector position;
    private Vector velocity;

    /**
     * Creates a new standalone physics trait for an object that is located at the origin of its {@link ActiveObjectSystem} and doesn't move initially.
     */
    public StandalonePhysicsTrait() {

        this(new Vector());
    }

    /**
     * Creates a new standalone physics trait for an object that is located at the given position and doesn't move initially.
     *
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public StandalonePhysicsTrait(Vector position) {

        this(position, new Vector());
    }

    /**
     * Creates a new standalone physics trait for an object that is located at the given position and initially moves with the given velocity.
     *
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public StandalonePhysicsTrait(Vector position, Vector velocity) {

        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public Vector getPosition() {

        return position.clone();
    }

    @Override
    public StandalonePhysicsTrait setPosition(Vector position) {

        this.position = position.clone();
        return this;
    }

    @Override
    public Vector getVelocity() {

        return velocity.clone();
    }

    @Override
    public StandalonePhysicsTrait setVelocity(Vector velocity) {

        this.velocity = velocity.clone();
        return this;
    }

}
