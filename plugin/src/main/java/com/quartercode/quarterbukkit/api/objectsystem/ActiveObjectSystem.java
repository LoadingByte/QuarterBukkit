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
import java.util.stream.Stream;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import com.quartercode.quarterbukkit.api.objectsystem.traits.PhysicsTrait;

/**
 * An active object system runs the rules defined by an {@link ObjectSystemDefinition}.
 * It contains several objects that are constantly modified by {@link ObjectSystemDefinition#getBehaviors() global behaviors}.
 * For example, such a global behavior might add a force field that accelerates the objects into a certain direction by modifying its velocity vector.
 * Furthermore, global behaviors can also be used to spawn new objects or remove old ones.
 * Note that the locations of the objects are relative to a reference origin location.<br>
 * <br>
 * It's important to note that each active object system is in fact also a {@link Trait}.
 * By adding it to other objects, you can nest one active system inside another one and thereby create way more complex setups than you otherwise could.<br>
 * <br>
 * <b>Trait dependencies:</b> {@link PhysicsTrait}
 *
 * @see ObjectSystemDefinition
 * @see BaseObject
 */
@TraitDependencies (PhysicsTrait.class)
public class ActiveObjectSystem extends Trait {

    private final ObjectSystemDefinition definition;

    private final Collection<BaseObject> objects = new ArrayList<>();
    private int                          lifetime;

    // In case this is a root active object system
    private Location                     manualOrigin;

    /**
     * Creates a new active object system that runs the given {@link ObjectSystemDefinition} and is centered on the origin {@code (0/0/0)} in the first available {@link World}.
     * This constructor is most useful when you just need a dummy origin because you want to nest this object system inside another one.
     *
     * @param definition The object system definition which defines the spawning of new objects and the behavior of existing ones.
     */
    public ActiveObjectSystem(ObjectSystemDefinition definition) {

        this.definition = definition;
        manualOrigin = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);
    }

    /**
     * Creates a new active object system that runs the given {@link ObjectSystemDefinition} and is centered on the given origin {@link Location}.
     *
     * @param definition The object system definition which defines the spawning of new objects and the behavior of existing ones.
     * @param origin The origin location the new active system is centered on.
     *        All object locations are relative to this origin.
     */
    public ActiveObjectSystem(ObjectSystemDefinition definition, Location origin) {

        this.definition = definition;
        manualOrigin = origin.clone();
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
     * Returns whether this active object system is in fact not a root one, but nested inside another system.
     * That's possible since the active system class is a {@link Trait} in itself.
     *
     * @return Whether this is a system that's nested inside another one.
     */
    public boolean isNested() {

        return getObject() != null && getObject().getSystem() != null;
    }

    /**
     * Returns the origin {@link Location} the active system is centered on.
     * All object locations are relative to this origin.
     *
     * @return The origin location of the active system.
     */
    public Location getOrigin() {

        if (isNested()) {
            return manualOrigin.clone();
        } else {
            ActiveObjectSystem parentSystem = getObject().getSystem();
            Vector relativePosition = getObject().get(PhysicsTrait.class).getPosition();
            return parentSystem.getOrigin().add(relativePosition);
        }
    }

    /**
     * If this active system is not nested inside another system, this method manually sets the origin this system is centered on.
     * All object locations are relative to this origin.
     *
     * @param origin The new origin location the active system should be centered on.
     */
    public void setOrigin(Location origin) {

        manualOrigin = origin.clone();
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
     * Returns a {@link Stream} with the objects, which must implement {@link BaseObject}, that are simulated by the active system.
     * Their behavior is defined by the system's {@link ObjectSystemDefinition}.
     *
     * @return A stream with the objects that are simulated by the active system.
     */
    public Stream<BaseObject> objectStream() {

        return objects.stream();
    }

    /**
     * Adds the given objects, which must implement {@link BaseObject}, to the active system that should simulate them.
     * Their behavior is defined by the system's {@link ObjectSystemDefinition}.
     *
     * @param objects The objects that should be added to the active system.
     * @throws IllegalStateException If one of the given objects is already part of another active object system.
     */
    public void addObjects(BaseObject... objects) {

        addObjects(Arrays.asList(objects));
    }

    /**
     * Adds the given objects, which must implement {@link BaseObject}, to the active system that should simulate them.
     * Their behavior is defined by the system's {@link ObjectSystemDefinition}.
     *
     * @param objects The objects that should be added to the active system.
     * @throws IllegalStateException If one of the given objects is already part of another active object system.
     */
    public void addObjects(Collection<BaseObject> objects) {

        Validate.noNullElements(objects, "Cannot add null objects to active object system");

        // Ensure that the new objects are not part of any object system yet
        for (BaseObject object : objects) {
            if (object.getSystem() != null) {
                throw new IllegalStateException("An object can't be added to two active object systems at the same time");
            }
        }

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

        // Tell the objects that they have just been removed from this active system
        for (BaseObject object : objects) {
            object.setSystem(null);
        }
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

}
