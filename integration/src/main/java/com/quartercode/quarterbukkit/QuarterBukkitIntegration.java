/*
 * This file is part of QuarterBukkit-Integration.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Integration is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Integration is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Integration. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

/**
 * This class is used for integrating QuarterBukkit into a {@link Plugin}.
 */
public class QuarterBukkitIntegration {

    private static final String PLUGIN_NAME = "QuarterBukkit-Plugin";

    private static final String TITLE_TAG   = "title";
    private static final String LINK_TAG    = "link";
    private static final String ITEM_TAG    = "item";

    private static URL          feedUrl;

    // The plugins which called the integrate() method
    private static Set<Plugin>  callers     = new HashSet<Plugin>();
    // Determinates if the integration process was already invoked
    private static boolean      invoked     = false;

    static {

        URL feed = null;
        try {
            feed = new URL("http://dev.bukkit.org/server-mods/quarterbukkit/files.rss");
        }
        catch (MalformedURLException e) {
            Bukkit.getLogger().severe("Error while initalizing URL (" + e + ")");
        }

        feedUrl = feed;

    }

    /**
     * Call this method in onEnable() for integrating QuarterBukkit into your plugin.
     * It creates a config where the user has to turn a value to "Yes" for the actual installation.
     * The class notfies him on the console and every time an op joins to the server.
     * 
     * @param plugin The {@link Plugin} which tries to integrate QuarterBukkit.
     * @return True if QuarterBukkit can be used after the call, false if not.
     */
    public static boolean integrate(final Plugin plugin) {

        // Register caller
        callers.add(plugin);

        if (!Bukkit.getPluginManager().isPluginEnabled(PLUGIN_NAME)) {
            if (!invoked) {
                // Block this part (it should only be called once)
                invoked = true;

                // Clean up
                if (new File("plugins/" + PLUGIN_NAME + "_extract").exists()) {
                    try {
                        FileUtils.delete(new File("plugins/" + PLUGIN_NAME + "_extract"));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Read installation confirmation file
                File installConfigFile = new File("plugins/" + PLUGIN_NAME, "install.yml");

                try {
                    if (!installConfigFile.exists()) {
                        // No installation confirmation file -> create a new one and wait until restart
                        YamlConfiguration installConfig = new YamlConfiguration();
                        installConfig.set("install-" + PLUGIN_NAME, true);
                        installConfig.save(installConfigFile);
                    } else {
                        YamlConfiguration installConfig = YamlConfiguration.loadConfiguration(installConfigFile);
                        if (installConfig.isBoolean("install-" + PLUGIN_NAME) && installConfig.getBoolean("install-" + PLUGIN_NAME)) {
                            // Installation confirmed -> install
                            installConfigFile.delete();
                            install(new File("plugins", PLUGIN_NAME + ".jar"));
                            return true;
                        }
                    }

                    // Schedule with a time because the integrating plugin might get disabled
                    new Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {

                            Bukkit.broadcastMessage(ChatColor.YELLOW + "===============[ " + PLUGIN_NAME + " Installation ]===============");
                            String plugins = "";
                            for (Plugin caller : callers) {
                                plugins += ", " + caller.getName();
                            }
                            plugins = plugins.substring(2);
                            Bukkit.broadcastMessage(ChatColor.RED + "For using " + plugins + " which requires " + PLUGIN_NAME + ", you should " + ChatColor.DARK_AQUA + "restart" + ChatColor.RED + " the server!");
                        }
                    }, 100, 3 * 1000);
                }
                catch (UnknownHostException e) {
                    Bukkit.getLogger().warning("Can't connect to dev.bukkit.org for installing " + PLUGIN_NAME + "!");
                }
                catch (Exception e) {
                    Bukkit.getLogger().severe("An error occurred while installing " + PLUGIN_NAME + " (" + e + ")");
                    e.printStackTrace();
                }
            }

            return false;
        } else {
            return true;
        }
    }

    private static void install(File target) throws IOException, XMLStreamException, UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {

        Bukkit.getLogger().info("===============[ " + PLUGIN_NAME + " Installation ]===============");
        Bukkit.getLogger().info("Installing " + PLUGIN_NAME + " ...");

        Bukkit.getLogger().info("Downloading " + PLUGIN_NAME + " ...");
        File zipFile = new File(target.getParentFile(), PLUGIN_NAME + "_download.zip");
        URL url = new URL(getFileURL(getFeedData().get("link")));
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream(zipFile);
        outputStream.flush();

        byte[] tempBuffer = new byte[4096];
        int counter;
        while ( (counter = inputStream.read(tempBuffer)) > 0) {
            outputStream.write(tempBuffer, 0, counter);
            outputStream.flush();
        }

        inputStream.close();
        outputStream.close();

        Bukkit.getLogger().info("Extracting " + PLUGIN_NAME + " ...");
        File unzipDir = new File(target.getParentFile(), PLUGIN_NAME + "_extract");
        FileUtils.unzip(zipFile, unzipDir);
        FileUtils.delete(zipFile);
        File unzipInnerDir = unzipDir.listFiles()[0];
        FileUtils.copy(new File(unzipInnerDir, target.getName()), target);
        FileUtils.delete(unzipDir);

        Bukkit.getLogger().info("Loading " + PLUGIN_NAME + " ...");
        Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(target));

        Bukkit.getLogger().info("Successfully installed " + PLUGIN_NAME + "!");
        Bukkit.getLogger().info("Enabling other plugins ...");
    }

    private static String getFileURL(String link) throws IOException {

        URL url = new URL(link);
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ( (line = reader.readLine()) != null) {
            if (line.contains("<li class=\"user-action user-action-download\">")) {
                return line.split("<a href=\"")[1].split("\">Download</a>")[0];
            }
        }
        connection = null;
        reader.close();

        return null;
    }

    private static Map<String, String> getFeedData() throws IOException, XMLStreamException {

        Map<String, String> returnMap = new HashMap<String, String>();
        String title = null;
        String link = null;

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream inputStream = feedUrl.openStream();
        XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                if (event.asStartElement().getName().getLocalPart().equals(TITLE_TAG)) {
                    event = eventReader.nextEvent();
                    title = event.asCharacters().getData();
                    continue;
                }
                if (event.asStartElement().getName().getLocalPart().equals(LINK_TAG)) {
                    event = eventReader.nextEvent();
                    link = event.asCharacters().getData();
                    continue;
                }
            } else if (event.isEndElement()) {
                if (event.asEndElement().getName().getLocalPart().equals(ITEM_TAG)) {
                    returnMap.put("title", title);
                    returnMap.put("link", link);
                    return returnMap;
                }
            }
        }

        return returnMap;
    }

}
