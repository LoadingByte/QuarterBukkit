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

package com.quartercode.quarterbukkit.api.exception;

import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.Updater;

/**
 * The ParseException occurres if something goes wrong while installing a plugin via {@link Updater}.
 */
public class ParseException extends GameException {

    private final Exception cause;
    private final String    string;

    /**
     * Creates a ParseException filled with a cause as an {@link Exception} for the error.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param string {@link String} which could not be parsed.
     */
    public ParseException(final Plugin plugin, final Exception cause, final String string) {

        super(plugin);
        this.cause = cause;
        this.string = string;
    }

    /**
     * Creates a ParseException filled with a cause as an {@link Exception} for the error and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param cause The cause for the error.
     * @param string The {@link String} which could not be parsed.
     * @param message The informational message.
     */
    public ParseException(final Plugin plugin, final Exception cause, final String string, final String message) {

        super(plugin, message);
        this.cause = cause;
        this.string = string;
    }

    /**
     * Returns the cause for the error.
     * 
     * @return The cause for the error as an {@link Exception}.
     */
    public Exception getCause() {

        return cause;
    }

    /**
     * Returns the {@link String} which could not be parsed.
     * 
     * @return The {@link String} which could not be parsed.
     */
    public String getString() {

        return string;
    }

}
