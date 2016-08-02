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
     * Returns the amount of milliseconds the object has existed inside its {@link ActiveObjectSystem}.
     * Note that this is guaranteed to be 0 the first time the object is updated and {@link ModificationRule}s are called on it.
     * After that, this value increases by the elapsed time {@code dt} each time the object is updated.
     *
     * @return The current lifetime of the object in milliseconds.
     */
    public long getLifetime();

    /**
     * Increments the current lifetime of the object by the given number of milliseconds.
     * Note that this is an internal method and should not be used as an API function.
     *
     * @param dt The number of milliseconds that should be added to the object's lifetime.
     */
    public void incrementLifetime(long dt);

    /**
     * Returns the amount of milliseconds after which the object expires and is removed from its {@link ActiveObjectSystem}.
     * For example, if the expiration time is {@code 2000} the object is removed after 2 seconds.
     * The expiration time {@code -1} means that the object never expires.
     *
     * @return The expiration time of the object in milliseconds.
     */
    public long getExpirationTime();

    /**
     * Sets the amount of milliseconds after which the object expires and is removed from its {@link ActiveObjectSystem}.
     * For example, if the expiration time is {@code 2000} the object is removed after 2 seconds.
     * Note that this time can be set to a value smaller than {@link #getLifetime()}, in which case the object will be instantly removed in the next update.
     * The expiration time {@code -1} means that the object never expires.
     *
     * @param expirationTime The new expiration time of the object in milliseconds; {@code -1} for no expiration.
     */
    public void setExpirationTime(long expirationTime);

    /**
     * Returns the {@link ActiveObjectSystem} this object is part of, or {@code null} if this object hasn't been {@link ActiveObjectSystem#addObjects(BaseObject...) added} to any active system yet.
     * Please use the returned reference with caution and try to stick to the main design principles of the object system API.
     * If you ignore those, you quickly loose many of the advantages this API provides you with.
     * It's especially important to not modify the returned active system when your not allowed to (e.g. because you're in a {@link Modifier}).
     *
     * @return The active object system that contains and simulates this object.
     */
    public ActiveObjectSystem getSystem();

    /**
     * Tells the object that it has just been added to the given {@link ActiveObjectSystem}.
     * <b>Note that this is an internal method and should not be used as an API function.</b>
     * Also note that once you called this method once, you can never call it again on the same object.
     *
     * @param system The active object system this object is now part of.
     */
    public void setSystem(ActiveObjectSystem system);

    /**
     * Creates a <b>deep</b> clone of the object that exactly represents the cloned object.
     * Note that this should use the system clone method and then perform the deep cloning on the resulting object
     * in order to add easy inheritance support.
     *
     * @return A deep clone of the object.
     */
    public BaseObject clone();

}
