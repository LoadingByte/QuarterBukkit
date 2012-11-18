
package com.quartercode.quarterbukkit.api.hcl;

public interface ConfigParser {

    public Config insert(Config config, String string);

    public String get(Config config);

}
