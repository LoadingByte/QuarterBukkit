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
/*
 * This file is part of QuarterBukkit.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api.command;

import java.util.Arrays;
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
    public Command(final CommandSender sender, final String globalLabel, final String label, final String... arguments) {

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

        return arguments;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arguments);
        result = prime * result + (globalLabel == null ? 0 : globalLabel.hashCode());
        result = prime * result + (label == null ? 0 : label.hashCode());
        result = prime * result + (sender == null ? 0 : sender.hashCode());
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
        final Command other = (Command) obj;
        if (!Arrays.equals(arguments, other.arguments)) {
            return false;
        }
        if (globalLabel == null) {
            if (other.globalLabel != null) {
                return false;
            }
        } else if (!globalLabel.equals(other.globalLabel)) {
            return false;
        }
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        } else if (!label.equals(other.label)) {
            return false;
        }
        if (sender == null) {
            if (other.sender != null) {
                return false;
            }
        } else if (!sender.equals(other.sender)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [sender=" + sender + ", globalLabel=" + globalLabel + ", label=" + label + ", arguments=" + Arrays.toString(arguments) + "]";
    }

}
