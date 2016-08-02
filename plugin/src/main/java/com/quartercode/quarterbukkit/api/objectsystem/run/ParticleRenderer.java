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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InternalException;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleObject;

/**
 * A {@link Renderer} that displays all {@link ParticleObject}s by spawning minecraft particles.
 *
 * @see ParticleObject
 * @see Renderer
 */
public class ParticleRenderer extends StatelessRenderer<ParticleObject> {

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
            throw new RuntimeException("Cannot initialize particle renderer reflection handles", e);
        }

    }

    @Override
    public Class<ParticleObject> getObjectType() {

        return ParticleObject.class;
    }

    @Override
    public void render(Plugin plugin, ActiveObjectSystem objectSystem, long dt, ParticleObject object) {

        if (object.hasSpeedBasedFrequency() && !RenderingUtils.checkSpeedBasedFrequency(object, 0.5F)) {
            return;
        }

        spawn(plugin, objectSystem, object);
    }

    private void spawn(Plugin plugin, ActiveObjectSystem objectSystem, ParticleObject object) {

        Location location = objectSystem.getOrigin().add(object.getPosition());

        try {
            for (ParticleDefinition particle : object.getParticles()) {
                sendPacket(location.getWorld(), createPacket(particle, location));
            }
        } catch (RuntimeException e) {
            ExceptionHandler.exception(new InternalException(plugin, e, "Particle renderer reflection error"));
        }
    }

    private Object createPacket(ParticleDefinition particle, Location location) {

        try {
            Object packet = NMS_PACKET__CONSTRUCTOR.newInstance();

            if (ReflectionConstants.MINOR_VERSION >= 8) {
                setField(packet, "a", NMS_ENUM_PARTICLE__VALUE_OF.invoke(null, particle.getType().getName()));
                setField(packet, "j", true);
            } else {
                setField(packet, "a", particle.getType().getName());
            }

            setField(packet, "b", (float) location.getX());
            setField(packet, "c", (float) location.getY());
            setField(packet, "d", (float) location.getZ());

            Vector spread = particle.getSpread();
            setField(packet, "e", (float) spread.getX());
            setField(packet, "f", (float) spread.getY());
            setField(packet, "g", (float) spread.getZ());

            setField(packet, "h", particle.getType().hasParameter() ? particle.getParameter() : 0);
            setField(packet, "i", particle.getAmount());

            return packet;
        } catch (Exception e) {
            throw new RuntimeException("Cannot create particle packet for particle renderer", e);
        }
    }

    private void setField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {

        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private void sendPacket(World world, Object packet) {

        String worldName = world.getName();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().getName().equals(worldName)) {
                try {
                    Object playerConnection = NMS_ENTITY_PLAYER__PLAYER_CONNECTION.get(CRAFT_PLAYER__GET_HANDLE.invoke(player));
                    NMS_PLAYER_CONNECTION__SEND_PACKET.invoke(playerConnection, packet);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot send particle packet to player '" + player.getName() + "' for particle renderer", e);
                }
            }
        }
    }

}
