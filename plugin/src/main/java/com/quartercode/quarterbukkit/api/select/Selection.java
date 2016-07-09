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

package com.quartercode.quarterbukkit.api.select;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.inventory.ItemStack;

/**
 * This class represents a selection in the {@link SelectInventory} with a value and the graphical {@link ItemStack}.
 */
public class Selection {

    private final Object    value;
    private final ItemStack itemStack;

    /**
     * Creates a new selection and sets the value as an {@link Object} and the graphical {@link ItemStack}.
     *
     * @param value The value as an {@link Object}.
     * @param itemStack The graphical {@link ItemStack}.
     */
    public Selection(Object value, ItemStack itemStack) {

        this.value = value;
        this.itemStack = itemStack;
    }

    /**
     * Returns the value as an {@link Object}.
     *
     * @return The value as an {@link Object}.
     */
    public Object getValue() {

        return value;
    }

    /**
     * Returns the graphical {@link ItemStack}.
     *
     * @return The graphical {@link ItemStack}.
     */
    public ItemStack getItemStack() {

        return itemStack;
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this, new String[] { "itemStack" });
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj, new String[] { "itemStack" });
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
