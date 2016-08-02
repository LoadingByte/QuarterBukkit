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

package com.quartercode.quarterbukkit.api.objectsystem.util;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.Source;
import com.quartercode.quarterbukkit.api.objectsystem.SourceWrapper;

/**
 * A {@link Source} wrapper that only calls its wrapped source once when the object system is first started, resulting in some kind of "initialization source".
 * It's extremely useful when your particles live forever and therefore only need to be added once.
 */
public class InitializationSource extends SourceWrapper {

    /**
     * Creates a new initialization source and immediately sets the wrapped {@link Source} which should be called only once.
     *
     * @param wrapped The wrapped source which should only be called one.
     */
    public InitializationSource(Source wrapped) {

        super(false, wrapped);
    }

    @Override
    public void update(Plugin plugin, ActiveObjectSystem objectSystem, long dt) {

        if (objectSystem.getLifetime() == 0) {
            getWrapped().update(plugin, objectSystem, dt);
        }
    }

}
