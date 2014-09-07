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
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.MathUtil;
import com.quartercode.quarterbukkit.api.objectsystem.ActiveObjectSystem;
import com.quartercode.quarterbukkit.api.objectsystem.BaseObject;
import com.quartercode.quarterbukkit.api.objectsystem.ModificationRule;
import com.quartercode.quarterbukkit.api.objectsystem.run.Renderer.RenderingResult;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;

/**
 * An object system runner takes an {@link ActiveObjectSystem} and a bunch of {@link Renderer}s and then simulates and displays the system using those renderers.
 */
public class ObjectSystemRunner {

    /**
     * A {@link List} containing the default {@link Renderer}s that are used by the runner if no custom renderers are specified.
     */
    public static final List<Renderer<?>> DEFAULT_RENDERERS;

    static {

        List<Renderer<?>> defaultRenderers = new ArrayList<Renderer<?>>();
        defaultRenderers.add(new BaseObjectRenderer());
        defaultRenderers.add(new StandalonePhysicsObjectRenderer());
        defaultRenderers.add(new ParticleRenderer());
        defaultRenderers.add(new FireworkRenderer());
        defaultRenderers.add(new RealEntityObjectRenderer());
        DEFAULT_RENDERERS = Collections.unmodifiableList(defaultRenderers);

    }

    private final Plugin                  plugin;
    private final List<Renderer<?>>       renderers;
    private final ActiveObjectSystem      objectSystem;
    private final boolean                 stopWhenNoObjects;

    private ScheduleTask                  updateTask;

    /**
     * Creates a new object system runner that simulates the given {@link ActiveObjectSystem} and uses the given {@link Plugin} as host.
     * 
     * @param plugin The plugin that is used to call some required bukkit methods.
     *        It hosts the new runner.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     */
    public ObjectSystemRunner(Plugin plugin, ActiveObjectSystem objectSystem) {

        this(plugin, DEFAULT_RENDERERS, objectSystem);
    }

    /**
     * Creates a new object system runner that simulates the given {@link ActiveObjectSystem}, uses the given {@link Plugin} as host, and can
     * stop if no more objects are stored in the active system.
     * 
     * @param plugin The plugin that is used to call some required bukkit methods.
     *        It hosts the new runner.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     * @param stopWhenNoObjects Whether the runner should stop if no more objects are stored in the given active system.
     *        This is useful for systems with a few manually spawned objects that expire after some time.
     */
    public ObjectSystemRunner(Plugin plugin, ActiveObjectSystem objectSystem, boolean stopWhenNoObjects) {

        this(plugin, DEFAULT_RENDERERS, objectSystem);
    }

    /**
     * Creates a new object system runner that simulates the given {@link ActiveObjectSystem} using the given {@link Renderer}s and uses the
     * given {@link Plugin} as host.
     * 
     * @param plugin The plugin that is used to call some required bukkit methods.
     *        It hosts the new runner.
     * @param renderers The {@link Renderer}s that simulate and display the objects of the active system.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     */
    public ObjectSystemRunner(Plugin plugin, List<Renderer<?>> renderers, ActiveObjectSystem objectSystem) {

        this(plugin, renderers, objectSystem, false);
    }

    /**
     * Creates a new object system runner that simulates the given {@link ActiveObjectSystem} using the given {@link Renderer}s, uses the
     * given {@link Plugin} as host, and can stop if no more objects are stored in the active system.
     * 
     * @param plugin The plugin that is used to call some required bukkit methods.
     *        It hosts the new runner.
     * @param renderers The {@link Renderer}s that simulate and display the objects of the active system.
     * @param objectSystem The active object system that should be ran and simulated by the runner.
     * @param stopWhenNoObjects Whether the runner should stop if no more objects are stored in the given active system.
     *        This is useful for systems with a few manually spawned objects that expire after some time.
     */
    public ObjectSystemRunner(Plugin plugin, List<Renderer<?>> renderers, ActiveObjectSystem objectSystem, boolean stopWhenNoObjects) {

        this.plugin = plugin;
        this.renderers = new ArrayList<Renderer<?>>(renderers);
        this.objectSystem = objectSystem;
        this.stopWhenNoObjects = stopWhenNoObjects;
    }

    /**
     * Returns whether the object system runner is currently running and simulating its {@link ActiveObjectSystem} using its {@link Renderer}s.
     * This state can be changed with {@link #setRunning(boolean)}.
     * 
     * @return Whether the runner is running.
     */
    public boolean isRunning() {

        return updateTask != null;
    }

    /**
     * Starts or stops the object system runner simulating its {@link ActiveObjectSystem} using its {@link Renderer}s.
     * 
     * @param running Whether the should be running.
     */
    public void setRunning(boolean running) {

        if (running && !isRunning()) {
            updateTask = new ScheduleTask(plugin) {

                @Override
                public void run() {

                    update();
                }

            }.run(true, 0, MathUtil.getMillis(1));
        } else if (!running && isRunning()) {
            updateTask.cancel();
            updateTask = null;
        }
    }

    private void update() {

        // Stop if "stopWhenNoObjects" is enabled and no objects are found
        if (stopWhenNoObjects && objectSystem.getObjects().isEmpty()) {
            setRunning(false);
            return;
        }

        // Apply modification rules
        for (BaseObject object : objectSystem.getObjects()) {
            for (ModificationRule<?, ?> modificationRule : objectSystem.getDefinition().getModificationRules()) {
                tryApplyModificationRule(modificationRule, object);
            }
        }

        // Apply renderers
        for (Renderer<?> renderer : renderers) {
            Iterator<BaseObject> objectRenderingIterator = objectSystem.getModifiableObjectsIterator();
            while (objectRenderingIterator.hasNext()) {
                RenderingResult result = tryApplyRenderer(renderer, objectRenderingIterator.next());

                if (result != null && result == RenderingResult.REMOVE) {
                    objectRenderingIterator.remove();
                }
            }
        }
    }

    @SuppressWarnings ("unchecked")
    private <O extends BaseObject> RenderingResult tryApplyRenderer(Renderer<O> renderer, BaseObject object) {

        if (renderer.getObjectType().isInstance(object)) {
            return renderer.render(plugin, objectSystem, (O) object);
        } else {
            return null;
        }
    }

    @SuppressWarnings ("unchecked")
    private <O extends BaseObject> void tryApplyModificationRule(ModificationRule<O, ?> modificationRule, BaseObject object) {

        if (modificationRule.getObjectType().isInstance(object)) {
            modificationRule.apply((O) object);
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
