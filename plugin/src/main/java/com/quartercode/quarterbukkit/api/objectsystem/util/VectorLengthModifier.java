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

package com.quartercode.quarterbukkit.api.objectsystem.util;

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;
import com.quartercode.quarterbukkit.api.objectsystem.ModifierWrapper;

/**
 * A {@link Modifier} wrapper that changes the length of the modification {@link Vector} returned by a wrapped modifier by a defined factor.
 * For example, you could use the modifier to manipulate an acceleration value by applying the modifier to a velocity modification vector.
 *
 * @param <O> The type of object the vector length modifier accepts. This must extend {@link BaseObject}.
 * @see Vector
 */
public class VectorLengthModifier<O extends BaseObject> extends ModifierWrapper<O, Vector> {

    private float factor;

    /**
     * Creates a new vector length modifier that changes the length of the modification {@link Vector} returned by the given wrapped {@link Modifier} by the given factor.
     *
     * @param factor The factor the modification vectors returned by the wrapped modifier are multiplied with.
     * @param wrapped The wrapped modifier whose returned vectors are modified.
     *        Obviously, it must return a vector as modification object.
     */
    public VectorLengthModifier(float factor, Modifier<? super O, Vector> wrapped) {

        super(false, wrapped);

        this.factor = factor;
    }

    /**
     * Returns the factor the modification {@link Vector} returned by the wrapped {@link Modifier} should be multiplied with.
     *
     * @return The vector length factor.
     */
    public float getFactor() {

        return factor;
    }

    /**
     * Sets the factor the modification {@link Vector} returned by the wrapped {@link Modifier} should be multiplied with.
     *
     * @param factor The new vector length factor.
     * @return This object.
     */
    public VectorLengthModifier<O> setFactor(float factor) {

        this.factor = factor;
        return this;
    }

    @Override
    public Vector getModification(O object) {

        Vector modification = getWrapped().getModification(object);
        return modification == null ? null : modification.multiply(factor);
    }

}
