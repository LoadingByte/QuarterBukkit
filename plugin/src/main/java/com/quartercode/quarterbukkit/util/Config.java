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

package com.quartercode.quarterbukkit.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.GameException;

public class Config extends YamlConfiguration {

    private final Plugin plugin;
    private final File   file;

    public Config(final Plugin plugin, final File file) {

        this.plugin = plugin;
        this.file = file;

        if (file.exists()) {
            load(file);
        }

        setDefaults();
        save(file);
    }

    @Override
    public void load(final File file) {

        try {
            super.load(file);
        }
        catch (final IOException e) {
            ExceptionHandler.exception(new GameException(plugin, "Can't load QuarterBukkit config (" + e + ")"));
        }
        catch (final InvalidConfigurationException e) {
            ExceptionHandler.exception(new GameException(plugin, "Can't load QuarterBukkit config (" + e + ")"));
        }
    }

    @Override
    public void save(final File file) {

        try {
            super.save(file);
        }
        catch (final IOException e) {
            ExceptionHandler.exception(new GameException(plugin, "Can't save QuarterBukkit config (" + e + ")"));
        }
    }

    public void save() {

        save(file);
    }

    private void setDefaults() {

        addDefaultGV("autoupdate", true);
    }

    private void addDefaultGV(final String path, final Object value) {

        if (!contains(path)) {
            set(path, value);
        }
    }

}
