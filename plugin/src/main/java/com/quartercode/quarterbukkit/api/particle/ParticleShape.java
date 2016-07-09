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

package com.quartercode.quarterbukkit.api.particle;

import org.bukkit.FireworkEffect.Type;

/**
 * A enumeration of all available particle shapes.
 *
 * @deprecated The particle api was replaced by the more flexible object system api.
 *             See the wiki for more information on that new api.
 */
@Deprecated
public enum ParticleShape {

    /**
     * A small default-like ball.
     */
    BALL_SMALL (Type.BALL),
    /**
     * A large default-like ball.
     */
    BALL_LARGE (Type.BALL_LARGE),
    /**
     * A 3D star.
     */
    STAR (Type.STAR),
    /**
     * A bursty shape.
     */
    BURST (Type.BURST),
    /**
     * A creeper face.
     */
    CREEPER (Type.CREEPER);

    /**
     * Returns a particle shape from a firework {@link Type}.
     *
     * @param fireworkType The firework {@link Type}.
     * @return The particle shape.
     */
    public static ParticleShape getShape(Type fireworkType) {

        for (ParticleShape shape : values()) {
            if (shape.getFireworkType().equals(fireworkType)) {
                return shape;
            }
        }

        return null;
    }

    private Type fireworkType;

    private ParticleShape(Type fireworkType) {

        this.fireworkType = fireworkType;
    }

    /**
     * Returns the firework {@link Type} represented by the particle shape.
     *
     * @return The firework {@link Type} represented by the particle shape.
     */
    public Type getFireworkType() {

        return fireworkType;
    }

}
