QuarterBukkit
=============

QuarterBukkit is a modern modular API-Extension for Bukkit.
It helps finding and avoiding bugs and mistakes (like accessing the Bukkit API in different thread) and only loading components if they are actually used.
So you can use the Bukkit specification for developing even if QuarterBukkit contains code needs the CraftBukkit implementation to compile.
The API makes a lot of things easier and speeds up the developing process.

There's no work for server operators since QuarterBukkit always checks for updates and new versions.
Furthemore, plugins which use QuarterBukkit automatically download the QuarterBukkit plugin if it is not installed yet.
A server operator doesn't need to download it manually from BukkitDev.

QuarterBukkit features:

* No work for server operators with self-installation and self-update functions.
* New intuitive mistake-catching.
* Thread control.
* Many helping classes for scheduling, config, permissions, commands & more.
* Easy integration for developers.
* OpenSource: Repository on GitHub.

License
-------

Copyright (c) 2013 QuarterCode <http://www.quartercode.com/>

QuarterBukkit may be used under the terms of either the GNU General Public License (GPL). See the LICENSE.md file for details.

Using QuarterBukkit in your plugin
==================================

This section shows you the first steps with QuarterBukkit.
If you want to read the complete tutorial and api documentation, go to http://github.com/QuarterCode/QuarterBukkit/wiki.

Setup
-----

First, you have to add several QuarterBukkit jars to the classpath of your project.
You can do that in two different ways:

### Maven

If you build with Maven, you have to add some dependecies related to QuarterBukkit to your project object model.

* Add the QuarterCode maven repository to the pom.xml of your maven project:

        <repository>
            <id>quartercode-repository</id>
            <name>QuarterCode Repository</name>
            <url>http://repo.quartercode.com/content/groups/public/</url>
        </repository>

* Add these dependencies to the pom.xml of your maven project (only change VERSION to the version of QuarterBukkit you just cloned and built):

        <dependency>
            <groupId>com.quartercode</groupId>
            <artifactId>quarterbukkit-plugin</artifactId>
            <version>VERSION</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.quartercode</groupId>
            <artifactId>quarterbukkit-integration</artifactId>
            <version>VERSION</version>
        </dependency>

* Add the scope `provided` to all of your other dependencies which shouldn't be included in the final jar (e.g. bukkit org craftbukkit):

        <dependency>
            <groupId>org.bukkit</groupId>
            <artifactId>bukkit</artifactId>
            <version>...</version>
            <scope>provided</scope>
        </dependency>

* Add the maven shade plugin (if you don't use it already) to your build plugin list:

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>2.1</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <createDependencyReducedPom>false</createDependencyReducedPom>
                    </configuration>
                </execution>
            </executions>
        </plugin>

In the end, your final pom.xml should look like this:

        <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>

            <groupId>...</groupId>
            <artifactId>...</artifactId>
            <version>...</version>
            <packaging>jar</packaging>

            ...

            <properties>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                ...
            </properties>

            <dependencies>
                <!-- Bukkit -->
                <dependency>
                    <groupId>org.bukkit</groupId>
                    <artifactId>bukkit</artifactId>
                    <version>...</version>
                    <scope>provided</scope>
                </dependency>

                <!-- QuarterBukkit -->
                <dependency>
                    <groupId>com.quartercode</groupId>
                    <artifactId>quarterbukkit-plugin</artifactId>
                    <version>...</version>
                    <scope>provided</scope>
                </dependency>
                <dependency>
                    <groupId>com.quartercode</groupId>
                    <artifactId>quarterbukkit-integration</artifactId>
                    <version>...</version>
                </dependency>

                ...
            </dependencies>

            <repositories>
                <repository>
                    <id>bukkit-repo</id>
                    <name>Bukkit Repository</name>
                    <url>http://repo.bukkit.org/content/groups/public</url>
                </repository>
                <repository>
                    <id>quartercode-repository</id>
                    <name>QuarterCode Repository</name>
                    <url>http://repo.quartercode.com/content/groups/public/</url>
                </repository>
            </repositories>

            <build>
                <plugins>
                    ...

                    <!-- Compiler -->
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <version>2.3.2</version>
                        <configuration>
                            <source>1.6</source>
                            <target>1.6</target>
                        </configuration>
                    </plugin>

                    <!-- Build JAR -->
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-jar-plugin</artifactId>
                        <version>2.4</version>
                        <configuration>
                            <finalName>${project.name}</finalName>
                        </configuration>
                    </plugin>

                    <!-- Shade JAR -->
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-shade-plugin</artifactId>
                        <version>2.1</version>
                        <executions>
                            <execution>
                                <phase>package</phase>
                                <goals>
                                    <goal>shade</goal>
                                </goals>
                                <configuration>
                                    <createDependencyReducedPom>false</createDependencyReducedPom>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
        </project>

Keep in mind that you have to change the versions in your pom.xml if you want to use a new updated version of QuarterBukkit.

### Ant

If you build with Ant, you can download the precompiled package for the current version from http://dev.bukkit.org/bukkit-plugins/QuarterBukkit/files/.
After that, you can add QuarterBukkit-Plugin.jar and QuarterBukkit-Integration.jar to your classpath.

Your build.xml script should look like this:

    <?xml version="1.0" encoding="UTF-8"?>
    <project name="Build Plugin Jar" default="main" basedir=".">
        <!-- Set this to the path to your class folder -->
        <property name="bin" value="bin/" />
        <!-- Set this to the path to your final plugin jar file -->
        <property name="dest" value="Plugin.jar" />
        <!-- Set this to the path to your QuarterBukkit-Integration.jar library -->
        <property name="integrationlib" value="QuarterBukkit-Integration.jar" />

        <target name="main">
            <jar destfile="${dest}">
                <fileset dir="${bin}"/>
                <zipfileset src="${integrationlib}"/>
            </jar>
        </target>
    </project>

Of course, you have to modify the property values to suit your directory structure.

Integration Code
----------------

Simply import `com.quartercode.quarterbukkit.QuarterBukkitIntegration` and call `QuarterBukkitIntegration.integrate()` in your `onEnable()`-method.
The method returns `true` if the integration was sucessfully. If not, the method and disables the plugin, so you can simple put a `return` there.

Example:

    @Override
    public void onEnable() {

        if (!QuarterBukkitIntegration.integrate(this)) {
            return;
        }

        // Your code here
    }

After that, you should add `QuarterBukkit-Plugin` to your `softdepend`-list in the `plugin.yml` of your plugin.
If you don't have this entry, simply add this line:

    softdepend: [QuarterBukkit-Plugin]

And you're done! Now you can use all the amazing QuarterBukkit-features!
See the JavaDoc and [API-Page](API Overview) for more information on those features.

Important Notes
---------------

If you want to use QuarterBukkit in your plugin, remember one thing:
Don't import/use any QuarterBukkit class or method anywhere in your main class (the one which extends `JavaPlugin`).
You cannot import something from QuarterBukkit before using QuarterBukkitIntegration!
That will cause NoClassDefFoundErrors because QuarterBukkit may not be installed yet!

We recommend to use a second main class which also implements `onEnable()` and `onDisable()`.
You can put all your code in there and call the methods from your real main plugin class.

Keep in mind: Always check your plugins for this issue!

