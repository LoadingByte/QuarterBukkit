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

import com.quartercode.quarterbukkit.api.objectsystem.run.Renderer;
import com.quartercode.quarterbukkit.api.objectsystem.traits.PhysicsTrait;

/**
 * A behavior is a function that can be attached to a Java object (a body) and will run at each update step while being supplied with the object it's attached to.
 * For example, the body could be an {@link ActiveObjectSystem} if the behavior affects to the whole system, e.g. for applying a force of gravity to all phyiscal objects inside that system:
 *
 * <pre>{@code
 * public void behave(long dt, ActiveObjectSystem activeSystem) {
 *     // ... apply gravity each update
 * }
 * }</pre>
 *
 * However, the behavior could just as well be attached to a single {@link BaseObject object} if it only cares about that very object:
 *
 * <pre>{@code
 * public void behave(long dt, BaseObject object) {
 *     // ... do something amazing each update
 * }
 * }</pre>
 *
 * You can say that a behavior adds some special characteristic (aka. behavior) to the body it is attached to.<br>
 * <br>
 * Behaviors are probably most useful when being implemented using lambdas:
 *
 * <pre>{@code
 * systemDef.addBehaviors((dt, activeSystem) -> {
 *     for (BaseObject obj : activeSystem.getObjects())
 *         obj.get(PhysicsTrait.class).ifPresent(physics -> physics.addVelocity(Forces.staticForce(new Vector(0, 1, 0), dt)));
 * });
 * }</pre>
 *
 * Or, if you really want to use streams, you can, of course, do that as well:
 *
 * <pre>{@code
 * systemDef.addBehaviors((dt, activeSystem) -> activeSystem.objectStream()
 *         .forEach(obj -> obj.get(PhysicsTrait.class).ifPresent(physics -> physics.addVelocity(Forces.staticForce(new Vector(0, 1, 0), dt)))));
 * }</pre>
 *
 * Note that behaviors can also be chained together using the {@link BehaviorWrapper} class:
 *
 * <pre>{@code
 * systemDef.addBehaviors(new InitializationBehavior((dt, activeSystem) -> ...));
 * }</pre>
 *
 * Some may ask why there's a distinction between behaviors and {@link Renderer}s.
 * One clear difference is the broader and more flexible approach the rendering system takes.
 * For example, each renderer is executed on all objects both in the root system as well as in all nested systems; therefore, it provides less abstraction than the simple behaviors.
 * Moreover, and maybe more importantly, frontend users generally capture the characteristics of an object system in behaviors, while backend developers use renderers to implement fundamentals like
 * the laws of physics.
 *
 * @param <B> The type of Java object this behavior can be attached to.
 */
@FunctionalInterface
public interface Behavior<B> {

    /**
     * This method is executed at every object system update; it will be supplied with both {@code dt} as well as the body it's attached to.
     * For example, say that we want to build an accelerator behavior that accelerates all {@link PhysicsTrait physical} {@link BaseObject objects} in one direction.
     * That behavior obviously needs to be attached to an {@link ActiveObjectSystem} since it affects all objects.
     * Firstly, it would calculate an acceleration vector using {@code dt}.
     * It would then iterate through all objects inside the active system (the behavior's body) and add the acceleration vector to the velocity vector of each physical object.<br>
     * <br>
     * Note the important variable {@code dt} which basically informs you about the currently used time resolution.
     * For example, our accelerator would really need to consider it in order to correctly model acceleration and apply the correct velocity change for the last time interval {@code dt}.
     *
     * @param dt The amount of milliseconds which have elapsed since the last update of the object system this behavior is part of.
     * @param body The Java object this is attached to and therefore reasons about.
     *        For example, this could be an {@link ActiveObjectSystem} if the behavior applies to the whole thing, or just one {@link BaseObject object} if it only cares about that very object.
     */
    public void behave(long dt, B body);

}
