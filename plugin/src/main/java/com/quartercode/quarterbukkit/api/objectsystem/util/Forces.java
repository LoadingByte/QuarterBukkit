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

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.traits.PhysicsTrait;

/**
 * This utility class contains methods for quickly generating {@link Vector}s that can be {@link PhysicsTrait#addVelocity(Vector) added} to the velocity of {@link PhysicsTrait physical objects}.
 * Those methods correspond to all kinds of different forces you could imagine.
 * Moreover, if you want to implement a "custom force" that yields an acceleration vector in m/s, you can use {@link #staticForce(Vector, long)} to convert that acceleration vector into a discrete
 * velocity change vector which can be added to the velocity of a physical object.
 *
 * @see PhysicsTrait
 */
public class Forces {

    /**
     * Simulates a static force that pulls all objects into the same direction.
     * Returns a {@link Vector} that can be {@link PhysicsTrait#addVelocity(Vector) added} to the velocity of a {@link PhysicsTrait physical object}.<br>
     * <br>
     * The unit of that acceleration vector is m/s<sup>2</sup>. Since all blocks have an edge length of 1 m, the unit can also be expressed as blocks/s<sup>2</sup>.
     * Effectively, you input a typical acceleration vector, and you get a discretized velocity change vector that you can add to the velocity of your object.
     * That means that this method just performs that process of discretization. You can use it to discretize your custom force acceleration vectors as well!
     *
     * @param acceleration The acceleration vector by which the object should be accelerated in m/s<sup>2</sup>.
     * @param dt The amount of milliseconds which have elapsed since the last physics update.
     *        Just shove the {@code dt} value you already have in here.
     * @return The discretized velocity change vector that you can {@link PhysicsTrait#addVelocity(Vector) add} to the velocity of your {@link PhysicsTrait physical object}.
     */
    public static Vector staticForce(Vector acceleration, long dt) {

        return acceleration.clone().multiply(dt / 1000d);
    }

    /**
     * Simulates a targeted force that pulls all objects towards a given target. The strength of the force is the same regardless of the distance from the target.
     * Returns a {@link Vector} that can be {@link PhysicsTrait#addVelocity(Vector) added} to the velocity of a {@link PhysicsTrait physical object}.<br>
     * <br>
     * By default, the force accelerates all objects with 1 m/s<sup>2</sup>.
     * In order to change the strength of the force, just {@link Vector#multiply(double)} the returned vector with the amount of m/s<sup>2</sup> you wish for.
     * If you want to push all objects away from the target instead of pulling them towards it, just multiply the returned vector with a negative value.
     *
     * @param target The target toward which all objects will be accelerated.
     * @param dt The amount of milliseconds which have elapsed since the last physics update.
     *        Just shove the {@code dt} value you already have in here.
     * @param physics The {@link PhysicsTrait} of your object. It is used to calculate the force, but it is not modified in any way!
     * @return The discretized velocity change vector that you can {@link PhysicsTrait#addVelocity(Vector) add} to the velocity of your {@link PhysicsTrait physical object}.
     */
    public static Vector targetedUniformForce(Vector target, long dt, PhysicsTrait physics) {

        return staticForce(physics.getPosition().subtract(target).normalize().multiply(-1), dt);
    }

    /**
     * Simulates a targeted force that pulls all objects towards a given target. The force becomes <b>weaker</b> the further an object is away from the target.
     * Returns a {@link Vector} that can be {@link PhysicsTrait#addVelocity(Vector) added} to the velocity of a {@link PhysicsTrait physical object}.<br>
     * <br>
     * The force acts like a pseudo-gravitational force that emulates gravity without the objects actually having any mass.
     * The acceleration (m/s<sup>2</sup>) of an object towards the target is calculated using 1/r<sup>2</sup>, with r being the distance between the target and the object.
     * In order to change the strength of the force, just {@link Vector#multiply(double)} the returned vector with some factor.
     * If you want to push all objects away from the target instead of pulling them towards it, just multiply the returned vector with a negative value.<br>
     * <br>
     * This force can quickly get dangerous because the acceleration becomes really high when an object gets really closer to the target.
     * The acceleration is squared each time the distance between the object and the target halves.
     * Therefore, the acceleration is infinite when the object directly passes through the target, possibly resulting in {@code NaN}s!
     * However, if you are careful, realistic single body orbital mechanics can be simulated quite nicely if the objects don't get too close to the target.
     *
     * @param target The target toward which all objects will be accelerated.
     * @param dt The amount of milliseconds which have elapsed since the last physics update.
     *        Just shove the {@code dt} value you already have in here.
     * @param physics The {@link PhysicsTrait} of your object. It is used to calculate the force, but it is not modified in any way!
     * @return The discretized velocity change vector that you can {@link PhysicsTrait#addVelocity(Vector) add} to the velocity of your {@link PhysicsTrait physical object}.
     */
    public static Vector targetedGravitationalForce(Vector target, long dt, PhysicsTrait physics) {

        Vector distanceVec = physics.getPosition().subtract(target);
        double distanceSquared = distanceVec.lengthSquared();
        return staticForce(distanceVec.normalize().multiply(-1 / distanceSquared), dt);
    }

    /**
     * Simulates a targeted force that pulls all objects towards a given target. The force becomes <b>stronger</b> the further an object is away from the target.
     * Returns a {@link Vector} that can be {@link PhysicsTrait#addVelocity(Vector) added} to the velocity of a {@link PhysicsTrait physical object}.<br>
     * <br>
     * The force acts like an inverse pseudo-gravitational force which effectively guarantees that objects can never escape the target.
     * Internally, the acceleration (m/s<sup>2</sup>) of an object towards the target is equal to the distance between the target and the object.
     * In order to change the strength of the force, just {@link Vector#multiply(double)} the returned vector with some factor.
     * If you want to push all objects away from the target instead of pulling them towards it, just multiply the returned vector with a negative value.<br>
     * <br>
     * In contrast to {@link #targetedGravitationalForce(Vector, long, PhysicsTrait) gravitation}, this force's acceleration can only become infinite if the object is infinitely far away from the
     * target.
     * Therefore, it's practically impossible to end up with some deadly infinities or {@code NaN}s, which makes this force a lots safer than gravitation.
     *
     * @param target The target toward which all objects will be accelerated.
     * @param dt The amount of milliseconds which have elapsed since the last physics update.
     *        Just shove the {@code dt} value you already have in here.
     * @param physics The {@link PhysicsTrait} of your object. It is used to calculate the force, but it is not modified in any way!
     * @return The discretized velocity change vector that you can {@link PhysicsTrait#addVelocity(Vector) add} to the velocity of your {@link PhysicsTrait physical object}.
     */
    public static Vector targetedProportionalForce(Vector target, long dt, PhysicsTrait physics) {

        return staticForce(physics.getPosition().subtract(target).multiply(-1), dt);
    }

    private Forces() {

    }

}
