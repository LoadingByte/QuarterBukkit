
package com.quartercode.quarterbukkit.api.wrapper;

import java.util.List;
import com.quartercode.quarterbukkit.api.reflect.ClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.MethodAccessor;
import com.quartercode.quarterbukkit.api.reflect.NMSClassTemplate;

public class DataWatcher {

    public static final ClassTemplate<Object>        template                   = new NMSClassTemplate("DataWatcher");
    public static final MethodAccessor<Void>         write                      = template.getMethod("a", int.class, Object.class);
    public static final MethodAccessor<Void>         watch                      = template.getMethod("watch", int.class, Object.class);
    public static final MethodAccessor<List<Object>> returnAllWatched           = template.getMethod("c");
    public static final MethodAccessor<List<Object>> unwatchAndReturnAllWatched = template.getMethod("b");
    public static final MethodAccessor<Object>       read                       = template.getMethod("i", int.class);
    public static final MethodAccessor<Boolean>      isChanged                  = template.getMethod("a");
    public static final MethodAccessor<Boolean>      isEmpty                    = template.getMethod("d");

    public static void write(Object datawatcher, int index, Object value) {

        write.invoke(datawatcher, index, value);
    }

    public static void watch(Object datawatcher, int index, Object value) {

        watch.invoke(index, value);
    }

    public static List<Object> getAllWatched(Object datawatcher) {

        return returnAllWatched.invoke(datawatcher);
    }

    public static List<Object> unwatchAndGetAllWatched(Object datawatcher) {

        return unwatchAndReturnAllWatched.invoke(datawatcher);
    }

    public static Object create() {

        return template.newInstance();
    }

}
