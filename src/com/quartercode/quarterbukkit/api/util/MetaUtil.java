
package com.quartercode.quarterbukkit.api.util;

import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Class for modifying meta-tags easily.
 * This is e.g. for modifying the name of items etc.
 */
public class MetaUtil {

    /**
     * Returns the name of an {@link ItemStack}.
     * 
     * @param itemStack The {@link ItemStack}.
     * @return The name of the {@link ItemStack}.
     */
    public static String getName(final ItemStack itemStack) {

        return itemStack.getItemMeta().getDisplayName();
    }

    /**
     * Sets the name of an {@link ItemStack}.
     * You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param name The name to set.
     */
    public static void setName(final ItemStack itemStack, final String name) {

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (name == null || name.isEmpty()) {
            itemMeta.setDisplayName(null);
        } else {
            itemMeta.setDisplayName(name);
        }
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Returns the description lines of an {@link ItemStack}.
     * They equals to the "Lore".
     * 
     * @param itemStack The {@link ItemStack}.
     * @return The description lines of the {@link ItemStack} as an {@link String}-{@link List}.
     */
    public static List<String> getDescriptions(final ItemStack itemStack) {

        return itemStack.getItemMeta().getLore();
    }

    /**
     * Sets the description lines of an {@link ItemStack}.
     * They equals to the "Lore". You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param descriptions The description lines to set as an {@link String}-{@link List}.
     */
    public static void setDescriptions(final ItemStack itemStack, final List<String> descriptions) {

        final ItemMeta itemMeta = itemStack.getItemMeta();
        if (descriptions == null || descriptions.isEmpty()) {
            itemMeta.setLore(null);
        } else {
            itemMeta.setLore(descriptions);
        }
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Sets the description lines of an {@link ItemStack}.
     * They equals to the "Lore". You can use every chat code.
     * 
     * @param itemStack The {@link ItemStack} to modify.
     * @param descriptions The description lines to set as an {@link String}-array.
     */
    public static void setDescriptions(final ItemStack itemStack, final String... descriptions) {

        setDescriptions(itemStack, Arrays.asList(descriptions));
    }

    private MetaUtil() {

    }

}
