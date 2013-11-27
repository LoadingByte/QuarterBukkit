
package com.quartercode.quarterbukkit.api.particle;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Interface for particle spawners which spawn/render the particles.
 */
public interface ParticleSpawner {

    /**
     * Spawns/Renders the particles of a particle run.
     * 
     * @param plugin The {@link Plugin} for exception-handling etc.
     * @param descriptions The {@link ParticleDescription}s for the particle effect.
     * @param location The spawn {@link Location} for the particles.
     */
    public void spawn(Plugin plugin, final List<ParticleDescription> descriptions, final Location location, List<Player> players);

}
