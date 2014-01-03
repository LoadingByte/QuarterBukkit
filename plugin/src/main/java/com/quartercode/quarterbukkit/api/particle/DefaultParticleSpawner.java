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

package com.quartercode.quarterbukkit.api.particle;

import java.lang.reflect.Method;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InternalException;

/**
 * This is a default {@link ParticleSpawner} which executes the particle effects using fireworks.
 * The method spawns a new {@link Firework}, let it explode directly after that using reflection and then removes the {@link Entity}.
 */
public class DefaultParticleSpawner implements ParticleSpawner {

    private static Method world_getHandle                = null;
    private static Method firework_getHandle             = null;
    private static Method nmsFirwork_setInvisible        = null;
    private static Method nmsWorld_broadcastEntityEffect = null;

    public DefaultParticleSpawner() {

    }

    @Override
    public void spawn(final Plugin plugin, final List<ParticleDescription> descriptions, final Location location) {

        final Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);

        try {
            checkMethodCache(firework.getWorld(), firework);
            final Object nmsWorld = world_getHandle.invoke(firework.getWorld());
            final Object nmsFirework = firework_getHandle.invoke(firework);
            nmsFirwork_setInvisible.invoke(nmsFirework, true);

            final FireworkMeta meta = firework.getFireworkMeta();
            meta.clearEffects();
            for (final ParticleDescription description : descriptions) {
                final Builder builder = FireworkEffect.builder();
                builder.with(description.getShape().getFireworkType());
                builder.withColor(description.getColors().toArray(new Color[description.getColors().size()]));
                builder.withFade(description.getFadeColors().toArray(new Color[description.getFadeColors().size()]));
                meta.addEffect(builder.build());
            }
            meta.setPower(1);
            firework.setFireworkMeta(meta);

            nmsWorld_broadcastEntityEffect.invoke(nmsWorld, nmsFirework, (byte) 17);
        }
        catch (final Exception e) {
            ExceptionHandler.exception(new InternalException(plugin, e, "Reflection read error"));
        }

        firework.remove();
    }

    private void checkMethodCache(final World world, final Firework firework) throws Exception {

        if (world_getHandle == null) {
            world_getHandle = world.getClass().getDeclaredMethod("getHandle");
        }

        if (firework_getHandle == null) {
            firework_getHandle = firework.getClass().getDeclaredMethod("getHandle");
        }

        if (nmsFirwork_setInvisible == null) {
            for (final Method method : firework_getHandle.invoke(firework).getClass().getMethods()) {
                if (method.getName().equals("setInvisible")) {
                    nmsFirwork_setInvisible = method;
                    break;
                }
            }
        }

        if (nmsWorld_broadcastEntityEffect == null) {
            for (final Method method : world_getHandle.invoke(world).getClass().getMethods()) {
                if (method.getName().equals("broadcastEntityEffect")) {
                    nmsWorld_broadcastEntityEffect = method;
                    break;
                }
            }
        }
    }

}
