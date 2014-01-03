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

package com.quartercode.quarterbukkit.api.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class is a modified {@link ArrayList} for managing {@link ScheduleTask}s.
 * It adds some extra-methods to the {@link List}
 */
public class ScheduleGroup extends ArrayList<ScheduleTask> {

    private static final long serialVersionUID = 3553950861058787765L;

    /**
     * The default constructor.
     * Creates an empty schedule-group.
     */
    public ScheduleGroup() {

        super();
    }

    /**
     * A constructor for creating a filled group.
     * 
     * @param collection The {@link ScheduleTask} collection.
     */
    public ScheduleGroup(final Collection<? extends ScheduleTask> collection) {

        super(collection);
    }

    /**
     * Cancels all scheduling {@link ScheduleTask}s.
     */
    public void cancel() {

        for (final ScheduleTask scheduleTask : this) {
            if (scheduleTask.isRunning()) {
                scheduleTask.cancel();
            }
        }
    }

}
