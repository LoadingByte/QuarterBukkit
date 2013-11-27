
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
