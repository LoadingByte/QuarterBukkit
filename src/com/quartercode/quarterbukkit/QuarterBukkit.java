
package com.quartercode.quarterbukkit;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.quartercode.quarterbukkit.util.VersionUtil;

/**
 * The main class of the QuarterBukkit API.
 * This is for easy API loading.
 */
public class QuarterBukkit extends JavaPlugin {

    /**
     * The default constructor for Bukkit.
     */
    public QuarterBukkit() {

    }

    /**
     * This method is called when the plugin loads. It will initalize the most important functions.
     * The plugin will check for new versions and updates, if required.
     */
    @Override
    public void onLoad() {

        try {
            VersionUtil.tryUpdate(this);
        }
        catch (final Exception e) {
            Bukkit.getLogger().severe("An error occurred while updating QuarterBukkit: " + e.getClass() + ": " + e.getLocalizedMessage());
        }

        getLogger().info("Successfully loaded " + getName() + "!");
    }

    /**
     * This method is called when the plugin gets enabled.
     * It will initalize the main API functions.
     */
    @Override
    public void onEnable() {

        getDataFolder().mkdirs();

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
