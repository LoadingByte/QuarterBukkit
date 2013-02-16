
package com.quartercode.quarterbukkit.api;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ItemData {

    public static boolean equals(final ItemStack itemStack1, final ItemStack itemStack2) {

        return new ItemData(itemStack1).equals(itemStack2);
    }

    public static boolean equals(final Block block1, final Block block2) {

        return new ItemData(block1).equals(block2);
    }

    private Material material;
    private byte     data;

    public ItemData(final Material material) {

        this.material = material;
        data = 0;
    }

    public ItemData(final Material material, final byte data) {

        this.material = material;
        this.data = data;
    }

    public ItemData(final ItemStack itemStack) {

        material = itemStack.getType();
        data = itemStack.getData().getData();
    }

    public ItemData(final Block block) {

        material = block.getType();
        data = block.getData();
    }

    public Material getMaterial() {

        return material;
    }

    public void setMaterial(final Material material) {

        this.material = material;
    }

    public byte getData() {

        return data;
    }

    public void setData(final byte data) {

        this.data = data;
    }

    public boolean equals(final ItemData itemData) {

        return itemData.getMaterial() == material && itemData.getData() == data;
    }

    public boolean equals(final ItemStack itemStack) {

        return itemStack.getType() == material && itemStack.getData().getData() == data;
    }

    public boolean equals(final Block block) {

        return block.getType() == material && block.getData() == data;
    }

    public void apply(final ItemStack itemStack) {

        itemStack.setType(material);
        final MaterialData itemData = itemStack.getData();
        itemData.setData(data);
        itemStack.setData(itemData);
    }

    public void apply(final Block block) {

        block.setType(material);
        block.setData(data);
    }

    @Override
    public boolean equals(final Object object) {

        if (object == null || ! (object instanceof ItemData)) {
            return false;
        } else {
            final ItemData itemData = (ItemData) object;
            return itemData.getMaterial() == material && itemData.getData() == data;
        }
    }

}
