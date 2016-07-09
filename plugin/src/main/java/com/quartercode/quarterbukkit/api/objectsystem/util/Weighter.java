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

/**
 * A weighter takes an object and returns a value between 0 and 1 based on some criteria.
 * For example, the value returned by a weighter could be based on the distance between the object and some other point.
 *
 * @param <O> The type of object the weighter can calculate a weighting value for. It must extend {@link BaseObject}.
 */
public interface Weighter<O extends BaseObject> {

    /**
     * Returns the weighting value between 0 and 1 that was calculated by the weighter for the given object based on some criteria.
     * For example, the value could be based on the distance between the object and some other point.
     *
     * @param object The object that should be weighted.
     * @return The calculated weight for the given object.
     */
    public float getWeight(O object);

}
