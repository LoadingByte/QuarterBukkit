
package com.quartercode.quarterbukkit.api.util;

import java.lang.reflect.Field;
import org.bukkit.entity.Player;

public class PlayerUtil {

    /**
     * Class for some useful Player Methods
     */

    /**
     * Return the local language from a {@link Player}.
     * 
     * @param player The {@link Player} from get the Language.
     * @return The Language.
     */

    public static String getLanguage(final Player player) {

        String language = null;

        try {

            Object entityPlayer = BukkitPlayerToCraftPlayer(player);
            Field l = ReflectionUtil.getField("locale", entityPlayer.getClass());
            l.setAccessible(true);
            language = (String) l.get(entityPlayer);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return language;
    }

    /**
     * Convert {@link Player} to CraftPlayer Object.
     * 
     * @param player The {@link Player} to convert.
     * @return The CraftPlayer Object
     */

    public static Object BukkitPlayerToCraftPlayer(Player player) {

        Object entityPlayer = null;
        try {
            entityPlayer = ReflectionUtil.getMethod("getHandle", player.getClass(), 0).invoke(player);
        }
        catch (Exception e) {
            return null;
        }
        return entityPlayer;
    }
}
