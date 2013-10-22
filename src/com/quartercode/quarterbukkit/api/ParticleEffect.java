package com.quartercode.quarterbukkit.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public enum ParticleEffect {

    HUGE_EXPLOSION("hugeexplosion", 0), LARGE_EXPLODE("largeexplode", 1), FIREWORKS_SPARK("fireworksSpark", 2), BUBBLE("bubble", 3), SUSPEND("suspend", 4), DEPTH_SUSPEND("depthSuspend", 5), TOWN_AURA(
            "townaura", 6), CRIT("crit", 7), MAGIC_CRIT("magicCrit", 8), MOB_SPELL("mobSpell", 9), MOB_SPELL_AMBIENT("mobSpellAmbient", 10), SPELL("spell", 11), INSTANT_SPELL("instantSpell", 12), WITCH_MAGIC(
            "witchMagic", 13), NOTE("note", 14), PORTAL("portal", 15), ENCHANTMENT_TABLE("enchantmenttable", 16), EXPLODE("explode", 17), FLAME("flame", 18), LAVA("lava", 19), FOOTSTEP("footstep", 20), SPLASH(
            "splash", 21), LARGE_SMOKE("largesmoke", 22), CLOUD("cloud", 23), RED_DUST("reddust", 24), SNOWBALL_POOF("snowballpoof", 25), DRIP_WATER("dripWater", 26), DRIP_LAVA("dripLava", 27), SNOW_SHOVEL(
            "snowshovel", 28), SLIME("slime", 29), HEART("heart", 30), ANGRY_VILLAGER("angryVillager", 31), HAPPY_VILLAGER("happyVillager", 32);
 
    private String name;
    private int id;
 
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
 
    private static final Map<String, ParticleEffect> NAME_MAP = new HashMap<String, ParticleEffect>();
    private static final Map<Integer, ParticleEffect> ID_MAP = new HashMap<Integer, ParticleEffect>();
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
 
    /**
     * Plays a particle effect at a location which is only shown to a specific player.
     * 
     * @param plugin your Plugin.
     * @param player play effect for {@link Player}.
     * @param loc the play {@link Location}.
     * @param offsetX the X offset of the {@link ParticleEffect}
     * @param offsetY the Y offset of the {@link ParticleEffect}
     * @param offsetZ the Z offset of the {@link ParticleEffect}
     * @param speed the speed of the {@link ParticleEffect}.
     * @param amount the amount of particles.
     */
    public void play(Plugin plugin, Player player, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        createNormalPacket(plugin, this, loc, offsetX, offsetY, offsetZ, speed, amount).send(player);
    }

    /**
     * 
     * @param plugin
     * @param player
     * @param loc
     * @param id
     * @param data
     * @param offsetX
     * @param offsetY
     * @param offsetZ
     * @param amount
     */
    public static void playTileCrack(Plugin plugin, Player player, Location loc, int id, byte data, float offsetX, float offsetY, float offsetZ, int amount) {
        createTileCrackPacket(plugin, id, data, loc, offsetX, offsetY, offsetZ, amount).send(player);
    }
 
    /**
     * 
     * @param plugin
     * @param player
     * @param loc
     * @param id
     * @param offsetX
     * @param offsetY
     * @param offsetZ
     * @param amount
     */
    public static void playIconCrack(Plugin plugin, Player player, Location loc, int id, float offsetX, float offsetY, float offsetZ, int amount) {
        createIconCrackPacket(plugin, id, loc, offsetX, offsetY, offsetZ, amount).send(player);
    }
 
    private Packet createNormalPacket(Plugin plugin, ParticleEffect effect, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
        return createPacket(plugin, effect.getName(), loc, offsetX, offsetY, offsetZ, speed, amount);
    }
 
    private static Packet createTileCrackPacket(Plugin plugin, int id, byte data, Location loc, float offsetX, float offsetY, float offsetZ, int amount) {
        return createPacket(plugin, "tilecrack_" + id + "_" + data, loc, offsetX, offsetY, offsetZ, 0.1F, amount);
    }
 
    private static Packet createIconCrackPacket(Plugin plugin, int id, Location loc, float offsetX, float offsetY, float offsetZ, int amount) {
        return createPacket(plugin, "iconcrack_" + id, loc, offsetX, offsetY, offsetZ, 0.1F, amount);
    }
 
    private static Packet createPacket(Plugin plugin, String effectName, Location loc, float offsetX, float offsetY, float offsetZ, float speed, int amount) {
       
        Packet packet = new Packet("Packet63WorldParticles", plugin);
        
        packet.setPublicValue("a", effectName);
        packet.setPublicValue("b", (float) loc.getX()); 
        packet.setPublicValue("c", (float) loc.getY()); 
        packet.setPublicValue("d", (float) loc.getZ()); 
        packet.setPublicValue("e", (float) offsetX); 
        packet.setPublicValue("f", (float) offsetY); 
        packet.setPublicValue("g", (float) offsetZ); 
        packet.setPublicValue("h", (float) speed); 
        packet.setPublicValue("i", (float) amount); 
        
        return packet;
    }
}
