
package com.quartercode.quarterbukkit.util;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.GameException;
import com.quartercode.quarterbukkit.api.exception.InstallException;

public class QuarterBukkitExceptionHandler extends ExceptionHandler {

    public QuarterBukkitExceptionHandler(final Plugin plugin) {

        super(plugin);
    }

    @Override
    public void handle(final GameException exception) {

        if (exception instanceof InstallException) {
            plugin.getLogger().warning("Can't update QuarterBukkit: " + exception.getLocalizedMessage());
        } else {
            plugin.getLogger().warning(exception.toString());
        }
    }

}
