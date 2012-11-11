
package com.quartercode.quarterbukkit.util;

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
import java.util.Map;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

/**
 * This class is for checking the QuarterBukkit-version and updating the plugin.
 */
public class VersionUtil {

    private static final String TITLE_TAG = "title";
    private static final String LINK_TAG  = "link";
    private static final String ITEM_TAG  = "item";

    private static URL          feedUrl;

    static {
        try {
            feedUrl = new URL("http://dev.bukkit.org/server-mods/QuarterBukkit");
        }
        catch (final MalformedURLException e) {
            Bukkit.getLogger().severe("Error while initalizing URL (" + e.getClass() + ": " + e.getLocalizedMessage() + ")");
        }
    }

    /**
     * This method checks the latest QuarterBukkit-version and updates the plugin, if required.
     * 
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     * @throws IOException If there is an error with the file system.
     * @throws InvalidDescriptionException If the plugin.yml of the downloaded plugin isn't valid.
     * @throws InvalidPluginException If the downloaded plugin isn't valid.
     * @throws UnknownDependencyException The the downloaded plugin has a depency to a plugin which isn't installed.
     */
    public static void tryUpdate(final Plugin plugin) throws IOException, XMLStreamException, UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {

        try {
            if (isNewVersionAvaiable(plugin)) {
                final File file = new File("plugins", "QuarterBukkit.jar");

                if (!Bukkit.getPluginManager().isPluginEnabled(plugin.getName())) {
                    install(file, plugin);
                }
            }
        }
        catch (final UnknownHostException e) {
            plugin.getLogger().warning("Can't connect to dev.bukkit.org!");
        }
    }

    private static String getLatestVersion() throws IOException, XMLStreamException {

        final String title = getFeedData().get("title");

        if (title.split(" ").length >= 2) {
            return title.split(" ")[1];
        } else {
            return null;
        }
    }

    private static boolean isNewVersionAvaiable(final Plugin plugin) throws IOException, XMLStreamException {

        final String latestVersion = getLatestVersion();
        if (latestVersion != null) {
            if (!latestVersion.equals(plugin.getDescription().getVersion())) {
                return true;
            }
        }

        return false;
    }

    private static void install(final File file, final Plugin plugin) throws IOException, XMLStreamException, UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {

        plugin.getLogger().info("Installing " + plugin.getName() + " ...");

        final URL url = new URL(getFileURL(getFeedData().get("link")));
        final InputStream inputStream = url.openStream();
        final OutputStream outputStream = new FileOutputStream(file);
        outputStream.flush();

        final byte[] tempBuffer = new byte[4096];
        int counter;
        while ( (counter = inputStream.read(tempBuffer)) > 0) {
            outputStream.write(tempBuffer, 0, counter);
            outputStream.flush();
        }

        inputStream.close();
        outputStream.close();

        plugin.getLogger().info("Reloading " + plugin.getName() + " ...");
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(file));
    }

    private static String getFileURL(final String link) throws IOException {

        final URL url = new URL(link);
        URLConnection connection = url.openConnection();
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

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

        final Map<String, String> returnMap = new HashMap<String, String>();
        String title = null;
        String link = null;

        final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        final InputStream inputStream = feedUrl.openStream();
        final XMLEventReader eventReader = inputFactory.createXMLEventReader(inputStream);

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

    private VersionUtil() {

    }

}
