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
import java.util.Optional;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import com.quartercode.quarterbukkit.api.BiOptional;

/**
 * The base interface for all objects that can be put into an {@link ActiveObjectSystem}.
 * It defines the fundamental lifetime property and manages a collection of {@link Trait}s that further specialize the object.
 * For example, the physics trait adds a position and a velocity to the object so that it can be located and take part in the physics simulation.
 * Moreover, it holds a reference to the active object system the object is currently part of.
 *
 * @see Trait
 * @see ActiveObjectSystem
 */
public class BaseObject {

    private long               lifetime;
    private final List<Trait>  traits = new ArrayList<>();

    private ActiveObjectSystem system;

    /**
     * Returns the amount of milliseconds the object has existed inside its {@link ActiveObjectSystem}.
     * Note that this is guaranteed to be 0 the first time the object is updated.
     * After that, this value increases by the elapsed time {@code dt} each time the object is updated.
     *
     * @return The current lifetime of the object in milliseconds.
     */
    public long getLifetime() {

        return lifetime;
    }

    /**
     * Increments the current lifetime of the object by the given number of milliseconds.
     * <b>Note that this is an internal method and should not be used as an API function.</b>
     *
     * @param dt The number of milliseconds that should be added to the object's lifetime.
     */
    protected void incrementLifetime(long dt) {

        lifetime += dt;
    }

    /**
     * Returns all {@link Trait}s which specialize this very object.
     * You will probably never use this method. Consider using {@link #get(Class)} instead.
     *
     * @return All traits which are held by this object.
     */
    public List<Trait> getTraits() {

        return Collections.unmodifiableList(traits);
    }

    /**
     * Returns the {@link Trait} of this object that is an instance or a subclass of the given type.
     * For example, this method could be used to get the physics trait which is attached to a specific object.
     * If there are multiple traits that match the given criterion, only the first one is returned. No warnings or such things are printed.
     * If there is no trait at all that matches the criterion, {@link Optional#empty()} is returned.
     *
     * @param <T> The type of trait you want to look for.
     * @param traitType The type of trait you want to look for, as a class object.
     * @return The first trait that fulfills the given type, or {@link Optional#empty()} if there's no such trait.
     */
    public <T extends Trait> Optional<T> get(Class<T> traitType) {

        Trait trait = getTraitWithCustomCollection(traits, traitType);
        return Optional.ofNullable(trait == null ? null : traitType.cast(trait));
    }

    /**
     * Returns the two {@link Trait}s of this object that are an instance or a subclass of the respective given type.
     * For example, this method could be used to get the both physics trait and the category trait which are attached to a specific object simultaneously.
     * If, for any of the two trait types, there are multiple traits that match the specified criterion, only the first one is returned as part of the {@link BiOptional}.
     * No warnings or such things are printed.
     * If, for any of the two trait types, there is no trait at all that matches the criterion, {@link BiOptional#empty()} is returned.
     *
     * @param <T1> The first type of trait you want to look for.
     * @param <T2> The second type of trait you want to look for.
     * @param traitType1 The type of the first trait you want to look for, as a class object.
     * @param traitType2 The type of the second trait you want to look for, as a class object.
     * @return For both trait types, the respective first trait that fulfills the given type, or {@link BiOptional#empty()} if either or both wanted traits can't be found.
     */
    public <T1 extends Trait, T2 extends Trait> BiOptional<T1, T2> get(Class<T1> traitType1, Class<T2> traitType2) {

        Trait trait1 = getTraitWithCustomCollection(traits, traitType1);
        Trait trait2 = getTraitWithCustomCollection(traits, traitType2);
        return BiOptional.ofNullables(trait1 == null ? null : traitType1.cast(trait1), trait2 == null ? null : traitType2.cast(trait2));
    }

    /*
     * Returns the first trait in the given collection that is an instance of the given class.
     * If there's no such trait, null is returned.
     */
    private Trait getTraitWithCustomCollection(Collection<Trait> traits, Class<? extends Trait> traitType) {

        for (Trait trait : traits) {
            if (traitType.isInstance(trait)) {
                return trait;
            }
        }

        return null;
    }

