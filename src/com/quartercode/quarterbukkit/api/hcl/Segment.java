
package com.quartercode.quarterbukkit.api.hcl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for representing a HCL config segment.
 */
public class Segment extends ConfigEntry implements ParentEntry {

    private List<ConfigEntry> children;

    /**
     * Creates a new HCL config segment and sets path.
     * 
     * @param config The config.
     */
    public Segment(final Config config) {

        super(config);
    }

    /**
     * Creates a new HCL config segment, sets path and fills the object with chuldren.
     * 
     * @param config The config.
     * @param children The children to add.
     */
    public Segment(final Config config, final ConfigEntry... children) {

        super(config);

        for (ConfigEntry child : children) {
            addChild(child);
        }
    }

    @Override
    public List<ConfigEntry> getChildren() {

        return Collections.unmodifiableList(children);
    }

    @Override
    public List<ConfigEntry> getAllChildren() {

        final List<ConfigEntry> configEntries = new ArrayList<ConfigEntry>();
        for (final ConfigEntry configEntry : children) {
            configEntries.add(configEntry);

            if (configEntry instanceof ParentEntry) {
                configEntries.addAll( ((ParentEntry) configEntry).getAllChildren());
            }
        }

        return configEntries;
    }

    @Override
    public void addChild(ConfigEntry configEntry) {

        if (children.contains(configEntry)) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " already exists");
        }

        children.add(configEntry);
    }

    @Override
    public void removeChild(ConfigEntry configEntry) {

        if (children.contains(configEntry)) {
            throw new IllegalStateException("ConfigEntry " + configEntry + " not exists");
        }

        children.remove(configEntry);
    }

}
