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

package com.quartercode.quarterbukkit.api.shape;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * This class represents a shape of any form that contains some blocks inside a world.
 * Every shape must allow to retrieve its center and the {@link Block}s inside it.
 * It must also be possible to check whether a certain point is inside the shape.
 */
public interface Shape {

    /**
     * Returns the center position of the shape.
     * You can use this to get the center of gravity.
     *
     * @return The center of the shape.
     */
    public Vector getCenter();

    /**
     * Returns the rounded block center position of the shape.
     * You can use this to get the center of gravity as a block location.
     *
     * @return The center position of the shape as a rounded block location.
     */
    public Vector getBlockCenter();

    /**
     * Returns a new cuboid shape the current shape exactly fits into.
     * Note that no spare space is allowed to be left on any side.
     *
     * @return A cuboid that surrounds the current shape.
     */
    public Shape getAxisAlignedBoundingBox();

    /**
     * Returns a collection of {@link Vector}s that are located inside the shape and completely fill in the shape.
     * The distance parameter controls how far away the vectors are from each other.
     * By making the distance smaller, the resolution becomes higher and more vectors are returned.
     * Note that inserting the distance 1 will return one vector for each block inside the shape.<br>
     * <br>
     * For example, you could retrieve the blocks inside the shape as follows:
     *
     * <pre>{@code
     * for (Vector vector : shape.getContent(1)) {
     *     Block block = vector.toLocation(world).getBlock();
     *     ...
     * }
     * }</pre>
     *
     * @param distance The distance between the returned vectors.
     * @return The vectors inside the shape separated by the given distance.
     */
    public Collection<Vector> getContent(double distance);

    /**
     * Checks whether the given location is inside the shape.
     * The location is represented by three doubles representing the three coordinates.
     * There will only be a positive result if all three coordinates are intersecting the shape.
     *
     * @param x The x-coordinate of the location that should be checked for intersection.
     * @param y The y-coordinate of the location that should be checked for intersection.
     * @param z The z-coordinate of the location that should be checked for intersection.
     * @return Whether the provided location intersects the shape.
     */
    public boolean intersects(double x, double y, double z);

    /**
     * Checks whether the given {@link Vector} is inside the shape.
     * There will only be a positive result if all three coordinates of the vector are intersecting the shape.
     *
     * @param vector The vector that should be checked for intersection.
     * @return Whether the provided vector intersects the shape.
     */
    public boolean intersects(Vector vector);

    /**
     * Checks whether the given {@link Location} is inside the shape.
     * There will only be a positive result if all three coordinates of the location are intersecting the shape.
     *
     * @param location The location that should be checked for intersection.
     * @return Whether the provided location intersects the shape.
     */
    public boolean intersects(Location location);

}
