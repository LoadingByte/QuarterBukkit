/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api.particle;

import java.util.List;
import org.bukkit.Location;
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
    public void spawn(Plugin plugin, final List<ParticleDescription> descriptions, final Location location);

}
