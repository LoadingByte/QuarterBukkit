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

import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * This class represents a cuboid shape between two different {@link Vector}s.
 * The cuboid is immutable and can't be modified after construction.<br>
 * <br>
 * All input coordinates are converted to a minimum vector and a maximum vector.
 * This will lose the original vectors, but you can work better with the data later on.
 * The {@link #getMinVector()} method returns the small one while {@link #getMinVector()} returns the large one.<br>
 * Here's an example on how the sorting works:
 * There are two vectors with the coordinates {@code 5, 10, 7} and {@code 12, 6, 18}.
 * After the sorting, the two {@link Vector}s will be {@code 5, 6, 7} (the small one) and {@code 12, 10, 18} (the large one).
 *
 * @see Shape
 */
public class Cuboid implements Shape {

    private final Vector minVector;
    private final Vector maxVector;

    /**
     * Creates a new cuboid shape that is defined by two {@link Vector}s provided by six doubles (three ones for each vector).
     * Note that the original vectors are not preserved. See {@link Cuboid} for more information on that.
     *
     * @param x1 The x-coordinate of the first vector.
     * @param y1 The y-coordinate of the first vector.
     * @param z1 The z-coordinate of the first vector.
     * @param x2 The x-coordinate of the second vector.
     * @param y2 The y-coordinate of the second vector.
     * @param z2 The z-coordinate of the second vector,
     */
    public Cuboid(double x1, double y1, double z1, double x2, double y2, double z2) {

        this(new Vector(x1, y1, z1), new Vector(x2, y2, z2));
    }

    /**
     * Creates a new cuboid shape that is defined by the two given {@link Vector}s.
     * Note that the original vectors are not preserved. See {@link Cuboid} for more information on that.
     *
     * @param vector1 The first vector.
     * @param vector2 The second vector.
     */
    public Cuboid(Vector vector1, Vector vector2) {

        minVector = Vector.getMinimum(vector1, vector2);
        maxVector = Vector.getMaximum(vector1, vector2);
    }

    /**
     * Creates a new cuboid shape that is defined by the two given {@link Location}s.
     * Note that the original vectors are not preserved. See {@link Cuboid} for more information on that.
     *
     * @param location1 The location that represents the first {@link Vector}.
     * @param location2 The location that represents the second vector.
     */
    public Cuboid(Location location1, Location location2) {

        this(location1.toVector(), location2.toVector());
    }

    /**
     * Returns the minimum cuboid {@link Vector} with coordinates that are smaller than the ones provided by the maximum vector.
     * Note the modifications on the returned vector are not transfered into the cuboid object.
     * Also note that the original vectors were not preserved on construction. See {@link Cuboid} for more information on that.
     *
     * @return The minimum cuboid vector with the small coordinates.
     */
    public Vector getMinVector() {

        return minVector.clone();
    }

    /**
     * Returns the maximum cuboid {@link Vector} with coordinates that are larger than the ones provided by the minimum vector.
     * Note the modifications on the returned vector are not transfered into the cuboid object.
     * Also note that the original vectors were not preserved on construction. See {@link Cuboid} for more information on that.
     *
     * @return The maximum cuboid vector with the large coordinates.
     */
    public Vector getMaxVector() {

        return maxVector.clone();
    }

    /**
     * Returns the distance between the two x-coordinates by subtracting the smaller from the larger one.
     * This method will always return a positive distance.
     * You could use the result to retrieve the distance between the two edges of the cuboid on the x-axis.
     *
     * @return The distance between the two x-coordinates.
     */
    public double getXDistance() {

        return maxVector.getX() - minVector.getX();
    }

    /**
     * Returns the distance between the two rounded block x-coordinates by subtracting the smaller from the larger one.
     * This method will always return a positive distance.
     * You could use the result to retrieve the distance between the two rounded block edges of the cuboid on the x-axis.
     *
     * @return The distance between the two rounded block x-coordinates.
     */
    public double getBlockXDistance() {

        return maxVector.getBlockX() - minVector.getBlockX();
    }

    /**
     * Returns the distance between the two y-coordinates by subtracting the smaller from the larger one.
     * This method will always return a positive distance.
     * You could use the result to retrieve the distance between the two edges of the cuboid on the y-axis.
     *
     * @return The distance between the two y-coordinates.
     */
    public double getYDistance() {

        return maxVector.getY() - minVector.getY();
    }

    /**
     * Returns the distance between the two rounded block y-coordinates by subtracting the smaller from the larger one.
     * This method will always return a positive distance.
     * You could use the result to retrieve the distance between the two rounded block edges of the cuboid on the y-axis.
     *
     * @return The distance between the two rounded block y-coordinates.
     */
    public double getBlockYDistance() {

        return maxVector.getBlockY() - minVector.getBlockY();
    }

    /**
     * Returns the distance between the two z-coordinates by subtracting the smaller from the larger one.
     * This method will always return a positive distance.
     * You could use the result to retrieve the distance between the two edges of the cuboid on the z-axis.
     *
     * @return The distance between the two z-coordinates.
     */
    public double getZDistance() {

        return maxVector.getZ() - minVector.getZ();
    }

    /**
     * Returns the distance between the two rounded block z-coordinates by subtracting the smaller from the larger one.
     * This method will always return a positive distance.
     * You could use the result to retrieve the distance between the two rounded block edges of the cuboid on the z-axis.
     *
     * @return The distance between the two rounded block z-coordinates.
     */
    public double getBlockZDistance() {

        return maxVector.getBlockZ() - minVector.getBlockZ();
    }

    @Override
    public Vector getCenter() {

        return minVector.getMidpoint(maxVector);
    }

    @Override
    public Vector getBlockCenter() {

        Vector center = getCenter();
        return new Vector(center.getBlockX(), center.getBlockY(), center.getBlockZ());
    }

    @Override
    public Shape getAxisAlignedBoundingBox() {

        return this;
    }

    @Override
    public Collection<Vector> getContent(double distance) {

        Collection<Vector> vectors = new ArrayList<>();

        for (double x = minVector.getX(); x <= maxVector.getX(); x += distance) {
            for (double y = minVector.getY(); y <= maxVector.getY(); y += distance) {
                for (double z = minVector.getZ(); z <= maxVector.getZ(); z += distance) {
                    vectors.add(new Vector(x, y, z));
                }
            }
        }

        return vectors;
    }

    @Override
    public boolean intersects(double x, double y, double z) {

        return intersects(new Vector(x, y, z));
    }

    @Override
    public boolean intersects(Vector vector) {

        return vector.isInAABB(minVector, maxVector);
    }

    @Override
    public boolean intersects(Location location) {

        return intersects(location.toVector());
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
