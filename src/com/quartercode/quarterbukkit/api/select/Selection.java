
package com.quartercode.quarterbukkit.api.select;

import org.bukkit.inventory.ItemStack;

/**
 * This class represents a selection in the {@link SelectInventory} with a value and the graphical {@link ItemStack}.
 */
public class Selection {

    private final Object    value;
    private final ItemStack itemStack;

    /**
     * Creates a new selection and sets the value as an {@link Object} and the graphical {@link ItemStack}.
     * 
     * @param value The value as an {@link Object}.
     * @param itemStack The graphical {@link ItemStack}.
     */
    public Selection(final Object value, final ItemStack itemStack) {

        this.value = value;
        this.itemStack = itemStack;
    }

    /**
     * Returns the value as an {@link Object}.
     * 
     * @return The value as an {@link Object}.
     */
    public Object getValue() {

        return value;
    }

    /**
     * Returns the graphical {@link ItemStack}.
     * 
     * @return The graphical {@link ItemStack}.
     */
    public ItemStack getItemStack() {

        return itemStack;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Selection other = (Selection) obj;
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "Selection [value=" + value + ", itemStack=" + itemStack + "]";
    }

}
