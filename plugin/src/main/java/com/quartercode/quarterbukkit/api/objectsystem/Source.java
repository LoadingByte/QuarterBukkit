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

import org.bukkit.plugin.Plugin;

/**
 * Object sources are responsible for spawning new objects into an {@link ActiveObjectSystem}.
 * That can either be done deterministically or randomly.
 * Note that sources can be chained together using the {@link SourceWrapper} class.
 *
 * @see ObjectSystemDefinition
 */
public interface Source {

    /**
     * Spawns new objects into the given {@link ActiveObjectSystem} based on some rules.
     * Note the important variable {@code dt} which basically informs you about the currently used time resolution.
     *
     * @param plugin The {@link Plugin} that started a runner which now simulates the active system and called the object source.
     * @param objectSystem The active system new objects should be spawned into.
     * @param dt The amount of milliseconds which have elapsed since the last update of the object system this source is part of.
     */
    public void update(Plugin plugin, ActiveObjectSystem objectSystem, long dt);

}
