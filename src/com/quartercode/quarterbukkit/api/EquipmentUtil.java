
package com.quartercode.quarterbukkit.api;

import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import com.quartercode.quarterbukkit.api.thread.ThreadUtil;

/**
 * Class for setting the equipment of {@link LivingEntity}s as {@link ItemStack}s.
 */
public class EquipmentUtil {

    /**
     * Returns the holding item of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getItem(final LivingEntity entity) {

        ThreadUtil.check();
        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(0));
    }

    /**
     * Sets the holding item of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setItem(final LivingEntity entity, final ItemStack itemStack) {

        ThreadUtil.check();
        ((CraftLivingEntity) entity).getHandle().setEquipment(0, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the helmet of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getHelmet(final LivingEntity entity) {

        ThreadUtil.check();
        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(1));
    }

    /**
     * Sets the helmet of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setHelmet(final LivingEntity entity, final ItemStack itemStack) {

        ThreadUtil.check();
        ((CraftLivingEntity) entity).getHandle().setEquipment(1, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the chestplate of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getChestplate(final LivingEntity entity) {

        ThreadUtil.check();
        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(2));
    }

    /**
     * Sets the chestplate of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setChestplate(final LivingEntity entity, final ItemStack itemStack) {

        ThreadUtil.check();
        ((CraftLivingEntity) entity).getHandle().setEquipment(2, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the pants of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getLeggins(final LivingEntity entity) {

        ThreadUtil.check();
        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(3));
    }

    /**
     * Sets the pants of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setLeggins(final LivingEntity entity, final ItemStack itemStack) {

        ThreadUtil.check();
        ((CraftLivingEntity) entity).getHandle().setEquipment(3, ((CraftItemStack) itemStack).getHandle());
    }

    /**
     * Returns the boots of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getBoots(final LivingEntity entity) {

        ThreadUtil.check();
        return new CraftItemStack( ((CraftLivingEntity) entity).getHandle().getEquipment(4));
    }

    /**
     * Sets the boots of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setBoots(final LivingEntity entity, final ItemStack itemStack) {

        ThreadUtil.check();
        ((CraftLivingEntity) entity).getHandle().setEquipment(4, ((CraftItemStack) itemStack).getHandle());
    }

    private EquipmentUtil() {

    }

}
