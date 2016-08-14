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

package com.quartercode.quarterbukkit.api.objectsystem;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A trait provides some additional variables and methods that can be attached to a {@link BaseObject}.
 * Usually, that mechanism is used to specialize a certain object by adding more information to it.
 * For example, the physics trait adds a position and a velocity to the object so that it can be located and take part in the physics simulation.
 * On the other hand, the particle trait adds rendering information which tells the rendering system that particles should be rendered at the position of the object.
 * Of course, you can combine as many traits as possible.<br>
 * <br>
 * If one trait depends on another (for example, the particle trait depends on the physics trait so that the object has a position), you can force the fulfillment of those dependencies by declaring a
 * {@link TraitDependencies} annotation.
 * If a user then tries to add a trait to an object without caring about one of its dependencies, the object shouts back at him by throwing an exception.
 * Therefore, it's recommended to always specify all required dependencies so that wrong configurations fail fast instead of silently.
 *
 * @see BaseObject
 * @see TraitDependencies
 */
public abstract class Trait {

    private BaseObject object;

    /**
     * Returns the {@link BaseObject} this trait is part of, or {@code null} if this trait hasn't been {@link BaseObject#add(Trait...) added} to any object yet.
     *
     * @return The object that is specialized by this trait.
     */
    public BaseObject getObject() {

        return object;
    }

    /**
     * Tells the trait that it has just been added to the given {@link BaseObject}.
     * If you provide {@code null} as an argument, it tells the trait that it has just been removed from its previous base object.
     * <b>Note that this is an internal method and should not be used as an API function.</b>
     *
     * @param object The base object this trait is now part of.
     */
    protected void setObject(BaseObject object) {

        this.object = object;
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
