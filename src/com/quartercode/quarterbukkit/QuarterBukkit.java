
package com.quartercode.quarterbukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class QuarterBukkit extends JavaPlugin {

    public QuarterBukkit() {

    }

    @Override
    public void onEnable() {

        getDataFolder().mkdirs();

        getLogger().info("Successfully enabled " + getName() + "!");
    }

    @Override
    public void onDisable() {

    }

}
