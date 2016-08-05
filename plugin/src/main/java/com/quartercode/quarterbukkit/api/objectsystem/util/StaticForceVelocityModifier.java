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

package com.quartercode.quarterbukkit.api.objectsystem.util;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;
import com.quartercode.quarterbukkit.api.objectsystem.mods.PhysicsObject;

/**
 * A velocity {@link Modifier} that always returns the same velocity modification {@link Vector} for any {@link PhysicsObject}.
 * It can be compared with a static force that pulls every object into the same direction with the same acceleration.
 * Note that you can limit the velocity modification to a specific area using the {@link ShapedModifier} wrapper.
 *
 * @see PhysicsObject
 * @see ShapedModifier
 */
public class StaticForceVelocityModifier implements Modifier<PhysicsObject, Vector> {

    private Vector acceleration;

    /**
     * Creates a new static velocity modifier that always returns the given acceleration {@link Vector} for any {@link PhysicsObject}.
     * The unit of that acceleration vector is m/s<sup>2</sup>. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s<sup>2</sup>.
     *
     * @param acceleration The static acceleration vector in m/s<sup>2</sup> that is returned for every object.
     */
    public StaticForceVelocityModifier(Vector acceleration) {

        setAcceleration(acceleration);
    }

    /**
     * Returns the velocity modification {@link Vector} that is returned for any {@link PhysicsObject}.
     * The unit of that acceleration vector is m/s<sup>2</sup>. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s<sup>2</sup>.
     *
     * @return The static acceleration vector in m/s<sup>2</sup>.
     */
    public Vector getAcceleration() {

        return acceleration.clone();
    }

    /**
     * Sets the velocity modification {@link Vector} that is returned for any {@link PhysicsObject}.
     * The unit of that acceleration vector is m/s<sup>2</sup>. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s<sup>2</sup>.
     *
     * @param acceleration The new static acceleration vector in m/s<sup>2</sup>.
     * @return This object.
     */
    public StaticForceVelocityModifier setAcceleration(Vector acceleration) {

        Validate.notNull(acceleration, "Acceleration vector of static velocity modifier cannot be null");
        this.acceleration = acceleration.clone();
        return this;
    }

    @Override
    public Vector getModification(long dt, PhysicsObject object) {

        // Consider dt
        return acceleration.clone().multiply(dt / 1000d);
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
