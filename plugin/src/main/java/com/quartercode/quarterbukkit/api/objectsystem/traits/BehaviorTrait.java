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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.Validate;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.Behavior;
import com.quartercode.quarterbukkit.api.objectsystem.Trait;

/**
 * A {@link Trait} that adds special {@link Behavior} routines to a single {@link BaseObject object} so that those routines are only ran on that very object.
 * As you might imagine, the body of those object-specific routines is the object the routines are attached to via this trait.
 * So, essentially, this trait allows you to add custom code to individual objects, which can be quite useful if a single object should behave differently.
 * If, however, you want to add custom behavior to <b>multiple</b> objects of the same "type", try using the {@link CategoryTrait} instead.<br>
 * <br>
 * <b>Trait dependencies:</b> <i>none</i>
 *
 * @see Behavior
 * @see Trait
 */
public class BehaviorTrait extends Trait {

    private final List<Behavior<BaseObject>> behaviors = new ArrayList<>();

    /**
     * Creates a new behavior trait which doesn't have any object-specific {@link Behavior}s yet.
     */
    public BehaviorTrait() {

    }

    /**
     * Creates a new behavior trait with the given object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait will be part
     * of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @param behaviors The object-specific behaviors that should be used for this trait, which makes them apply to the object this trait will be part of.
     */
    @SafeVarargs
    public BehaviorTrait(Behavior<BaseObject>... behaviors) {

        addBehaviors(behaviors);
    }

    /**
     * Creates a new behavior trait with the given object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait will be part
     * of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @param behaviors The object-specific behaviors that should be used for this trait, which makes them apply to the object this trait will be part of.
     */
    public BehaviorTrait(Collection<Behavior<BaseObject>> behaviors) {

        addBehaviors(behaviors);
    }

    /**
     * Returns the object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait is part of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @return The object-specific behaviors of the object this trait is part of.
     */
    public List<Behavior<BaseObject>> getBehaviors() {

        return Collections.unmodifiableList(behaviors);
    }

    /**
     * Adds the given object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait is part of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @param behaviors The object-specific behaviors that should be added to this trait, which makes them apply to the object this trait is actually part of.
     * @return This object.
     */
    @SafeVarargs
    public final BehaviorTrait addBehaviors(Behavior<BaseObject>... behaviors) {

        addBehaviors(Arrays.asList(behaviors));
        return this;
    }

    /**
     * Adds the given object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait is part of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @param behaviors The object-specific behaviors that should be added to this trait, which makes them apply to the object this trait is actually part of.
     * @return This object.
     */
    public BehaviorTrait addBehaviors(Collection<Behavior<BaseObject>> behaviors) {

        Validate.noNullElements(behaviors, "Cannot add null behaviors to object system definition");
        this.behaviors.addAll(behaviors);
        return this;
    }

    /**
     * Removes the given object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait is part of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @param behaviors The object-specific behaviors that should be removed from this trait, which results in them no longer applying to the object this trait is actually part of.
     * @return This object.
     */
    @SafeVarargs
    public final BehaviorTrait removeBehaviors(Behavior<BaseObject>... behaviors) {

        removeBehaviors(Arrays.asList(behaviors));
        return this;
    }

    /**
     * Removes the given object-specific {@link Behavior}s, which are responsible for controlling the behavior of the single {@link BaseObject object} this trait is part of.
     * Although it might sound dumb at first, such behaviors can still use data from or even affect other objects in the {@link ActiveObjectSystem active system}.
     * For example, you could attach a "gravity" behavior to an object so that it pulls all other physical objects inside the system to it.
     * Remember that you can access the system an object is part of using {@link BaseObject#getSystem()}.
     *
     * @param behaviors The object-specific behaviors that should be removed from this trait, which results in them no longer applying to the object this trait is actually part of.
     * @return This object.
     */
    public BehaviorTrait removeBehaviors(Collection<Behavior<BaseObject>> behaviors) {

        this.behaviors.removeAll(behaviors);
        return this;
    }

}
