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
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.Location;

/**
 * An active object system runs the rules defined by an {@link ObjectSystemDefinition}.
 * It contains several objects that are constantly modified by {@link ModificationRule}s.
 * For example, a rule might add a force field that accelerates the objects into a certain direction by modifying its velocity vector.
 * Furthermore, object {@link Source}s defined by the object system definition constantly spawn new objects,
 * and old object have the possibility to expire and be removed from the system.
 * Note that the {@link Location}s of the objects are relative to a reference origin location.
 *
 * @see ObjectSystemDefinition
 * @see BaseObject
 */
public class ActiveObjectSystem {

    private final ObjectSystemDefinition definition;

    private final Location               origin;
    private final Collection<BaseObject> objects = new ArrayList<BaseObject>();
    private long                         lifetime;

    /**
     * Creates a new active object system that runs the given {@link ObjectSystemDefinition} and is centered on the given origin {@link Location}.
     *
     * @param definition The object system definition which defines the spawning of new objects and the behavior of existing ones.
     * @param origin The origin location the new active system is centered on.
     *        All object locations are relative to this origin.
     */
    public ActiveObjectSystem(ObjectSystemDefinition definition, Location origin) {

        this.definition = definition;
        this.origin = origin.clone();
    }

    /**
     * Returns the {@link ObjectSystemDefinition} which defines the spawning of new objects and the behavior of existing ones.
     *
     * @return The object system definition of the active system.
     */
    public ObjectSystemDefinition getDefinition() {

        return definition;
    }

    /**
     * Returns the origin {@link Location} the active system is centered on.
     * All object locations are relative to this origin.
     *
     * @return The origin location of the active system.
     */
    public Location getOrigin() {

        return origin.clone();
    }

    /**
     * Returns the objects, which must implement {@link BaseObject}, that are simulated by the active system.
     * Their behavior is defined by the system's {@link ObjectSystemDefinition}.
     *
     * @return The objects that are simulated by the active system.
     */
    public Collection<BaseObject> getObjects() {

        return Collections.unmodifiableCollection(objects);
    }

    /**
     * Adds the given objects, which must implement {@link BaseObject}, to the active system that should simulate them.
     * Their behavior is defined by the system's {@link ObjectSystemDefinition}.
     *
     * @param objects The objects that should be added to the active system.
     */
    public void addObjects(BaseObject... objects) {

        addObjects(Arrays.asList(objects));
    }

    /**
     * Adds the given objects, which must implement {@link BaseObject}, to the active system that should simulate them.
     * Their behavior is defined by the system's {@link ObjectSystemDefinition}.
     *
     * @param objects The objects that should be added to the active system.
     */
    public void addObjects(Collection<BaseObject> objects) {

        Validate.noNullElements(objects, "Cannot add null objects to active object system");
        this.objects.addAll(objects);

        // Tell the objects that they have just been added to this active system
        for (BaseObject object : objects) {
            object.setSystem(this);
        }
    }

    /**
     * Removes the given objects, which must implement {@link BaseObject}, from the active system in order to stop them from being simulated.
     *
     * @param objects The objects that should be removed from the active system.
     */
    public void removeObjects(BaseObject... objects) {

        removeObjects(Arrays.asList(objects));
    }

    /**
     * Removes the given objects, which must implement {@link BaseObject}, from the active system in order to stop them from being simulated.
     *
     * @param objects The objects that should be removed from the active system.
     */
    public void removeObjects(Collection<BaseObject> objects) {

        this.objects.removeAll(objects);
    }

    /**
     * Returns the amount of milliseconds the active object system has been simulated for.
     * Note that this is guaranteed to be 0 the first time the active system is updated.
     * After that, this value increases by the elapsed time {@code dt} each time the active system is updated.
     *
     * @return The current lifetime of the active system.
     */
    public long getLifetime() {

        return lifetime;
    }

    /**
     * Increments the current lifetime of the active object system by the given number of milliseconds.
     * <b>Note that this is an internal method and should not be used as an API function.</b>
     *
     * @param dt The number of milliseconds that should be added to the active object system's lifetime.
     */
    public void incrementLifetime(long dt) {

        lifetime += dt;

        for (BaseObject object : objects) {
            object.incrementLifetime(dt);
        }
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
