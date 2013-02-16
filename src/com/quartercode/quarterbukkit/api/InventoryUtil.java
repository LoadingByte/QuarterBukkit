
package com.quartercode.quarterbukkit.api;

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
    public static boolean contains(final Inventory inventory, final ItemStack itemStack) {

        return containsAtLeast(inventory, itemStack, 1);
    }

    /**
     * Returns if the given {@link Inventory} contains at least the given amount of {@link ItemStack}s which equal the given {@link ItemStack}.
     * 
     * @param inventory The {@link Inventory} to check.
     * @param itemStack The {@link ItemStack} to check.
     * @return If the given {@link Inventory} contains at least the given amount of {@link ItemStack}s which equal the given {@link ItemStack}.
     */
    public static boolean containsAtLeast(final Inventory inventory, final ItemStack itemStack, final int minimumAmount) {

        int foundItems = 0;

        if (inventory instanceof PlayerInventory) {
            final PlayerInventory playerInventory = (PlayerInventory) inventory;
            if (playerInventory.getBoots() != null && playerInventory.getBoots().equals(itemStack) || playerInventory.getLeggings() != null && playerInventory.getLeggings().equals(itemStack) || playerInventory.getChestplate() != null && playerInventory.getChestplate().equals(itemStack) || playerInventory.getHelmet() != null && playerInventory.getHelmet().equals(itemStack)) {
                foundItems++;
            }
        }

        for (final ItemStack itemStack2 : inventory.getContents()) {
            if (itemStack2 != null && itemStack.equals(itemStack2)) {
                foundItems++;
            }
        }

        return foundItems >= minimumAmount;
    }

    private InventoryUtil() {

    }

}
