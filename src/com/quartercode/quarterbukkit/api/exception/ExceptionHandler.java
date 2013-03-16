
package com.quartercode.quarterbukkit.api.exception;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

/**
 * The main class for handling event-exceptions.
 */
public class ExceptionHandler {

    /**
     * Handles a {@link GameException} and returns if the process should continue (if the exception is an instance of {@link Cancellable}, else true).
     * 
     * @param exception The {@link GameException} to handle.
     * @return If the process should continue (if the exception is an instance of {@link Cancellable}, else true).
     */
    public static boolean exception(final GameException exception) {

        call(exception);

        if (exception instanceof Cancellable) {
            return ! ((Cancellable) exception).isCancelled();
        } else {
            return true;
        }
    }

    private static void call(final GameException exception) {

        final HandlerList handlers = exception.getHandlers();
        final RegisteredListener[] listeners = handlers.getRegisteredListeners();

        for (final RegisteredListener registration : listeners) {
            if (!registration.getPlugin().isEnabled() || !registration.getPlugin().equals(exception.getPlugin())) {
                continue;
            }

            try {
                registration.callEvent(exception);
            }
            catch (final AuthorNagException ex) {
                final Plugin plugin = registration.getPlugin();

                if (plugin.isNaggable()) {
                    plugin.setNaggable(false);
                    Bukkit.getLogger().log(Level.SEVERE, String.format("Nag author(s): '%s' of '%s' about the following: %s", plugin.getDescription().getAuthors(), plugin.getDescription().getFullName(), ex.getMessage()));
                }
            }
            catch (final Throwable e) {
                Bukkit.getLogger().log(Level.SEVERE, "Could not pass exception " + exception.getEventName() + " to " + registration.getPlugin().getDescription().getFullName(), e);
            }
        }
    }

    private ExceptionHandler() {

    }

}
