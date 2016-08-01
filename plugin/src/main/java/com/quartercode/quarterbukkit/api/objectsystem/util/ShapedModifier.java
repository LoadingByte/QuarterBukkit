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
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;
import com.quartercode.quarterbukkit.api.objectsystem.ModifierWrapper;
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;
import com.quartercode.quarterbukkit.api.shape.Shape;

/**
 * A {@link Modifier} wrapper that cancels the rest of the modifier chain if the given {@link PhysicsObject} isn't located inside a given {@link Shape}.
 * Different null values which should be returned when the chain is cancelled apart from {@code null} can be defined.
 *
 * @param <O> The type of object the shaped modifier accepts. This must extend {@link PhysicsObject}.
 * @param <M> The type of the modification object that is returned by the shaped modifier and the rest of the modifier chain.
 *        Note that the null value must be an instance of this type.
 * @see Shape
 */
public class ShapedModifier<O extends PhysicsObject, M> extends ModifierWrapper<O, M> {

    private Shape   shape;
    private boolean inversed;
    private M       nullObject;

    /**
     * Creates a new shaped modifier that checks whether each {@link PhysicsObject} is located inside the given {@link Shape} for the modifier chain to proceed.
     * In case of a canceling of the chain, a {@code null} reference is returned.
     *
     * @param shape The shape each object must be located in for the check to succeed.
     *        Note that all coordinates stored by the shape are always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param wrapped The wrapped {@link Modifier} that is only called when the check succeeds.
     */
    public ShapedModifier(Shape shape, Modifier<? super O, M> wrapped) {

        this(shape, false, null, wrapped);
    }

    /**
     * Creates a new shaped modifier that checks whether each {@link PhysicsObject} is located either inside or outside the given {@link Shape} for the modifier chain to proceed.
     * In case of a canceling of the chain, a {@code null} reference is returned.
     *
     * @param shape The shape each object must be located in for the check to succeed.
     *        Note that all coordinates stored by the shape are always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param inversed Whether the shape modifier should check that each {@link PhysicsObject} is <b>outside</b> the provided shape instead of inside.
     * @param wrapped The wrapped {@link Modifier} that is only called when the check succeeds.
     */
    public ShapedModifier(Shape shape, boolean inversed, Modifier<? super O, M> wrapped) {

        this(shape, inversed, null, wrapped);
    }

    /**
     * Creates a new shaped modifier that checks whether each {@link PhysicsObject} is located inside the given {@link Shape} for the modifier chain to proceed.
     * In case of a canceling of the chain, the given null object is returned instead of a plain {@code null} reference.
     *
     * @param shape The shape each object must be located in for the check to succeed.
     *        Note that all coordinates stored by the shape are always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param nullObject The null object that is returned when the modifier chain is cancelled.
     * @param wrapped The wrapped {@link Modifier} that is only called when the check succeeds.
     */
    public ShapedModifier(Shape shape, M nullObject, Modifier<? super O, M> wrapped) {

        this(shape, false, nullObject, wrapped);
    }

    /**
     * Creates a new shaped modifier that checks whether each {@link PhysicsObject} is located either inside or outside the given {@link Shape} for the modifier chain to proceed.
     * In case of a canceling of the chain, the given null object is returned instead of a plain {@code null} reference.
     *
     * @param shape The shape each object must be located in for the check to succeed.
     *        Note that all coordinates stored by the shape are always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param inversed Whether the shape modifier should check that each {@link PhysicsObject} is <b>outside</b> the provided shape instead of inside.
     * @param nullObject The null object that is returned when the modifier chain is cancelled.
     * @param wrapped The wrapped {@link Modifier} that is only called when the check succeeds.
     */
    public ShapedModifier(Shape shape, boolean inversed, M nullObject, Modifier<? super O, M> wrapped) {

        super(false, wrapped);

        setShape(shape);
        this.inversed = inversed;
        this.nullObject = nullObject;
    }

    /**
     * Returns the {@link Shape} each {@link PhysicsObject} must be located in for the modifier chain to proceed.
     * Note that all coordinates stored by the shape are always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     *
     * @return The shape for the intersection check.
     */
    public Shape getShape() {

        return shape;
    }

    /**
     * Sets the {@link Shape} each {@link PhysicsObject} must be located in for the modifier chain to proceed.
     * Note that all coordinates stored by the shape are always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     *
     * @param shape The new shape for the intersection check.
     * @return This object.
     */
    public ShapedModifier<O, M> setShape(Shape shape) {

        Validate.notNull(shape, "Shape of shaped modifier cannot be null");
        this.shape = shape;
        return this;
    }

    /**
     * Returns whether the shape modifier checks that each {@link PhysicsObject} is <b>outside</b> the provided {@link Shape}.
     *
     * @return Whether the check mode is inversed.
     */
    public boolean isInversed() {

        return inversed;
    }

    /**
     * Sets whether the shape modifier checks that each {@link PhysicsObject} is <b>outside</b> the provided {@link Shape}.
     *
     * @param inversed Whether the check mode should be inversed.
     * @return This object.
     */
    public ShapedModifier<O, M> setInversed(boolean inversed) {

        this.inversed = inversed;
        return this;
    }

    /**
     * Returns the null object that is returned when the modifier chain is cancelled.
     * By default, the null object is a {@code null} reference.
     * Note that the null object is not cloned when it is returned, so be careful with mutable objects.
     *
     * @return The null return object for canceling the chain.
     */
    public M getNullObject() {

        return nullObject;
    }

    /**
     * Sets the null object that is returned when the modifier chain is cancelled.
     * By default, the null object is a {@code null} reference.
     * Note that the null object is not cloned when it is returned, so be careful with mutable objects.
     *
     * @param nullObject The new null return object for canceling the chain.
     * @return This object.
     */
    public ShapedModifier<O, M> setNullObject(M nullObject) {

        this.nullObject = nullObject;
        return this;
    }

    @Override
    public M getModification(long dt, O object) {

        boolean intersectsShape = shape.intersects(object.getPosition());

        if (intersectsShape == !inversed) {
            return getWrapped().getModification(dt, object);
        } else {
            return nullObject;
        }
    }

}
