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

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.object.ParticleObject;

/**
 * A {@link Renderer} that displays all {@link ParticleObject}s by spawning minecraft particles.
 * 
 * @see ParticleObject
 * @see Renderer
 */
public class ParticleRenderer extends StatelessRenderer<ParticleObject> {

    @Override
    public Class<ParticleObject> getObjectType() {

        return ParticleObject.class;
    }

    @Override
    public RenderingResult render(Plugin plugin, ActiveObjectSystem objectSystem, ParticleObject object) {

        if (object.hasSpeedBasedFrequency() && !RenderingUtils.checkSpeedBasedFrequency(object, 0.5F)) {
            return RenderingResult.NOTHING;
        }

        Location location = objectSystem.getOrigin().add(object.getPosition());

        // TODO: Write the particle rendering code

        return RenderingResult.NOTHING;
    }

}
