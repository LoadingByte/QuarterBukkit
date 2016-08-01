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

import org.apache.commons.lang.Validate;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;
import com.quartercode.quarterbukkit.api.objectsystem.ModifierWrapper;

/**
 * A {@link Modifier} wrapper that changes the length of the modification {@link Vector} returned by a wrapped modifier based on the object's weight returned by a {@link Weighter}.
 * For example, you could use the modifier to manipulate an acceleration value depending on the distance to a certain point by applying the modifier to a
 * velocity modification vector.
 *
 * @param <O> The type of object the weighted vector length modifier accepts. This must extend {@link BaseObject}.
 * @see Weighter
 * @see Vector
 */
public class WeightedVectorLengthModifier<O extends BaseObject> extends ModifierWrapper<O, Vector> {

    private Weighter<? super O> weighter;

    /**
     * Creates a new weighted vector length modifier that changes the length of the modification {@link Vector} returned by the given wrapped {@link Modifier} using the given {@link Weighter}.
     *
     * @param weighter The weighter that is used to calculate the {@link Vector} length manipulation factor.
     *        The weight returned by the weighter is multiplied with the vector returned by the given wrapped modifier.
     * @param wrapped The wrapped modifier whose returned vectors are modified.
     *        Obviously, it must return a vector as modification object.
     */
    public WeightedVectorLengthModifier(Weighter<? super O> weighter, Modifier<? super O, Vector> wrapped) {

        super(false, wrapped);

        setWeighter(weighter);
    }

    /**
     * Returns the {@link Weighter} that is used to calculate the {@link Vector} length manipulation factor.
     * The weight returned by the weighter is multiplied with the vector returned by the wrapped {@link Modifier}.
     *
     * @return The weighter used to calculate the manipulation factor.
     */
    public Weighter<? super O> getWeighter() {

        return weighter;
    }

    /**
     * Sets the {@link Weighter} that is used to calculate the {@link Vector} length manipulation factor.
     * The weight returned by the weighter is multiplied with the vector returned by the wrapped {@link Modifier}.
     *
     * @param weighter The new weighter to use for calculating the manipulation factor.
     * @return This object.
     */
    public WeightedVectorLengthModifier<O> setWeighter(Weighter<? super O> weighter) {

        Validate.notNull(weighter, "Weighter of weighted vector length modifier cannot be null");
        this.weighter = weighter;
        return this;
    }

    /*
     * This method does not need to consider dt.
     * Proof: factor * vec1 + factor * vec2 = factor * (vec1 + vec2)
     * It's irrelevant whether we multiply two small modifications or the combined modification with the factor.
     * We can therefore safely ignore dt, which just tells us whether it's a small or a large modification.
     */
    @Override
    public Vector getModification(long dt, O object) {

        Vector modification = getWrapped().getModification(dt, object);
        return modification == null ? null : modification.multiply(weighter.getWeight(object));
    }

}
