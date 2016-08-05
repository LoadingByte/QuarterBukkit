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

package com.quartercode.quarterbukkit.api.objectsystem;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.quartercode.quarterbukkit.api.objectsystem.run.Renderer;

/**
 * A modification rule takes a {@link Trait} and then lets a {@link Modifier} calculate a modification of a specific property of that trait.
 * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the physics trait.
 * Afterwards, the rule lets a {@link ModificationApplier} actually apply the modification to the trait property.
 * Continuing the previous example, a velocity modification applier would add the velocity modification vector to the current velocity vector of the physics trait.<br>
 * <br>
 * Note that a modifier can be wrapped around another one using a {@link ModifierWrapper} in order to create a modifier chain.
 * The first modifier of that chain can then be set as the rule modifier.
 * That way, different modifiers can be combined together.
 * For example, a second modifier could adjust the actual velocity modification depending on the location of the physics trait.<br>
 * <br>
 * Some may ask why there's a distinction between modification rules and {@link Renderer}s.
 * One clear difference is the broader and more flexible approach the rendering system takes.
 * For example, it operates on object basis instead of trait basis and therefore provides way less abstraction.
 * Moreover, and maybe more importantly, frontend users generally capture the characteristics of an object system in modification rules, while backend developers use renderers to implement fundamental
 * behavior like the laws of physics.
 *
 * @param <T> The type of trait that can be modified by the rule. This must extend {@link Trait}.
 * @param <M> The type of the modification object that is returned by the modifier.
 *        For example, a velocity modifier would use a vector as modification object.
 * @see Trait
 * @see ObjectSystemDefinition
 */
public class ModificationRule<T extends Trait, M> {

    private final Class<T>                    traitType;
    private ModificationApplier<? super T, M> applier;
    private Modifier<? super T, M>            modifier;

    /**
     * Creates a new modification rule that modifies {@link Trait}s of the given type using the given {@link Modifier} and
     * then lets the given {@link ModificationApplier} execute the changes.
     *
     * @param traitType The type of trait that can be modified by the rule. This must extend {@link Trait}.
     * @param applier The modification applier which is responsible for applying the modification to the actual trait property.
     *        See {@link ModificationRule} for more information on that.
     * @param modifier The modifier which is responsible for calculating the changes to the trait property.
     */
    public ModificationRule(Class<T> traitType, ModificationApplier<? super T, M> applier, Modifier<? super T, M> modifier) {

        this.traitType = traitType;
        setApplier(applier);
        setModifier(modifier);
    }

    /**
     * Returns the type of trait that can be modified by the rule. This must extend {@link Trait}.
     *
     * @return A class object which specifies the supported trait type.
     */
    public Class<T> getTraitType() {

        return traitType;
    }

    /**
     * Returns the {@link ModificationApplier} which is responsible for applying the changes made by the {@link Modifier} to an actual {@link Trait} property.
     * For example, a velocity modification applier would add the velocity modification vector to the current velocity vector of the physics trait.
     * See {@link ModificationRule} for more information on that.
     *
     * @return The set modification applier.
     */
    public ModificationApplier<? super T, M> getApplier() {

        return applier;
    }

    /**
     * Sets the {@link ModificationApplier} which is responsible for applying the changes made by the {@link Modifier} to an actual {@link Trait} property.
     * For example, a velocity modification applier would add the velocity modification vector to the current velocity vector of the physics trait.
     * See {@link ModificationRule} for more information on that.
     *
     * @param applier The new modification applier.
     */
    public void setApplier(ModificationApplier<? super T, M> applier) {

        Validate.notNull(applier, "Modification applier of modification rule cannot be null");
        this.applier = applier;
    }

    /**
     * Returns the {@link Modifier} which is responsible for calculating the changes to the modified {@link Trait} property.
     * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the physics trait.
     * See {@link ModificationRule} for more information on that.
     *
     * @return The set modifier.
     */
    public Modifier<? super T, M> getModifier() {

        return modifier;
    }

    /**
     * Sets the {@link Modifier} which is responsible for calculating the changes to the modified {@link Trait} property.
     * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the physics trait.
     * See {@link ModificationRule} for more information on that.
     *
     * @param modifier The new modifier.
     */
    public void setModifier(Modifier<? super T, M> modifier) {

        Validate.notNull(modifier, "Modifier of modification rule cannot be null");
        this.modifier = modifier;
    }

    /**
     * Applies the modification rule to the given {@link Trait}.
     * This first calls the {@link Modifier} and then passes the changes into the {@link ModificationApplier}.
     *
     * @param dt The amount of milliseconds which have elapsed since the last update of the object system this modification rule is part of.
     * @param trait The trait that should be modified.
     */
    public void apply(long dt, T trait) {

        applier.applyModification(trait, modifier.getModification(dt, trait));
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
