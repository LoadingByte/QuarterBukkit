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

package com.quartercode.quarterbukkit.api.objectsystem.util;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.Modifier;
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;

/**
 * A velocity {@link Modifier} that returns a velocity modification {@link Vector} which accelerates the {@link PhysicsObject} towards or away from a certain point.
 * It can be compared with a (possibly inverted) gravitational force that pulls every object towards or away from its center.
 * Note that you can limit the velocity modification to a specific area using the {@link ShapedModifier} wrapper.
 *
 * @see PhysicsObject
 * @see ShapedModifier
 */
public class TargetedForceVelocityModifier implements Modifier<PhysicsObject, Vector> {

    /**
     * The different types of targeted velocity modifications.
     * They have different behaviors depending on the distance of a {@link PhysicsObject} to the target point.
     *
     * @see TargetedForceVelocityModifier
     */
    public static enum TargetedForceType {

        /**
         * Takes the position {@link Vector} of the {@link PhysicsObject} to the target and multiplies it by the negative value of the provided factor.
         * That means that the acceleration is bigger if the object is farther away from the target point.
         * If the object is really close to the center, the acceleration is very close to zero.<br>
         * <br>
         * The default factor is 1.
         */
        NEGATIVE_POSITION_VECTOR (1),
        /**
         * Takes the position {@link Vector} of the {@link PhysicsObject} to the target, uniforms its length and multiplies it by the negative value of the provided factor.
         * That means that the acceleration doesn't change if the object is farther away from or closer to the target point.<br>
         * <br>
         * The default factor is 1.
         */
        UNIFORM (1),
        /**
         * Takes the position {@link Vector} of the {@link PhysicsObject} to the target, uniforms its length and multiplies it by the result of a basic gravitational formula without mass.
         * Basically, the vector is multiplied with the result of the following term:
         *
         * <pre>
         * - (factor / distance &circ; 2)
         * </pre>
         *
         * This type might be dangerous because the acceleration becomes really high when an object gets closer to the target.
         * The velocity change is squared each time the distance between the object and the target halves.
         * Actually, the acceleration is infinite when the object directly passes through center.
         * However, realistic single body orbital mechanics can be simulated if all objects don't get to close.<br>
         * <br>
         * The default factor is 500.
         */
        GRAVITY (500);

        private final float defaultFactor;

        private TargetedForceType(float defaultFactor) {

            this.defaultFactor = defaultFactor;
        }

        /**
         * Returns the default factor that is used for the type if no factor is explicitly specified.
         *
         * @return The default factor of the type.
         */
        public float getDefaultFactor() {

            return defaultFactor;
        }

    }

    private static final Vector ZERO_VECTOR        = new Vector();

    private TargetedForceType   type;
    private Vector              target;
    private float               factor;
    private boolean[]           ignoredComponenets = new boolean[3];

    private Vector              effectiveTarget;

    /**
     * Creates a new targeted velocity modifier that makes {@link PhysicsObject}s accelerate towards the given target {@link Vector}.
     * This uses the {@link TargetedForceType#NEGATIVE_POSITION_VECTOR} type and its default factor.
     *
     * @param target The target vector all objects accelerate towards.
     *        Note that this vector is always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     */
    public TargetedForceVelocityModifier(Vector target) {

        this(target, TargetedForceType.NEGATIVE_POSITION_VECTOR);
    }

    /**
     * Creates a new targeted velocity modifier that makes {@link PhysicsObject}s accelerate towards or away from the given target {@link Vector} with the given factor.
     * This uses the {@link TargetedForceType#NEGATIVE_POSITION_VECTOR} type.
     *
     * @param target The target vector all objects accelerate towards or away from.
     *        Note that this vector is always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param factor The factor that defines how fast an object accelerates towards or away from the target vector.
     *        By choosing this value to be negative, all objects accelerate away from the target.
     *        Note that the range of appropriate values highly depends on the modification type.
     *        You should look up the default factor of {@link TargetedForceType#NEGATIVE_POSITION_VECTOR} to get a starting point for further experiments.
     */
    public TargetedForceVelocityModifier(Vector target, float factor) {

        this(target, TargetedForceType.NEGATIVE_POSITION_VECTOR, factor);
    }

    /**
     * Creates a new targeted velocity modifier that makes {@link PhysicsObject}s accelerate towards the given target {@link Vector} with the given {@link TargetedForceType}.
     * This uses the default factor of the given modification type.
     *
     * @param target The target vector all objects accelerate towards.
     *        Note that this vector is always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param type The modification type that defines how the acceleration changes when the distance between the object and the target changes
     */
    public TargetedForceVelocityModifier(Vector target, TargetedForceType type) {

        this(target, type, type.getDefaultFactor());
    }

    /**
     * Creates a new targeted velocity modifier that makes {@link PhysicsObject}s accelerate towards or away from the given target {@link Vector} with the given {@link TargetedForceType} and factor.
     *
     * @param target The target vector all objects accelerate towards or away from.
     *        Note that this vector is always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     * @param type The modification type that defines how the acceleration changes when the distance between the object and the target changes
     * @param factor The factor that defines how fast an object accelerates towards or away from the target vector.
     *        By choosing this value to be negative, all objects accelerate away from the target.
     *        Note that the range of appropriate values highly depends on the modification type.
     *        You should look up the default factors of the different types to get a starting point for further experiments.
     */
    public TargetedForceVelocityModifier(Vector target, TargetedForceType type, float factor) {

        setTarget(target);
        this.factor = factor;
        setType(type);
    }

