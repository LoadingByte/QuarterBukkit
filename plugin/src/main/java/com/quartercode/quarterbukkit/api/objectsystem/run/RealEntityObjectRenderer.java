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

package com.quartercode.quarterbukkit.api.objectsystem.run;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.object.RealEntityObject;

/**
 * A {@link Renderer} that updates all {@link RealEntityObject}s.
 * Currently, it just removes them if the underlying entities vanished by dying or logging out.
 *
 * @see RealEntityObject
 * @see Renderer
 */
public class RealEntityObjectRenderer extends StatelessRenderer<RealEntityObject> {

    @Override
    public Class<RealEntityObject> getObjectType() {

        return RealEntityObject.class;
    }

    @Override
    public RenderingResult render(Plugin plugin, ActiveObjectSystem objectSystem, long dt, RealEntityObject object) {

        // Remove the entity if it has vanished
        if (!object.getEntity().isValid()) {
            return RenderingResult.REMOVE;
        } else {
            return RenderingResult.NOTHING;
        }
    }

}
