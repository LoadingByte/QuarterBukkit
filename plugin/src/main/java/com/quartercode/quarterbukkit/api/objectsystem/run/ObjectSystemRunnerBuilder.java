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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.MathUtil;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;

/**
 * A builder object for creating instances of {@link ObjectSystemRunner}.
 *
 * @see ObjectSystemRunner
 */
public class ObjectSystemRunnerBuilder {

    /**
     * A {@link List} containing the default {@link Renderer}s that are used by the runner if no custom renderers are specified.
     */
    public static final List<Renderer> DEFAULT_RENDERERS;

    static {

        List<Renderer> defaultRenderers = new ArrayList<>();
        defaultRenderers.add(new BehaviorTraitRenderer());
        defaultRenderers.add(new ExpirationTraitRenderer());
        defaultRenderers.add(new StandalonePhysicsTraitRenderer());
        defaultRenderers.add(new RealEntityPhysicsTraitRenderer());
        defaultRenderers.add(new ParticleTraitRenderer());
        defaultRenderers.add(new FireworkTraitRenderer());
        DEFAULT_RENDERERS = Collections.unmodifiableList(defaultRenderers);

    }

    private final Plugin             plugin;
    private final ActiveObjectSystem objectSystem;

    private List<Renderer>           renderers         = DEFAULT_RENDERERS;
    private long                     timeResolution    = MathUtil.getMillis(1);
    private boolean                  stopWhenNoObjects = false;

    /**
     * Creates a new object system runner builder for a new object system runner that simulates the given {@link ActiveObjectSystem} and uses the given {@link Plugin} as host.
     *
     * @param plugin The plugin that is used to call some required Bukkit methods.
     *        It hosts the new runner.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     */
    public ObjectSystemRunnerBuilder(Plugin plugin, ActiveObjectSystem objectSystem) {

        this.plugin = plugin;
        this.objectSystem = objectSystem;
    }

    /**
     * Adds the given list of custom {@link Renderer}s, which are used to simulate and display the objects of the {@link ActiveObjectSystem}, to the end of the current renderer list.
     * In contrast to {@link #renderersReplaceAll(List)}, this method keeps the default renderers in place, which is the desired behavior in most cases.
     *
     * @param renderers The new renderers that also simulate and display the objects of the active system.
     * @return This object.
     */
    public ObjectSystemRunnerBuilder renderers(List<Renderer> renderers) {

        this.renderers.addAll(renderers);
        return this;
    }

    /**
     * Uses the given list of custom {@link Renderer}s for simulating and displaying the objects of the {@link ActiveObjectSystem}.
     * Note that this completely overrides the previously set list of {@link #DEFAULT_RENDERERS}, which you most certainly want to add manually as well.
     *
     * @param renderers The renderers that simulate and display the objects of the active system.
     * @return This object.
     */
    public ObjectSystemRunnerBuilder renderersReplaceAll(List<Renderer> renderers) {

        this.renderers = new ArrayList<>(renderers);
        return this;
    }

    /**
     * Defines the time in milliseconds that needs to pass between two updates of the {@link ActiveObjectSystem}.
     * In a perfect scenario where there's no lag at all, this time is equivalent to the famously known {@code dt} variable that can be found all over this API.<br>
     * <br>
     * The default time resolution is 1 tick (50 milliseconds).
     *
     * @param timeResolution The time between to object system updates in milliseconds.
     * @return This object.
     */
    public ObjectSystemRunnerBuilder timeResolution(long timeResolution) {

        this.timeResolution = timeResolution;
        return this;
    }

    /**
     * Tells the new runner whether to stop as soon no more objects are stored in the {@link ActiveObjectSystem}.<br>
     * <br>
     * By default, this option is set to {@code false}.
     *
     * @param stopWhenNoObjects Whether the runner should stop if no more objects are stored in the given active system.
     *        This is useful for systems with a few manually spawned objects that expire after some time.
     * @return This object.
     */
    public ObjectSystemRunnerBuilder stopWhenNoObjects(boolean stopWhenNoObjects) {

        this.stopWhenNoObjects = stopWhenNoObjects;
        return this;
    }

    /**
     * Finally creates the new instance of {@link ObjectSystemRunner} with the configured settings.
     *
     * @return The newly created object system runner.
     */
    public ObjectSystemRunner build() {

        return new ObjectSystemRunner(plugin, objectSystem, renderers, timeResolution, stopWhenNoObjects);
    }

}
