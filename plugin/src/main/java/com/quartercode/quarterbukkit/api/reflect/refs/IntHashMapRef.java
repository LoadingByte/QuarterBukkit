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

package com.quartercode.quarterbukkit.api.reflect.refs;

import com.quartercode.quarterbukkit.api.reflect.ClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.MethodAccessor;
import com.quartercode.quarterbukkit.api.reflect.NMSClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.SafeConstructor;

public class IntHashMapRef {

    /**
     * ===========================================
     * ===== Required for internal processes =====
     * ===========================================
     */

    public static final ClassTemplate<?>        TEMPLATE    = new NMSClassTemplate("IntHashMap");
    public static final SafeConstructor<?>      constructor = TEMPLATE.getConstructor();
    public static final MethodAccessor<Object>  get         = TEMPLATE.getMethod("get", int.class);
    public static final MethodAccessor<Object>  remove      = TEMPLATE.getMethod("d", int.class);
    public static final MethodAccessor<Void>    put         = TEMPLATE.getMethod("a", int.class, Object.class);
    public static final MethodAccessor<Boolean> contains    = TEMPLATE.getMethod("b", int.class);
    public static final MethodAccessor<Object>  clear       = TEMPLATE.getMethod("c");

}
