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

import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;

/**
 * By adding this {@link Trait} to an {@link BaseObject object}, you can make it expire after a defined amount of milliseconds without adding custom code.
 * That just means that the object is removed from the {@link ActiveObjectSystem system} as soon as its {@link BaseObject#getLifetime() lifetime} exceeds the defined expiration time.<br>
 * <br>
 * <b>Trait dependencies:</b> <i>none</i>
 *
 * @see Trait
 */
public class ExpirationTrait extends Trait {

    private long expirationTime;

    /**
     * Creates a new expiration trait for an object that expires and is removed from its {@link ActiveObjectSystem} after the given amount of milliseconds.
     * For example, if the expiration time is {@code 2000}, the object is removed after 2 seconds.
     * Note that this time can be set to a value smaller than the object's {@link BaseObject#getLifetime() lifetime}, in which case the object will be removed instantly during the next update.
     *
     * @param expirationTime The expiration time of the object in milliseconds.
     */
    public ExpirationTrait(long expirationTime) {

        this.expirationTime = expirationTime;
    }

    /**
     * Returns the amount of milliseconds after which the object expires and is removed from its {@link ActiveObjectSystem}.
     * For example, if the expiration time is {@code 2000}, the object is removed after 2 seconds.
     *
     * @return The expiration time of the object in milliseconds.
     */
    public long getExpirationTime() {

        return expirationTime;
    }

    /**
     * Sets the amount of milliseconds after which the object expires and is removed from its {@link ActiveObjectSystem}.
     * For example, if the expiration time is {@code 2000}, the object is removed after 2 seconds.
     * Note that this time can be set to a value smaller than the object's {@link BaseObject#getLifetime() lifetime}, in which case the object will be removed instantly during the next update.
     *
     * @param expirationTime The new expiration time of the object in milliseconds.
     * @return This object.
     */
    public ExpirationTrait setExpirationTime(long expirationTime) {

        this.expirationTime = expirationTime;
        return this;
    }

}
