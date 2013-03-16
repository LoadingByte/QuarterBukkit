
package com.quartercode.quarterbukkit.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class SelectInventory implements Listener {

    /**
     * This enum defines four different click types.
     * Currently, there're four values: LEFT, RIGHT, LEFT_SHIFT, RIGHT_SHIFT.
     * 
     * Every type has three methods which can output the concrete boolean values:
     * 
     * public boolean isLeft()
     * public boolean isRight()
     * public boolean isShift()
     */
    public enum ClickType {

        /**
         * A default left click.
         */
        LEFT (true, false, false),
        /**
         * A default right click.
         */
        RIGHT (false, true, false),
        /**
         * A left click while holding shift.
         */
        LEFT_SHIFT (true, false, true),
        /**
         * A right click while holding shift.
         */
        RIGHT_SHIFT (false, true, true);

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

        /**
         * If the left mouse button was clicked.
         * 
         * @return If the left mouse button was clicked.
         */
        public boolean isLeft() {

            return left;
        }

        /**
         * If the right mouse button was clicked.
         * 
         * @return If the right mouse button was clicked.
         */
        public boolean isRight() {

            return right;
        }

        /**
         * If shift was holded while clicking.
         * 
         * @return If shift was holded while clicking.
         */
        public boolean isShift() {

            return shift;
        }
    }

    private final Plugin                 plugin;
    private final Player                 player;
    private final String                 title;
    private final int                    slots;
    private final Map<ItemStack, Object> entries = new LinkedHashMap<ItemStack, Object>();

    private final Inventory              inventory;
    private InventoryView                inventoryView;

    /**
     * Creates an empty inventory for the final {@link Player} `player`, the final title `"Title"` and `9` slots.
     * Of course you can define your own values, e.g. you can read the title from a language or message-config system. You can also colorize the title with {@link ChatColor}s.
     * Keep in mind the you can only define multiples of 9 as slots.
     * 
     * @param plugin The binding plugin.
     * @param player The holding player.
     * @param title The visible title, maybe colored with {@link ChatColor}s.
     * @param slots The amount of slots.
     */
    public SelectInventory(final Plugin plugin, final Player player, final String title, final int slots) {

        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.slots = slots;

        inventory = Bukkit.createInventory(player, slots, title);
    }

    /**
     * Opens the inventory for the holder.
     * 
     * @return This instance of SelectInventory.
     */
    public SelectInventory openInventoryView() {

        inventoryView = player.openInventory(inventory);
        refreshItemStacks();

        Bukkit.getPluginManager().registerEvents(this, plugin);

        return this;
    }

    /**
     * Closes the inventory for the holder.
     * 
     * @return This instance of SelectInventory.
     */
    public SelectInventory closeInventoryView() {

        if (inventoryView != null) {
            inventoryView.close();
            inventoryView = null;
        }

        HandlerList.unregisterAll(this);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material) {

        add(value, new ItemStack(material));

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material} and the amount.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param amount The amount of items.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final int amount) {

        add(value, new ItemStack(material, amount));

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material} and the data/damage.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param data The data for non-damageable items and damage for damageable ones.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final short data) {

        add(value, new ItemStack(material, 1, data));

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the amount and the data/damage.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param amount The amount of items.
     * @param data The data for non-damageable items and damage for damageable ones.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final int amount, final short data) {

        add(value, new ItemStack(material, amount, data));

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link ItemStack} directly.
     * 
     * @param value The information for the option as {@link Object}.
     * @param itemStack The {@link ItemStack}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final ItemStack itemStack) {

        entries.put(itemStack, value);

        if (inventory != null) {
            refreshItemStacks();
        }

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the name and the descriptions as {@link String}-array.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-array.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final String name, final String... descriptions) {

        add(value, new ItemStack(material), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the amount, the name and the descriptions as {@link String}-array.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param amount The amount of items.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-array.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final int amount, final String name, final String... descriptions) {

        add(value, new ItemStack(material, amount), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the data/damage, the name and the descriptions as {@link String}-array.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param data The data for non-damageable items and damage for damageable ones.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-array.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final short data, final String name, final String... descriptions) {

        add(value, new ItemStack(material, 1, data), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the amount, the data/damage, the name and the descriptions as {@link String}-array.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param amount The amount of items.
     * @param data The data for non-damageable items and damage for damageable ones.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-array.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final int amount, final short data, final String name, final String... descriptions) {

        add(value, new ItemStack(material, amount, data), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link ItemStack} directly. Furthemore, it sets the name and the descriptions as {@link String}-array.
     * 
     * @param value The information for the option as {@link Object}.
     * @param itemStack The {@link ItemStack}.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-array.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final ItemStack itemStack, final String name, final String... descriptions) {

        MetaUtil.setName(itemStack, name);
        MetaUtil.setDescriptions(itemStack, descriptions);

        entries.put(itemStack, value);

        if (inventory != null) {
            refreshItemStacks();
        }

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the name and the descriptions as {@link String}-{@link List}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-{@link List}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the amount, the name and the descriptions as {@link String}-{@link List}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param amount The amount of items.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-{@link List}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final int amount, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material, amount), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the data/damage, the name and the descriptions as {@link String}-{@link List}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param data The data for non-damageable items and damage for damageable ones.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-{@link List}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final short data, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material, 1, data), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}, the amount, the data/damage, the name and the descriptions as {@link String}-{@link List}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @param amount The amount of items.
     * @param data The data for non-damageable items and damage for damageable ones.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-{@link List}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final Material material, final int amount, final short data, final String name, final List<String> descriptions) {

        add(value, new ItemStack(material, amount, data), name, descriptions);

        return this;
    }

    /**
     * Adds a new item option to the inventory and sets the {@link ItemStack} directly. Furthemore, it sets the name and the descriptions as {@link String}-{@link List}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param itemStack The {@link ItemStack}.
     * @param name The name of the item as {@link String}.
     * @param descriptions The descriptions for the item as {@link String}-{@link List}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(final Object value, final ItemStack itemStack, final String name, final List<String> descriptions) {

        MetaUtil.setName(itemStack, name);
        MetaUtil.setDescriptions(itemStack, descriptions);

        entries.put(itemStack, value);

        if (inventory != null) {
            refreshItemStacks();
        }

        return this;
    }

    private void refreshItemStacks() {

        int slot = 0;
        for (final Entry<ItemStack, Object> entry : entries.entrySet()) {
            inventory.setItem(slot, entry.getKey());

            slot++;
            if (slot >= slots) {
                break;
            }
        }
    }

    /**
     * Returns the holder of the inventory as a {@link Player}.
     * 
     * @return The holder of the inventory.
     */
    public Player getPlayer() {

        return player;
    }

    /**
     * Returns the title of the inventory.
     * 
     * @return The title of the inventory.
     */
    public String getTitle() {

        return title;
    }

    /**
     * Returns the amount of slots of the inventory.
     * 
     * @return The amount of slots of the inventory.
     */
    public int getSlots() {

        return slots;
    }

    /**
     * Returns the {@link Inventory} (don't modify the slots, it will break this class).
     * 
     * @return The {@link Inventory}.
     */
    public Inventory getInventory() {

        return inventory;
    }

    /**
     * Returns the {@link InventoryView} if the inventory is open, else it is null.
     * 
     * @return The {@link InventoryView} if the inventory is open.
     */
    public InventoryView getInventoryView() {

        return inventoryView;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {

        if (event.getSlot() >= 0 && event.getSlot() < slots) {
            if (event.getView().equals(inventoryView) && event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                for (final Entry<ItemStack, Object> entry : entries.entrySet()) {
                    if (event.getCurrentItem().equals(entry.getKey())) {
                        onClick(entry.getValue(), ClickType.getClickType(event.isLeftClick(), event.isRightClick(), event.isShiftClick()));
                        return;
                    }
                }
            }
        }
    }

    /**
     * Gets called if the holder clicks on a registered option.
     * 
     * @param value The identifier (maybe with information).
     * @param clickType The {@link ClickType} of the click.
     */
    protected abstract void onClick(Object value, ClickType clickType);

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
