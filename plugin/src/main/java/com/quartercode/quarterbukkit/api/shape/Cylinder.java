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
 * This class represents a cylinder shape that has two confining {@link Vector}s the cylinder is located between and a radius.
 * The two vectors define the origins of the top and bottom circles of the cylinder.
 * The cylinder is immutable and cannot be modified after construction.
 * 
 * @see Shape
 */
public class Cylinder implements Shape {

    private final Vector topCircleOrigin;
    private final Vector bottomCircleOrigin;
    private final float  radius;

    private final float  lengthSquared;

    /**
     * Creates a new cylinder shape with the given two circle origin {@link Vector}s defined by six double coordinates (three ones for each vector) and the given radius.
     * The two vectors define the origins of the top and bottom circles of the cylinder.
     * 
     * @param circle1OriginX The x-coordinate of the origin vector of the first circle that confines the represented cylinder.
     * @param circle1OriginY The y-coordinate of the origin vector of the first circle that confines the represented cylinder.
     * @param circle1OriginZ The z-coordinate of the origin vector of the first circle that confines the represented cylinder.
     * @param circle2OriginX The x-coordinate of the origin vector of the second circle that confines the represented cylinder.
     * @param circle2OriginY The y-coordinate of the origin vector of the second circle that confines the represented cylinder.
     * @param circle2OriginZ The z-coordinate of the origin vector of the second circle that confines the represented cylinder.
     * @param radius The radius of the represented sphere.
     */
    public Cylinder(double circle1OriginX, double circle1OriginY, double circle1OriginZ, double circle2OriginX, double circle2OriginY, double circle2OriginZ, float radius) {

        this(new Vector(circle1OriginX, circle1OriginY, circle1OriginZ), new Vector(circle2OriginX, circle2OriginY, circle2OriginZ), radius);
    }

    /**
     * Creates a new cylinder shape with the given two circle origin {@link Vector}s and the given radius.
     * The two vectors define the origins of the top and bottom circles of the cylinder.
     * 
     * @param circle1Origin The origin vector (center) of the first circle that confines the represented cylinder.
     * @param circle2Origin The origin vector (center) of the second circle that confines the represented cylinder.
     * @param radius The radius of the represented sphere.
     */
    public Cylinder(Vector circle1Origin, Vector circle2Origin, float radius) {

        boolean circle1HigherThan2 = circle1Origin.getY() > circle2Origin.getY();
        topCircleOrigin = (circle1HigherThan2 ? circle1Origin : circle2Origin).clone();
        bottomCircleOrigin = (circle1HigherThan2 ? circle2Origin : circle1Origin).clone();

        this.radius = radius;

        lengthSquared = (float) topCircleOrigin.distanceSquared(bottomCircleOrigin);
    }

    /**
     * Creates a new cylinder shape with the given two circle origin {@link Location}s and the given radius.
     * The two locations define the origins of the top and bottom circles of the cylinder.
     * 
     * @param circle1Origin The origin location (center) of the first circle that confines the represented cylinder.
     * @param circle2Origin The origin location (center) of the second circle that confines the represented cylinder.
     * @param radius The radius of the represented sphere.
     */
    public Cylinder(Location circle1Origin, Location circle2Origin, float radius) {

        this(circle1Origin.toVector(), circle2Origin.toVector(), radius);
    }

    /**
     * Returns the origin {@link Vector} of the upper circle that confines the cylinder on its top.
     * 
     * @return The origin vector of the top circle.
     */
    public Vector getTopCircleOrigin() {

        return topCircleOrigin.clone();
    }

    /**
     * Returns the origin {@link Vector} of the lower circle that confines the cylinder on its bottom.
     * 
     * @return The origin vector of the bottom circle.
     */
    public Vector getBottomCircleOrigin() {

        return bottomCircleOrigin.clone();
    }

    /**
     * Returns a new cylinder shape with the given two circle origin {@link Vector}s defined by six double coordinates (three ones for each vector) and the same radius as the current cylinder.
     * The two vectors define the origins of the top and bottom circles of the cylinder.
     * 
     * @param circle1OriginX The x-coordinate of the new origin vector of the first circle that confines the returned cylinder copy.
     * @param circle1OriginY The y-coordinate of the new origin vector of the first circle that confines the returned cylinder copy.
     * @param circle1OriginZ The z-coordinate of the new origin vector of the first circle that confines the returned cylinder copy.
     * @param circle2OriginX The x-coordinate of the new origin vector of the second circle that confines the returned cylinder copy.
     * @param circle2OriginY The y-coordinate of the new origin vector of the second circle that confines the returned cylinder copy.
     * @param circle2OriginZ The z-coordinate of the new origin vector of the second circle that confines the returned cylinder copy.
     * 
     * @return The new cylinder copy.
     */
    public Cylinder withCircleOrigins(double circle1OriginX, double circle1OriginY, double circle1OriginZ, double circle2OriginX, double circle2OriginY, double circle2OriginZ) {

        return new Cylinder(circle1OriginX, circle1OriginY, circle1OriginZ, circle2OriginX, circle2OriginY, circle2OriginZ, radius);
    }

    /**
     * Returns a new cylinder shape with the given two circle origin {@link Vector}s and the same radius as the current cylinder.
     * The two vectors define the origins of the top and bottom circles of the cylinder.
     * 
     * @param circle1Origin The new origin vector (center) of the first circle that confines the returned cylinder copy.
     * @param circle2Origin The new origin vector (center) of the second circle that confines the returned cylinder copy.
     * @return The new cylinder copy.
     */
    public Cylinder withCircleOrigins(Vector circle1Origin, Vector circle2Origin) {

        return new Cylinder(circle1Origin, circle2Origin, radius);
    }

