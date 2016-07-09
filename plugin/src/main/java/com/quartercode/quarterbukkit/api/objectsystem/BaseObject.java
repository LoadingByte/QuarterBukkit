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

/**
 * The base interface for all objects that can be put into an {@link ActiveObjectSystem}.
 * It defines some basic object properties and a public {@link #clone()} method.
 *
 * @see ActiveObjectSystem
 */
public interface BaseObject extends Cloneable {

    /**
     * Returns the amount of ticks the object has existed inside its {@link ActiveObjectSystem}.
     * Note that this is 0 the first time the object is updated and {@link ModificationRule}s are called on it.
     * After that, this value increases by 1 each time the object is updated.
     *
     * @return The current lifetime of the object.
     */
    public int getLifetime();

    /**
     * Increments the current lifetime of the object.
     * Note that this is an internal method and should not be used as an api function.
     */
    public void incrementLifetime();

    /**
     * Returns the amount of updates after which the object expires and is removed from its {@link ActiveObjectSystem}.
     * For example, if the expiration time is 5 the object is removed after 5 updates/ticks/{@link ModificationRule} calls on the object.
     * The expiration time -1 means that the object never expires.
     *
     * @return The expiration time of the object.
     */
    public int getExpirationTime();

    /**
     * Sets the amount of updates after which the object expires and is removed from its {@link ActiveObjectSystem}.
     * For example, if the expiration time is set 5 the object is removed after 5 updates/ticks/{@link ModificationRule} calls on the object.
     * Note that this time can be set to a value smaller than {@link #getLifetime()}, in which case the object is removed immediately.
     * The expiration time -1 means that the object never expires.
     *
     * @param expirationTime The new expiration time of the object; -1 for no expiration.
     */
    public void setExpirationTime(int expirationTime);

    /**
     * Creates a <b>deep</b> clone of the object that exactly represents the cloned object.
     * Note that this should use the system clone method and then perform the deep cloning on the resulting object
     * in order to add easy inheritance support.
     *
     * @return A deep clone of the object.
     */
    public BaseObject clone();

}
