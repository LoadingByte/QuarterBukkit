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
import java.util.Collection;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InternalException;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.object.FireworkEffectDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.object.FireworkObject;

/**
 * A {@link Renderer} that displays all {@link FireworkObject}s by spawning fireworks.
 *
 * @see FireworkObject
 * @see Renderer
 */
public class FireworkRenderer extends StatelessRenderer<FireworkObject> {

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
    public Class<FireworkObject> getObjectType() {

        return FireworkObject.class;
    }

    @Override
    public RenderingResult render(Plugin plugin, ActiveObjectSystem objectSystem, FireworkObject object) {

        // Determine whether the different effects should be spawned
        double objectVelocity = object.getVelocity().length();
        boolean spawnNoTrailObjects = !object.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object.getLifetime(), objectVelocity, 0.5F);
        boolean spawnTrailObjects = !object.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object.getLifetime(), objectVelocity, 0.75F);

        // Collect all effects that are spawned this round
        Collection<FireworkEffectDefinition> spawnEffects = new ArrayList<FireworkEffectDefinition>();
        for (FireworkEffectDefinition effect : object.getEffects()) {
            if (!effect.hasTrail() && spawnNoTrailObjects || effect.hasTrail() && spawnTrailObjects) {
                spawnEffects.add(effect);
            }
        }

        // Check whether at least one effect would be spawned this round
        if (spawnEffects.isEmpty()) {
            return RenderingResult.NOTHING;
        }

        // Actually spawn all effects for this round
        spawn(plugin, objectSystem, object, spawnEffects);

        return RenderingResult.NOTHING;
    }

    private void spawn(Plugin plugin, ActiveObjectSystem objectSystem, FireworkObject object, Collection<FireworkEffectDefinition> spawnEffects) {

        Location location = objectSystem.getOrigin().add(object.getPosition());
        Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

        try {
            Object nmsWorld = CRAFT_WORLD__GET_HANDLE.invoke(firework.getWorld());
            Object nmsFirework = CRAFT_FIREWORK__GET_HANDLE.invoke(firework);
            NMS_ENTITY__SET_INVISIVBLE.invoke(nmsFirework, true);

            FireworkMeta meta = firework.getFireworkMeta();
            applyEffects(meta, spawnEffects);
            meta.setPower(object.getPower());
            firework.setFireworkMeta(meta);

            NMS_WORLD__BROADCAST_ENTITY_EFFECT.invoke(nmsWorld, nmsFirework, (byte) 17);
        } catch (Exception e) {
            ExceptionHandler.exception(new InternalException(plugin, e, "Firework renderer reflection error"));
        }

        firework.remove();
    }

    private void applyEffects(FireworkMeta meta, Collection<FireworkEffectDefinition> effects) {

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
