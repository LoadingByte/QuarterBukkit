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

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Class for setting the equipment of {@link LivingEntity}s as {@link ItemStack}s.
 *
 * @deprecated This class is no longer required since the Bukkit method {@link LivingEntity#getEquipment()} provides an alternative.
 */
@Deprecated
public class EquipmentUtil {

    /**
     * Returns the holding item of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @return The holding {@link ItemStack}.
     */
    public static ItemStack getItem(LivingEntity entity) {

        return entity.getEquipment().getItemInHand();
    }

    /**
     * Sets the holding item of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setItem(LivingEntity entity, ItemStack itemStack) {

        entity.getEquipment().setItemInHand(itemStack);
    }

    /**
     * Returns the helmet of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @return The helmet {@link ItemStack}.
     */
    public static ItemStack getHelmet(LivingEntity entity) {

        return entity.getEquipment().getHelmet();
    }

    /**
     * Sets the helmet of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setHelmet(LivingEntity entity, ItemStack itemStack) {

        entity.getEquipment().setHelmet(itemStack);
    }

    /**
     * Returns the chestplate of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @return The chestplate {@link ItemStack}.
     */
    public static ItemStack getChestplate(LivingEntity entity) {

        return entity.getEquipment().getChestplate();
    }

    /**
     * Sets the chestplate of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setChestplate(LivingEntity entity, ItemStack itemStack) {

        entity.getEquipment().setChestplate(itemStack);
    }

    /**
     * Returns the pants of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @return The pants {@link ItemStack}.
     */
    public static ItemStack getLeggins(LivingEntity entity) {

        return entity.getEquipment().getLeggings();
    }

    /**
     * Sets the pants of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setLeggins(LivingEntity entity, ItemStack itemStack) {

        entity.getEquipment().setBoots(itemStack);
    }

    /**
     * Returns the boots of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @return The boots {@link ItemStack}.
     */
    public static ItemStack getBoots(LivingEntity entity) {

        return entity.getEquipment().getBoots();
    }

    /**
     * Sets the boots of an {@link LivingEntity}.
     *
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setBoots(LivingEntity entity, ItemStack itemStack) {

        entity.getEquipment().setBoots(itemStack);
    }

    private EquipmentUtil() {

    }

}
