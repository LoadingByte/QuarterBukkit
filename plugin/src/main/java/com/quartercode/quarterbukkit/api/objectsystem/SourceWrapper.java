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

import java.util.Random;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.plugin.Plugin;

/**
 * A source wrapper wraps around another {@link Source} and implements some kind of intermediate behavior which might or might not call the wrapped source one or more times.
 * That way, different source behaviors can be combined together creating chains of sources.
 * For example, a source wrapper could only call its wrapped source once when the object system is first started, resulting in some kind of "initialization source".
 *
 * @see Source
 */
public abstract class SourceWrapper implements Source {

    private final boolean nullAllowed;
    private Source        wrapped;

    /**
     * Creates a new source wrapper that allows a {@code null} reference as wrapped source.
     * That means that the wrapper might have no wrapped source in some cases.
     */
    public SourceWrapper() {

        nullAllowed = true;
    }

    /**
     * Creates a new source wrapper and immediately sets the wrapped {@link Source}.
     * Also sets whether the wrapper is allowed to have a {@code null} reference as wrapped source.
     * If that's the case, the wrapper might have no wrapped source in some cases.
     *
     * @param nullAllowed Whether a non-null wrapped source must not be provided at all times.
     * @param wrapped The initial wrapped source.
     *        If {@code nullAllowed} is {@code false}, this cannot be {@code null}.
     */
    public SourceWrapper(boolean nullAllowed, Source wrapped) {

        this.nullAllowed = nullAllowed;
        setWrapped(wrapped);
    }

    /**
     * Returns the wrapped {@link Source} that should be called during the {@link #update(Plugin, ActiveObjectSystem, long, Random)} call in order to create a source chain.
     * Note that this is not allowed to be {@code null} if {@code nullAllowed} was set to {@code false} on construction.
     *
     * @return The wrapped source.
     */
    public Source getWrapped() {

        return wrapped;
    }

    /**
     * Sets the wrapped {@link Source} that should be called during the {@link #update(Plugin, ActiveObjectSystem, long, Random)} call in order to create a source chain.
     * Note that this is not allowed to be {@code null} if {@code nullAllowed} was set to {@code false} on construction.
     *
     * @param wrapped The new wrapped source.
     */
    public void setWrapped(Source wrapped) {

        if (!nullAllowed) {
            Validate.notNull(wrapped, "Wrapped source of this source wrapper cannot be null");
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
