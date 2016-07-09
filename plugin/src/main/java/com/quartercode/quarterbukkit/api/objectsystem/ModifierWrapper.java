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

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A modifier wrapper wraps around another {@link Modifier} and manipulates the modification objects produced by that modifier.
 * That way, different modifiers can be combined together creating chains of modifiers.
 * The last modifier in the chain produces a modification object and all subsequent modifiers manipulate that object further.
 * For example, a manipulation modifier could adjust the velocity modification of a velocity modifier depending on the location of the system object.
 *
 * @param <O> The type of object the modifier wrapper can use to calculate a modification object. This must extend {@link BaseObject}.
 * @param <M> The type of the modification object that is returned by the modifier wrapper and the wrapped modifier.
 *        For example, a velocity modifier wrapper would use a vector as modification object.
 * @see Modifier
 */
public abstract class ModifierWrapper<O extends BaseObject, M> implements Modifier<O, M> {

    private final boolean                    nullAllowed;
    private Modifier<? super O, ? extends M> wrapped;

    /**
     * Creates a new modification wrapper that allows a {@code null} reference as wrapped modifier.
     * That means that the wrapper might have no wrapped modifier in some cases.
     */
    public ModifierWrapper() {

        nullAllowed = true;
    }

    /**
     * Creates a new modification wrapper and immediately sets the wrapped {@link Modifier}.
     * Also sets whether the wrapper is allowed to have a {@code null} reference as wrapped modifier.
     * If that's the case, the wrapper might have no wrapped modifier in some cases.
     *
     * @param nullAllowed Whether a non-null wrapped modifier must not be provided in all cases.
     * @param wrapped The initial wrapped modifier.
     *        If {@code nullAllowed} is {@code false}, this cannot be {@code null}.
     */
    public ModifierWrapper(boolean nullAllowed, Modifier<? super O, ? extends M> wrapped) {

        this.nullAllowed = nullAllowed;
        setWrapped(wrapped);
    }

    /**
     * Returns the wrapped {@link Modifier} that should be called during the {@link #getModification(BaseObject)} call in order to create a modifier chain.
     * Note that this is not allowed to be {@code null} if {@code nullAllowed} was set to {@code false} on construction.
     *
     * @return The wrapped modifier.
     */
    public Modifier<? super O, ? extends M> getWrapped() {

        return wrapped;
    }

    /**
     * Sets the wrapped {@link Modifier} that should be called during the {@link #getModification(BaseObject)} call in order to create a modifier chain.
     * Note that this is not allowed to be {@code null} if {@code nullAllowed} was set to {@code false} on construction.
     *
     * @param wrapped The new wrapped modifier.
     */
    public void setWrapped(Modifier<? super O, ? extends M> wrapped) {

        if (!nullAllowed) {
            Validate.notNull(wrapped, "Wrapped modifer of this modifier wrapper cannot be null");
        }

        this.wrapped = wrapped;
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
