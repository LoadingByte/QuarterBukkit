/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api.select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
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
import com.quartercode.quarterbukkit.api.MetaUtil;

/**
 * This class is for simple creating of custom selection {@link Inventory}s.
 * You can define your own titles using {@link ChatColor}s and sort your selections in the {@link Inventory}.
 */
public abstract class SelectInventory implements Listener {

    private final Plugin               plugin;
    private String                     title;
    private InventoryLayouter          layouter     = new LineInventoryLayouter();
    private final List<Selection>      selections   = new ArrayList<Selection>();

    private final Map<Player, ViewMap> inventoryMap = new HashMap<Player, ViewMap>();

    /**
     * Creates an empty select inventory with the given title.
     * 
     * @param plugin The plugin to bind the internal methods on.
     * @param title The visible title of the inventory; it can be colored with {@link ChatColor}s.
     */
    public SelectInventory(Plugin plugin, String title) {

        this.plugin = plugin;
        this.title = title;
    }

    /**
     * Creates an empty select inventory with the given title and an {@link InventoryLayouter}.
     * 
     * @param plugin The plugin to bind the internal methods on.
     * @param title The visible title of the inventory; it can be colored with {@link ChatColor}s.
     * @param layouter The {@link InventoryLayouter} for layouting the {@link Inventory}.
     */
    public SelectInventory(Plugin plugin, String title, InventoryLayouter layouter) {

        this.plugin = plugin;
        this.title = title;
        this.layouter = layouter;
    }

    /**
     * Returns the title of the {@link Inventory}.
     * 
     * @return The title of the {@link Inventory}.
     */
    public String getTitle() {

        return title;
    }

    /**
     * Sets the title of the {@link Inventory}.
     * 
     * @param title The new title of the {@link Inventory}.
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * Returns the {@link InventoryLayouter}.
     * 
     * @return The {@link InventoryLayouter}.
     */
    public InventoryLayouter getLayouter() {

        return layouter;
    }

    /**
     * Sets the new {@link InventoryLayouter}.
     * 
     * @param layouter The new {@link InventoryLayouter}.
     */
    public void setLayouter(InventoryLayouter layouter) {

        this.layouter = layouter;
    }

    /**
     * Returns all existing selections.
     * 
     * @return All existing selections.
     */
    public List<Selection> getSelections() {

        return Collections.unmodifiableList(selections);
    }

