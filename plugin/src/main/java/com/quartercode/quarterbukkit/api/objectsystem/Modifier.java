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
 * A modifier is responsible for calculating a modification object that represents the modification of a certain {@link Trait} property, with the trait in turn of course being part of an
 * {@link BaseObject object} inside an {@link ActiveObjectSystem}.
 * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the physics trait.
 * See {@link ModificationRule} for more information on the role of modifiers.
 * Note that modifiers can be chained together using the {@link ModifierWrapper} class.
 *
 * @param <T> The type of trait the modifier can use to calculate a modification object. This must extend {@link Trait}.
 * @param <M> The type of the modification object that is returned by the modifier.
 *        For example, a velocity modifier would use a vector as modification object.
 * @see Trait
 * @see ModificationRule
 */
public interface Modifier<T extends Trait, M> {

    /**
     * Calculates the modification object for the given {@link Trait}.
     * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the physics trait here.
     * <b>It is vital to understand that a modifier is in no case allowed to actually perform some changes on the physics trait or even remove itself or its parent {@link BaseObject object}!</b>
     * It should only construct and return a modification object. It's the {@link ModificationApplier}s responsibility to execute the changes.
     * Otherwise, the whole chaining concept wouldn't work out.<br>
     * <br>
     * Note the important variable {@code dt} which basically informs you about the currently used time resolution.
     * For example, our velocity modifier would need to consider it in order to correctly model acceleration and apply the correct velocity change for the last time interval {@code dt}.
     *
     * @param dt The amount of milliseconds which have elapsed since the last update of the object system this modifier is part of.
     * @param trait The trait the modifier should calculate a modification object for.
     * @return The calculated modification object.
     */
    public M getModification(long dt, T trait);

}
