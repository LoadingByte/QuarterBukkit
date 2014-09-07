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
 * A modifier is responsible for calculating a modification object that represents the modification of a certain object property.
 * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the object.
 * See {@link ModificationRule} for more information on the role of modifiers.
 * Note that modifiers can be chained together using the {@link ModifierWrapper} class.
 * 
 * @param <O> The type of object the modifier can use to calculate a modification object. This must extend {@link BaseObject}.
 * @param <M> The type of the modification object that is returned by the modifier.
 *        For example, a velocity modifier would use a vector as modification object.
 * @see ModificationRule
 */
public interface Modifier<O extends BaseObject, M> {

    /**
     * Calculates the modification object for the given object inside an object system.
     * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the object here.
     * 
     * @param object The object the modifier should calculate a modification object for.
     * @return The calculated modification object.
     */
    public M getModification(O object);

}
