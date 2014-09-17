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
import com.quartercode.quarterbukkit.api.objectsystem.ModificationApplier;

/**
 * A {@link ModificationApplier} that applies a velocity modification {@link Vector} produced by a {@link VelocityModifier} to a {@link PhysicsObject}.
 * 
 * @param <O> The type of object the velocity modification applier can apply a velocity modification vector to. This must extend {@link PhysicsObject}.
 *        Note that this type parameter is somewhat unnecessary since the velocity applier accepts all kinds of physics objects.
 *        However, the type system requires it.
 * @see ModificationApplier
 * @see VelocityModificationRule
 * @see VelocityModifier
 */
public class VelocityModificationApplier<O extends PhysicsObject> implements ModificationApplier<O, Vector> {

    private static final Vector ZERO_VECTOR = new Vector();

    @Override
    public void applyModification(PhysicsObject object, Vector modification) {

        if (modification != null && !modification.equals(ZERO_VECTOR)) {
            object.setVelocity(object.getVelocity().add(modification));
        }
    }

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return getClass().isInstance(obj);
    }

    @Override
    public String toString() {

        return getClass().getSimpleName();
    }

}
