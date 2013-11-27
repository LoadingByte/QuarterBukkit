
package com.quartercode.quarterbukkit.api.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import com.quartercode.quarterbukkit.api.protocol.Packet;
import com.quartercode.quarterbukkit.api.protocol.PacketType;

/**
 * 
 * i will add the JavaDoc later!
 * 
 */
public enum ParticleEffect {

    HUGE_EXPLOSION ("hugeexplosion", 0), LARGE_EXPLODE ("largeexplode", 1), FIREWORKS_SPARK ("fireworksSpark", 2), BUBBLE ("bubble", 3), SUSPEND ("suspend", 4), DEPTH_SUSPEND ("depthSuspend", 5), TOWN_AURA ("townaura", 6), CRIT ("crit", 7), MAGIC_CRIT ("magicCrit", 8), MOB_SPELL ("mobSpell", 9), MOB_SPELL_AMBIENT ("mobSpellAmbient", 10), SPELL ("spell", 11), INSTANT_SPELL ("instantSpell", 12), WITCH_MAGIC ("witchMagic", 13), NOTE ("note", 14), PORTAL ("portal", 15), ENCHANTMENT_TABLE ("enchantmenttable", 16), EXPLODE ("explode", 17), FLAME ("flame", 18), LAVA ("lava", 19), FOOTSTEP ("footstep", 20), SPLASH ("splash", 21), LARGE_SMOKE ("largesmoke", 22), CLOUD ("cloud", 23), RED_DUST ("reddust", 24), SNOWBALL_POOF ("snowballpoof", 25), DRIP_WATER ("dripWater", 26), DRIP_LAVA ("dripLava", 27), SNOW_SHOVEL ("snowshovel", 28), SLIME ("slime", 29), HEART ("heart", 30), ANGRY_VILLAGER ("angryVillager", 31), HAPPY_VILLAGER ("happyVillager", 32), ICONCRACK ("iconcrack", 33), TILECRACK ("tilecrack", 34);

    private String name;
    private int    id;

    ParticleEffect(String name, int id) {

        this.name = name;
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public int getId() {

        return id;
    }

    private static final Map<String, ParticleEffect>  NAME_MAP = new HashMap<String, ParticleEffect>();
    private static final Map<Integer, ParticleEffect> ID_MAP   = new HashMap<Integer, ParticleEffect>();
    static {
        for (ParticleEffect effect : values()) {
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
        }
    }

    public static ParticleEffect fromName(String name) {

        if (name == null) {
            return null;
        }
        for (Entry<String, ParticleEffect> e : NAME_MAP.entrySet()) {
            if (e.getKey().equalsIgnoreCase(name)) {
                return e.getValue();
            }
        }
        return null;
    }

    public static ParticleEffect fromId(int id) {

        return ID_MAP.get(id);
    }

    public static void sendToPlayer(ParticleEffect effect, Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {

        PlayerUtil.sendPacket(player, createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count));
    }

    public static void sendToLocation(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerUtil.sendPacket(player, createPacket(effect, location, offsetX, offsetY, offsetZ, speed, count));
        }
    }

    public static void sendCrackToPlayer(boolean icon, int id, byte data, Player player, Location location, float offsetX, float offsetY, float offsetZ, int count) {

        PlayerUtil.sendPacket(player, createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count));
    }

    public static void sendCrackToLocation(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerUtil.sendPacket(player, createCrackPacket(icon, id, data, location, offsetX, offsetY, offsetZ, count));
        }
    }

    public static Packet createPacket(ParticleEffect effect, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) {

        if (count <= 0) {
            count = 1;
        }

        Packet packet = new Packet(PacketType.WORLD_PARTICLES);

        packet.write("a", effect.getName());
        packet.write("b", (float) location.getX());
        packet.write("c", (float) location.getY());
        packet.write("d", (float) location.getZ());
        packet.write("e", offsetX);
        packet.write("f", offsetY);
        packet.write("g", offsetZ);
        packet.write("h", speed);
        packet.write("i", count);

        return packet;
    }

    public static Packet createCrackPacket(boolean icon, int id, byte data, Location location, float offsetX, float offsetY, float offsetZ, int count) {

        String modifier = "iconcrack_" + id;

        if (count <= 0) {
            count = 1;
        }

        if (!icon) {
            modifier = "tilecrack_" + id + "_" + data;
        }

        Packet packet = new Packet(PacketType.WORLD_PARTICLES);

        packet.write("a", modifier);
        packet.write("b", (float) location.getX());
        packet.write("c", (float) location.getY());
        packet.write("d", (float) location.getZ());
        packet.write("e", offsetX);
        packet.write("f", offsetY);
        packet.write("g", offsetZ);
        packet.write("h", 0.1F);
        packet.write("i", count);

        return packet;
    }
}
