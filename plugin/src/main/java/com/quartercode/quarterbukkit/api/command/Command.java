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
import org.bukkit.command.CommandSender;

/**
 * This class represents a typed command with different components.
 */
public class Command {

    private final CommandSender sender;
    private final String        globalLabel;
    private final String        label;
    private final String[]      arguments;

    /**
     * Creates a new command representing object.
     *
     * @param sender The {@link CommandSender} of the command.
     * @param globalLabel The global label of the command.
     * @param label The secondary label of the command.
     * @param arguments The parsed arguments of the command as a {@link String}-array.
     */
    public Command(CommandSender sender, String globalLabel, String label, String... arguments) {

        this.sender = sender;
        this.globalLabel = globalLabel;
        this.label = label;
        this.arguments = arguments;
    }

    /**
     * Returns the {@link CommandSender} of the command.
     *
     * @return The {@link CommandSender}.
     */
    public CommandSender getSender() {

        return sender;
    }

    /**
     * Returns the global label of the command.
     * If you type in /command help, command will be the global label.
     *
     * @return The global label of the command.
     */
    public String getGlobalLabel() {

        return globalLabel;
    }

    /**
     * Returns the secondary label of the command.
     * If you type in /command help, help will be the secondary label.
     *
     * @return The secondary label of the command.
     */
    public String getLabel() {

        return label;
    }

    /**
     * Returns the parsed arguments of the command as a {@link String}-array.
     * If you type in /command help 2, the array will only contains [2].
     *
     * @return The parsed arguments of the command.
     */
    public String[] getArguments() {

        return arguments.clone();
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
