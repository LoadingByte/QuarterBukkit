
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
