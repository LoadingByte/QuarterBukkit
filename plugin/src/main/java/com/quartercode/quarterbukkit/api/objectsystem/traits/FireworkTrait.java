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

package com.quartercode.quarterbukkit.api.objectsystem.traits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang.Validate;
import com.quartercode.quarterbukkit.api.fx.firework.FireworkEffectDefinition;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;
import com.quartercode.quarterbukkit.api.objectsystem.TraitDependencies;

/**
 * A {@link Trait} that makes its {@link BaseObject object} spawn fireworks wherever it currently is.
 * By using the default power value 0, the firework immediately explodes, creating a firework effect at the current object position.
 * The effects can be freely configured and support every feature of fireworks.<br>
 * <br>
 * <b>Trait dependencies:</b> {@link PhysicsTrait}
 *
 * @see FireworkEffectDefinition
 * @see Trait
 */
@TraitDependencies (PhysicsTrait.class)
public class FireworkTrait extends Trait {

    private final Collection<FireworkEffectDefinition> effects             = new ArrayList<>();
    private boolean                                    speedBasedFrequency = true;

    /**
     * Creates a new firework trait which doesn't have any {@link FireworkEffectDefinition}s yet.
     */
    public FireworkTrait() {

    }

    /**
     * Creates a new firework trait with the given {@link FireworkEffectDefinition}s that define how the spawned fireworks look like when they explode.
     * Those effects will be applied to each spawned firework.
     *
     * @param effects The firework effect definitions that should be used.
     */
    public FireworkTrait(FireworkEffectDefinition... effects) {

        addEffects(effects);
    }

    /**
     * Creates a new firework trait with the given {@link FireworkEffectDefinition}s that define how the spawned fireworks look like when they explode.
     * Those effects will be applied to each spawned firework.
     *
     * @param effects The firework effect definitions that should be used.
     */
    public FireworkTrait(Collection<FireworkEffectDefinition> effects) {

        addEffects(effects);
    }

    /**
     * Returns the {@link FireworkEffectDefinition}s that define how the spawned fireworks look like when they explode.
     * All effects will be applied to each spawned firework.
     *
     * @return The firework effect definitions that define the look of the fireworks.
     */
    public Collection<FireworkEffectDefinition> getEffects() {

        return Collections.unmodifiableCollection(effects);
    }

    /**
     * Adds the given {@link FireworkEffectDefinition}s that define how the spawned fireworks look like when they explode.
     * The new effects and the already existing ones will be applied to each spawned firework.
     *
     * @param effects The firework effect definitions that should be added.
     * @return This object.
     */
    public FireworkTrait addEffects(FireworkEffectDefinition... effects) {

        addEffects(Arrays.asList(effects));
        return this;
    }

    /**
     * Adds the given {@link FireworkEffectDefinition}s that define how the spawned fireworks look like when they explode.
     * The new effects and the already existing ones will be applied to each spawned firework.
     *
     * @param effects The firework effect definitions that should be added.
     * @return This object.
     */
    public FireworkTrait addEffects(Collection<FireworkEffectDefinition> effects) {

        Validate.noNullElements(effects, "Cannot add null firework effect definitions to firework explosion object");
        this.effects.addAll(effects);
        return this;
    }

    /**
     * Removes the given {@link FireworkEffectDefinition}s so they will no longer influence how the spawned fireworks look like when they explode.
     * The removed effects won't be applied to each spawned firework any longer.
     *
     * @param effects The firework effect definitions that should be removed.
     * @return This object.
     */
    public FireworkTrait removeEffects(FireworkEffectDefinition... effects) {

        removeEffects(Arrays.asList(effects));
        return this;
    }

    /**
     * Removes the given {@link FireworkEffectDefinition}s so they will no longer influence how the spawned fireworks look like when they explode.
     * The removed effects won't be applied to each spawned firework any longer.
     *
     * @param effects The firework effect definitions that should be removed.
     * @return This object.
     */
    public FireworkTrait removeEffects(Collection<FireworkEffectDefinition> effects) {

        this.effects.removeAll(effects);
        return this;
    }

    /**
     * Returns whether the speed based frequency adjustment mode is enabled.
     * If it is, objects with a low velocity will spawn fireworks less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     *
     * @return Whether speed based frequency is enabled.
     */
    public boolean hasSpeedBasedFrequency() {

        return speedBasedFrequency;
    }

    /**
     * Sets whether the speed based frequency adjustment mode is enabled.
     * If it is, objects with a low velocity will spawn fireworks less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     *
     * @param speedBasedFrequency Whether speed based frequency should be enabled.
     * @return This object.
     */
    public FireworkTrait setSpeedBasedFrequency(boolean speedBasedFrequency) {

        this.speedBasedFrequency = speedBasedFrequency;
        return this;
    }

}
