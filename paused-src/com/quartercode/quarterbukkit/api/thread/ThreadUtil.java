
package com.quartercode.quarterbukkit.api.thread;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.scheduler.ScheduleTask;

/**
 * This class is for handle the Bukkit-Thread-Errors.
 * It will manage the called method with their actions.
 */
public class ThreadUtil {

    public static final WrongThreadAction DEFAULT     = WrongThreadAction.EXCEPTION;

    private static Thread                 bukkitThread;
    private static List<PreparedMethod>   callMethods = new ArrayList<PreparedMethod>();

    /**
     * Initalizes the {@link Thread} management.
     * This can only be called one time.
     * 
     * @param plugin The QuarterBukkit plugin.
     */
    public static void initalizeThread(final Plugin plugin) {

        if (bukkitThread != null) {
            throw new IllegalStateException("bukkitThread already initalized");
        }

        bukkitThread = Thread.currentThread();

        new ScheduleTask(plugin) {

            @Override
            public void run() {

                if (callMethods.size() > 0) {
                    for (final PreparedMethod preparedMethod : callMethods) {
                        try {
                            preparedMethod.getMethod().invoke(preparedMethod.getObject(), preparedMethod.getParameters());
                        }
                        catch (final Exception e) {
                            plugin.getLogger().info("An error occurred while calling method (" + e + ")");
                        }
                        finally {
                            callMethods.remove(preparedMethod);
                        }
                    }
                }
            }
        }.run(WrongThreadAction.WAIT, true, 0, 10);
    }

    /**
     * Returns the initalized Bukkit-Main-{@link Thread}.
     * 
     * @return The Bukkit-Main-{@link Thread}.
     */
    public static Thread getBukkitThrad() {

        return bukkitThread;
    }

    /**
     * Checks if the current {@link Thread} is the Bukkit-Main-{@link Thread}.
     * 
     * @return If the current {@link Thread} is valid for Bukkit-API-functions.
     */
    public static boolean isInBukkitThread() {

        final Thread currentThread = Thread.currentThread();

        if (currentThread.getId() == bukkitThread.getId()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the current {@link Thread} is valid for Bukkit-API-functions and executes the {@link WrongThreadAction} if not.
     * You cannot use the {@link WrongThreadAction#WAIT} in this checking method.
     * 
     * @param wrongThreadAction The action which executes when this is the wrong {@link Thread}.
     * @return If the method can continue running.
     */
    public static boolean check(final WrongThreadAction wrongThreadAction) {

        if (wrongThreadAction == WrongThreadAction.WAIT) {
            throw new IllegalArgumentException("The wait wrong thread action must be called with method, object and parameters");
        }

        return check(wrongThreadAction, null, null);
    }

    /**
     * Checks if the current {@link Thread} is valid for Bukkit-API-functions and executes the {@link WrongThreadAction} if not.
     * To use the {@link WrongThreadAction#WAIT} in this checking method, you have to set the {@link Method}, {@link Object} (null for static access) and invoke parameters as an {@link Object}-array.
     * 
     * @param wrongThreadAction The action which executes when this is the wrong thread.
     * @return If the method can continue running.
     */
    public static boolean check(final WrongThreadAction wrongThreadAction, final Method method, final Object object, final Object... parameters) {

        if (isInBukkitThread()) {
            return true;
        } else {
            if (wrongThreadAction == WrongThreadAction.EXCEPTION) {
                throw new IllegalThreadStateException("You can call this only in the Bukkit Main-Thread");
            } else if (wrongThreadAction == WrongThreadAction.WAIT) {
                if (method != null) {
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length != parameters.length) {
                        throw new IllegalThreadStateException("Wrong parameters");
                    }

                    for (int counter = 0; counter < parameters.length; counter++) {
                        if (!parameterTypes[counter].equals(parameters[counter].getClass())) {
                            throw new IllegalThreadStateException("Wrong parameters");
                        }
                    }

                    callMethods.add(new PreparedMethod(method, object, parameters));
                }
            } else if (wrongThreadAction == WrongThreadAction.IGNORE) {
                return true;
            }

            return false;
        }
    }

    /**
     * Returns a refelction-{@link Method} and throws human exceptions (API Helper).
     * 
     * @param c The {@link Class} with the method.
     * @param name The name of the method.
     * @param parameterTypes The parameter {@link Class}es of the method in the right order.
     * @return The refelction-{@link Method}.
     */
    public static Method getMethod(final Class<?> c, final String name, final Class<?>... parameterTypes) {

        try {
            return c.getMethod(name, parameterTypes);
        }
        catch (final SecurityException e) {
            throw new IllegalArgumentException("Not supported secruity level", e);
        }
        catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException("Wrong method definition", e);
        }
    }

    private ThreadUtil() {

    }

}
