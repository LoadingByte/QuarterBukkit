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

package com.quartercode.quarterbukkit.api.protocol;

import java.lang.reflect.Field;
import java.util.logging.Level;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.reflect.ClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.FieldAccessor;
import com.quartercode.quarterbukkit.api.reflect.SafeField;

/**
 * 
 * i will add the JavaDoc later!
 * 
 */

public class Packet {

    private final ClassTemplate<?> packetTemplate;
    private final Object           handle;

    public Packet(PacketType packetType) {

        packetTemplate = packetType.getPacketTemplate();
        handle = packetType.getPacket();
    }

    public Object getHandle() {

        return handle;
    }

    public <T> void write(FieldAccessor<T> accessor, T value) {

        accessor.set(getHandle(), value);
    }

    public void write(Field field, Object value) {

        try {
            field.set(getHandle(), value);
        }
        catch (IllegalAccessException e) {
            QuarterBukkit.getPlugin().getLogger().log(Level.WARNING, "Could not access field: '{0}'!", field.getName());
            e.printStackTrace();
        }
    }

    public void write(String fieldName, Object value) {

        SafeField.set(getHandle(), fieldName, value);
    }

    public void write(int index, Object value) {

        String field = String.valueOf(packetTemplate.getFields().get(index));
        write(field, value);
    }
}
