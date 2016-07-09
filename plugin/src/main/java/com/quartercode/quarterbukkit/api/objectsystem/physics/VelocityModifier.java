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

package com.quartercode.quarterbukkit.api.objectsystem.physics;

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;

/**
 * A shortcut for {@link Modifier}s that modify the velocity of a {@link PhysicsObject}.
 * That velocity modification can be compared to the effect of a force.
 * See {@link Modifier} for more information on modifiers.
 *
 * @param <O> The type of object the velocity modifier can use to calculate a velocity modification {@link Vector}.
 *        This must extend {@link PhysicsObject}.
 * @see Modifier
 * @see VelocityModificationRule
 * @see VelocityModificationApplier
 */
public interface VelocityModifier<O extends PhysicsObject> extends Modifier<O, Vector> {

    /**
     * Calculates the velocity modification {@link Vector} for the given {@link PhysicsObject}.
     * A {@link VelocityModificationApplier} then adjusts the actual velocity vector of the object.
     *
     * @param object The object the velocity modifier should calculate a velocity modification vector for.
     * @return The calculated velocity modification vector.
     */
    @Override
    public Vector getModification(O object);

}
