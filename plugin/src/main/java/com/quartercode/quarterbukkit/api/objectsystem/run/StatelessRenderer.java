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

package com.quartercode.quarterbukkit.api.objectsystem.run;

import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;

/**
 * An abstract class that implements the {@code hashCode()} ... methods for stateless {@link Renderer}s.
 * Such renderers have no settings so each instance does exactly the same thing.
 * 
 * @param <O> The type of object the stateless renderer can use to perform some action. This must extend {@link BaseObject}.
 * @see Renderer
 */
public abstract class StatelessRenderer<O extends BaseObject> implements Renderer<O> {

    @Override
    public int hashCode() {

        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return getClass().isInstance(obj);
    }

    @Override
    public String toString() {

        return getClass().getSimpleName();
    }

}
