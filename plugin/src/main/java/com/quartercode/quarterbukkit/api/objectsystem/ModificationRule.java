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

/**
 * A modification rule takes an object and then lets a {@link Modifier} calculate a modification of a specific property of that object.
 * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the object.
 * Afterwards, the rule lets a {@link ModificationApplier} actually apply the modification to the object property.
 * Continuing the previous example, a velocity modification applier would add the velocity modification vector to the current velocity vector of the object.<br>
 * <br>
 * Note that a modifier can be wrapped around another one using a {@link ModifierWrapper} in order to create a modifier chain.
 * The first modifier of that chain can then be set as the rule modifier.
 * That way, different modifiers can be combined together.
 * For example, a second modifier could adjust the actual velocity modification depending on the location of the object.
 *
 * @param <O> The type of object that can be modified by the rule. This must extend {@link BaseObject}.
 * @param <M> The type of the modification object that is returned by the modifier.
 *        For example, a velocity modifier would use a vector as modification object.
 * @see ObjectSystemDefinition
 */
public class ModificationRule<O extends BaseObject, M> {

    private final Class<O>            objectType;
    private ModificationApplier<O, M> applier;
    private Modifier<O, M>            modifier;

    /**
     * Creates a new modification rule that modifies objects of the given type and then lets the given {@link ModificationApplier} execute the changes.
     *
     * @param objectType The type of object that can be modified by the rule. This must extend {@link BaseObject}.
     * @param applier The modification applier, which is responsible for applying the modification to the actual object property.
     *        See {@link ModificationRule} for more information on that.
     */
    public ModificationRule(Class<O> objectType, ModificationApplier<O, M> applier) {

        setApplier(applier);
        this.objectType = objectType;
    }

    /**
     * Creates a new modification rule that modifies objects of the given type using the given {@link Modifier} and
     * then lets the given {@link ModificationApplier} execute the changes.
     *
     * @param objectType The type of object that can be modified by the rule. This must extend {@link BaseObject}.
     * @param applier The modification applier which is responsible for applying the modification to the actual object property.
     *        See {@link ModificationRule} for more information on that.
     * @param modifier The modifier which is responsible for calculating the changes to the object property.
     */
    public ModificationRule(Class<O> objectType, ModificationApplier<O, M> applier, Modifier<O, M> modifier) {

        this(objectType, applier);

        setModifier(modifier);
    }

    /**
     * Returns the type of object that can be modified by the rule. This must extend {@link BaseObject}.
     *
     * @return A class object which specifies the supported object types.
     */
    public Class<O> getObjectType() {

        return objectType;
    }

    /**
     * Returns the {@link ModificationApplier} which is responsible for applying the changes made by the {@link Modifier} to an actual object property.
     * For example, a velocity modification applier would add the velocity modification vector to the current velocity vector of the object.
     * See {@link ModificationRule} for more information on that.
     *
     * @return The set modification applier.
     */
    public ModificationApplier<O, M> getApplier() {

        return applier;
    }

    /**
     * Sets the {@link ModificationApplier} which is responsible for applying the changes made by the {@link Modifier} to an actual object property.
     * For example, a velocity modification applier would add the velocity modification vector to the current velocity vector of the object.
     * See {@link ModificationRule} for more information on that.
     *
     * @param applier The new modification applier.
     */
    public void setApplier(ModificationApplier<O, M> applier) {

        Validate.notNull(applier, "Modification applier of modification rule cannot be null");
        this.applier = applier;
    }

    /**
     * Returns the {@link Modifier} which is responsible for calculating the changes to the modified object property.
     * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the object.
     * See {@link ModificationRule} for more information on that.
     *
     * @return The set modifier.
     */
    public Modifier<O, M> getModifier() {

        return modifier;
    }

    /**
     * Sets the {@link Modifier} which is responsible for calculating the changes to the modified object property.
     * For example, a velocity modifier would return a modification vector that adjusts the velocity vector of the object.
     * See {@link ModificationRule} for more information on that.
     *
     * @param modifier The new modifier.
     */
    public void setModifier(Modifier<O, M> modifier) {

        Validate.notNull(modifier, "Modifier of modification rule cannot be null");
        this.modifier = modifier;
    }

    /**
     * Applies the modification rule to the given object.
     * This first calls the {@link Modifier} and then passes the changes into the {@link ModificationApplier}.
     *
     * @param object The object that should be modified.
     */
    public void apply(O object) {

        applier.applyModification(object, modifier.getModification(object));
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
