
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
