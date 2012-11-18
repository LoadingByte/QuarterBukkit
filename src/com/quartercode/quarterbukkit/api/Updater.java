
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
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;
import com.quartercode.quarterbukkit.QuarterBukkit;
import com.quartercode.quarterbukkit.api.exception.InstallException;

/**
 * This class is for checking the QuarterBukkit-version and updating the plugin.
 */
public abstract class Updater {

    private static final String TITLE_TAG = "title";
    private static final String LINK_TAG  = "link";
    private static final String ITEM_TAG  = "item";

    private final Plugin        plugin;
    private final Plugin        updatePlugin;
    private final String        slug;
    private URL                 url;
    private URL                 feedUrl;

    /**
     * Creates a new abstract Updater.
     * 
     * @param plugin The plugin which starts the updater.
     * @param updatePlugin The plugin which should be updated.
     * @param slug The BukkitDev-slug. Say we have the {@link URL} http://dev.bukkit.org/server-mods/quarterbukkit, quarterbukkit is the slug.
     */
    public Updater(final Plugin plugin, final Plugin updatePlugin, final String slug) {

        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannor be null");
        }

        this.plugin = plugin;
        this.updatePlugin = updatePlugin;
        this.slug = slug;

        try {
            url = new URL("http://dev.bukkit.org/server-mods/" + slug);
            feedUrl = new URL(url.toExternalForm() + "/files.rss");
        }
        catch (final MalformedURLException e) {
            throw new IllegalArgumentException("Error while initalizing URL (slug: " + slug + ")", e);
        }
    }

    /**
     * This method checks the latest plugin-version and updates it if required.
     * You can call this in onEnable().
     */
    public void tryUpdate() {

        try {
            if (isNewVersionAvaiable()) {
                install(new File("plugins"));
            }
        }
        catch (final UnknownHostException e) {
            QuarterBukkit.exception(new InstallException(plugin, e, "Can't connect to dev.bukkit.org"));
        }
        catch (final IOException e) {
            QuarterBukkit.exception(new InstallException(plugin, e, "Something went wrong with the file system"));
        }
        catch (final XMLStreamException e) {
            QuarterBukkit.exception(new InstallException(plugin, e, "Something went wrong with the version XML-feed (" + feedUrl.toExternalForm() + ")"));
        }
        catch (final InvalidPluginException e) {
            QuarterBukkit.exception(new InstallException(plugin, e, "The downloaded plugin isn't valid"));
        }
        catch (final InvalidDescriptionException e) {
            QuarterBukkit.exception(new InstallException(plugin, e, "The plugin.yml in the downloaded plugin isn't valid"));
        }
        catch (final UnknownDependencyException e) {
            QuarterBukkit.exception(new InstallException(plugin, e, "The downloaded plugin has a depency to a plugin which isn't installed"));
        }
    }

    /**
     * Returns the latest plugin version.
     * 
     * @return The latest plugin version.
     * @throws IOException If something goes wrong with the file system.
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     */
    public String getLatestVersion() throws IOException, XMLStreamException {

        return parseVersion(getFeedData().get("title"));
    }

    protected abstract String parseVersion(String title);

    /**
     * Returns if a new version is avaiable.
     * 
     * @return If a new version is avaiable.
     * @throws IOException If something goes wrong with the file system.
     * @throws XMLStreamException If something goes wrong with the version XML-feed.
     */
    public boolean isNewVersionAvaiable() throws IOException, XMLStreamException {

        if (updatePlugin == null) {
            return true;
        } else {
            final String latestVersion = getLatestVersion();
            if (latestVersion != null) {
                if (!latestVersion.equals(updatePlugin.getDescription().getVersion())) {
                    return true;
                }
            }

            return false;
        }
    }

    private void install(final File directory) throws IOException, XMLStreamException, UnknownDependencyException, InvalidPluginException, InvalidDescriptionException {

        final URL url = new URL(getFileURL(getFeedData().get("link")));
        final String fileName = url.getPath().split("/")[url.getPath().split("/").length - 1];
        final File file = new File(directory, fileName);
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

        doInstall(file);

        if (updatePlugin == null) {
            plugin.getLogger().info("Successfully installed " + slug + "!");
            plugin.getLogger().warning("Reload the server to enable " + slug + ".");
        } else {
            plugin.getLogger().info("Successfully updated " + updatePlugin.getName() + "!");
            plugin.getLogger().warning("Reload the server to enable " + updatePlugin.getName() + ".");
        }
    }

    protected abstract void doInstall(File downloadedFile) throws IOException;

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
    public void extract(final File zip, final String zipPath, final File destinationFile) throws ZipException, IOException {

        final ZipFile zipFile = new ZipFile(zip);
        final ZipEntry zipEntry = zipFile.getEntry(zipPath);

        final byte[] BUFFER = new byte[0xFFFF];

        if (zipEntry.isDirectory()) {
            destinationFile.mkdirs();
        } else {
            destinationFile.getParentFile().mkdirs();

            final InputStream inputStream = zipFile.getInputStream(zipEntry);
            final OutputStream outputStream = new FileOutputStream(destinationFile);

            for (int lenght; (lenght = inputStream.read(BUFFER)) != -1;) {
                outputStream.write(BUFFER, 0, lenght);
            }

            outputStream.close();
            inputStream.close();
        }

        zipFile.close();
    }

    private String getFileURL(final String link) throws IOException {

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

    private Map<String, String> getFeedData() throws IOException, XMLStreamException {

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

  

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ( (feedUrl == null) ? 0 : feedUrl.hashCode());
        result = prime * result + ( (plugin == null) ? 0 : plugin.hashCode());
        result = prime * result + ( (slug == null) ? 0 : slug.hashCode());
        result = prime * result + ( (updatePlugin == null) ? 0 : updatePlugin.hashCode());
        result = prime * result + ( (url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Updater other = (Updater) obj;
        if (feedUrl == null) {
            if (other.feedUrl != null)
                return false;
        } else if (!feedUrl.equals(other.feedUrl))
            return false;
        if (plugin == null) {
            if (other.plugin != null)
                return false;
        } else if (!plugin.equals(other.plugin))
            return false;
        if (slug == null) {
            if (other.slug != null)
                return false;
        } else if (!slug.equals(other.slug))
            return false;
        if (updatePlugin == null) {
            if (other.updatePlugin != null)
                return false;
        } else if (!updatePlugin.equals(other.updatePlugin))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @Override
    public String toString() {

        return getClass() + " [plugin=" + plugin + ", updatePlugin=" + updatePlugin + ", slug=" + slug + ", url=" + url + "]";
    }

}
