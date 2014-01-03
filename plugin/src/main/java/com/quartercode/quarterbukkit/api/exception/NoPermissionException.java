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

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

/**
 * The NoPermissionException represents an exception caused by not enough permissions.
 */
public class NoPermissionException extends GameException implements Cancellable {

    private boolean             cancelled;

    private final String        permission;
    private final CommandSender causer;

    /**
     * Creates an NoPermissionException filled with a {@link CommandSender} causer and the permission.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     */
    public NoPermissionException(final Plugin plugin, final String permission, final CommandSender causer) {

        super(plugin);
        this.permission = permission;
        this.causer = causer;
    }

    /**
     * Creates an NoPermissionException filled with a {@link CommandSender} causer, the permission and an informational message.
     * 
     * @param plugin The causing {@link Plugin}.
     * @param permission The permission which the causer doesn't have.
     * @param causer The {@link CommandSender} who has't enough permissions.
     * @param message The information message.
     */
    public NoPermissionException(final Plugin plugin, final String permission, final CommandSender causer, final String message) {

        super(plugin, message);
        this.permission = permission;
        this.causer = causer;
    }

    @Override
    public boolean isCancelled() {

        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {

        this.cancelled = cancelled;
    }

    /**
     * Returns the permission which the causer doesn't have.
     * 
     * @return The permission.
     */
    public String getPermission() {

        return permission;
    }

    /**
     * Returns the {@link CommandSender} causer.
     * 
     * @return The causer who hasn't enough permissions.
     */
    public CommandSender getCauser() {

        return causer;
    }

}