    /**
     * Adds a new item option to the inventory and sets the {@link Material}.
     * 
     * @param value The information for the option as {@link Object}.
     * @param material The {@link Material} for the item.
     * @return This instance of SelectInventory.
     */
    public SelectInventory add(Object value, Material material) {

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
    public SelectInventory add(Object value, Material material, int amount) {

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
    public SelectInventory add(Object value, Material material, short data) {

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
    public SelectInventory add(Object value, Material material, int amount, short data) {

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
    public SelectInventory add(Object value, ItemStack itemStack) {

        selections.add(new Selection(value, itemStack));
        repaint();
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
    public SelectInventory add(Object value, Material material, String name, String... descriptions) {

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
    public SelectInventory add(Object value, Material material, int amount, String name, String... descriptions) {

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
    public SelectInventory add(Object value, Material material, short data, String name, String... descriptions) {

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
    public SelectInventory add(Object value, Material material, int amount, short data, String name, String... descriptions) {

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
    public SelectInventory add(Object value, ItemStack itemStack, String name, String... descriptions) {

        MetaUtil.setName(itemStack, name);
        MetaUtil.setDescriptions(itemStack, descriptions);
        add(value, itemStack);
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
    public SelectInventory add(Object value, Material material, String name, List<String> descriptions) {

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
    public SelectInventory add(Object value, Material material, int amount, String name, List<String> descriptions) {

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
    public SelectInventory add(Object value, Material material, short data, String name, List<String> descriptions) {

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
    public SelectInventory add(Object value, Material material, int amount, short data, String name, List<String> descriptions) {

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
    public SelectInventory add(Object value, ItemStack itemStack, String name, List<String> descriptions) {

        MetaUtil.setName(itemStack, name);
        MetaUtil.setDescriptions(itemStack, descriptions);
        add(value, itemStack);
        return this;
    }

    /**
     * Removes an existing item option from the inventory.
     * 
     * @param value The information of the option as {@link Object}.
     * @return This instance of SelectInventory.
     */
    public SelectInventory remove(Object value) {

        for (Selection selection : new ArrayList<Selection>(selections)) {
            if (selection.getValue().equals(value)) {
                selections.remove(selection);
            }
        }
        repaint();

        return this;
    }

    /**
     * Clears all existing item options.
     * 
     * @return This instance of SelectInventory.
     */
    public SelectInventory clear() {

        selections.clear();
        repaint();
        return this;
    }

    /**
     * Returns if the defined {@link Player} has an open {@link Inventory} of this SelectInventory.
     * 
     * @param player The {@link Player} to check.
     * @return If the defined {@link Player} has an open {@link Inventory} of this SelectInventory.
     */
    public boolean isOpen(Player player) {

        return inventoryMap.containsKey(player);
    }

    /**
     * Opens an {@link Inventory} for a defined {@link Player}.
     * 
     * @param player The {@link Player} which gets the {@link Inventory}.
     */
    public void open(Player player) {

        InventoryLayout layout = layouter.getLayout(this, selections);

        int slots = 0;
        for (List<Selection> column : layout.getLayout()) {
            if (column.size() > slots) {
                slots = column.size();
            }
        }
        slots *= 9;

        if (slots > 0) {
            if (inventoryMap.isEmpty()) {
                Bukkit.getPluginManager().registerEvents(this, plugin);
            }

            if (isOpen(player)) {
                close(player);
            }

            Inventory inventory = Bukkit.createInventory(player, slots, title);
            Map<Integer, Selection> slotMap = paint(inventory);

            inventoryMap.put(player, new ViewMap(inventory, player.openInventory(inventory), slotMap));
        }
    }

    private void repaint() {

        for (Entry<Player, ViewMap> entry : new HashMap<Player, ViewMap>(inventoryMap).entrySet()) {
            inventoryMap.put(entry.getKey(), new ViewMap(entry.getValue().getInventory(), entry.getValue().getView(), paint(entry.getValue().getInventory())));
        }
    }

    private Map<Integer, Selection> paint(Inventory inventory) {

        InventoryLayout layout = layouter.getLayout(this, selections);

        Map<Integer, Selection> slotMap = new HashMap<Integer, Selection>();
        for (int x = 0; x < layout.getLayout().size(); x++) {
            for (int y = 0; y < layout.getLayout().get(x).size() && y < inventory.getSize() / 9; y++) {
                inventory.setItem(x + y * 9, layout.get(x, y) == null ? new ItemStack(Material.AIR) : layout.get(x, y).getItemStack());
                slotMap.put(x + y * 9, layout.get(x, y));
            }
        }

        return slotMap;
    }

    /**
     * Closes the {@link Inventory} for a defined {@link Player}.
     * 
     * @param player The {@link Player} which inventory should get closed.
     */
    public void close(final Player player) {

        Bukkit.getScheduler().callSyncMethod(plugin, new Callable<Void>() {

            @Override
            public Void call() {

                if (isOpen(player)) {
                    inventoryMap.remove(player);
                    player.closeInventory();
                }

                if (inventoryMap.isEmpty()) {
                    HandlerList.unregisterAll(SelectInventory.this);
                }

                return null;
            }
        });
    }

    /**
     * Closes all open {@link Inventory}s.
     */
    public void closeAll() {

        for (Player player : new ArrayList<Player>(inventoryMap.keySet())) {
            close(player);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getWhoClicked() instanceof Player && isOpen((Player) event.getWhoClicked()) && event.getView().equals(inventoryMap.get(event.getWhoClicked()).getView())) {
            event.setCancelled(true);

            Map<Integer, Selection> slotMap = inventoryMap.get(event.getWhoClicked()).getSlotMap();
            if (slotMap.containsKey(event.getRawSlot())) {
                onClick(slotMap.get(event.getRawSlot()), ClickType.getClickType(event.isLeftClick(), event.isRightClick(), event.isShiftClick()), (Player) event.getWhoClicked());
                return;
            }
        }
    }

    /**
     * Gets called if the holder clicks on a registered option.
     * 
     * @param selection The selected {@link Selection} (with the informational value and the graphical {@link ItemStack}).
     * @param clickType The {@link ClickType} of the click.
     * @param player The {@link Player} who selected.
     */
    protected abstract void onClick(Selection selection, ClickType clickType, Player player);

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if (inventoryMap.containsKey(event.getPlayer()) && event.getView().equals(inventoryMap.get(event.getPlayer()).getView())) {
            close((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {

        if (event.getPlugin().equals(plugin)) {
            for (Entry<Player, ViewMap> entry : inventoryMap.entrySet()) {
                close(entry.getKey());
            }
        }
    }

    private static class ViewMap {

        private final Inventory               inventory;
        private final InventoryView           view;
        private final Map<Integer, Selection> slotMap;

        private ViewMap(Inventory inventory, InventoryView view, Map<Integer, Selection> slotMap) {

            this.inventory = inventory;
            this.view = view;
            this.slotMap = slotMap;
        }

        private Inventory getInventory() {

            return inventory;
        }

        private InventoryView getView() {

            return view;
        }

        private Map<Integer, Selection> getSlotMap() {

            return slotMap;
        }

    }

}