    /**
     * Returns the {@link TargetedForceType} that defines how the acceleration changes when the distance between the {@link PhysicsObject} and the target changes.
     *
     * @return The modification type.
     */
    public TargetedForceType getType() {

        return type;
    }

    /**
     * Sets the {@link TargetedForceType} that defines how the acceleration changes when the distance between the {@link PhysicsObject} and the target changes.
     *
     * @param type The new modification type.
     * @return This object.
     */
    public TargetedForceVelocityModifier setType(TargetedForceType type) {

        Validate.notNull(target, "Type of targeted velocity modifier cannot be null");
        this.type = type;
        return this;
    }

    /**
     * Returns the target {@link Vector} all objects accelerate towards or away from depending on the sign of the factor.
     * Note that this vector is always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     *
     * @return The target vector.
     */
    public Vector getTarget() {

        return target;
    }

    /**
     * Sets the target {@link Vector} all objects accelerate towards or away from depending on the sign of the factor.
     * Note that this vector is always relative to the origin of any {@link ActiveObjectSystem} the modifier is used by.
     *
     * @param target The new target vector.
     * @return This object.
     */
    public TargetedForceVelocityModifier setTarget(Vector target) {

        Validate.notNull(target, "Target of targeted velocity modifier cannot be null");
        this.target = target.clone();
        updateEffectiveTarget();
        return this;
    }

    /**
     * Returns the factor that defines how fast an object accelerates towards or away from the target vector.
     * By making this value negative, all objects accelerate away from the target.
     * Note that the range of appropriate values highly depends on the {@link TargetedForceType}.
     * You should look up the default factors of the different types to get a starting point for further experiments.
     *
     * @return The acceleration factor.
     */
    public float getFactor() {

        return factor;
    }

    /**
     * Sets the factor that defines how fast an object accelerates towards or away from the target vector.
     * By making this value negative, all objects accelerate away from the target.
     * Note that the range of appropriate values highly depends on the {@link TargetedForceType}.
     * You should look up the default factors of the different types to get a starting point for further experiments.
     *
     * @param factor The new acceleration factor.
     * @return This object.
     */
    public TargetedForceVelocityModifier setFactor(float factor) {

        this.factor = factor;
        return this;
    }

    /**
     * Returns which {@link Vector} components (x/y/z) aren't changed by the velocity modifier.
     * For example, if the y component is ignored, the y component of any modification vector will be zero.
     * That way, you can create objects that are dragged towards a specific line (1 ignored) or surface (2 ignored) instead of a point.
     *
     * @return The vector components that are ignored.
     *         The array has 3 elements.
     *         The first element represents the x component, the second one the y component, and the last one the z component.
     */
    public boolean[] getIgnoredComponenets() {

        return ignoredComponenets.clone();
    }

    /**
     * Sets which {@link Vector} components (x/y/z) aren't changed by the velocity modifier.
     * For example, if the y component is ignored, the y component of any modification vector will be zero.
     * That way, you can create objects that are dragged towards a specific line (1 ignored) or surface (2 ignored) instead of a point.
     *
     * @param ignoreX Whether the x vector component should be ignored.
     * @param ignoreY Whether the y vector component should be ignored.
     * @param ignoreZ Whether the z vector component should be ignored.
     * @return This object.
     */
    public TargetedForceVelocityModifier setIgnoredComponenets(boolean ignoreX, boolean ignoreY, boolean ignoreZ) {

        ignoredComponenets = new boolean[] { ignoreX, ignoreY, ignoreZ };
        updateEffectiveTarget();
        return this;
    }

    @Override
    public Vector getModification(long dt, PhysicsObject object) {

        Vector acceleration = removeIgnoredComponents(object.getPosition()).subtract(effectiveTarget);

        if (!acceleration.equals(ZERO_VECTOR)) {
            if (type == TargetedForceType.NEGATIVE_POSITION_VECTOR) {
                acceleration.multiply(-1 * factor);
            } else if (type == TargetedForceType.UNIFORM) {
                acceleration.normalize().multiply(-1 * factor);
            } else if (type == TargetedForceType.GRAVITY) {
                double gravityAcceleration = factor / acceleration.lengthSquared();
                acceleration.normalize().multiply(-1 * gravityAcceleration);
            }
        }

        // Consider dt
        return acceleration.multiply(dt / 1000d);
    }

    private void updateEffectiveTarget() {

        effectiveTarget = removeIgnoredComponents(target.clone());
    }

    private Vector removeIgnoredComponents(Vector vector) {

        if (ignoredComponenets[0]) {
            vector.setX(0);
        }
        if (ignoredComponenets[1]) {
            vector.setY(0);
        }
        if (ignoredComponenets[2]) {
            vector.setZ(0);
        }
        return vector;
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
