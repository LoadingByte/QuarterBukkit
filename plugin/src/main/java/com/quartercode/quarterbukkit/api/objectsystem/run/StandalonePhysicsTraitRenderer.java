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
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.traits.StandalonePhysicsTrait;

/**
 * A {@link Renderer} that simulates the physics of all {@link BaseObject objects} with the {@link StandalonePhysicsTrait}.
 * For doing that, it applies the objects' current velocity vectors the the position vectors.
 *
 * @see StandalonePhysicsTrait
 * @see Renderer
 */
public class StandalonePhysicsTraitRenderer extends StatelessRenderer {

    @Override
    public void render(Plugin plugin, long dt, BaseObject object) {

        object.get(StandalonePhysicsTrait.class).ifPresent(physics -> {
            Vector positionChange = physics.getVelocity().multiply(dt / 1000d);
            physics.setPosition(physics.getPosition().add(positionChange));
        });
    }

}