    /**
     * Returns for all given trait types whether this object contains a {@link Trait} that is an instance or a subclass of the given type.
     * For example, this method could be used to check whether an object has some kind of physics trait attached to it.
     *
     * @param traitTypes The types of trait you want to look for, as a class object.
     * @return Whether this object contains a trait that fulfills the given type for all given types.
     */
    @SafeVarargs
    public final boolean has(Class<? extends Trait>... traitTypes) {

        for (Class<? extends Trait> traitType : traitTypes) {
            if (!get(traitType).isPresent()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds the given {@link Trait}s to the object so that they specialize it more.
     * If any of the traits defines {@link TraitDependencies dependencies on other traits}, those dependencies are immediately checked.
     * In case some dependency of some trait wouldn't be fulfilled after the addition completed, the whole operation is aborted.
     *
     * @param traits The traits that should be added to the object in order to specialize it.
     * @return This object.
     * @throws IllegalStateException If one of the given traits is already part of another object, or if one new trait dependency cannot be fulfilled.
     */
    public BaseObject add(Trait... traits) {

        add(Arrays.asList(traits));
        return this;
    }

    /**
     * Adds the given {@link Trait}s to the object so that they specialize it more.
     * If any of the traits defines {@link TraitDependencies dependencies on other traits}, those dependencies are immediately checked.
     * In case some dependency of some trait wouldn't be fulfilled after the addition completed, the whole operation is aborted.
     *
     * @param traits The traits that should be added to the object in order to specialize it.
     * @return This object.
     * @throws IllegalStateException If one of the given traits is already part of another object, or if one new trait dependency cannot be fulfilled.
     */
    public BaseObject add(Collection<Trait> traits) {

        Validate.noNullElements(traits, "Cannot add null traits to object");

        // Ensure that the new objects are not part of any object yet, and that all trait dependencies are fulfilled
        for (Trait newTrait : traits) {
            if (newTrait.getObject() != null) {
                throw new IllegalStateException("A trait can't be added to two objects at the same time");
            }

            for (Class<? extends Trait> dependency : getTraitDependencies(newTrait.getClass())) {
                if (!has(dependency) && getTraitWithCustomCollection(traits, dependency) == null) {
                    throw new IllegalStateException("The dependency of the trait '" + newTrait.getClass().getName() + "' on '" + dependency.getName() + "' cannot be fulfilled");
                }
            }
        }

        this.traits.addAll(traits);

        // Tell the traits that they have just been added to this object
        for (Trait trait : traits) {
            trait.setObject(this);
        }

        return this;
    }

    /**
     * Removes the given {@link Trait}s from the object and thus makes the object lose some of its specialization.
     * If any of the remaining traits defines {@link TraitDependencies dependencies on other traits}, this method immediately checks whether they can still be fulfilled after the removal completed.
     * If that's not the case, the whole operation is aborted.
     *
     * @param traits The traits that should be removed from the object in order to remove some of its specialization.
     * @return This object.
     * @throws IllegalStateException If one of the traits that remains after the operation has completed has a dependency that wouldn't be fulfilled anymore.
     */
    public BaseObject remove(Trait... traits) {

        remove(Arrays.asList(traits));
        return this;
    }

    /**
     * Removes the given {@link Trait}s from the object and thus makes the object lose some of its specialization.
     * If any of the remaining traits defines {@link TraitDependencies dependencies on other traits}, this method immediately checks whether they can still be fulfilled after the removal completed.
     * If that's not the case, the whole operation is aborted.
     *
     * @param traits The traits that should be removed from the object in order to remove some of its specialization.
     * @return This object.
     * @throws IllegalStateException If one of the traits that remains after the operation has completed has a dependency that wouldn't be fulfilled anymore.
     */
    public BaseObject remove(Collection<Trait> traits) {

        List<Trait> remainingTraits = new ArrayList<>(this.traits);
        remainingTraits.removeAll(traits);

        // Ensure that the new objects are not part of any object yet, and that all trait dependencies are fulfilled
        for (Trait remainingTrait : remainingTraits) {
            for (Class<? extends Trait> dependency : getTraitDependencies(remainingTrait.getClass())) {
                if (getTraitWithCustomCollection(remainingTraits, dependency) == null) {
                    throw new IllegalStateException("The dependency of the trait '" + remainingTrait.getClass().getName() + "' on '" + dependency.getName() + "' could no longer be fulfilled because the dependency should be removed");
                }
            }
        }

        this.traits.removeAll(traits);

        // Tell the traits that they have just been removed from this object
        for (Trait trait : traits) {
            trait.setObject(null);
        }

        return this;
    }

    private List<Class<? extends Trait>> getTraitDependencies(Class<? extends Trait> traitType) {

        if (traitType.isAnnotationPresent(TraitDependencies.class)) {
            return Arrays.asList(traitType.getAnnotation(TraitDependencies.class).value());
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns the {@link ActiveObjectSystem} this object is part of, or {@code null} if this object hasn't been {@link ActiveObjectSystem#addObjects(BaseObject...) added} to any active system yet.
     * Please use the returned reference with caution and try to stick to the main design principles of the object system API.
     * If you ignore those, you quickly lose many of the advantages this API provides you with.
     * Most importantly, if you see another more elegant way to get something done without accessing this reference, do it that way!
     *
     * @return The active object system that contains and simulates this object.
     */
    public ActiveObjectSystem getSystem() {

        return system;
    }

    /**
     * Tells the object that it has just been added to the given {@link ActiveObjectSystem}.
     * If you provide {@code null} as an argument, it tells the object that it has just been removed from its previous active system.
     * <b>Note that this is an internal method and should not be used as an API function.</b>
     *
     * @param system The active object system this object is now part of.
     */
    protected void setSystem(ActiveObjectSystem system) {

        this.system = system;
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
