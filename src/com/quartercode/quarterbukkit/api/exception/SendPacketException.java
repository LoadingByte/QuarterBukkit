
package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.Packet;

public class SendPacketException extends GameException {

    private final Player player;
    private final Packet packet;

    public SendPacketException(final Plugin plugin, final Player player, Packet packet) {

        super(plugin);

        this.player = player;
        this.packet = packet;

    }

    public Player getPlayer() {

        return player;
    }

    public Packet getPacket() {

        return packet;
    }

}
