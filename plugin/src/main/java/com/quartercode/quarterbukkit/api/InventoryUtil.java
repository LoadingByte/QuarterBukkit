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

package com.quartercode.quarterbukkit.api;

import java.util.Arrays;
import java.util.Collections;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Class for handling several {@link Inventory}-related processes easier.
 */
public class InventoryUtil {

    /**
     * Returns if the given {@link Inventory} contains at least one {@link ItemStack} which equals the given {@link ItemStack}.
     *
     * @param inventory The {@link Inventory} to check.
     * @param itemStack The {@link ItemStack} to check.
     * @return If the given {@link Inventory} contains at least one {@link ItemStack} which equals the given {@link ItemStack}.
     */
    public static boolean contains(Inventory inventory, ItemStack itemStack) {

        return containsAtLeast(inventory, itemStack, 1);
    }

    /**
     * Returns if the given {@link Inventory} contains at least the given amount of {@link ItemStack}s which equal the given {@link ItemStack}.
     *
     * @param inventory The {@link Inventory} to check.
     * @param itemStack The {@link ItemStack} to check.
     * @param minimumAmount The least amount of items for a positive result.
     * @return If the given {@link Inventory} contains at least the given amount of {@link ItemStack}s which equal the given {@link ItemStack}.
     */
    public static boolean containsAtLeast(Inventory inventory, ItemStack itemStack, int minimumAmount) {

        int foundItems = 0;

        // Count the items in the regular inventory
        foundItems += Collections.frequency(Arrays.asList(inventory.getContents()), itemStack);

        if (inventory instanceof PlayerInventory) {
            PlayerInventory playerInventory = (PlayerInventory) inventory;
            // Count the items in the armor slots
            foundItems += Collections.frequency(Arrays.asList(playerInventory.getArmorContents()), itemStack);
            // Check whether the player holds the item on his cursor
            if (itemStack.equals(playerInventory.getHolder().getItemOnCursor())) {
                foundItems++;
            }
        }

        return foundItems >= minimumAmount;
    }

    private InventoryUtil() {

    }

}
