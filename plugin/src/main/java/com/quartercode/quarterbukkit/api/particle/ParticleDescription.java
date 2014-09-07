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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.Color;

/**
 * This class describes the look of one particle run.
 * 
 * @deprecated The particle api was replaced by the more flexible object system api.
 *             See the wiki for more information on that new api.
 */
@Deprecated
public class ParticleDescription {

    private ParticleShape shape;
    private List<Color>   colors     = new ArrayList<Color>();
    private List<Color>   fadeColors = new ArrayList<Color>();

    /**
     * Creates a new empty particle description.
     */
    public ParticleDescription() {

    }

    /**
     * Creates a new particle description and sets the {@link ParticleShape}.
     * 
     * @param shape The {@link ParticleShape}.
     */
    public ParticleDescription(ParticleShape shape) {

        this.shape = shape;
    }

    /**
     * Creates a new particle description and sets the particle {@link Color}s as an array/vararg.
     * 
     * @param colors The particle {@link Color}s as an array/vararg.
     */
    public ParticleDescription(Color... colors) {

        this.colors = Arrays.asList(colors);
    }

    /**
     * Creates a new particle description and sets the particle {@link Color}s as a {@link List}.
     * 
     * @param colors The particle {@link Color}s as a {@link List}.
     */
    public ParticleDescription(List<Color> colors) {

        this.colors = colors;
    }

    /**
     * Creates a new particle description and sets the {@link ParticleShape} and the particle {@link Color}s as an array/vararg.
     * 
     * @param shape The {@link ParticleShape}.
     * @param colors The particle {@link Color}s as an array/vararg.
     */
    public ParticleDescription(ParticleShape shape, Color... colors) {

        this.shape = shape;
        this.colors = Arrays.asList(colors);
    }

    /**
     * Creates a new particle description and sets the {@link ParticleShape} and the particle {@link Color}s as a {@link List}.
     * 
     * @param shape The {@link ParticleShape}.
     * @param colors The particle {@link Color}s as a {@link List}.
     */
    public ParticleDescription(ParticleShape shape, List<Color> colors) {

        this.shape = shape;
        this.colors = colors;
    }

    /**
     * Returns the {@link ParticleShape}.
     * 
     * @return The {@link ParticleShape}.
     */
    public ParticleShape getShape() {

        return shape;
    }

    /**
     * Sets the {@link ParticleShape}.
     * 
     * @param shape The {@link ParticleShape}.
     * @return This particle description.
     */
    public ParticleDescription setShape(ParticleShape shape) {

        this.shape = shape;
        return this;
    }

    /**
     * Returns the particle {@link Color}s.
     * 
     * @return This particle description.
     */
    public List<Color> getColors() {

        return Collections.unmodifiableList(colors);
    }

    /**
     * Sets the particle {@link Color}s as an array/vararg.
     * 
     * @param colors The particle {@link Color}s as an array/vararg.
     * @return This particle description.
     */
    public ParticleDescription setColors(Color... colors) {

        this.colors = Arrays.asList(colors);
        return this;
    }

    /**
     * Sets the particle {@link Color}s as a {@link List}.
     * 
     * @param colors The particle {@link Color}s as a {@link List}.
     * @return This particle description.
     */
    public ParticleDescription setColors(List<Color> colors) {

        this.colors = colors;
        return this;
    }

    /**
     * Adds a particle {@link Color}.
     * 
     * @param color The particle {@link Color} to add.
     * @return This particle description.
     */
    public ParticleDescription addColor(Color color) {

        colors.add(color);
        return this;
    }

    /**
     * Removes a particle {@link Color}.
     * 
     * @param color The particle {@link Color} to remove.
     * @return This particle description.
     */
    public ParticleDescription removeColor(Color color) {

        colors.remove(color);
        return this;
    }

    /**
     * Returns the fade particle {@link Color}s.
     * 
     * @return The fade particle {@link Color}s.
     */
    public List<Color> getFadeColors() {

        return Collections.unmodifiableList(fadeColors);
    }

    /**
     * Sets the fade particle {@link Color}s as an array/vararg.
     * 
     * @param fadeColors The fade particle {@link Color}s as an array/vararg.
     * @return This particle description.
     */
    public ParticleDescription setFadeColors(Color... fadeColors) {

        this.fadeColors = Arrays.asList(fadeColors);
        return this;
    }

    /**
     * Sets the fade particle {@link Color}s as a {@link List}.
     * 
     * @param fadeColors The fade particle {@link Color}s as a {@link List}.
     * @return This particle description.
     */
    public ParticleDescription setFadeColors(List<Color> fadeColors) {

        this.fadeColors = fadeColors;
        return this;
    }

    /**
     * Adds a fade particle {@link Color}.
     * 
     * @param fadeColor The fade particle {@link Color} to add.
     * @return This particle description.
     */
    public ParticleDescription addFadeColor(Color fadeColor) {

        fadeColors.add(fadeColor);
        return this;
    }

    /**
     * Removes a fade particle {@link Color}.
     * 
     * @param fadeColor The fade particle {@link Color} to remove.
     * @return This particle description.
     */
    public ParticleDescription removeFadeColor(Color fadeColor) {

        fadeColors.remove(fadeColor);
        return this;
    }

}
