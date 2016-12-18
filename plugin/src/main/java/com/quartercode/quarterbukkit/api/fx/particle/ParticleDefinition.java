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

package com.quartercode.quarterbukkit.api.fx.particle;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.util.Vector;

/**
 * This class represents a single particle that can spawned in the Minecraft world.
 * Almost all particle effects provided by Minecraft are supported by this class.
 *
 * @see ParticleType
 */
public class ParticleDefinition {

    private ParticleType type;
    private int          amount = 1;
    private Vector       spread = new Vector();
    private float        parameter;

    /**
     * Creates a new particle definition with the given {@link ParticleType}.
     *
     * @param type The particle type that defines which kind of particle should be spawned.
     */
    public ParticleDefinition(ParticleType type) {

        setType(type);
    }

    /**
     * Creates a new particle definition that uses the information which is stored inside the given particle definition.
     *
     * @param from The particle definition to copy the initial information from.
     */
    public ParticleDefinition(ParticleDefinition from) {

        from(from);
    }

    /**
     * Returns the {@link ParticleType} that defines which kind of particle should be spawned.
     *
     * @return The particle type.
     * @see ParticleType
     */
    public ParticleType getType() {

        return type;
    }

    /**
     * Sets the {@link ParticleType} that defines which kind of particle should be spawned.
     *
     * @param type The new particle type.
     * @return This object.
     * @see ParticleType
     */
    public ParticleDefinition setType(ParticleType type) {

        Validate.notNull(type, "Particle type cannot be null");
        this.type = type;
        return this;
    }

    /**
     * Returns the amount of spawned individual particles.
     * This is quite useful in combination with the spread vector (see {@link #getSpread()}).
     *
     * @return The particle amount.
     */
    public int getAmount() {

        return amount;
    }

    /**
     * Returns the amount of spawned individual particles.
     * This is quite useful in combination with the spread vector (see {@link #setSpread(Vector)}).
     *
     * @param amount The new particle amount.
     * @return This object.
     */
    public ParticleDefinition setAmount(int amount) {

        Validate.isTrue(amount > 0, "Amount must be > 0: ", amount);
        this.amount = amount;
        return this;
    }

    /**
     * Returns the {@link Vector} that defines how far away from the spawning location individual particles can appear.
     * For example, if the x component of the vector is 2, the particle can spawn up to 2 blocks above or below the spawning location.
     * Note that the amount property is quite useful in combination with the spread vector, since you can use high amounts to create a spread of multiple individual particles (see {@link #getAmount()}).
     *
     * @return The spread vector.
     */
    public Vector getSpread() {

        return spread.clone();
    }

    /**
     * Sets the {@link Vector} that defines how far away from the spawning location individual particles can appear.
     * For example, if the x component of the vector is 2, the particle can spawn up to 2 blocks above or below the spawning location.
     * Note that the amount property is quite useful in combination with the spread vector, since you can use high amounts to create a spread of multiple individual particles (see {@link #getAmount()}).
     *
     * @param spread The new spread vector.
     * @return This object.
     */
    public ParticleDefinition setSpread(Vector spread) {

        Validate.notNull("Spread vector cannot be null");
        this.spread = spread.clone();
        return this;
    }

    /**
     * Returns the parameter that customizes the spawned particles.
     * The effect of the parameter is different for each {@link ParticleType}.
     * See the description of the different types for more information.
     * Note that some types do not support a parameter. The {@link ParticleType#hasParameter()} returns {@code false} for those types.
     *
     * @return The customization parameter.
     */
    public float getParameter() {

        return parameter;
    }

    /**
     * Sets the parameter that customizes the spawned particles.
     * The effect of the parameter is different for each {@link ParticleType}.
     * See the description of the different types for more information.
     * Note that some types do not support a parameter. The {@link ParticleType#hasParameter()} returns {@code false} for those types.
     * If such a type is currently set for the definition, this throws an exception.
     *
     * @param parameter The customization parameter.
     * @return This object.
     */
    public ParticleDefinition setParameter(float parameter) {

        Validate.isTrue(type.hasParameter(), "Cannot use parameter with non-parameter particle type: ", type);
        Validate.isTrue(parameter >= 0, "Parameter must be >= 0: ", parameter);
        this.parameter = parameter;
        return this;
    }

    /**
     * Copies the the information which is stored inside the given particle definition into this one.
     * After the operation completed, the two definitions are exact copies of each other.
     *
     * @param from The particle definition to copy the information from.
     * @return This object.
     */
    public ParticleDefinition from(ParticleDefinition from) {

        type = from.getType();
        amount = from.getAmount();
        spread = from.getSpread();
        parameter = from.getParameter();

        return this;
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
