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

import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.physics.PhysicsObject;

/**
 * A class that contains some general rendering utilities.
 */
public class RenderingUtils {

    /**
     * Returns whether the given {@link PhysicsObject} should be rendered based on the "speed based frequency mode".
     * It basically means that the object is only rendered if the last rendering happened the given distance away based on the current object velocity.
     *
     * @param object The physics object that should be checked.
     * @param minRenderDistance The minimum distance from the current position of the object its last rendering must have happened.
     * @return Whether the object should be rendered.
     */
    public static boolean checkSpeedBasedFrequency(PhysicsObject object, float minRenderDistance) {

        return checkSpeedBasedFrequency(object.getLifetime(), object.getVelocity().length(), minRenderDistance);
    }

    /**
     * Does the same as {@link #checkSpeedBasedFrequency(PhysicsObject, float)} using raw values.
     *
     * @param objectLifetime The lifetime of the object.
     *        It can be determined with {@link PhysicsObject#getLifetime()}.
     * @param objectVelocity The current velocity value of the object.
     *        It can be determined with {@link PhysicsObject#getVelocity()} and then {@link Vector#length()}.
     * @param minRenderDistance The minimum distance from the current position of the object its last rendering must have happened.
     * @return Whether the object should be rendered.
     */
    public static boolean checkSpeedBasedFrequency(int objectLifetime, double objectVelocity, float minRenderDistance) {

        if (objectVelocity >= minRenderDistance) {
            return true;
        }

        double effectiveObjectVelocity = objectVelocity > minRenderDistance / 10 ? objectVelocity : minRenderDistance / 10;
        return objectLifetime % Math.round(minRenderDistance / effectiveObjectVelocity) == 0;
    }

    private RenderingUtils() {

    }

}
