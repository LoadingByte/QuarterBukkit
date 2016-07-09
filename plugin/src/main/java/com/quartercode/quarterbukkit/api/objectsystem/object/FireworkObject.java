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

package com.quartercode.quarterbukkit.api.objectsystem.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang.Validate;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.physics.StandalonePhysicsObject;

/**
 * A {@link StandalonePhysicsObject} that represents a firework which is spawned at the different positions of the object.
 * By using the default power value 0, the firework immediately explodes creating a firework effect at the current object position.
 * The effects can be freely configured and support every feature of fireworks.
 *
 * @see FireworkEffectDefinition
 */
public class FireworkObject extends StandalonePhysicsObject {

    private int                                  power               = 0;
    private Collection<FireworkEffectDefinition> effects             = new ArrayList<FireworkEffectDefinition>();
    private boolean                              speedBasedFrequency = true;

    /**
     * Creates a new firework object that never expires, is located at the origin of its {@link ActiveObjectSystem} and doesn't move initially.
     */
    public FireworkObject() {

        super();
    }

    /**
     * Creates a new firework object that never expires, is located at the given position and doesn't move initially.
     *
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public FireworkObject(Vector position) {

        super(position);
    }

    /**
     * Creates a new firework object that never expires, is located at the given position and initially moves with the given velocity.
     *
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public FireworkObject(Vector position, Vector velocity) {

        super(position, velocity);
    }

    /**
     * Creates a new firework object that is removed after the given amount of updates, is located at the origin of its {@link ActiveObjectSystem} and doesn't move initially.
     *
     * @param expirationTime The amount of updates after which the object expires and is removed.
     */
    public FireworkObject(int expirationTime) {

        super(expirationTime);
    }

    /**
     * Creates a new firework object that is removed after the given amount of updates, is located at the given position and doesn't move initially.
     *
     * @param expirationTime The amount of updates after which the object expires and is removed.
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     */
    public FireworkObject(int expirationTime, Vector position) {

        super(expirationTime, position);
    }

    /**
     * Creates a new firework object that is removed after the given amount of updates, is located at the given position and initially moves with the given velocity.
     *
     * @param expirationTime The amount of updates after which the object expires and is removed.
     * @param position The initial position {@link Vector}, which is relative to the origin of the object's {@link ActiveObjectSystem}, of the new object.
     * @param velocity The initial velocity {@link Vector}, which defines the initial movement, of the new object.
     */
    public FireworkObject(int expirationTime, Vector position, Vector velocity) {

        super(expirationTime, position, velocity);
    }

    /**
     * Returns the approximate height all spawned fireworks will fly.
     *
     * @return The approximate flight height.
     */
    public int getPower() {

        return power;
    }

    /**
     * Sets the approximate flight height of all spawned fireworks.
     * Each level of power is half a second of flight time.
     *
     * @param power The new approximate flight height of all spawned fireworks.
     *        Must be between 0 and 128.
     * @return This object.
     */
    public FireworkObject setPower(int power) {

        Validate.isTrue(power >= 0 && power <= 128, "Firework object power must be > 0");
        this.power = power;
        return this;
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
    public FireworkObject addEffects(FireworkEffectDefinition... effects) {

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
    public FireworkObject addEffects(Collection<FireworkEffectDefinition> effects) {

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
    public FireworkObject removeEffects(FireworkEffectDefinition... effects) {

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
    public FireworkObject removeEffects(Collection<FireworkEffectDefinition> effects) {

        this.effects.removeAll(effects);
        return this;
    }

    /**
     * Returns whether the speed based frequency adjustment mode is enabled.
     * If it is, firework objects with a low velocity will spawn less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     *
     * @return Whether speed based frequency is enabled.
     */
    public boolean hasSpeedBasedFrequency() {

        return speedBasedFrequency;
    }

    /**
     * Sets whether the speed based frequency adjustment mode is enabled.
     * If it is, firework objects with a low velocity will spawn less frequently than ones with a high velocity.
     * That reduces the overall number of spawned particles in order to prevent the despawning of old ones.
     *
     * @param speedBasedFrequency Whether speed based frequency should be enabled.
     * @return This object.
     */
    public FireworkObject setSpeedBasedFrequency(boolean speedBasedFrequency) {

        this.speedBasedFrequency = speedBasedFrequency;
        return this;
    }

    @Override
    public FireworkObject clone() {

        FireworkObject clone = (FireworkObject) super.clone();

        // Deep cloning
        clone.effects = new ArrayList<FireworkEffectDefinition>(effects);

        return clone;
    }

}
