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

import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.fx.firework.FireworkEffectDefinition;
import com.quartercode.quarterbukkit.api.fx.firework.FireworkEffectSpawner;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.traits.FireworkTrait;
import com.quartercode.quarterbukkit.api.objectsystem.traits.PhysicsTrait;

/**
 * A {@link Renderer} that displays all {@link BaseObject objects} with {@link FireworkTrait}s by spawning fireworks.
 *
 * @see FireworkTrait
 * @see Renderer
 */
public class FireworkTraitRenderer extends StatelessRenderer {

    @Override
    public void render(Plugin plugin, long dt, BaseObject object) {

        object.get(PhysicsTrait.class, FireworkTrait.class).ifPresent((physics, frwk) -> {
            // Determine whether the different effects should be spawned
            double objectVelocity = physics.getVelocity().length();
            boolean spawnNoTrailObjects = !frwk.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object.getLifetime(), objectVelocity, 0.5F);
            boolean spawnTrailObjects = !frwk.hasSpeedBasedFrequency() || RenderingUtils.checkSpeedBasedFrequency(object.getLifetime(), objectVelocity, 0.75F);

            // Collect all effects that are spawned this round
            List<FireworkEffectDefinition> spawnEffects = new ArrayList<>();
            for (FireworkEffectDefinition effect : frwk.getEffects()) {
                if (!effect.hasTrail() && spawnNoTrailObjects || effect.hasTrail() && spawnTrailObjects) {
                    spawnEffects.add(effect);
                }
            }

            // Check whether at least one effect would be spawned this round
            if (spawnEffects.isEmpty()) {
                return;
            }

            // Actually spawn all effects for this round
            FireworkEffectSpawner.spawn(object.getSystem().getOrigin().add(physics.getPosition()), frwk.getPower(), spawnEffects);
        });
    }

}
