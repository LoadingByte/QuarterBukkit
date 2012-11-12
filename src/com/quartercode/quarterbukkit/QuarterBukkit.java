
package com.quartercode.quarterbukkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.GameException;
import com.quartercode.quarterbukkit.util.Metrics;
import com.quartercode.quarterbukkit.util.QuarterBukkitUpdater;

/**
 * The main class of the QuarterBukkit API.
 * This is for easy API loading.
 */
public class QuarterBukkit extends JavaPlugin {

    private static Plugin                 plugin;
    private static List<ExceptionHandler> exceptionHandlers = new ArrayList<ExceptionHandler>();

    /**
     * Returns the current {@link Plugin}.
     * 
     * @return The current {@link Plugin}.
     */
    public static Plugin getPlugin() {

        return plugin;
    }

    /**
     * Returns all registered {@link ExceptionHandler}s.
     * 
     * @return The {@link ExceptionHandler}s.
     */
    public static List<ExceptionHandler> getExceptionHandlers() {

        return exceptionHandlers;
    }

    /**
     * Returns the registered {@link ExceptionHandler} for a {@link Plugin}.
     * 
     * @param plugin The binding {@link Plugin}.
     * @return The {@link ExceptionHandler}.
     */
    public static ExceptionHandler getExceptionHandler(final Plugin plugin) {

        for (final ExceptionHandler exceptionHandler : exceptionHandlers) {
            if (exceptionHandler.getPlugin().equals(plugin)) {
                return exceptionHandler;
            }
        }

        throw new IllegalStateException("ExceptionHandler for plugin " + plugin.getName() + " not set");
    }

    /**
     * Registers the {@link ExceptionHandler} for a binding {@link Plugin}.
     * 
     * @param exceptionHandler The {@link ExceptionHandler} to register.
     */
    public static void setExceptionHandler(final ExceptionHandler exceptionHandler) {

        for (final ExceptionHandler exceptionHandler2 : exceptionHandlers) {
            if (exceptionHandler2.getPlugin().equals(exceptionHandler.getPlugin())) {
                exceptionHandlers.remove(exceptionHandler2);
            }
        }

        exceptionHandlers.add(exceptionHandler);
    }

    /**
     * Unregisters the {@link ExceptionHandler} of a binding {@link Plugin}.
     * 
     * @param plugin The {@link Plugin} to unregister.
     */
    public static void removeExceptionHandler(final Plugin plugin) {

        for (final ExceptionHandler exceptionHandler : exceptionHandlers) {
            if (exceptionHandler.getPlugin().equals(plugin)) {
                exceptionHandlers.remove(plugin);
            }
        }

        throw new IllegalStateException("ExceptionHandler for plugin " + plugin.getName() + " not set");
    }

    /**
     * Handles an {@link GameException} in the correct {@link ExceptionHandler}.
     * 
     * @param exception The {@link GameException} to handle.
     */
    public static void exception(final GameException exception) {

        for (final ExceptionHandler exceptionHandler : exceptionHandlers) {
            if (exceptionHandler.getPlugin().equals(exception.getPlugin())) {
                exceptionHandler.handle(exception);
                return;
            }
        }

        plugin.getLogger().warning("No exception handler set (can't catch " + exception.getClass() + ": " + exception.getLocalizedMessage() + ")");
    }

    private Metrics metrics;

    /**
     * The default constructor for Bukkit.
     */
    public QuarterBukkit() {

        if (plugin == null) {
            plugin = this;
        } else {
            throw new IllegalStateException("Plugin already initalized");
        }
    }

    /**
     * This method is called when the plugin loads. It will initalize the most important functions.
     * The plugin will check for new versions and updates, if required.
     */
    @Override
    public void onLoad() {

        try {
            new QuarterBukkitUpdater(this).tryUpdate();
        }
        catch (final Exception e) {
            Bukkit.getLogger().severe("An error occurred while updating QuarterBukkit (" + e.getClass() + ": " + e.getLocalizedMessage() + ")");
        }

        getLogger().info("Successfully loaded " + getName() + "!");
    }

    /**
     * This method is called when the plugin gets enabled.
     * It will initalize the main API functions.
     */
    @Override
    public void onEnable() {

        try {
            metrics = new Metrics(this);
            metrics.start();
        }
        catch (final IOException e) {
            getLogger().severe("Am error occurred while enabling Metrics (" + e.getClass() + ": " + e.getLocalizedMessage() + ")");
        }

        getLogger().info("Successfully enabled " + getName() + "!");
    }

    /**
     * This method is called when the plugin gets disabled.
     * It will disable the enabled API functions and clear the space.
     */
    @Override
    public void onDisable() {

        getLogger().info("Successfully disabled " + getName() + "!");
    }

}
