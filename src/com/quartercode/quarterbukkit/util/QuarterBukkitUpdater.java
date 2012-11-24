
package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.Updater;

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
    protected void doInstall(final File downloadedFile) throws IOException {

        extract(downloadedFile, "QuarterBukkit.jar", new File("plugins", "QuarterBukkit.jar"));
        downloadedFile.delete();
    }

}