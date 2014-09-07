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
 * A modification applier is responsible for applying a modification object calculated by a {@link Modifier} to a property of an object inside an object system.
 * For example, a velocity modification applier would add the velocity modification vector calculated by a modifier to the current velocity vector of the object.
 * See {@link ModificationRule} for more information on the role of modification appliers.
 * 
 * @param <O> The type of object the modification applier can apply modifications to. This must extend {@link BaseObject}.
 * @param <M> The type of the modification object that is applied to objects by the modification applier.
 *        For example, a velocity modification applier would consume a vector as modification object.
 * @see ModificationRule
 */
public interface ModificationApplier<O extends BaseObject, M> {

    /**
     * Applies the given modification object to the given system object.
     * For example, a velocity modification applier would add a given velocity modification vector to the current velocity vector of the object.
     * 
     * @param object The object the modification should be applied to.
     * @param modification The modification that should be applied to the given object.
     */
    public void applyModification(O object, M modification);

}
