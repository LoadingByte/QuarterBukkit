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
import com.quartercode.quarterbukkit.api.reflect.FieldAccessor;
import com.quartercode.quarterbukkit.api.reflect.NMSClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.SafeField;
import com.quartercode.quarterbukkit.api.reflect.wrappers.IntHashMap;

/**
 * ===========================================
 * ===== Required for internal processes =====
 * ===========================================
 */

public class PacketRef {

    public static final ClassTemplate<Object> packet = NMSClassTemplate.create("Packet");
    public static final FieldAccessor<?>      l_Map  = new SafeField<Object>(packet.getType(), "l");

    public static IntHashMap<?> getEvilMap() {

        Object map = l_Map.get(null);
        IntHashMap<?> evilMap = new IntHashMap<Object>(map);
        return evilMap;
    }
}
