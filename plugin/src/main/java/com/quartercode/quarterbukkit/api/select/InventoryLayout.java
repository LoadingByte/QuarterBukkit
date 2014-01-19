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
import java.util.List;

/**
 * This class represents the layout of the {@link Selection}s in a {@link SelectInventory}.
 */
public class InventoryLayout {

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
    public Selection get(int x, int y) {

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
    public boolean set(int x, int y, Selection selection) {

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
    public List<Selection> getColumn(int x) {

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
    public boolean setColumn(int x, List<Selection> column) {

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
    public List<Selection> getRow(int y) {

        List<Selection> row = new ArrayList<Selection>();
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
    public boolean setRow(int y, List<Selection> row) {

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
    public void setLayout(List<List<Selection>> layout) {

        this.layout = layout;
    }

}
