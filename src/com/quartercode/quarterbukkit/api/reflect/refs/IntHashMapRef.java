
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
