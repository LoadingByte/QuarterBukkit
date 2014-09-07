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

package com.quartercode.quarterbukkit.api.command;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

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
    public CommandInfo(boolean ignoreCase, String parameterUsage, String description, String permission, String... labels) {

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

        return labels.clone();
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
