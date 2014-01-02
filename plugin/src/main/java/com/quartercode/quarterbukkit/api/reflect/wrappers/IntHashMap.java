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

package com.quartercode.quarterbukkit.api.reflect.wrappers;

import com.quartercode.quarterbukkit.api.reflect.refs.IntHashMapRef;

public class IntHashMap<T> extends BasicWrapper {

    /**
     * ===========================================
     * ===== Required for internal processes =====
     * ===========================================
     */

    public IntHashMap() {

        setHandle(IntHashMapRef.constructor.newInstance());
    }

    public IntHashMap(Object handle) {

        setHandle(handle);
    }

    @SuppressWarnings ("unchecked")
    public T get(int key) {

        return (T) IntHashMapRef.get.invoke(handle, key);
    }

    public boolean contains(int key) {

        return IntHashMapRef.contains.invoke(handle, key);
    }

    @SuppressWarnings ("unchecked")
    public T remove(int key) {

        return (T) IntHashMapRef.remove.invoke(handle, key);
    }

    public void put(int key, Object value) {

        IntHashMapRef.put.invoke(handle, key, value);
    }

    public void clear() {

        IntHashMapRef.clear.invoke(handle);
    }
}
