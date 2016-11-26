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

package com.quartercode.quarterbukkit.api.fx.firework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.ReflectionConstants;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InternalException;

/**
 * This utility class contains static methods for quickly spawning firework effects without actually spawning a firework entity.
 * Actually, an entity is spawned in the background, but it is hidden from everybody so that everything that remains is the desired firework effect.
 * Note that this class supports both the Bukkit {@link FireworkEffect} class and the custom {@link FireworkEffectDefinition} class.
 *
 * @see FireworkEffect
 * @see FireworkEffectDefinition
 */
public class FireworkEffectSpawner {

    private static final Method CRAFT_WORLD__GET_HANDLE;
    private static final Method CRAFT_FIREWORK__GET_HANDLE;
    private static final Method NMS_ENTITY__SET_INVISIVBLE;
    private static final Method NMS_WORLD__BROADCAST_ENTITY_EFFECT;

    static {

        try {
            CRAFT_WORLD__GET_HANDLE = Class.forName(ReflectionConstants.CB_PACKAGE + ".CraftWorld").getMethod("getHandle");
            CRAFT_FIREWORK__GET_HANDLE = Class.forName(ReflectionConstants.CB_ENTITY_PACKAGE + ".CraftFirework").getMethod("getHandle");

            Class<?> nmsEntityClass = Class.forName(ReflectionConstants.NMS_PACKAGE + ".Entity");
            NMS_ENTITY__SET_INVISIVBLE = nmsEntityClass.getMethod("setInvisible", boolean.class);
            NMS_WORLD__BROADCAST_ENTITY_EFFECT = Class.forName(ReflectionConstants.NMS_PACKAGE + ".World").getMethod("broadcastEntityEffect", nmsEntityClass, byte.class);
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize firework effect spawner reflection handles", e);
        }

    }

    /**
     * Spawns an invisible firework with the given {@link FireworkEffectDefinition}s.
     *
     * @param location The {@link Location} where the firework should be spawned at.
     * @param effects The firework effect definitions that define how the spawned firework looks like when it explodes.
     *        All effects will be applied to the firework at once.
     */
    public static void spawn(Location location, FireworkEffectDefinition... effects) {

        spawn(location, Arrays.asList(effects));
    }

    /**
     * Spawns an invisible firework with the given {@link FireworkEffectDefinition}s.
     *
     * @param location The {@link Location} where the firework should be spawned at.
     * @param effects The firework effect definitions that define how the spawned firework looks like when it explodes.
     *        All effects will be applied to the firework at once.
     */
    public static void spawn(Location location, Collection<FireworkEffectDefinition> effects) {

        Validate.noNullElements(effects, "Cannot use null firework effect definitions");

        List<FireworkEffect> bukkitEffects = new ArrayList<>();
        for (FireworkEffectDefinition effect : effects) {
            bukkitEffects.add(effect.toBukkit());
        }

        spawnBukkit(location, bukkitEffects);
    }

    /**
     * Spawns an invisible firework with the given Bukkit {@link FireworkEffect}s.
     *
     * @param location The {@link Location} where the firework should be spawned at.
     * @param bukkitEffects The Bukkit firework effect definitions that define how the spawned firework looks like when it explodes.
     *        All effects will be applied to the firework at once.
     */
    public static void spawnBukkit(Location location, FireworkEffect... bukkitEffects) {

        spawnBukkit(location, Arrays.asList(bukkitEffects));
    }

    /**
     * Spawns an invisible firework with the given Bukkit {@link FireworkEffect}s.
     *
     * @param location The {@link Location} where the firework should be spawned at.
     * @param bukkitEffects The Bukkit firework effect definitions that define how the spawned firework looks like when it explodes.
     *        All effects will be applied to the firework at once.
     */
    public static void spawnBukkit(Location location, Collection<FireworkEffect> bukkitEffects) {

        Validate.noNullElements(bukkitEffects, "Cannot use null firework effects");

        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

        try {
            Object nmsWorld = CRAFT_WORLD__GET_HANDLE.invoke(firework.getWorld());
            Object nmsFirework = CRAFT_FIREWORK__GET_HANDLE.invoke(firework);
            NMS_ENTITY__SET_INVISIVBLE.invoke(nmsFirework, true);

            FireworkMeta meta = firework.getFireworkMeta();
            meta.addEffects(bukkitEffects);
            meta.setPower(0);
            firework.setFireworkMeta(meta);

            NMS_WORLD__BROADCAST_ENTITY_EFFECT.invoke(nmsWorld, nmsFirework, (byte) 17);
        } catch (Exception e) {
            ExceptionHandler.exception(new InternalException(QuarterBukkit.getPlugin(), e, "Firework effect spawner reflection error"));
        }

        firework.remove();
    }

    private FireworkEffectSpawner() {}

}
