
package com.quartercode.quarterbukkit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

/*
 * Copyright 2011 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list
 * of conditions and the following disclaimer in the documentation and/or other materials
 * provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

/**
 * The metrics class obtains data about a plugin and submits statistics about it to the metrics backend.
 * Public methods provided by this class:
 */
public class Metrics {

    private final static int        REVISION              = 5;
    private static final String     BASE_URL              = "http://mcstats.org";
    private static final String     REPORT_URL            = "/report/%s";
    private static final String     CUSTOM_DATA_SEPARATOR = "~~";
    private static final int        PING_INTERVAL         = 10;
    private final Plugin            plugin;
    private final Set<Graph>        graphs                = Collections.synchronizedSet(new HashSet<Graph>());
    private final Graph             defaultGraph          = new Graph("Default");
    private final YamlConfiguration configuration;
    private final File              configurationFile;
    private final String            guid;
    private final Object            optOutLock            = new Object();
    private volatile int            taskId                = -1;

    /**
     * Creates a new Metrics-Sender for a defined {@link Plugin}.
     * 
     * @param plugin The Metrics-{@link Plugin}.
     * @throws IOException If something goes wrong while creating the configuration-file.
     */
    public Metrics(final Plugin plugin) throws IOException {

        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }

        this.plugin = plugin;

        configurationFile = getConfigFile();
        configuration = YamlConfiguration.loadConfiguration(configurationFile);

        configuration.addDefault("opt-out", false);
        configuration.addDefault("guid", UUID.randomUUID().toString());

        if (configuration.get("guid", null) == null) {
            configuration.options().header("http://mcstats.org").copyDefaults(true);
            configuration.save(configurationFile);
        }

