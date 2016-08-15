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
import com.quartercode.quarterbukkit.api.fx.particle.ParticleDefinition;
import com.quartercode.quarterbukkit.api.fx.particle.ParticleSpawner;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.traits.ParticleTrait;
import com.quartercode.quarterbukkit.api.objectsystem.traits.PhysicsTrait;

/**
 * A {@link Renderer} that displays all {@link BaseObject objects} with {@link ParticleTrait}s by spawning Minecraft particles.
 *
 * @see ParticleTrait
 * @see Renderer
 */
public class ParticleTraitRenderer extends StatelessRenderer {

    @Override
    public void render(Plugin plugin, long dt, BaseObject object) {

        object.get(PhysicsTrait.class, ParticleTrait.class).ifPresent((physics, prtcl) -> {
            if (!prtcl.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object, 0.5F)) {
                Location location = object.getSystem().getOrigin().add(physics.getPosition());
                for (ParticleDefinition particle : prtcl.getParticles()) {
                    ParticleSpawner.spawn(location, particle);
                }
            }
        });
    }

}
