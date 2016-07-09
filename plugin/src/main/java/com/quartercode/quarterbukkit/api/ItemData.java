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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.MaterialData;

public class ItemData {

    /**
     * Checks if two {@link ItemStack}s are the same.
     *
     * @param itemStack1 The first {@link ItemStack}.
     * @param itemStack2 The second {@link ItemStack}.
     * @return If both {@link ItemStack}s are the same.
     */
    public static boolean equals(ItemStack itemStack1, ItemStack itemStack2) {

        return new ItemData(itemStack1).equals(itemStack2);
    }

    /**
     * Checks if two {@link Block}s are the same.
     *
     * @param block1 The first {@link Block}.
     * @param block2 The second {@link Block}.
     * @return If both {@link Block}s are the same.
     */
    public static boolean equals(Block block1, Block block2) {

        return new ItemData(block1).equals(block2);
    }

    /**
     * Checks if the datas of an {@link ItemStack} and a {@link Block} are the same.
     *
     * @param itemStack1 The first {@link ItemStack}.
     * @param block2 The second {@link Block}.
     * @return If the datas of the {@link ItemStack} and the {@link Block} are the same.
     */
    public static boolean equals(ItemStack itemStack1, Block block2) {

        return new ItemData(itemStack1).equals(block2);
    }

    private Material material;
    private byte     data;

    /**
     * Creates a new empty item data object.
     */
    public ItemData() {

        data = 0;
    }

    /**
     * Creates a new item data object and fills it with a {@link Material}.
     *
     * @param material The {@link Material}.
     */
    public ItemData(Material material) {

        this.material = material;
        data = 0;
    }

    /**
     * Creates a new item data object and fills it with a {@link Material} and a data value.
     *
     * @param material The {@link Material}.
     * @param data The data value.
     */
    public ItemData(Material material, byte data) {

        this.material = material;
        this.data = data;
    }

    /**
     * Creates a new item data object and fills it with the data of an {@link ItemStack}.
     *
     * @param itemStack The {@link ItemStack} to read the data from.
     */
    @SuppressWarnings ("deprecation")
    public ItemData(ItemStack itemStack) {

        material = itemStack.getType();
        data = itemStack.getData().getData();
    }

    /**
     * Creates a new item data object and fills it with the data of a {@link Block}.
     *
     * @param block The {@link Block} to read the data from.
     */
    @SuppressWarnings ("deprecation")
    public ItemData(Block block) {

        material = block.getType();
        data = block.getData();
    }

    /**
     * Returns the {@link Material} of the item data object.
     *
     * @return The {@link Material} of the item data object.
     */
    public Material getMaterial() {

        return material;
    }

    /**
     * Sets the {@link Material} of the item data object.
     *
     * @param material The new {@link Material} of the item data object.
     */
    public void setMaterial(Material material) {

        this.material = material;
    }

    /**
     * Returns the data value of the item data object.
     *
     * @return The data value of the item data object.
     */
    public byte getData() {

        return data;
    }

    /**
     * Sets the data value of the item data object.
     *
     * @param data The new data value of the item data object.
     */
    public void setData(byte data) {

        this.data = data;
    }

    /**
     * Returns if this item data object equals another.
     *
     * @param itemData The item data object to compare with.
     * @return If this item data object equals the other one.
     */
    public boolean equals(ItemData itemData) {

        return itemData.getMaterial() == material && itemData.getData() == data;
    }

    /**
     * Returns if the data of this item data object equals the data from an {@link ItemStack}.
     *
     * @param itemStack The {@link ItemStack} to compare with.
     * @return If the data of this item data object equals the data from the {@link ItemStack}.
     */
    @SuppressWarnings ("deprecation")
    public boolean equals(ItemStack itemStack) {

        return itemStack != null && itemStack.getType() == material && itemStack.getData().getData() == data;
    }

    /**
     * Returns if the data of this item data object equals the data from an {@link Block}.
     *
     * @param block The {@link Block} to compare with.
     * @return If the data of this item data object equals the data from the {@link Block}.
     */
    @SuppressWarnings ("deprecation")
    public boolean equals(Block block) {

        return block != null && block.getType() == material && block.getData() == data;
    }

    /**
     * Returns if the given {@link Inventory} contains at least one {@link ItemStack} with the data of this item data object.
     *
     * @param inventory The {@link Inventory} to check.
     * @return If the given {@link Inventory} contains at least one {@link ItemStack} with the data of this item data object.
     */
    public boolean contains(Inventory inventory) {

        return containsAtLeast(inventory, 1);
    }

    /**
     * Returns if the given {@link Inventory} contains at least the given amount of {@link ItemStack}s with the data of this item data object.
     *
     * @param inventory The {@link Inventory} to check.
     * @param minimumAmount The minimum count of item stacks.
     * @return If the given {@link Inventory} contains at least the given amount of {@link ItemStack}s with the data of this item data object.
     */
    public boolean containsAtLeast(Inventory inventory, int minimumAmount) {

        int foundItems = 0;

        if (inventory instanceof PlayerInventory) {
            PlayerInventory playerInventory = (PlayerInventory) inventory;
            if (equals(playerInventory.getBoots()) || equals(playerInventory.getLeggings()) || equals(playerInventory.getChestplate()) || equals(playerInventory.getHelmet())) {
                foundItems++;
            }
        }

        for (ItemStack itemStack : inventory.getContents()) {
            if (equals(itemStack)) {
                foundItems++;
            }
        }

        return foundItems >= minimumAmount;
    }

    /**
     * Applys the data of this item data object to an {@link ItemStack}.
     *
     * @param itemStack The {@link ItemStack} to apply the data.
     */
    @SuppressWarnings ("deprecation")
    public void apply(ItemStack itemStack) {

        itemStack.setType(material);
        MaterialData itemData = itemStack.getData();
        itemData.setData(data);
        itemStack.setData(itemData);
    }

    /**
     * Applys the data of this item data object to an {@link Block}.
     *
     * @param block The {@link Block} to apply the data.
     */
    @SuppressWarnings ("deprecation")
    public void apply(Block block) {

        block.setType(material);
        block.setData(data);
    }

    @Override
    public boolean equals(Object object) {

        if (! (object instanceof ItemData)) {
            return false;
        } else {
            ItemData itemData = (ItemData) object;
            return itemData.getMaterial() == material && itemData.getData() == data;
        }
    }

}
