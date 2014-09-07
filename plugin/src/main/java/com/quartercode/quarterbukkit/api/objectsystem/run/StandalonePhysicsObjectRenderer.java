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
import com.quartercode.quarterbukkit.api.objectsystem.physics.StandalonePhysicsObject;

/**
 * A {@link Renderer} that simulates the physics of all {@link StandalonePhysicsObject}s.
 * For doing that, it applies the objects' current velocity vectors the the position vectors.
 * 
 * @see StandalonePhysicsObject
 * @see Renderer
 */
public class StandalonePhysicsObjectRenderer extends StatelessRenderer<StandalonePhysicsObject> {

    @Override
    public Class<StandalonePhysicsObject> getObjectType() {

        return StandalonePhysicsObject.class;
    }

    @Override
    public RenderingResult render(Plugin plugin, ActiveObjectSystem objectSystem, StandalonePhysicsObject object) {

        object.setPosition(object.getPosition().add(object.getVelocity()));

        return RenderingResult.NOTHING;
    }

}
