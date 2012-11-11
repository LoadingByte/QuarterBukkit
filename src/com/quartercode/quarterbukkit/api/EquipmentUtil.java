
package com.quartercode.quarterbukkit.api;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Class for setting the equipment of {@link LivingEntity}s as {@link ItemStack}s.
 */
public class EquipmentUtil {

    /**
     * Returns the holding item of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getItem(LivingEntity entity) {

        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(0));
    }

    /**
     * Sets the holding item of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setItem(LivingEntity entity, ItemStack itemStack) {

        ((CraftLivingEntity) entity).getHandle().setEquipment(0, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the helmet of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getHelmet(LivingEntity entity) {

        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(1));
    }

    /**
     * Sets the helmet of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setHelmet(LivingEntity entity, ItemStack itemStack) {

        ((CraftLivingEntity) entity).getHandle().setEquipment(1, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the chestplate of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getChestplate(LivingEntity entity) {

        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(2));
    }

    /**
     * Sets the chestplate of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setChestplate(LivingEntity entity, ItemStack itemStack) {

        ((CraftLivingEntity) entity).getHandle().setEquipment(2, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the pants of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getLeggins(LivingEntity entity) {

        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(3));
    }

    /**
     * Sets the pants of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setLeggins(LivingEntity entity, ItemStack itemStack) {

        ((CraftLivingEntity) entity).getHandle().setEquipment(3, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the boots of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getBoots(LivingEntity entity) {

        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(4));
    }

    /**
     * Sets the boots of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setBoots(LivingEntity entity, ItemStack itemStack) {

        ((CraftLivingEntity) entity).getHandle().setEquipment(4, ((CraftItemStack) itemStack).getHandle());
    }

    private EquipmentUtil() {

    }

}
