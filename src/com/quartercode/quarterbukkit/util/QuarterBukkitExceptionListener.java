
package com.quartercode.quarterbukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.InstallException;
import com.quartercode.quarterbukkit.api.exception.InternalException;

public class QuarterBukkitExceptionListener implements Listener {

    private final Plugin plugin;

    public QuarterBukkitExceptionListener(final Plugin plugin) {

        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void installException(final InstallException exception) {

        if (exception.getCauser() != null) {
            exception.getCauser().sendMessage(ChatColor.RED + "Can't update QuarterBukkit: " + exception.getMessage());
        } else {
            plugin.getLogger().warning("Can't update QuarterBukkit: " + exception.getMessage());
        }
    }

    @EventHandler
    public void internalException(final InternalException exception) {

        exception.getCause().printStackTrace();
    }

}
