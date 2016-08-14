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

package com.quartercode.quarterbukkit.api.objectsystem.run;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InternalException;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.traits.FireworkEffectDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.traits.FireworkTrait;
import com.quartercode.quarterbukkit.api.objectsystem.traits.PhysicsTrait;

/**
 * A {@link Renderer} that displays all {@link BaseObject objects} with {@link FireworkTrait}s by spawning fireworks.
 *
 * @see FireworkTrait
 * @see Renderer
 */
public class FireworkTraitRenderer extends StatelessRenderer {

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
            throw new RuntimeException("Cannot initialize firework renderer reflection handles", e);
        }

    }

    @Override
    public void render(Plugin plugin, long dt, BaseObject object) {

        object.get(PhysicsTrait.class, FireworkTrait.class).ifPresent((physics, frwk) -> {
            // Determine whether the different effects should be spawned
            double objectVelocity = physics.getVelocity().length();
            boolean spawnNoTrailObjects = !frwk.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object.getLifetime(), objectVelocity, 0.5F);
            boolean spawnTrailObjects = !frwk.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object.getLifetime(), objectVelocity, 0.75F);

            // Collect all effects that are spawned this round
            List<FireworkEffectDefinition> spawnEffects = new ArrayList<>();
            for (FireworkEffectDefinition effect : frwk.getEffects()) {
                if (!effect.hasTrail() && spawnNoTrailObjects || effect.hasTrail() && spawnTrailObjects) {
                    spawnEffects.add(effect);
                }
            }

            // Check whether at least one effect would be spawned this round
            if (spawnEffects.isEmpty()) {
                return;
            }

            // Actually spawn all effects for this round
            spawn(plugin, object.getSystem().getOrigin().add(physics.getPosition()), frwk.getPower(), spawnEffects);
        });
    }

    private void spawn(Plugin plugin, Location location, int power, List<FireworkEffectDefinition> spawnEffects) {

        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

        try {
            Object nmsWorld = CRAFT_WORLD__GET_HANDLE.invoke(firework.getWorld());
            Object nmsFirework = CRAFT_FIREWORK__GET_HANDLE.invoke(firework);
            NMS_ENTITY__SET_INVISIVBLE.invoke(nmsFirework, true);

            FireworkMeta meta = firework.getFireworkMeta();
            applyEffects(meta, spawnEffects);
            meta.setPower(power);
            firework.setFireworkMeta(meta);

            NMS_WORLD__BROADCAST_ENTITY_EFFECT.invoke(nmsWorld, nmsFirework, (byte) 17);
        } catch (Exception e) {
            ExceptionHandler.exception(new InternalException(plugin, e, "Firework renderer reflection error"));
        }

        firework.remove();
    }

    private void applyEffects(FireworkMeta meta, List<FireworkEffectDefinition> effects) {

        meta.clearEffects();
        for (FireworkEffectDefinition effect : effects) {
            FireworkEffect.Builder builder = FireworkEffect.builder();
            builder.with(effect.getType());
            builder.flicker(effect.hasFlicker());
            builder.trail(effect.hasTrail());
            builder.withColor(effect.getColors());
            builder.withFade(effect.getFadeColors());
            meta.addEffect(builder.build());
        }
    }

}
