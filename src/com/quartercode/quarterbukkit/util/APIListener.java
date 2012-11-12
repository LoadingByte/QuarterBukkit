
package com.quartercode.quarterbukkit.util;

import java.util.Map.Entry;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet20NamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.TagUtil;

public class APIListener implements Listener {

    public APIListener(final Plugin plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {

        for (final Entry<Player, String> entry : TagUtil.getShowPlayerNames().entrySet()) {
            final EntityPlayer entityPlayer = ((CraftPlayer) entry.getKey()).getHandle();
            final String oldName = entityPlayer.name;
            entityPlayer.name = entry.getValue();
            ((CraftPlayer) event.getPlayer()).getHandle().netServerHandler.sendPacket(new Packet20NamedEntitySpawn(entityPlayer));
            entityPlayer.name = oldName;
        }
    }

}
