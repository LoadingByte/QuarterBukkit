/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api;

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
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import com.quartercode.quarterbukkit.api.exception.ExceptionHandler;
import com.quartercode.quarterbukkit.api.exception.InstallException;

/**
 * This class is for checking the plugin-versions and updating plugins.
 */
public abstract class Updater {

    private static final String TITLE_TAG = "title";
    private static final String LINK_TAG  = "link";
    private static final String ITEM_TAG  = "item";

    protected Plugin            plugin;
    protected Plugin            updatePlugin;
    protected String            slug;
    private URL                 url;
    private URL                 feedUrl;

    /**
     * Creates a new abstract Updater.
     * 
     * @param plugin The {@link Plugin} which starts the updater.
     * @param updatePlugin The {@link Plugin} which should be updated.
     * @param slug The BukkitDev-slug. Say we have the {@link URL} http://dev.bukkit.org/server-mods/quarterbukkit, {@code quarterbukkit} is the slug.
     */
    public Updater(Plugin plugin, Plugin updatePlugin, String slug) {

        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }

        this.plugin = plugin;
        this.updatePlugin = updatePlugin;
        this.slug = slug;

        try {
            url = new URL("http://dev.bukkit.org/server-mods/" + slug);
            feedUrl = new URL(url.toExternalForm() + "/files.rss");
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException("Error while initalizing URL (slug: " + slug + ")", e);
        }
    }

    /**
     * Returns the {@link Plugin} which started the updater.
     * 
     * @return The {@link Plugin} which started the updater.
     */
    public Plugin getPlugin() {

        return plugin;
    }

    /**
     * Return the {@link Plugin} which should be updated.
     * 
     * @return The {@link Plugin} which should be updated.
     */
    public Plugin getUpdatePlugin() {

        return updatePlugin;
    }

    /**
     * This method checks the latest plugin-version and updates it if required.
     * You can call this in onEnable().
     */
    public boolean tryInstall() {

        return tryInstall(null);
    }

    /**
     * This method checks the latest plugin-version and updates it if required.
     * You can call this in onEnable().
     * 
     * @param causer The executor of the action.
     * @return If the installation was successful.
     */
    public boolean tryInstall(CommandSender causer) {

        try {
            if (isNewVersionAvaiable()) {
                return install(new File("plugins"), causer);
            }
        }
        catch (UnknownHostException e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, causer, "Can't connect to dev.bukkit.org"));
        }
        catch (IOException e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, causer, "Something went wrong with the file system"));
        }
        catch (XMLStreamException e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, causer, "Something went wrong with the version XML-feed (" + feedUrl.toExternalForm() + ")"));
        }
        catch (InvalidPluginException e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, causer, "The downloaded plugin isn't valid"));
        }
        catch (InvalidDescriptionException e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, causer, "The plugin.yml in the downloaded plugin isn't valid"));
        }
        catch (UnknownDependencyException e) {
            ExceptionHandler.exception(new InstallException(plugin, this, e, causer, "The downloaded plugin has a depency to a plugin which isn't installed"));
        }

        return false;
    }

    /**
     * Returns the latest plugin version.
     * 
     * @return The latest plugin version.
     * @throws IOException If something goes wrong with the file system.
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     */
    public String getLatestVersion() throws IOException, XMLStreamException {

        return getLatestVersion(null);
    }

    /**
     * Returns the latest plugin version.
     * 
     * @param causer The executor of the action.
     * @return The latest plugin version.
     * @throws IOException If something goes wrong with the file system.
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     */
    public String getLatestVersion(CommandSender causer) throws IOException, XMLStreamException {

        return parseVersion(getFeedData().get("title"));
    }

    /**
     * Parses the version out of the BukkitDev-upload-title.
     * 
     * @param title The BukkitDev-upload-title.
     * @return The parsed version.
     */
    protected abstract String parseVersion(String title);

    /**
     * Returns if a new version is avaiable.
     * 
     * @return If a new version is avaiable.
     * @throws IOException If something goes wrong with the file system.
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     */
    public boolean isNewVersionAvaiable() throws IOException, XMLStreamException {

        return isNewVersionAvaiable(null);
    }

    /**
     * Returns if a new version is avaiable.
     * 
     * @param causer The executor of the action.
     * @return If a new version is avaiable.
     * @throws IOException If something goes wrong with the file system.
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     */
    public boolean isNewVersionAvaiable(CommandSender causer) throws IOException, XMLStreamException {

        if (updatePlugin == null) {
            return true;
        } else {
            String latestVersion = getLatestVersion();
            if (latestVersion != null) {
                if (!latestVersion.equals(updatePlugin.getDescription().getVersion())) {
                    return true;
                }
            }

            return false;
        }
    }

    private boolean install(File directory, CommandSender causer) throws IOException, XMLStreamException, UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {

        URL url = new URL(getFileURL(getFeedData().get("link")));
        String fileName = url.getPath().split("/")[url.getPath().split("/").length - 1];
        File file = new File(directory, fileName);
        InputStream inputStream = url.openStream();
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.flush();

        byte[] tempBuffer = new byte[4096];
        int counter;
        while ( (counter = inputStream.read(tempBuffer)) > 0) {
            outputStream.write(tempBuffer, 0, counter);
            outputStream.flush();
        }

        inputStream.close();
        outputStream.close();

        return doInstall(file, causer);
    }

    /**
     * Does some post-installation-activities, like extracting zips or relaoding the plugin.
     * 
     * @param downloadedFile The downloaded file from BukkitDev.
     * @param causer The executor of the action.
     * @return If the post-installation-activity was successful.
     * @throws IOException If something goes wrong with the post-installation-activities.
     */
    protected abstract boolean doInstall(File downloadedFile, CommandSender causer) throws IOException;

    /**
     * Extracts some {@link File}s in a zip.
     * You can use this in doInstall().
     * 
     * @param zip The zip as a {@link File}.
     * @param zipPath The path in the zip file (relative).
     * @param destinationFile Where to extract the {@link File}.
     * @throws ZipException If something goes wrong in the {@link ZipFile}.
     * @throws IOException If something goes wrong with the file system.
     */
    public void extract(File zip, String zipPath, File destinationFile) throws ZipException, IOException {

        ZipFile zipFile = new ZipFile(zip);
        ZipEntry zipEntry = zipFile.getEntry(zipPath);

        byte[] BUFFER = new byte[0xFFFF];

        if (zipEntry.isDirectory()) {
            destinationFile.mkdirs();
        } else {
            destinationFile.getParentFile().mkdirs();

            InputStream inputStream = zipFile.getInputStream(zipEntry);
            OutputStream outputStream = new FileOutputStream(destinationFile);

            for (int lenght; (lenght = inputStream.read(BUFFER)) != -1;) {
                outputStream.write(BUFFER, 0, lenght);
            }

            outputStream.close();
            inputStream.close();
        }

        zipFile.close();
    }

    private String getFileURL(String link) throws IOException {

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

    private Map<String, String> getFeedData() throws IOException, XMLStreamException {

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

    @Override
    public int hashCode() {

        int prime = 31;
        int result = 1;
        result = prime * result + (feedUrl == null ? 0 : feedUrl.hashCode());
        result = prime * result + (plugin == null ? 0 : plugin.hashCode());
        result = prime * result + (slug == null ? 0 : slug.hashCode());
        result = prime * result + (updatePlugin == null ? 0 : updatePlugin.hashCode());
        result = prime * result + (url == null ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Updater other = (Updater) obj;
        if (feedUrl == null) {
            if (other.feedUrl != null) {
                return false;
            }
        } else if (!feedUrl.equals(other.feedUrl)) {
            return false;
        }
        if (plugin == null) {
            if (other.plugin != null) {
                return false;
            }
        } else if (!plugin.equals(other.plugin)) {
            return false;
        }
        if (slug == null) {
            if (other.slug != null) {
                return false;
            }
        } else if (!slug.equals(other.slug)) {
            return false;
        }
        if (updatePlugin == null) {
            if (other.updatePlugin != null) {
                return false;
            }
        } else if (!updatePlugin.equals(other.updatePlugin)) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass() + " [plugin=" + plugin + ", updatePlugin=" + updatePlugin + ", slug=" + slug + ", url=" + url + "]";
    }

}
