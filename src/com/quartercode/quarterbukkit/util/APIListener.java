
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

    private final Plugin plugin;

    public APIListener(final Plugin plugin) {

        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
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

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (plugin == null ? 0 : plugin.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final APIListener other = (APIListener) obj;
        if (plugin == null) {
            if (other.plugin != null) {
                return false;
            }
        } else if (!plugin.equals(other.plugin)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [plugin=" + plugin + "]";
    }

}
