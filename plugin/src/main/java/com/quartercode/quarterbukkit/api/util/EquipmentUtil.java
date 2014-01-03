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
    public static ItemStack getItem(final LivingEntity entity) {

        return entity.getEquipment().getItemInHand();
    }

    /**
     * Sets the holding item of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setItem(final LivingEntity entity, final ItemStack itemStack) {

        entity.getEquipment().setItemInHand(itemStack);
    }

    /**
     * Returns the helmet of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getHelmet(final LivingEntity entity) {

        return entity.getEquipment().getHelmet();
    }

    /**
     * Sets the helmet of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setHelmet(final LivingEntity entity, final ItemStack itemStack) {

        entity.getEquipment().setHelmet(itemStack);
    }

    /**
     * Returns the chestplate of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getChestplate(final LivingEntity entity) {

        return entity.getEquipment().getChestplate();
    }

    /**
     * Sets the chestplate of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setChestplate(final LivingEntity entity, final ItemStack itemStack) {

        entity.getEquipment().setChestplate(itemStack);
    }

    /**
     * Returns the pants of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getLeggins(final LivingEntity entity) {

        return entity.getEquipment().getLeggings();
    }

    /**
     * Sets the pants of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setLeggins(final LivingEntity entity, final ItemStack itemStack) {

        entity.getEquipment().setBoots(itemStack);
    }

    /**
     * Returns the boots of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     */
    public static ItemStack getBoots(final LivingEntity entity) {

        return entity.getEquipment().getBoots();
    }

    /**
     * Sets the boots of an {@link LivingEntity}.
     * 
     * @param entity The {@link LivingEntity}.
     * @param itemStack The {@link ItemStack}.
     */
    public static void setBoots(final LivingEntity entity, final ItemStack itemStack) {

        entity.getEquipment().setBoots(itemStack);
    }

    private EquipmentUtil() {

    }

}
