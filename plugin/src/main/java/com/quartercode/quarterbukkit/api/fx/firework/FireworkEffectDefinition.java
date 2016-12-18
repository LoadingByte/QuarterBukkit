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

package com.quartercode.quarterbukkit.api.fx.firework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;

/**
 * This class represents a single effect that is part of the explosion of a firework.
 * Essentially, this class is a mutable version of the default {@link FireworkEffect} class that comes with Bukkit.
 *
 * @see FireworkEffect
 * @see FireworkEffectSpawner
 */
public class FireworkEffectDefinition {

    private Type              type;
    private boolean           flicker;
    private boolean           trail;
    private final List<Color> colors     = new ArrayList<>();
    private final List<Color> fadeColors = new ArrayList<>();

    /**
     * Creates a new firework effect definition with the given {@link Type}.
     * Don't forget to {@link #addColors(Color...) add some colors} to it, or otherwise it won't be visible!
     *
     * @param type The firework effect type.
     */
    public FireworkEffectDefinition(Type type) {

        setType(type);
    }

    /**
     * Creates a new firework effect definition that copies the information which is stored inside the given firework effect definition.
     *
     * @param from The firework effect definition to copy the initial information from.
     */
    public FireworkEffectDefinition(FireworkEffectDefinition from) {

        from(from);
    }

    /**
     * Creates a new firework effect definition that copies the information which is stored inside the given Bukkit {@link FireworkEffect}.
     *
     * @param from The Bukkit firework effect to copy the initial information from.
     */
    public FireworkEffectDefinition(FireworkEffect from) {

        fromBukkit(from);
    }

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

        Validate.notNull(type, "Firework effect type cannot be null");
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
     *
     * @return Whether the firework effect particles leave behind a trail.
     */
    public boolean hasTrail() {

        return trail;
    }

    /**
     * Sets whether the particles spawned by this effect during the firework explosion leave behind a trail of other particles.
     * Note that this option is only recommended in systems with few objects because the trail increases the particle amount by quite a bit.
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
    public List<Color> getColors() {

        return Collections.unmodifiableList(colors);
    }

    /**
     * Sets the {@link Color}s the particles spawned by this effect should have immediately after the firework explosion.
     * Note that the already existing primary colors, which can be retrieved with {@link #getColors()}, are removed through this call.
     * If you just want to add new colors, try {@link #addColors(Color...)}.
     *
     * @param colors The new primary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition setColors(Color... colors) {

        setColors(Arrays.asList(colors));
        return this;
    }

    /**
     * Sets the {@link Color}s the particles spawned by this effect should have immediately after the firework explosion.
     * Note that the already existing primary colors, which can be retrieved with {@link #getColors()}, are removed through this call.
     * If you just want to add new colors, try {@link #addColors(Collection)}.
     *
     * @param colors The new primary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition setColors(Collection<Color> colors) {

        Validate.noNullElements(colors, "Cannot add null colors to firework effect definition");
        this.colors.clear();
        this.colors.addAll(colors);
        return this;
    }

    /**
     * Adds the given {@link Color}s the particles spawned by this effect should have immediately after the firework explosion.
     * Note that the already existing primary colors, which can be retrieved with {@link #getColors()}, are displayed as well if you don't remove them.
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
     * Note that the already existing primary colors, which can be retrieved with {@link #getColors()}, are displayed as well if you don't remove them.
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
    public List<Color> getFadeColors() {

        return Collections.unmodifiableList(fadeColors);
    }

    /**
     * Sets the {@link Color}s the particles spawned by this effect should fade to some time after the firework explosion.
     * Note that the already existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are removed through this call.
     * If you just want to add new fade colors, try {@link #addFadeColors(Color...)}.
     *
     * @param fadeColors The new secondary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition setFadeColors(Color... fadeColors) {

        setFadeColors(Arrays.asList(fadeColors));
        return this;
    }

    /**
     * Sets the {@link Color}s the particles spawned by this effect should fade to some time after the firework explosion.
     * Note that the already existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are removed through this call.
     * If you just want to add new fade colors, try {@link #addFadeColors(Collection)}.
     *
     * @param fadeColors The new secondary firework particle colors.
     * @return This object.
     */
    public FireworkEffectDefinition setFadeColors(Collection<Color> fadeColors) {

        Validate.noNullElements(fadeColors, "Cannot add null fade colors to firework effect definition");
        this.fadeColors.clear();
        this.fadeColors.addAll(fadeColors);
        return this;
    }

    /**
     * Adds the given {@link Color}s the particles spawned by this effect should fade to some time after the firework explosion.
     * Note that the already existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are displayed as well if you don't remove them.
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
     * Note that the already existing secondary colors, which can be retrieved with {@link #getFadeColors()}, are displayed as well if you don't remove them.
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

    /**
     * Copies the the information which is stored inside the given firework effect definition into this one.
     * After the operation completed, the two definitions are exact copies of each other.
     *
     * @param from The firework effect definition to copy the information from.
     * @return This object.
     */
    public FireworkEffectDefinition from(FireworkEffectDefinition from) {

        type = from.getType();
        flicker = from.hasFlicker();
        trail = from.hasTrail();
        colors.addAll(from.getColors());
        fadeColors.addAll(from.getFadeColors());

        return this;
    }

    /**
     * Copies the the information which is stored inside the given Bukkit {@link FireworkEffect} into this one.
     * After the operation completed, this definitions exactly represents the firework effect described by the given Bukkit object.
     *
     * @param from The Bukkit firework effect to copy the information from.
     * @return This object.
     */
    public FireworkEffectDefinition fromBukkit(FireworkEffect from) {

        type = from.getType();
        flicker = from.hasFlicker();
        trail = from.hasTrail();
        colors.addAll(from.getColors());
        fadeColors.addAll(from.getFadeColors());

        return this;
    }

    /**
     * Converts this firework effect definition into a native Bukkit {@link FireworkEffect}.
     *
     * @return The Bukkit firework effect which contains this definition's information.
     */
    public FireworkEffect toBukkit() {

        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.with(type);
        builder.flicker(flicker);
        builder.trail(trail);
        builder.withColor(colors);
        builder.withFade(fadeColors);
        return builder.build();
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
