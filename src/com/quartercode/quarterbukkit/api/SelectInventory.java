
package com.quartercode.quarterbukkit.api;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class SelectInventory implements Listener {

    public enum ClickType {

        LEFT (true, false, false), RIGHT (false, true, false), LEFT_SHIFT (true, false, true), RIGHT_SHIFT (false, true, true);

        public static ClickType getClickType(final boolean left, final boolean right, final boolean shift) {

            for (final ClickType clickType : values()) {
                if (clickType.isLeft() == left && clickType.isRight() == right && clickType.isShift() == shift) {
                    return clickType;
                }
            }

            return null;
        }

        private boolean left;
        private boolean right;
        private boolean shift;

        private ClickType(final boolean left, final boolean right, final boolean shift) {

            this.left = left;
            this.right = right;
            this.shift = shift;
        }

        public boolean isLeft() {

            return left;
        }

        public boolean isRight() {

            return right;
        }

        public boolean isShift() {

            return shift;
        }
    }

    private final Plugin                 plugin;
    private final Player                 player;
    private final String                 title;
    private final int                    slots;
    private final Map<ItemStack, String> entries = new LinkedHashMap<ItemStack, String>();

    private final Inventory              inventory;
    private InventoryView                inventoryView;

    public SelectInventory(final Plugin plugin, final Player player, final String title, final int slots) {

        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.slots = slots;

        inventory = Bukkit.createInventory(player, slots, title);

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public SelectInventory openInventoryView() {

        inventoryView = player.openInventory(inventory);
        refreshItemStacks();

        return this;
    }

    public SelectInventory closeInventoryView() {

        if (inventoryView != null) {
            inventoryView.close();
            inventoryView = null;
        }

        PlayerInteractEvent.getHandlerList().unregister(this);

        return this;
    }

    public SelectInventory add(final String value, final Material material) {

        add(value, new ItemStack(material));

        return this;
    }

    public SelectInventory add(final String value, final Material material, final int amount) {

        add(value, new ItemStack(material, amount));

        return this;
    }

    public SelectInventory add(final String value, final Material material, final short data) {

        add(value, new ItemStack(material, 1, data));

        return this;
    }

    public SelectInventory add(final String value, final Material material, final int amount, final short data) {

        add(value, new ItemStack(material, amount, data));

        return this;
    }

    public SelectInventory add(final String value, final ItemStack itemStack) {

        entries.put(itemStack, value);

        if (inventory != null) {
            refreshItemStacks();
        }

        return this;
    }

    public SelectInventory add(final String value, final Material material, final String name, final String... descriptions) {

        add(value, new ItemStack(material), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final Material material, final int amount, final String name, final String... descriptions) {

        add(value, new ItemStack(material, amount), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final Material material, final short data, final String name, final String... descriptions) {

        add(value, new ItemStack(material, 1, data), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final Material material, final int amount, final short data, final String name, final String... descriptions) {

        add(value, new ItemStack(material, amount, data), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final ItemStack itemStack, final String name, final String... descriptions) {

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(descriptions));
        itemStack.setItemMeta(itemMeta);

        entries.put(itemStack, value);

        if (inventory != null) {
            refreshItemStacks();
        }

        return this;
    }

    public SelectInventory add(final String value, final Material material, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final Material material, final int amount, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material, amount), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final Material material, final short data, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material, 1, data), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final Material material, final int amount, final short data, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material, amount, data), name, descriptions);

        return this;
    }

    public SelectInventory add(final String value, final ItemStack itemStack, final String name, final List<String> descriptions) {

        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(descriptions);
        itemStack.setItemMeta(itemMeta);

        entries.put(itemStack, value);

        if (inventory != null) {
            refreshItemStacks();
        }

        return this;
    }

    private void refreshItemStacks() {

        int slot = 0;
        for (final Entry<ItemStack, String> entry : entries.entrySet()) {
            inventory.setItem(slot, entry.getKey());

            slot++;
            if (slot >= 9) {
                break;
            }
        }
    }

    public Player getPlayer() {

        return player;
    }

    public String getTitle() {

        return title;
    }

    public int getSlots() {

        return slots;
    }

    public Inventory getInventory() {

        return inventory;
    }

    public InventoryView getInventoryView() {

        return inventoryView;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {

        if (event.getSlot() >= 0 && event.getSlot() < slots) {
            if (event.getView().equals(inventoryView) && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                for (final Entry<ItemStack, String> entry : entries.entrySet()) {
                    if (event.getCurrentItem().equals(entry.getKey())) {
                        onClick(entry.getValue(), ClickType.getClickType(event.isLeftClick(), event.isRightClick(), event.isShiftClick()));
                        return;
                    }
                }
            }
        }
    }

    protected abstract void onClick(String value, ClickType clickType);

    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent event) {

        if (event.getView().equals(inventoryView)) {
            closeInventoryView();
        }
    }

    @EventHandler
    public void onPluginDisable(final PluginDisableEvent event) {

        if (event.getPlugin().equals(plugin)) {
            closeInventoryView();
        }
    }

}
