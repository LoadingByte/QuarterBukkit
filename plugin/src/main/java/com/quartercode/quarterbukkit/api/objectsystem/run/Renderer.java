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

package com.quartercode.quarterbukkit.api.objectsystem.run;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;

/**
 * A renderer takes an object, which must extend {@link BaseObject}, and performs some action using it.
 * For example, it could display a simple particle. However, it might also modify the object itself.
 * Note that all renderers are called after the modifications were performed on each object.
 * 
 * @param <O> The type of object the renderer can use to perform some action. This must extend {@link BaseObject}.
 */
public interface Renderer<O extends BaseObject> {

    /**
     * The different results of the rendering process.
     * For example, the rendered object might be removed.
     */
    public static enum RenderingResult {

        /**
         * Nothing special should happen.
         */
        NOTHING,
        /**
         * The object should be removed from its {@link ActiveObjectSystem}.
         * Note that following renderers will not receive a rendering call for that object during the system update.
         */
        REMOVE;

    }

    /**
     * Returns the generic {@code O} parameter, which specifies the type of object the renderer accepts, as a {@link Class} object.
     * This is used to determine whether the renderer is able to handle the object.
     * 
     * @return The generic {@code O} parameter as a class object.
     */
    public Class<O> getObjectType();

    /**
     * Performs some rendering actions using the given object which is located inside the given {@link ActiveObjectSystem}.
     * For example, this method could display a simple particle. However, it might also modify the object itself.
     * Note that this method is called on all renderers after the modifications were performed on each object.
     * 
     * @param plugin The {@link Plugin} that started a runner which now simulates the active system and called the renderer.
     * @param objectSystem The active system the object is used in.
     * @param object The actual object that should be rendered.
     * @return The result of the rendering process. See {@link RenderingResult} for more information on the possible results.
     */
    public RenderingResult render(Plugin plugin, ActiveObjectSystem objectSystem, O object);

}
