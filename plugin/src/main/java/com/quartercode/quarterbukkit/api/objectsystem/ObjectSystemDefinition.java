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

package com.quartercode.quarterbukkit.api.objectsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class defines how an object system should behave and allows to create new {@link ActiveObjectSystem}s, which actually implement those rules, using it.
 * It basically contains some global {@link Behavior}s, which are i.e. responsible for spawning new objects or controlling the behavior of existing ones.
 * For example, such a global behavior might add a force field that accelerates the objects into a certain direction by modifying its velocity vector.
 *
 * @see Behavior
 * @see ActiveObjectSystem
 */
public class ObjectSystemDefinition {

    private final List<Behavior<ActiveObjectSystem>> behaviors = new ArrayList<>();

    /**
     * Returns the global {@link Behavior}s, which are responsible for controlling the behavior of the {@link ActiveObjectSystem object system instances} itself, as well as all existing objects.
     * Such behaviors typically either affect all objects or add/remove objects from/to the active system.
     *
     * @return The global behaviors of the defined object system.
     */
    public List<Behavior<ActiveObjectSystem>> getBehaviors() {

        return Collections.unmodifiableList(behaviors);
    }

    /**
     * Adds the given global {@link Behavior}s, which are responsible for controlling the behavior of the {@link ActiveObjectSystem object system instances} itself, as well as all existing objects.
     * Such behaviors typically either affect all objects or add/remove objects from/to the active system.
     *
     * @param behaviors The global behaviors that should be added to the defined object system.
     */
    @SafeVarargs
    public final void addBehaviors(Behavior<ActiveObjectSystem>... behaviors) {

        addBehaviors(Arrays.asList(behaviors));
    }

    /**
     * Adds the given global {@link Behavior}s, which are responsible for controlling the behavior of the {@link ActiveObjectSystem object system instances} itself, as well as all existing objects.
     * Such behaviors typically either affect all objects or add/remove objects from/to the active system.
     *
     * @param behaviors The global behaviors that should be added to the defined object system.
     */
    public void addBehaviors(List<Behavior<ActiveObjectSystem>> behaviors) {

        Validate.noNullElements(behaviors, "Cannot add null behaviors to object system definition");
        this.behaviors.addAll(behaviors);
    }

    /**
     * Removes the given global {@link Behavior}s, which are responsible for controlling the behavior of the {@link ActiveObjectSystem object system instances} itself, as well as all existing objects.
     * Such behaviors typically either affect all objects or add/remove objects from/to the active system.
     *
     * @param behaviors The global behaviors that should be removed from the defined object system.
     */
    @SafeVarargs
    public final void removeBehaviors(Behavior<ActiveObjectSystem>... behaviors) {

        removeBehaviors(Arrays.asList(behaviors));
    }

    /**
     * Removes the given global {@link Behavior}s, which are responsible for controlling the behavior of the {@link ActiveObjectSystem object system instances} itself, as well as all existing objects.
     * Such behaviors typically either affect all objects or add/remove objects from/to the active system.
     *
     * @param behaviors The global behaviors that should be removed from the defined object system.
     */
    public void removeBehaviors(List<Behavior<ActiveObjectSystem>> behaviors) {

        this.behaviors.removeAll(behaviors);
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
