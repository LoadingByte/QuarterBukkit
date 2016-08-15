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

package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.GameException;

public class Config extends YamlConfiguration {

    private final File file;

    public Config(File file) {

        this.file = file;

        if (file.exists()) {
            load(file);
        }

        setDefaults();
        save(file);
    }

    @Override
    public void load(File file) {

        try {
            super.load(file);
        } catch (IOException e) {
            ExceptionHandler.exception(new GameException(QuarterBukkit.getPlugin(), "Can't load QuarterBukkit config (" + e + ")"));
        } catch (InvalidConfigurationException e) {
            ExceptionHandler.exception(new GameException(QuarterBukkit.getPlugin(), "Invalid QuarterBukkit config (" + e + ")"));
        }
    }

    @Override
    public void save(File file) {

        try {
            super.save(file);
        } catch (IOException e) {
            ExceptionHandler.exception(new GameException(QuarterBukkit.getPlugin(), "Can't save QuarterBukkit config (" + e + ")"));
        }
    }

    public void save() {

        save(file);
    }

    private void setDefaults() {

        addDefaultGV("autoupdate", true);
        addDefaultGV("server-mods-api-key", "");
    }

    private void addDefaultGV(String path, Object value) {

        if (!contains(path)) {
            set(path, value);
        }
    }

}
