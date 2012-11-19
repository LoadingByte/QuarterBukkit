
package com.quartercode.quarterbukkit.api.command;

import java.util.Arrays;

/**
 * Class for storing some information about a {@link Command}.
 */
public class CommandInfo {

    private final boolean  ignoreCase;
    private final String   parameterUsage;
    private final String   description;
    private final String   permission;
    private final String[] labels;

    /**
     * Creates a new information-object and sets all the information.
     * This should only be called by {@link CommandHandler}s in their {@link CommandHandler#getInfo()}-method.
     * 
     * @param ignoreCase Should the {@link CommandExecutor} ignore the case of the label.
     * @param parameterUsage How to use the parameters of the {@link Command} (null if you can't use parameters).
     * @param description A short description what the {@link Command} does.
     * @param permission The permission you need to execute the {@link Command}.
     * @param labels All labels a user can use for the {@link Command}. If you type in /command help, help will be the label (use &lt;empty&gt; if you want the command can be executed with no label,
     *        e.g. /command). You can define unltimited labels via vararg.
     */
    public CommandInfo(final boolean ignoreCase, final String parameterUsage, final String description, final String permission, final String... labels) {

        this.ignoreCase = ignoreCase;
        this.parameterUsage = parameterUsage;
        this.description = description;
        this.permission = permission;
        this.labels = labels;
    }

    /**
     * Returns if the {@link CommandExecutor} should ignore the case of the label.
     * 
     * @return If the {@link CommandExecutor} should ignore the case of the label.
     */
    public boolean isIgnoreCase() {

        return ignoreCase;
    }

    /**
     * Returns how to use the parameters of the {@link Command} (returns null if there aren't parameters).
     * This is useful for help-functions.
     * 
     * @return How to use the parameters of the {@link Command}.
     */
    public String getParameterUsage() {

        return parameterUsage;
    }

    /**
     * Returns a short description what the {@link Command} does.
     * 
     * @return A short description what the {@link Command} does.
     */
    public String getDescription() {

        return description;
    }

    /**
     * Returns the permission you need to execute the {@link Command}.
     * 
     * @return The permission you need to execute the {@link Command}.
     */
    public String getPermission() {

        return permission;
    }

    /**
     * Returns all labels a user can use for the {@link Command}. If you type in /command help, help will be the label (&lt;empty&gt; if the command can be executed without a label, e.g. /command).
     * 
     * @return All labels for the {@link Command}.
     */
    public String[] getLabels() {

        return labels;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (description == null ? 0 : description.hashCode());
        result = prime * result + (ignoreCase ? 1231 : 1237);
        result = prime * result + Arrays.hashCode(labels);
        result = prime * result + (parameterUsage == null ? 0 : parameterUsage.hashCode());
        result = prime * result + (permission == null ? 0 : permission.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommandInfo other = (CommandInfo) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (ignoreCase != other.ignoreCase) {
            return false;
        }
        if (!Arrays.equals(labels, other.labels)) {
            return false;
        }
        if (parameterUsage == null) {
            if (other.parameterUsage != null) {
                return false;
            }
        } else if (!parameterUsage.equals(other.parameterUsage)) {
            return false;
        }
        if (permission == null) {
            if (other.permission != null) {
                return false;
            }
        } else if (!permission.equals(other.permission)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [ignoreCase=" + ignoreCase + ", parameterUsage=" + parameterUsage + ", description=" + description + ", permission=" + permission + ", labels=" + Arrays.toString(labels) + "]";
    }

}
