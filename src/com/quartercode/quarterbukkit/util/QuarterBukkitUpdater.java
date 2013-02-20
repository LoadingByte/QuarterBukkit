
package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.Updater;
import com.quartercode.quarterbukkit.api.exception.ExceptionManager;
import com.quartercode.quarterbukkit.api.exception.InstallException;

/**
 * This class is for checking the QuarterBukkit-version and updating the plugin.
 */
public class QuarterBukkitUpdater extends Updater {

    /**
     * Creates a new QuarterBukkit updater.
     * 
     * @param plugin The {@link QuarterBukkit}-{@link Plugin}.
     */
    public QuarterBukkitUpdater(final Plugin plugin) {

        super(plugin, plugin, "quarterbukkit");
    }

    @Override
    protected String parseVersion(final String title) {

        return title.replaceAll("QuarterBukkit ", "");
    }

    @Override
    protected boolean doInstall(final File downloadedFile, final CommandSender causer) throws IOException {

        extract(downloadedFile, "QuarterBukkit.jar", new File("plugins", "QuarterBukkit.jar"));
        downloadedFile.delete();

        try {
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File("plugins", "QuarterBukkit.jar")));
            return true;
        }
        catch (final Exception e) {
            ExceptionManager.exception(new InstallException(plugin, this, e, "Error while reloading"));
        }

        plugin.getLogger().info("Successfull updated QuarterBukkit!");

        return false;
    }

}
