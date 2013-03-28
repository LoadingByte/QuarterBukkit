
package com.quartercode.quarterbukkit.api.select;

import java.util.List;

public interface InventoryLayouter {

    public InventoryLayout getLayout(SelectInventory selectInventory, List<Selection> selections);

}
