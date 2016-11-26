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

package com.quartercode.quarterbukkit.api.fx.particle;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.ReflectionConstants;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InternalException;

/**
 * This utility class contains static methods for quickly spawning particle effects which have been defined using the {@link ParticleDefinition} class.
 *
 * @see ParticleDefinition
 */
public class ParticleSpawner {

    private static final Method         CRAFT_PLAYER__GET_HANDLE;
    private static final Field          NMS_ENTITY_PLAYER__PLAYER_CONNECTION;
    private static final Method         NMS_PLAYER_CONNECTION__SEND_PACKET;
    private static final Constructor<?> NMS_PACKET__CONSTRUCTOR;
    private static final Method         NMS_ENUM_PARTICLE__VALUE_OF;

    static {

        try {
            CRAFT_PLAYER__GET_HANDLE = Class.forName(ReflectionConstants.CB_ENTITY_PACKAGE + ".CraftPlayer").getMethod("getHandle");
            NMS_ENTITY_PLAYER__PLAYER_CONNECTION = Class.forName(ReflectionConstants.NMS_PACKAGE + ".EntityPlayer").getField("playerConnection");
            NMS_PLAYER_CONNECTION__SEND_PACKET = NMS_ENTITY_PLAYER__PLAYER_CONNECTION.getType().getMethod("sendPacket", Class.forName(ReflectionConstants.NMS_PACKAGE + ".Packet"));

            String packetClassName = ReflectionConstants.NMS_PACKAGE + "." + (ReflectionConstants.MINOR_VERSION <= 6 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
            NMS_PACKET__CONSTRUCTOR = Class.forName(packetClassName).getConstructor();

            if (ReflectionConstants.MINOR_VERSION >= 8) {
                NMS_ENUM_PARTICLE__VALUE_OF = Class.forName(ReflectionConstants.NMS_PACKAGE + ".EnumParticle").getMethod("a", String.class);
            } else {
                NMS_ENUM_PARTICLE__VALUE_OF = null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize particle spawner reflection handles", e);
        }

    }

    /**
     * Spawns a particle with the properties which have been defined by the given {@link ParticleDefinition}.
     *
     * @param location The {@link Location} where the particle should be spawned at.
     * @param particle The particle definition that defines how the particle should look like.
     */
    public static void spawn(Location location, ParticleDefinition particle) {

        try {
            sendPacket(location.getWorld(), createPacket(particle, location));
        } catch (RuntimeException e) {
            ExceptionHandler.exception(new InternalException(QuarterBukkit.getPlugin(), e, "Particle spawner reflection error"));
        }
    }

    private static Object createPacket(ParticleDefinition particleDef, Location location) {

        try {
            Object packet = NMS_PACKET__CONSTRUCTOR.newInstance();

            if (ReflectionConstants.MINOR_VERSION >= 8) {
                setField(packet, "a", NMS_ENUM_PARTICLE__VALUE_OF.invoke(null, particleDef.getType().getName()));
                setField(packet, "j", true);
            } else {
                setField(packet, "a", particleDef.getType().getName());
            }

            setField(packet, "b", (float) location.getX());
            setField(packet, "c", (float) location.getY());
            setField(packet, "d", (float) location.getZ());

            Vector spread = particleDef.getSpread();
            setField(packet, "e", (float) spread.getX());
            setField(packet, "f", (float) spread.getY());
            setField(packet, "g", (float) spread.getZ());

            setField(packet, "h", particleDef.getType().hasParameter() ? particleDef.getParameter() : 0);
            setField(packet, "i", particleDef.getAmount());

            return packet;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create particle packet for particle spawner", e);
        }
    }

    private static void setField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {

        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private static void sendPacket(World world, Object packet) {

        String worldName = world.getName();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getName().equals(worldName)) {
                try {
                    Object playerConnection = NMS_ENTITY_PLAYER__PLAYER_CONNECTION.get(CRAFT_PLAYER__GET_HANDLE.invoke(player));
                    NMS_PLAYER_CONNECTION__SEND_PACKET.invoke(playerConnection, packet);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot send particle packet to player '" + player.getName() + "' for particle spawner", e);
                }
            }
        }
    }

    private ParticleSpawner() {}

}
