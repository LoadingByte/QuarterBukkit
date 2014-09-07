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
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;

/**
 * A {@link Renderer} that updates the lifetime of all {@link BaseObject}s and removes them if they expired.
 * 
 * @see BaseObject
 * @see Renderer
 */
public class BaseObjectRenderer extends StatelessRenderer<BaseObject> {

    @Override
    public Class<BaseObject> getObjectType() {

        return BaseObject.class;
    }

    @Override
    public RenderingResult render(Plugin plugin, ActiveObjectSystem objectSystem, BaseObject object) {

        RenderingResult result;
        if (object.getExpirationTime() != -1 && object.getLifetime() >= object.getExpirationTime()) {
            result = RenderingResult.REMOVE;
        } else {
            result = RenderingResult.NOTHING;
        }

        object.incrementLifetime();

        return result;
    }

}
