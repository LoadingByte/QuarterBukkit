
package com.quartercode.quarterbukkit.api.particle;

import org.bukkit.FireworkEffect.Type;

/**
 * A enumeration of all avaiable particle shapes.
 */
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
    public static ParticleShape getShape(final Type fireworkType) {

        for (final ParticleShape shape : values()) {
            if (shape.getFireworkType().equals(fireworkType)) {
                return shape;
            }
        }

        return null;
    }

    private Type fireworkType;

    private ParticleShape(final Type fireworkType) {

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
