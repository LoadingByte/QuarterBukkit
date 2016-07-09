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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * This class describes an object system and allows to create new equivalent {@link ActiveObjectSystem}s using it.
 * It basically contains some {@link Source}s, which spawn new objects, and some {@link ModificationRule}s, which define the behavior of these objects.
 *
 * @see Source
 * @see ModificationRule
 * @see ActiveObjectSystem
 */
public class ObjectSystemDefinition {

    private final Collection<Source>                 sources           = new ArrayList<Source>();
    private final Collection<ModificationRule<?, ?>> modificationRules = new ArrayList<ModificationRule<?, ?>>();

    /**
     * Returns the object {@link Source}s, which are responsible for spawning new objects.
     *
     * @return The sources of the defined object system.
     */
    public Collection<Source> getSources() {

        return Collections.unmodifiableCollection(sources);
    }

    /**
     * Adds the given object {@link Source}s, which are responsible for spawning new objects, to the defined object system.
     *
     * @param sources The sources that should be added to the object system.
     */
    public void addSources(Source... sources) {

        addSources(Arrays.asList(sources));
    }

    /**
     * Adds the given object {@link Source}s, which are responsible for spawning new objects, to the defined object system.
     *
     * @param sources The sources that should be added to the object system.
     */
    public void addSources(List<Source> sources) {

        Validate.noNullElements(sources, "Cannot add null sources to object system definition");
        this.sources.addAll(sources);
    }

    /**
     * Removes the given object {@link Source}s, which are responsible for spawning new objects, from the defined object system.
     *
     * @param sources The sources that should be removed from the object system.
     */
    public void removeSources(Source... sources) {

        removeSources(Arrays.asList(sources));
    }

    /**
     * Removes the given object {@link Source}s, which are responsible for spawning new objects, from the defined object system.
     *
     * @param sources The sources that should be removed from the object system.
     */
    public void removeSources(Collection<Source> sources) {

        this.sources.removeAll(sources);
    }

    /**
     * Returns the object {@link ModificationRule}s, which are responsible for controlling the behavior of existing objects.
     *
     * @return The modification rules of the defined object system.
     */
    public Collection<ModificationRule<?, ?>> getModificationRules() {

        return Collections.unmodifiableCollection(modificationRules);
    }

    /**
     * Adds the given object {@link ModificationRule}s, which are responsible for controlling the behavior of existing objects, to the defined object system.
     *
     * @param modificationRules The modification rules that should be added to the object system.
     */
    public void addModificationRules(ModificationRule<?, ?>... modificationRules) {

        addModificationRules(Arrays.asList(modificationRules));
    }

    /**
     * Adds the given object {@link ModificationRule}s, which are responsible for controlling the behavior of existing objects, to the defined object system.
     *
     * @param modificationRules The modification rules that should be added to the object system.
     */
    public void addModificationRules(List<ModificationRule<?, ?>> modificationRules) {

        Validate.noNullElements(modificationRules, "Cannot add null modification rules to object system definition");
        this.modificationRules.addAll(modificationRules);
    }

    /**
     * Removes the given object {@link ModificationRule}s, which are responsible for controlling the behavior of existing objects, from the defined object system.
     *
     * @param modificationRules The modification rules that should be removed from the object system.
     */
    public void removeModificationRules(ModificationRule<?, ?>... modificationRules) {

        removeModificationRules(Arrays.asList(modificationRules));
    }

    /**
     * Removes the given object {@link ModificationRule}s, which are responsible for controlling the behavior of existing objects, from the defined object system.
     *
     * @param modificationRules The modification rules that should be removed from the object system.
     */
    public void removeModificationRules(Collection<ModificationRule<?, ?>> modificationRules) {

        this.modificationRules.removeAll(modificationRules);
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
