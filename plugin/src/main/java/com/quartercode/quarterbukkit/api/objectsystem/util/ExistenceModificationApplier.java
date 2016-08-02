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

import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.ModificationApplier;

/**
 * A {@link ModificationApplier} that deletes an {@link BaseObject object} if it's existence boolean is modified to {@code false}.
 * It's required for the correct and a bit esoteric way to delete an object using modifiers.
 * The modification object is a boolean which determines whether the system object should exist ({@code true}) or not ({@code false}).
 *
 * @see ModificationApplier
 */
public class ExistenceModificationApplier implements ModificationApplier<BaseObject, Boolean> {

    @Override
    public void applyModification(BaseObject object, Boolean modification) {

        if (!modification) {
            object.getSystem().removeObjects(object);
        }
    }

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
