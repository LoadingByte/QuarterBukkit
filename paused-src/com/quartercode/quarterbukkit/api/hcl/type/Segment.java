
package com.quartercode.quarterbukkit.api.hcl.type;

import java.util.Collections;
import java.util.List;
import com.quartercode.quarterbukkit.api.hcl.Config;
import com.quartercode.quarterbukkit.api.hcl.ConfigEntry;
import com.quartercode.quarterbukkit.api.hcl.ParentEntry;

/**
 * Class for representing a HCL config segment.
 */
public class Segment extends ConfigEntry implements ParentEntry {

    private List<ConfigEntry> children;

    /**
     * Creates a new HCL config segment and sets the name.
     * 
     * @param config The config.
     * @param name The name of the entry.
     */
    public Segment(final Config config, final String name) {

        super(config, name);
    }

    @Override
    public List<ConfigEntry> getChildren() {

        return Collections.unmodifiableList(children);
    }

    @Override
    public void addChild(final ConfigEntry configEntry) {

        if (children.contains(configEntry) || config.getChild(this, configEntry.getName()) != null) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " already exists");
        }

        children.add(configEntry);
    }

    @Override
    public void removeChild(final ConfigEntry configEntry) {

        if (!children.contains(configEntry) || config.getChild(this, configEntry.getName()) == null) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " not exists");
        }

        children.remove(configEntry);
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (children == null ? 0 : children.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Segment other = (Segment) obj;
        if (children == null) {
            if (other.children != null) {
                return false;
            }
        } else if (!children.equals(other.children)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [children=" + children + "]";
    }

}
