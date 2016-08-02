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
import com.quartercode.quarterbukkit.api.objectsystem.ModificationRule;

/**
 * A renderer takes an object, which must extend {@link BaseObject}, and performs some action using it.
 * For example, it could display a simple particle. However, it might also modify the object itself.
 * Note that all renderers are called after the modifications were performed on each object.<br>
 * <br>
 * If you are interested in the conceptual differences between renderers and modification rules, look into the documentation for {@link ModificationRule}s.
 *
 * @param <O> The type of object the renderer can use to perform some action. This must extend {@link BaseObject}.
 */
public interface Renderer<O extends BaseObject> {

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
     * Note the important variable {@code dt} which basically informs you about the currently used time resolution.
     * Also note that this method is called on all renderers after the modifications were performed on each object.
     *
     * @param plugin The {@link Plugin} that started a runner which now simulates the active system and called the renderer.
     * @param dt The amount of milliseconds which have elapsed since the last update of the active system this renderer is part of.
     * @param object The actual object that should be rendered.
     */
    public void render(Plugin plugin, long dt, O object);

}
