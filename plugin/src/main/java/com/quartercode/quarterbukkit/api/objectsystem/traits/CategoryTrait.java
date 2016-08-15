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

package com.quartercode.quarterbukkit.api.objectsystem.traits;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.Validate;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Behavior;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;

/**
 * A {@link Trait} that just attaches a list of "categories" -- which are in fact just strings -- to an {@link BaseObject object}.
 * You can then respect those categories when writing {@link Behavior}s in order to only change a subset of objects in your object system.
 * For example, take a look at the following behavior, which only changes objects which are part of the category {@code test}:
 *
 * <pre>{@code
 * systemDef.addBehaviors((dt, activeSystem) -> {
 *     for (BaseObject obj : activeSystem.getObjects())
 *         if (obj.get(CategoryTrait.class).filter(cat -> cat.hasCategories("test")).isPresent())
 *             // ... do something with obj
 * });
 * }</pre>
 *
 * <b>Trait dependencies:</b> <i>none</i>
 *
 * @see Trait
 */
public class CategoryTrait extends Trait {

    private final Set<String> categories = new HashSet<>();

    /**
     * Returns all categories the object is part of.
     *
     * @return All category strings.
     */
    public Set<String> getCategories() {

        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns whether the object is part of each of the given categories.
     *
     * @param categories The category strings the object needs to be part of in order for this method to return {@code true}.
     * @return Whether the object is part of all given category strings.
     */
    public boolean hasCategories(String... categories) {

        return this.categories.containsAll(Arrays.asList(categories));
    }

    /**
     * Adds the object to the given categories.
     *
     * @param categories The category strings to add the object to.
     * @return This object.
     */
    public CategoryTrait addCategories(String... categories) {

        addCategories(Arrays.asList(categories));
        return this;
    }

    /**
     * Adds the object to the given categories.
     *
     * @param categories The category strings to add the object to.
     * @return This object.
     */
    public CategoryTrait addCategories(Collection<String> categories) {

        Validate.noNullElements(categories, "Cannot add null categories to category trait");
        this.categories.addAll(categories);
        return this;
    }

    /**
     * Removes the object from the given categories.
     *
     * @param categories The category strings to remove the object from.
     * @return This object.
     */
    public CategoryTrait removeCategories(String... categories) {

        removeCategories(Arrays.asList(categories));
        return this;
    }

    /**
     * Removes the object from the given categories.
     *
     * @param categories The category strings to remove the object from.
     * @return This object.
     */
    public CategoryTrait removeCategories(Collection<String> categories) {

        this.categories.removeAll(categories);
        return this;
    }

}
