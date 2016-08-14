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

import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Behavior;
import com.quartercode.quarterbukkit.api.objectsystem.BehaviorWrapper;

/**
 * A {@link Behavior} wrapper for {@link ActiveObjectSystem}s that only calls its wrapped behavior once when the active system is first started, and then never again, resulting in some kind of
 * "initialization behavior".
 * It's extremely useful when your {@link BaseObject objects} live forever and therefore only need to be added once.
 */
public class InitializationBehavior extends BehaviorWrapper<ActiveObjectSystem> {

    /**
     * Creates a new initialization behavior and immediately sets the wrapped {@link Behavior} which should be called only once.
     *
     * @param wrappedBehavior The wrapped behavior which should be called only once.
     */
    public InitializationBehavior(Behavior<? super ActiveObjectSystem> wrappedBehavior) {

        super(wrappedBehavior);

        new InitializationBehavior((dt, activeSystem) -> System.out.println(dt));
    }

    @Override
    public void behave(long dt, ActiveObjectSystem activeSystem) {

        if (activeSystem.getLifetime() == 0) {
            getWrappedBehavior().behave(dt, activeSystem);
        }
    }

}
