
package com.quartercode.quarterbukkit.api.protocol;

import org.bukkit.entity.Player;
import com.quartercode.quarterbukkit.api.reflect.BukkitServer;
import com.quartercode.quarterbukkit.api.reflect.ClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.NMSClassTemplate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Packet {

    protected static ClassTemplate<Object> packet = null;
    protected Object                       handle;

    protected void setHandle(NMSClassTemplate template) {

        packet = template;
        handle = packet.newInstance();
    }

    public void send(Player player) {

        try {
            Object entityPlayer = player.getClass().getDeclaredMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", new Class[] { BukkitServer.getNMSClass("Packet") });
            sendPacket.invoke(playerConnection, this.handle);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
