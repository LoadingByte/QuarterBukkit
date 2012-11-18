
package com.quartercode.quarterbukkit.api.hcl;

import java.util.List;

/**
 * Interface which have to implement classes which contains parents.
 */
public interface ParentEntry {

    /**
     * Returns all direct children.
     * 
     * @return All direct children.
     */
    public List<ConfigEntry> getChildren();

    /**
     * Adds a direct child.
     * 
     * @param configEntry The child to add.
     */
    public void addChild(ConfigEntry configEntry);

    /**
     * Removes a direct child.
     * 
     * @param configEntry The direct child to remove.
     */
    public void removeChild(ConfigEntry configEntry);

}