        guid = configuration.getString("guid");
    }

    /**
     * Construct and create a Graph that can be used to separate specific plotters to their own graphs on the metrics website.
     * Plotters can be added to the graph object returned.
     * 
     * @param name The name of the graph.
     * @return Graph object created. Will never return NULL under normal circumstances unless bad parameters are given.
     */
    public Graph createGraph(final String name) {

        if (name == null) {
            throw new IllegalArgumentException("Graph name cannot be null");
        }

        final Graph graph = new Graph(name);

        graphs.add(graph);

        return graph;
    }

    /**
     * Add a Graph object to Metrics that represents data for the plugin that should be sent to the backend.
     * 
     * @param graph The name of the graph.
     */
    public void addGraph(final Graph graph) {

        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }

        graphs.add(graph);
    }

    /**
     * Adds a custom data plotter to the default graph.
     * 
     * @param plotter The plotter to use to plot custom data.
     */
    public void addCustomData(final Plotter plotter) {

        if (plotter == null) {
            throw new IllegalArgumentException("Plotter cannot be null");
        }

        defaultGraph.addPlotter(plotter);

        graphs.add(defaultGraph);
    }

    /**
     * Start measuring statistics. This will immediately create an async repeating task as the plugin and send the initial data to the metrics backend,
     * and then after that it will post in increments of PING_INTERVAL * 1200 ticks.
     * 
     * @return True if statistics measuring is running, otherwise false.
     */
    public boolean start() {

        synchronized (optOutLock) {
            if (isOptOut()) {
                return false;
            }

            if (taskId >= 0) {
                return true;
            }

            taskId = plugin.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {

                private boolean firstPost = true;

                @Override
                public void run() {

                    try {
                        synchronized (optOutLock) {
                            if (isOptOut() && taskId > 0) {
	plugin.getServer().getScheduler().cancelTask(taskId);
	taskId = -1;
	for (final Graph graph : graphs) {
	    graph.onOptOut();
	}
                            }
                        }

                        postPlugin(!firstPost);

                        firstPost = false;
                    }
                    catch (final IOException e) {
                        Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage());
                    }
                }
            }, 0, PING_INTERVAL * 1200);

            return true;
        }
    }

    /**
     * Has the server owner denied plugin metrics?
     * 
     * @return True if metrics should be opted out of it.
     */
    public boolean isOptOut() {

        synchronized (optOutLock) {
            try {
                configuration.load(getConfigFile());
            }
            catch (final IOException ex) {
                Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
                return true;
            }
            catch (final InvalidConfigurationException ex) {
                Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
                return true;
            }
            return configuration.getBoolean("opt-out", false);
        }
    }

    /**
     * Enables metrics for the server by setting "opt-out" to false in the config file and starting the metrics task.
     * 
     * @throws IOException If something goes wrong while creating the configuration-file.
     */
    public void enable() throws IOException {

        synchronized (optOutLock) {
            if (isOptOut()) {
                configuration.set("opt-out", false);
                configuration.save(configurationFile);
            }

            if (taskId < 0) {
                start();
            }
        }
    }

    /**
     * Disables metrics for the server by setting "opt-out" to true in the config file and canceling the metrics task.
     * 
     * @throws IOException If something goes wrong while creating the configuration-file.
     */
    public void disable() throws IOException {

        synchronized (optOutLock) {
            if (!isOptOut()) {
                configuration.set("opt-out", true);
                configuration.save(configurationFile);
            }

            if (taskId > 0) {
                plugin.getServer().getScheduler().cancelTask(taskId);
                taskId = -1;
            }
        }
    }

    /**
     * Gets the File object of the config file that should be used to store data such as the GUID and opt-out status.
     * 
     * @return The File object for the config file.
     */
    public File getConfigFile() {

        final File pluginsFolder = plugin.getDataFolder().getParentFile();

        return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
    }

    private void postPlugin(final boolean isPing) throws IOException {

        final PluginDescriptionFile description = plugin.getDescription();

        final StringBuilder data = new StringBuilder();
        data.append(encode("guid")).append('=').append(encode(guid));
        encodeDataPair(data, "version", description.getVersion());
        encodeDataPair(data, "server", Bukkit.getVersion());
        encodeDataPair(data, "players", Integer.toString(Bukkit.getServer().getOnlinePlayers().length));
        encodeDataPair(data, "revision", String.valueOf(REVISION));

        if (isPing) {
            encodeDataPair(data, "ping", "true");
        }

        synchronized (graphs) {
            final Iterator<Graph> iter = graphs.iterator();

            while (iter.hasNext()) {
                final Graph graph = iter.next();

                for (final Plotter plotter : graph.getPlotters()) {
                    final String key = String.format("C%s%s%s%s", CUSTOM_DATA_SEPARATOR, graph.getName(), CUSTOM_DATA_SEPARATOR, plotter.getColumnName());
                    final String value = Integer.toString(plotter.getValue());
                    encodeDataPair(data, key, value);
                }
            }
        }

        final URL url = new URL(BASE_URL + String.format(REPORT_URL, encode(plugin.getDescription().getName())));
        URLConnection connection;

        if (isMineshafterPresent()) {
            connection = url.openConnection(Proxy.NO_PROXY);
        } else {
            connection = url.openConnection();
        }

        connection.setDoOutput(true);

        final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(data.toString());
        writer.flush();

        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        final String response = reader.readLine();

        writer.close();
        reader.close();

        if (response == null || response.startsWith("ERR")) {
            throw new IOException(response);
        } else {
            if (response.contains("OK This is your first update this hour")) {
                synchronized (graphs) {
                    final Iterator<Graph> iter = graphs.iterator();

                    while (iter.hasNext()) {
                        final Graph graph = iter.next();

                        for (final Plotter plotter : graph.getPlotters()) {
                            plotter.reset();
                        }
                    }
                }
            }
        }
    }

    private boolean isMineshafterPresent() {

        try {
            Class.forName("mineshafter.MineServer");
            return true;
        }
        catch (final Exception e) {
            return false;
        }
    }

    private static void encodeDataPair(final StringBuilder buffer, final String key, final String value) throws UnsupportedEncodingException {

        buffer.append('&').append(encode(key)).append('=').append(encode(value));
    }

    private static String encode(final String text) throws UnsupportedEncodingException {

        return URLEncoder.encode(text, "UTF-8");
    }

    /**
     * Represents a custom graph on the website.
     */
    public static class Graph {

        private final String       name;

        private final Set<Plotter> plotters = new LinkedHashSet<Plotter>();

        private Graph(final String name) {

            this.name = name;
        }

        /**
         * Gets the graph's name.
         * 
         * @return The Graph's name.
         */
        public String getName() {

            return name;
        }

        /**
         * Add a plotter to the graph, which will be used to plot entries.
         * 
         * @param plotter The plotter to add to the graph.
         */
        public void addPlotter(final Plotter plotter) {

            plotters.add(plotter);
        }

        /**
         * Remove a plotter from the graph.
         * 
         * @param plotter The plotter to remove from the graph.
         */
        public void removePlotter(final Plotter plotter) {

            plotters.remove(plotter);
        }

        /**
         * Gets an unmodifiable {@link Set} of the plotter objects in the graph.
         * 
         * @return An unmodifiable {@link Set} of the plotter objects.
         */
        public Set<Plotter> getPlotters() {

            return Collections.unmodifiableSet(plotters);
        }

        @Override
        public int hashCode() {

            return name.hashCode();
        }

        @Override
        public boolean equals(final Object object) {

            if (! (object instanceof Graph)) {
                return false;
            }

            final Graph graph = (Graph) object;
            return graph.name.equals(name);
        }

        protected void onOptOut() {

        }

    }

    /**
     * Interface used to collect custom data for a plugin.
     */
    public static abstract class Plotter {

        private final String name;

        /**
         * Construct a plotter with the default plot name.
         */
        public Plotter() {

            this("Default");
        }

        /**
         * Construct a plotter with a specific plot name.
         * 
         * @param name The name of the plotter to use, which will show up on the website.
         */
        public Plotter(final String name) {

            this.name = name;
        }

        /**
         * Get the current value for the plotted point.
         * Since this function defers to an external function it may or may not return immediately thus cannot be guaranteed to be thread friendly or safe. This function can be called from any thread
         * so care should be taken when accessing resources that need to be synchronized.
         * 
         * @return The current value for the point to be plotted.
         */
        public abstract int getValue();

        /**
         * Get the column name for the plotted point.
         * 
         * @return The plotted point's column name.
         */
        public String getColumnName() {

            return name;
        }

        /**
         * Called after the website graphs have been updated.
         */
        public void reset() {

        }

        @Override
        public int hashCode() {

            return getColumnName().hashCode();
        }

        @Override
        public boolean equals(final Object object) {

            if (! (object instanceof Plotter)) {
                return false;
            }

            final Plotter plotter = (Plotter) object;
            return plotter.name.equals(name) && plotter.getValue() == getValue();
        }

    }

}