    /**
     * Returns a new cylinder shape with the given two circle origin {@link Location}s and the same radius as the current cylinder.
     * The two locations define the origins of the top and bottom circles of the cylinder.
     * 
     * @param circle1Origin The new origin location (center) of the first circle that confines the returned cylinder copy.
     * @param circle2Origin The new origin location (center) of the second circle that confines the returned cylinder copy.
     * @return The new cylinder copy.
     */
    public Cylinder withCircleOrigins(Location circle1Origin, Location circle2Origin) {

        return new Cylinder(circle1Origin, circle2Origin, radius);
    }

    /**
     * Returns the radius of the cylinder.
     * 
     * @return The radius of the cylinder.
     */
    public float getRadius() {

        return radius;
    }

    /**
     * Returns a new cylinder shape with the given radius and the same circle origin vectors as the current cylinder.
     * 
     * @param radius The new radius of the returned cylinder copy.
     * @return The new cylinder copy.
     */
    public Cylinder withRadius(float radius) {

        return new Cylinder(topCircleOrigin, bottomCircleOrigin, radius);
    }

    /**
     * Returns the length of the cylinder, which is basically the distance between the two confining circle origins, squared.
     * 
     * @return The squared length of the cylinder.
     */
    public float getLengthSquared() {

        return lengthSquared;
    }

    @Override
    public Vector getCenter() {

        return topCircleOrigin.getMidpoint(bottomCircleOrigin);
    }

    @Override
    public Vector getBlockCenter() {

        Vector center = getCenter();
        return new Vector(center.getBlockX(), center.getBlockY(), center.getBlockZ());
    }

    @Override
    public Cuboid getAxisAlignedBoundingBox() {

        float angle = topCircleOrigin.angle(bottomCircleOrigin);

        double yOffset = Math.sin(angle) * radius;
        double y1 = topCircleOrigin.getY() + yOffset;
        double y2 = bottomCircleOrigin.getY() - yOffset;

        double horizontalCircleRadius = Math.cos(angle) * radius;
        double x1 = Math.max(topCircleOrigin.getX(), bottomCircleOrigin.getX()) + horizontalCircleRadius;
        double x2 = Math.min(topCircleOrigin.getX(), bottomCircleOrigin.getX()) - horizontalCircleRadius;
        double z1 = Math.max(topCircleOrigin.getZ(), bottomCircleOrigin.getZ()) + horizontalCircleRadius;
        double z2 = Math.min(topCircleOrigin.getZ(), bottomCircleOrigin.getZ()) - horizontalCircleRadius;

        return new Cuboid(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public Collection<Vector> getContent(float distance) {

        Collection<Vector> vectors = new ArrayList<Vector>();

        for (Vector bbVector : getAxisAlignedBoundingBox().getContent(distance)) {
            if (intersects(bbVector)) {
                vectors.add(bbVector);
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

        double distanceToAxisSquared = getDistanceToAxisSquared(vector);
        return distanceToAxisSquared != -1 && distanceToAxisSquared <= radius * radius;
    }

    @Override
    public boolean intersects(Location location) {

        return intersects(location.toVector());
    }

    /**
     * Returns the shortest distance from the given {@link Vector} to the center axis of the cylinder.
     * The vector is represented by three doubles representing the three coordinates.
     * If the vector is above the top or below the bottom of the cylinder and therefore cannot be reached by drawing a line segment which is perpendicular
     * to the center axis from the vector to that center axis, -1 is returned.
     * 
     * @param x The x-coordinate of the vector whose distance to the center axis should be caclulated.
     * @param y The y-coordinate of the vector whose distance to the center axis should be caclulated.
     * @param z The z-coordinate of the vector whose distance to the center axis should be caclulated.
     * @return The shortest distance from the given vector to the center axis.
     */
    public double getDistanceToAxisSquared(double x, double y, double z) {

        return getDistanceToAxisSquared(new Vector(x, y, z));
    }

    /**
     * Returns the shortest distance from the given {@link Vector} to the center axis of the cylinder.
     * If the vector is above the top or below the bottom of the cylinder and therefore cannot be reached by drawing a line segment which is perpendicular
     * to the center axis from the vector to that center axis, -1 is returned.
     * 
     * @param vector The vector whose distance to the center axis should be caclulated.
     * @return The shortest distance from the given vector to the center axis.
     */
    public double getDistanceToAxisSquared(Vector vector) {

        Vector circleOriginDistance = bottomCircleOrigin.clone().subtract(topCircleOrigin);
        Vector pointToTopOriginDistance = vector.clone().subtract(topCircleOrigin);
        double dot = circleOriginDistance.dot(pointToTopOriginDistance);

        if (dot < 0 || dot > lengthSquared) {
            return -1;
        } else {
            Vector pTTODSquared = pointToTopOriginDistance.clone().multiply(pointToTopOriginDistance);
            double distanceSquared = pTTODSquared.getX() + pTTODSquared.getY() + pTTODSquared.getZ() - dot * dot / lengthSquared;

            if (distanceSquared > radius * radius) {
                return -1;
            } else {
                return distanceSquared;
            }
        }
    }

    /**
     * Returns the shortest distance from the given {@link Location} to the center axis of the cylinder.
     * If the vector is above the top or below the bottom of the cylinder and therefore cannot be reached by drawing a line segment which is perpendicular
     * to the center axis from the vector to that center axis, -1 is returned.
     * 
     * @param location The location whose distance to the center axis should be caclulated.
     * @return The shortest distance from the given location to the center axis.
     */
    public double getDistanceToAxisSquared(Location location) {

        return getDistanceToAxisSquared(location.toVector());
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this, new String[] { "lengthSquared" });
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj, new String[] { "lengthSquared" });
    }

    @Override
    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
