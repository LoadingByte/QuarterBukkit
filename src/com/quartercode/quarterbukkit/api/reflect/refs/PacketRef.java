
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
