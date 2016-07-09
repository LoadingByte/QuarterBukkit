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
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;
import com.quartercode.quarterbukkit.api.objectsystem.physics.VelocityModifier;

/**
 * A {@link VelocityModifier} that always returns the same velocity modification {@link Vector} for any {@link PhysicsObject}.
 * It can be compared with a static force that pulls every object into the same direction.
 * Note that you can limit the velocity modification to a specific area using the {@link ShapedModifier} wrapper.
 *
 * @param <O> The type of object the static velocity modifier accepts. This must extend {@link PhysicsObject}.
 * @see PhysicsObject
 * @see ShapedModifier
 */
public class StaticVelocityModifier<O extends PhysicsObject> implements VelocityModifier<O> {

    private Vector modification;

    /**
     * Creates a new static velocity modifier that always returns the given velocity modification {@link Vector} for any {@link PhysicsObject}.
     *
     * @param modification The static velocity modification vector that is returned for every object.
     */
    public StaticVelocityModifier(Vector modification) {

        setModification(modification);
    }

    /**
     * Returns the velocity modification {@link Vector} that is returned for any {@link PhysicsObject}.
     *
     * @return The static velocity modification vector.
     */
    public Vector getModification() {

        return modification.clone();
    }

    /**
     * Returns the velocity modification {@link Vector} that is returned for any {@link PhysicsObject}.
     *
     * @param modification The static velocity modification vector.
     * @return This object.
     */
    public StaticVelocityModifier<O> setModification(Vector modification) {

        Validate.notNull(modification, "Modification vector of static velocity modifier cannot be null");
        this.modification = modification.clone();
        return this;
    }

    @Override
    public Vector getModification(O object) {

        return modification.clone();
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
