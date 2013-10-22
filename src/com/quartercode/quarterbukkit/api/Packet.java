
package com.quartercode.quarterbukkit.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.SendPacketException;
import com.quartercode.quarterbukkit.api.util.PlayerUtil;
import com.quartercode.quarterbukkit.api.util.ReflectionUtil;

public class Packet {

    private Object crafted_packet = null;
    private final Plugin plugin;

    /**
     * This is a little class that makes it possible for me
     * to easily craft/send packets. It has been created with the
     * aim to make it as easy as possible.
     */
    public Packet(String name, Plugin plugin) {

        this.plugin = plugin;
        
        try {
            crafted_packet = ReflectionUtil.getNMSClass(name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets a public field value of a class/packet.
     */
    public void setPublicValue(String field, Object value) {

        ReflectionUtil.setPublicValue(this, field, value);
    }

    /**
     * Sets a private field value of a class/packet.
     */
    public void setPrivateValue(String field, Object value) {

        ReflectionUtil.setPrivateValue(this, field, value);
    }

    /**
     * Return the packet-object you're working with.
     */
    public Object getPacketObject() {

        return this.crafted_packet;
    }

    /**
     * Method used to send the packet to specified player.
     * 
     * Adding PacketException later!!!
     * 
     * @param player The {@link Player} to send packet.
     */
    public void send(Player player) {

        try {
            Object entityPlayer = PlayerUtil.BukkitPlayerToCraftPlayer(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            ReflectionUtil.getMethod("sendPacket", playerConnection.getClass(), 1).invoke(playerConnection, crafted_packet);
        }
        catch (Exception e) {
            ExceptionHandler.exception(new SendPacketException(plugin, player, this));
        }
    }
}
