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

package com.quartercode.quarterbukkit.api.objectsystem;

import java.util.Random;
import org.bukkit.plugin.Plugin;

/**
 * Object sources are responsible for spawning new objects into an {@link ActiveObjectSystem}.
 * That can either be done deterministically or randomly using a provided {@link Random} object.
 *
 * @see ObjectSystemDefinition
 */
public interface Source {

    /**
     * Spawns new objects into the given {@link ActiveObjectSystem} based on some rules.
     * If the object spawning is done randomly, the provided {@link Random} object can be used.
     *
     * @param plugin The {@link Plugin} that started a runner which now simulates the active system and called the object source.
     * @param objectSystem The active system new objects should be spawned into.
     * @param random The random object that should be used for random decisions.
     */
    public void update(Plugin plugin, ActiveObjectSystem objectSystem, Random random);

}
