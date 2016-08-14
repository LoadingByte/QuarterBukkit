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
 * A behavior wrapper wraps around another {@link Behavior} and implements some kind of intermediate behavior which might or might not call the wrapped behavior one or more times.
 * That way, different behaviors can be combined together, creating chains of behaviors:
 *
 * <pre>{@code
 * systemDef.addBehaviors(new InitializationBehavior((dt, activeSystem) -> ...));
 * }</pre>
 *
 * In the example above, the behavior wrapper probably only calls its wrapped behavior once when the object system is first started, and then never again, resulting in some kind of
 * "initialization behavior".
 *
 * @param <B> The type of Java object this behavior can be attached to.
 *        The wrapped behavior needs to either have this body as well or a superclass of this body in order for the two to be compatible.
 * @see Behavior
 */
public abstract class BehaviorWrapper<B> implements Behavior<B> {

    private Behavior<? super B> wrappedBehavior;

    /**
     * Creates a new behavior wrapper and immediately sets the wrapped {@link Behavior}.
     *
     * @param wrappedBehavior The initial wrapped behavior.
     */
    public BehaviorWrapper(Behavior<? super B> wrappedBehavior) {

        setWrappedBehavior(wrappedBehavior);
    }

    /**
     * Returns the wrapped {@link Behavior} that should be called during the {@link #behave(long, Object)} call in order to create a behavior chain.
     *
     * @return The wrapped behavior. Cannot be {@code null}.
     */
    public Behavior<? super B> getWrappedBehavior() {

        return wrappedBehavior;
    }

    /**
     * Sets the wrapped {@link Behavior} that should be called during the {@link #behave(long, Object)} call in order to create a behavior chain.
     *
     * @param wrappedBehavior The new wrapped behavior. Cannot be {@code null}.
     */
    public void setWrappedBehavior(Behavior<? super B> wrappedBehavior) {

        Validate.notNull(wrappedBehavior, "Wrapped behavior of this behavior wrapper cannot be null");
        this.wrappedBehavior = wrappedBehavior;
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
