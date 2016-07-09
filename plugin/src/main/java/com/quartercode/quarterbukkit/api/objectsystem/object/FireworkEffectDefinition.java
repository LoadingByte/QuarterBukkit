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

package com.quartercode.quarterbukkit.api.objectsystem.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;

/**
 * This class represents a single effect that is part of the explosion of a firework.
 *
 * @see FireworkObject
 */
public class FireworkEffectDefinition implements Cloneable {

    private Type                    type;
    private boolean                 flicker;
    private boolean                 trail;
    private final Collection<Color> colors     = new ArrayList<Color>();
    private final Collection<Color> fadeColors = new ArrayList<Color>();

    /**
     * Returns the {@link Type} of the defined firework effect.
     *
     * @return The firework effect type.
     * @see Type
     */
    public Type getType() {

        return type;
    }

    /**
     * Sets the {@link Type} of the defined firework effect.
     *
     * @param type The new firework effect type.
     * @return This object.
     * @see Type
     */
    public FireworkEffectDefinition setType(Type type) {

        this.type = type;
        return this;
    }

    /**
     * Returns whether the particles spawned by this effect during the firework explosion flicker.
     *
     * @return Whether the firework effect particles flicker.
     */
    public boolean hasFlicker() {

        return flicker;
    }

    /**
     * Sets whether the particles spawned by this effect during the firework explosion flicker.
     *
     * @param flicker Whether the firework effect particles should flicker.
     * @return This object.
     */
    public FireworkEffectDefinition setFlicker(boolean flicker) {

        this.flicker = flicker;
        return this;
    }

    /**
     * Returns whether the particles spawned by this effect during the firework explosion leave behind a trail of other particles.
     * Note that this option is only recommended in systems with few objects because the trail increases the particle amount by quite a bit.
     * Also note that setting this option to true will result
     *
     * @return Whether the firework effect particles leave behind a trail.
     */
    public boolean hasTrail() {

        return trail;
    }

    /**
     * Sets whether the particles spawned by this effect during the firework explosion leave behind a trail of other particles.
     * Note that this option is only recommended in systems with few objects because the trail increases the particle amount by quite a bit.
     * Also note that setting this option to true will result
     *
     * @param trail Whether the firework effect particles should leave behind a trail.
     * @return This object.
     */
    public FireworkEffectDefinition setTrail(boolean trail) {

        this.trail = trail;
        return this;
    }

    /**
     * Returns the {@link Color}s the particles spawned by this effect have immediately after the firework explosion.
     *
     * @return The primary firework particle colors.
     */
    public Collection<Color> getColors() {

        return Collections.unmodifiableCollection(colors);
    }

    /**
     * Adds the given {@link Color}s the particles spawned by this effect should have immediately after the firework explosion.
     * Note that the existing primary colors, which can be retrieved with {@link #getColors()}, are displayed as well.
     *
     * @param colors The additional primary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition addColors(Color... colors) {

        addColors(Arrays.asList(colors));
        return this;
    }

    /**
     * Adds the given {@link Color}s the particles spawned by this effect should have immediately after the firework explosion.
     * Note that the existing primary colors, which can be retrieved with {@link #getColors()}, are displayed as well.
     *
     * @param colors The additional primary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition addColors(Collection<Color> colors) {

        Validate.noNullElements(colors, "Cannot add null colors to firework effect definition");
        this.colors.addAll(colors);
        return this;
    }

    /**
     * Removes the given {@link Color}s the particles spawned by this effect should not have immediately after the firework explosion.
     * Note that only the other existing primary colors, which can be retrieved with {@link #getColors()}, are displayed after the removal.
     *
     * @param colors The primary firework particle colors for removal.
     * @return This object.
     */
    public FireworkEffectDefinition removeColors(Color... colors) {

        removeColors(Arrays.asList(colors));
        return this;
    }

    /**
     * Removes the given {@link Color}s the particles spawned by this effect should not have immediately after the firework explosion.
     * Note that only the other existing primary colors, which can be retrieved with {@link #getColors()}, are displayed after the removal.
     *
     * @param colors The primary firework particle colors for removal.
     * @return This object.
     */
    public FireworkEffectDefinition removeColors(Collection<Color> colors) {

        this.colors.removeAll(colors);
        return this;
    }

    /**
     * Returns the {@link Color}s the particles spawned by this effect fade to some time after the firework explosion.
     *
     * @return The secondary firework particle colors.
     */
    public Collection<Color> getFadeColors() {

        return Collections.unmodifiableCollection(fadeColors);
    }

    /**
     * Adds the given {@link Color}s the particles spawned by this effect should fade to some time after the firework explosion.
     * Note that the existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are displayed as well.
     *
     * @param fadeColors The additional secondary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition addFadeColors(Color... fadeColors) {

        addFadeColors(Arrays.asList(fadeColors));
        return this;
    }

    /**
     * Adds the given {@link Color}s the particles spawned by this effect should fade to some time after the firework explosion.
     * Note that the existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are displayed as well.
     *
     * @param fadeColors The additional secondary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition addFadeColors(Collection<Color> fadeColors) {

        Validate.noNullElements(fadeColors, "Cannot add null fade colors to firework effect definition");
        this.fadeColors.addAll(fadeColors);
        return this;
    }

    /**
     * Removes the given {@link Color}s the particles spawned by this effect should not fade to some time after the firework explosion.
     * Note that only the other existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are displayed after the removal.
     *
     * @param fadeColors The secondary firework particle colors for removal.
     * @return This object.
     */
    public FireworkEffectDefinition removeFadeColors(Color... fadeColors) {

        removeFadeColors(Arrays.asList(fadeColors));
        return this;
    }

    /**
     * Removes the given {@link Color}s the particles spawned by this effect should not fade to some time after the firework explosion.
     * Note that only the other existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are displayed after the removal.
     *
     * @param fadeColors The secondary firework particle colors for removal.
     * @return This object.
     */
    public FireworkEffectDefinition removeFadeColors(Collection<Color> fadeColors) {

        this.fadeColors.removeAll(fadeColors);
        return this;
    }

    @Override
    public FireworkEffectDefinition clone() {

        return new FireworkEffectDefinition().setType(type).addColors(colors).addFadeColors(fadeColors);
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
