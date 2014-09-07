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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * A default implementation of {@link BaseObject} that just stores lifetime and expiration time as member variables.
 * 
 * @see BaseObject
 */
public class DefaultBaseObject implements BaseObject {

    private int lifetime;
    private int expirationTime;

    /**
     * Creates a new default base object that never expires.
     */
    public DefaultBaseObject() {

        expirationTime = -1;
    }

    /**
     * Creates a new default base object that expires and is removed after the given amount of updates.
     * 
     * @param expirationTime The amount of updates after which the object expires and is removed.
     */
    public DefaultBaseObject(int expirationTime) {

        this.expirationTime = expirationTime;
    }

    @Override
    public int getLifetime() {

        return lifetime;
    }

    @Override
    public void incrementLifetime() {

        lifetime++;
    }

    @Override
    public int getExpirationTime() {

        return expirationTime;
    }

    @Override
    public void setExpirationTime(int expirationTime) {

        this.expirationTime = expirationTime;
    }

    @Override
    public DefaultBaseObject clone() {

        try {
            return (DefaultBaseObject) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
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
