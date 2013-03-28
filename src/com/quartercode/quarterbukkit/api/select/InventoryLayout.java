
package com.quartercode.quarterbukkit.api.select;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.Listener;

/**
 * This class represents the layout of the {@link Selection}s in a {@link SelectInventory}.
 */
public class InventoryLayout implements Listener {

    private final int             maxColumns;
    private List<List<Selection>> layout = new ArrayList<List<Selection>>();

    /**
     * Creates a new InventoryLayout.
     */
    public InventoryLayout() {

        maxColumns = 9;

        for (int counter = 0; counter < maxColumns; counter++) {
            layout.add(counter, new ArrayList<Selection>());
        }
    }

    /**
     * Returns the maximal amount of columns.
     * 
     * @return The maximal amount of columns.
     */
    public int getMaxColumns() {

        return maxColumns;
    }

    /**
     * Returns the {@link Selection} at the x- and y-layout-coordinates.
     * 
     * @param x The x-layout-coordinate.
     * @param y The y-layout-coordinate.
     * @return The {@link Selection} at the x- and y-layout-coordinates.
     */
    public Selection get(final int x, final int y) {

        if (x >= layout.size()) {
            return null;
        } else {
            if (y >= layout.get(x).size()) {
                return null;
            } else {
                return layout.get(x).get(y);
            }
        }
    }

    /**
     * Sets the {@link Selection} at the x- and y-layout-coordinates.
     * 
     * @param x The x-layout-coordinate.
     * @param y The y-layout-coordinate.
     * @param selection The new {@link Selection} at the x- and y-layout-coordinates.
     * @return If the new {@link Selection} was set successfully.
     */
    public boolean set(final int x, final int y, final Selection selection) {

        if (x >= layout.size()) {
            return false;
        } else {
            for (int counter = layout.get(x).size(); counter <= y; counter++) {
                layout.get(x).add(counter, null);
            }

            layout.get(x).set(y, selection);
            return true;
        }
    }

    /**
     * Returns the column at the x-layout-coordinate as a {@link List} filled with {@link Selection}s.
     * 
     * @param x The x-layout-coordinate
     * @return The column at the x-layout-coordinate as a {@link List} filled with {@link Selection}s.
     */
    public List<Selection> getColumn(final int x) {

        if (x >= layout.size()) {
            return null;
        } else {
            return layout.get(x);
        }
    }

    /**
     * Sets the column at the x-layout-coordinate as a {@link List} filled with {@link Selection}s.
     * 
     * @param x The x-layout-coordinate
     * @param column The new column as a {@link List} filled with {@link Selection}s.
     * @return If the new column was set successfully.
     */
    public boolean setColumn(final int x, final List<Selection> column) {

        if (x >= layout.size()) {
            return false;
        } else {
            layout.set(x, column);
            return true;
        }
    }

    /**
     * Returns the row at the y-layout-coordinate as a {@link List} filled with {@link Selection}s.
     * 
     * @param y The y-layout-coordinate
     * @return The row at the y-layout-coordinate as a {@link List} filled with {@link Selection}s.
     */
    public List<Selection> getRow(final int y) {

        final List<Selection> row = new ArrayList<Selection>();
        for (int x = 0; x < layout.size(); x++) {
            row.set(x, get(x, y));
        }

        return row;
    }

    /**
     * Sets the row at the y-layout-coordinate as a {@link List} filled with {@link Selection}s.
     * 
     * @param y The y-layout-coordinate
     * @param row The new row as a {@link List} filled with {@link Selection}s.
     * @return If the new row was set successfully.
     */
    public boolean setRow(final int y, final List<Selection> row) {

        for (int x = 0; x < layout.size(); x++) {
            set(x, y, row.get(x));
        }

        return true;
    }

    /**
     * Returns the whole layout as a {@link List} filled with a {@link List} filled with {@link Selection}s.
     * 
     * @return The whole layout as a {@link List} filled with a {@link List} filled with {@link Selection}s.
     */
    public List<List<Selection>> getLayout() {

        return layout;
    }

    /**
     * Sets the whole new layout as a {@link List} filled with a {@link List} filled with {@link Selection}s.
     * 
     * @param layout The whole new layout as a {@link List} filled with a {@link List} filled with {@link Selection}s.
     */
    public void setLayout(final List<List<Selection>> layout) {

        this.layout = layout;
    }

}
