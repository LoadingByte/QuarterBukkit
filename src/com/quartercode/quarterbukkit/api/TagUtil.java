
package com.quartercode.quarterbukkit.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;
import net.minecraft.server.Packet20NamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Class for modifying NBT-Tags and mutating Packtes easily.
 * This is e.g. for modifying the name of items etc.
 */
public class TagUtil {

    private static Map<Player, String> showPlayerNames = new HashMap<Player, String>();

    private static NBTTagCompound getItemStackDisplayTag(final ItemStack itemStack) {

        NBTTagCompound nbtTagCompound = ((CraftItemStack) itemStack).getHandle().getTag();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
            ((CraftItemStack) itemStack).getHandle().setTag(nbtTagCompound);
        }
        if (!nbtTagCompound.hasKey("display")) {
            nbtTagCompound.setCompound("display", new NBTTagCompound());
        }

        return nbtTagCompound.getCompound("display");
    }

    /**
     * Returns the name of an {@link ItemStack}.
     * 
     * @param itemStack The {@link ItemStack}.
     * @return The name of the {@link ItemStack}.
     */
    public static String getName(final ItemStack itemStack) {

        final String name = getItemStackDisplayTag(itemStack).getString("Name");

        if (name == null || name.isEmpty()) {
            return null;
        } else {
            return name;
        }
    }

    /**
     * Sets the name of an {@link ItemStack}.
     * You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param name The name to set.
     */
    public static void setName(final ItemStack itemStack, final String name) {

        if (name == null || name.isEmpty()) {
            getItemStackDisplayTag(itemStack).remove("Name");
        } else {
            getItemStackDisplayTag(itemStack).setString("Name", name);
        }
    }

    /**
     * Returns the description lines of an {@link ItemStack}.
     * They equals to the "Lore".
     * 
     * @param itemStack The {@link ItemStack}.
     * @return The description lines of the {@link ItemStack} as an {@link String}-{@link List}.
     */
    public static List<String> getDescriptions(final ItemStack itemStack) {

        final NBTTagList nbtDescriptionList = getItemStackDisplayTag(itemStack).getList("Lore");
        final List<String> descriptions = new ArrayList<String>();
        for (int counter = 0; counter < nbtDescriptionList.size(); counter++) {
            if (nbtDescriptionList.get(counter) instanceof NBTTagString) {
                descriptions.add( ((NBTTagString) nbtDescriptionList.get(counter)).data);
            }
        }

        return Collections.unmodifiableList(descriptions);
    }

    /**
     * Sets the description lines of an {@link ItemStack}.
     * They equals to the "Lore". You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param descriptions The description lines to set as an {@link String}-{@link List}.
     */
    public static void setDescriptions(final ItemStack itemStack, final List<String> descriptions) {

        if (descriptions == null || descriptions.isEmpty()) {
            getItemStackDisplayTag(itemStack).remove("Lore");
        } else {
            final NBTTagList nbtDescriptionList = new NBTTagList();
            for (final String description : descriptions) {
                nbtDescriptionList.add(new NBTTagString(description));
            }

            getItemStackDisplayTag(itemStack).set("Lore", nbtDescriptionList);
        }
    }

    /**
     * Returns the saved player show names as unmodifiable map.
     * You can add players with setShowName().
     * 
     * @return The saved player show names.
     */
    public static Map<Player, String> getShowPlayerNames() {

        return Collections.unmodifiableMap(showPlayerNames);
    }

    /**
     * Sets the name above the {@link Player}'s head
     * 
     * @param player The {@link Player} to modify.
     * @param name The show name above the {@link Player}'s head to set.
     */
    public static void setShowName(final Player player, final String name) {

        if ( (name == null || name.isEmpty()) && showPlayerNames.containsKey(player)) {
            showPlayerNames.remove(player);
            sendPlayerShowNamePacket( ((CraftPlayer) player).getHandle(), player.getName());
            return;
        }

        if (showPlayerNames.containsKey(player)) {
            showPlayerNames.remove(player);
        }
        showPlayerNames.put(player, name);

        sendPlayerShowNamePacket( ((CraftPlayer) player).getHandle(), name);
    }

    private static void sendPlayerShowNamePacket(final EntityPlayer player, final String name) {

        final String oldName = player.getName();
        player.name = name;

        for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer != player) {
                ((CraftPlayer) onlinePlayer).getHandle().netServerHandler.sendPacket(new Packet20NamedEntitySpawn(player));
            }
        }

        player.name = oldName;
    }

    private TagUtil() {

    }

}
