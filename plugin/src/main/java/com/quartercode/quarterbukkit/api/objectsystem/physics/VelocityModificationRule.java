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
import com.quartercode.quarterbukkit.api.objectsystem.ModificationRule;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;

/**
 * A shortcut for {@link ModificationRule}s that modify the velocity of a {@link PhysicsObject} by applying the result of a velocity vector {@link Modifier} onto it.
 * This class just sets the {@link VelocityModificationApplier} as modification applier and removes the need for some manual generic arguments.
 * Note that the {@link VelocityModifier} interface can be used as a shortcut for velocity vector modifiers added to this rule.
 * See {@link ModificationRule} for more information on modification rules.
 * 
 * @param <O> The type of object the velocity modification rule can modify. This must extend {@link PhysicsObject}.
 * @see ModificationRule
 * @see VelocityModificationApplier
 * @see VelocityModifier
 */
public class VelocityModificationRule<O extends PhysicsObject> extends ModificationRule<O, Vector> {

    /**
     * Creates a new velocity modification rule that modifies the velocity {@link Vector} of {@link PhysicsObject}s of the given type.
     * 
     * @param objectType The type of physics object that can be modified by the rule. This must extend {@link PhysicsObject}.
     */
    public VelocityModificationRule(Class<O> objectType) {

        super(objectType, new VelocityModificationApplier<O>());
    }

    /**
     * Creates a new velocity modification rule that modifies the velocity {@link Vector} of {@link PhysicsObject}s of the given type using
     * the given velocity vector {@link Modifier}.
     * Note that the {@link VelocityModifier} interface can be used as a shortcut for the velocity vector modifier.
     * 
     * @param objectType The type of physics object that can be modified by the rule. This must extend {@link PhysicsObject}.
     * @param modifier The modifier which is responsible for calculating the changes to the object's velocity vector.
     */
    public VelocityModificationRule(Class<O> objectType, Modifier<O, Vector> modifier) {

        super(objectType, new VelocityModificationApplier<O>(), modifier);
    }

}
