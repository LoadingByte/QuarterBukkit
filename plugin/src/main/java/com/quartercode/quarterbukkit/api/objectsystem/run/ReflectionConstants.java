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

package com.quartercode.quarterbukkit.api.objectsystem.run;

import org.bukkit.Bukkit;

/**
 * A class that contains useful constants for NMS/CraftBukkit reflection operations.
 * Such operations are required in order to make the code version-independent.
 */
public class ReflectionConstants {

    /**
     * The string that is appended to the {@code net.minecraft.server} and {@code org.bukkit.craftbukkit} packages.
     * It basically represents the current version of the server implementation.
     * For example, this value could be {@code v1_7_R4}.
     * In that case, the NMS package would be called {@code net.minecraft.server.v1_7_R4} (of course, this also applies to the CraftBukkit package).
     */
    public static final String PACKAGE_VERSION   = Bukkit.getServer().getClass().getPackage().getName().substring(23);

    /**
     * The minor version number of the current minecraft version the server implements.
     * The minor component is the second part of the version string.
     * For example, the minor version number of the minecraft version {@code 1.7.10} would be {@code 7}.
     */
    public static final int    MINOR_VERSION     = Integer.parseInt(PACKAGE_VERSION.split("_")[1]);

    /**
     * The real name of the {@code org.bukkit.craftbukkit} package.
     * For example, this could be {@code org.bukkit.craftbukkit.v1_7_R4}.
     */
    public static final String CB_PACKAGE        = "org.bukkit.craftbukkit." + PACKAGE_VERSION;

    /**
     * The real name of the {@code org.bukkit.craftbukkit.entity} package.
     * For example, this could be {@code org.bukkit.craftbukkit.v1_7_R4.entity}.
     */
    public static final String CB_ENTITY_PACKAGE = CB_PACKAGE + ".entity";

    /**
     * The real name of the {@code net.minecraft.server} package.
     * For example, this could be {@code net.minecraft.server.v1_7_R4}.
     */
    public static final String NMS_PACKAGE       = "net.minecraft.server." + PACKAGE_VERSION;

    private ReflectionConstants() {

    }

}
