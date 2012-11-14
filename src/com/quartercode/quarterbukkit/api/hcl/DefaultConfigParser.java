
package com.quartercode.quarterbukkit.api.hcl;

public class DefaultConfigParser implements ConfigParser {

    public DefaultConfigParser() {

    }

    @Override
    public Config insert(Config config, String string) {

        return config;
    }

    @Override
    public String get(Config config) {

        return "";
    }

}
