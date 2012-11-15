
package com.quartercode.quarterbukkit.api.hcl;

public class DefaultConfigParser implements ConfigParser {

    public DefaultConfigParser() {

    }

    @Override
    public Config insert(final Config config, final String string) {

        return config;
    }

    @Override
    public String get(final Config config) {

        return "";
    }

}
