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
/*
 * This file is part of QuarterBukkit.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api.util;

import org.bukkit.entity.Player;
import com.quartercode.quarterbukkit.api.Language;
import com.quartercode.quarterbukkit.api.protocol.Packet;
import com.quartercode.quarterbukkit.api.reflect.BukkitServer;
import com.quartercode.quarterbukkit.api.reflect.FieldAccessor;
import com.quartercode.quarterbukkit.api.reflect.MethodAccessor;
import com.quartercode.quarterbukkit.api.reflect.SafeField;
import com.quartercode.quarterbukkit.api.reflect.SafeMethod;

/**
 * Class for some useful Player Methods
 */
public class PlayerUtil {

    /**
     * Return the {@link Language} from the player client.
     * 
     * @param player The {@link Player} to get the {@link Language}.
     * @return The Language.
     */
    public static Language getLanguage(final Player player) {

        FieldAccessor<String> locale = new SafeField<String>(BukkitServer.getNMSClass("EntityPlayer"), "locale");
        return Language.getByCode(locale.get(playerToEntityPlayer(player)));
    }

    /**
     * Sends a Packet to the Player
     * 
     * @param player The {@link Player} to send the packet.
     * @param packet The Packet to send.
     */
    public static void sendPacket(Player player, Packet packet) {

        MethodAccessor<Void> sendPacket = new SafeMethod<Void>(BukkitServer.getNMSClass("PlayerConnection"), "sendPacket", BukkitServer.getNMSClass("Packet"));
        Object playerConnection = getPlayerConnection(player);
        sendPacket.invoke(playerConnection, packet.getHandle());
    }

    /**
     * Return the EntityPlayer Object.
     * 
     * @param player The Player to convert.
     * @return The EntityPlayer Object.
     */
    public static Object playerToEntityPlayer(Player player) {

        MethodAccessor<Object> getHandle = new SafeMethod<Object>(player.getClass(), "getHandle");
        return getHandle.invoke(player);
    }

    /**
     * Return the PlayerConnection of the player.
     * 
     * @param player the {@link Player}.
     * @return The PlayerConnection.
     */
    public static Object getPlayerConnection(Player player) {

        FieldAccessor<Object> playerConnection = new SafeField<Object>(BukkitServer.getNMSClass("EntityPlayer"), "playerConnection");
        return playerConnection.get(playerToEntityPlayer(player));
    }
}
