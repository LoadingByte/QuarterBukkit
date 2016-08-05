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

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If a {@link Trait} depends on other ones, you can force the fulfillment of those hard dependencies by declaring a trait dependencies annotation.
 * For example, the particle trait depends on the physics trait because in order to spawn particles, you need a position at which you can spawn them.
 * If a user tries to add a particle trait to an object without caring about the physics trait dependency, the object shouts back at him by throwing an exception.<br>
 * <br>
 * It's recommended to always specify all required dependencies so that wrong configurations fail fast instead of silently.
 * Those silent fails can really drive you insane when, for example, a particle object just doesn't want to render because you forgot to add a standalone physics trait.
 *
 * @see Trait
 */
@Target ({ ElementType.TYPE })
@Retention (RetentionPolicy.RUNTIME)
@Inherited
public @interface TraitDependencies {

    public Class<? extends Trait>[] value ();

}
