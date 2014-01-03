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

package com.quartercode.quarterbukkit.api.util;

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Class for modifying meta-tags easily.
 * This is e.g. for modifying the name of items etc.
 */
public class MetaUtil {

    /**
     * Returns the name of an {@link ItemStack}.
     * 
     * @param itemStack The {@link ItemStack}.
     * @return The name of the {@link ItemStack}.
     */
    public static String getName(final ItemStack itemStack) {

        return itemStack.getItemMeta().getDisplayName();
    }

    /**
     * Sets the name of an {@link ItemStack}.
     * You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param name The name to set.
     */
    public static void setName(final ItemStack itemStack, final String name) {

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (name == null || name.isEmpty()) {
            itemMeta.setDisplayName(null);
        } else {
            itemMeta.setDisplayName(name);
        }
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Returns the description lines of an {@link ItemStack}.
     * They equals to the "Lore".
     * 
     * @param itemStack The {@link ItemStack}.
     * @return The description lines of the {@link ItemStack} as an {@link String}-{@link List}.
     */
    public static List<String> getDescriptions(final ItemStack itemStack) {

        return itemStack.getItemMeta().getLore();
    }

    /**
     * Sets the description lines of an {@link ItemStack}.
     * They equals to the "Lore". You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param descriptions The description lines to set as an {@link String}-{@link List}.
     */
    public static void setDescriptions(final ItemStack itemStack, final List<String> descriptions) {

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (descriptions == null || descriptions.isEmpty()) {
            itemMeta.setLore(null);
        } else {
            itemMeta.setLore(descriptions);
        }
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Sets the description lines of an {@link ItemStack}.
     * They equals to the "Lore". You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param descriptions The description lines to set as an {@link String}-array.
     */
    public static void setDescriptions(final ItemStack itemStack, final String... descriptions) {

        setDescriptions(itemStack, Arrays.asList(descriptions));
    }

    private MetaUtil() {

    }

}
