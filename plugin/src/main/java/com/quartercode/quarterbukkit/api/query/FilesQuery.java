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

package com.quartercode.quarterbukkit.api.query;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.quartercode.quarterbukkit.api.query.FilesQuery.ProjectFile.ReleaseType;

/**
 * The files query can be used to retrieve all files of a project which are currently available on BukkitDev.
 * The class internally uses the {@link ServerModsAPIQuery}.
 *
 * @see ServerModsAPIQuery
 */
public class FilesQuery {

    private final int     projectId;
    private VersionParser versionParser;

    /**
     * Creates a new files query which may retrieve all files of the project with the given project id <b>without their versions</b>.
     * If you want to use a {@link VersionParser} for retrieving the files' versions, you can use the constructor {@link #FilesQuery(int, VersionParser)}.
     * The files are ordered by the date of upload. The file which was uploaded first comes first.
     *
     * @param projectId The project id of the project whose available files should be retrieved.
     */
    public FilesQuery(int projectId) {

        this.projectId = projectId;
    }

    /**
     * Creates a new files query which may retrieve all files of the project with the given project id.
     * Every file's version is parsed by the given {@link VersionParser}, so you can retrieve the version of a file using {@link ProjectFile#getVersion()}.
     * The files are ordered by the date of upload. The file which was uploaded first comes first.
     *
     * @param projectId The project id of the project whose available files should be retrieved.
     * @param versionParser The {@link VersionParser} for parsing the version of every resulting {@link ProjectFile}.
     */
    public FilesQuery(int projectId, VersionParser versionParser) {

        this.projectId = projectId;
        this.versionParser = versionParser;
    }

    /**
     * Returns the project id of the project whose available files should be retrieved.
     *
     * @return The project id for retrieval.
     */
    public int getProjectId() {

        return projectId;
    }

    /**
     * Returns the {@link VersionParser} for parsing the version of every resulting {@link ProjectFile}.
     * It is called on every {@link ProjectFile} discovered by the {@link #execute()} method.
     * That way you can retrieve the version of a resulting file using {@link ProjectFile#getVersion()}.
     *
     * @return The {@link VersionParser} used by the files query.
     */
    public VersionParser getVersionParser() {

        return versionParser;
    }

    /**
     * Retrieves the available files of the project with the set project id ({@link #getProjectId()}) from BukkitDev.
     * If a {@link VersionParser} is set, it will be called in order to add versions to all {@link ProjectFile}s.
     *
     * @return The {@link ProjectFile}s the files query found.
     * @throws QueryException Something goes wrong while querying the server mods API.
     */
    public List<ProjectFile> execute() throws QueryException {

        String query = "files?projectIds=" + projectId;
        JSONArray result = new ServerModsAPIQuery(query).execute();

        List<ProjectFile> files = new ArrayList<ProjectFile>();
        for (Object resultEntry : result) {
            if (resultEntry instanceof JSONObject) {
                JSONObject entry = (JSONObject) resultEntry;

                String name = entry.get("name").toString();
                ReleaseType releaseType = ReleaseType.valueOf(entry.get("releaseType").toString().toUpperCase());
                URI location = null;
                try {
                    location = new URI(entry.get("downloadUrl").toString());
                } catch (URISyntaxException e) {
                    // Impossible
                }
                String fileName = entry.get("fileName").toString();

                String version = null;
                if (versionParser != null) {
                    version = versionParser.parseVersion(new ProjectFile(name, null, releaseType, location, fileName));
                }

                files.add(new ProjectFile(name, version, releaseType, location, fileName));
            }
        }

        return files;
    }

    /**
     * A version parser can be used to parse the version of a given {@link ProjectFile}.
     * That allows to create {@link ProjectFile} objects which have a version associated with them.
     */
    public static interface VersionParser {

        /**
         * Parses and returns the version of the given {@link ProjectFile}.
         * This should be implemented by subclasses to suit the project's naming conventions.
         *
         * @param file The file which was retrieved without the version attribute.
         * @return The version of the given {@link ProjectFile}.
         */
        public abstract String parseVersion(ProjectFile file);

    }

    /**
     * A project file represents a file uploaded to BukkitDev and contains its name, version and {@link ReleaseType}, as well as the link to the actual file and its name.
     * It can be retrieved using the {@link FilesQuery} class.
     */
    public static class ProjectFile {

        /**
         * The different types of progress a file of a project may have (e.g. alpha, beta, release).
         */
        public static enum ReleaseType {

            /**
             * An alpha release typically has the core features of a plugin but often lacks in stability.
             */
            ALPHA,
            /**
             * A beta release typically has most of the features of a plugin and is fairly stable.
             */
            BETA,
            /**
             * A final release typically can be used in production and has every planned feature, as well as very high stability.
             */
            RELEASE;

        }

        private final String      name;
        private final String      version;
        private final ReleaseType releaseType;
        private final URI         location;
        private final String      fileName;

        /**
         * Creates a new project file describing a file of a project hosted on BukkitDev.
         *
         * @param name The title of the file (e.g. {@code QuarterBukkit 1.1.2}).
         * @param version The version of the file (e.g. {@code 1.1.2}).
         * @param releaseType The {@link ReleaseType} of the file (e.g. {@link ReleaseType#RELEASE}).
         * @param location The location where the actual file described by the data object can be found under.
         * @param fileName The name of the actual file which can be found under the given file location.
         */
        public ProjectFile(String name, String version, ReleaseType releaseType, URI location, String fileName) {

            this.name = name;
            this.version = version;
            this.releaseType = releaseType;
            this.location = location;
            this.fileName = fileName;
        }

        /**
         * Returns the title of the file (e.g. {@code QuarterBukkit 1.1.2}).
         *
         * @return The title of the file.
         */
        public String getName() {

            return name;
        }

        /**
         * Returns the version of the file (e.g. {@code 1.1.2}).
         *
         * @return The version of the file.
         */
        public String getVersion() {

            return version;
        }

        /**
         * Returns the {@link ReleaseType} of the file (e.g. {@link ReleaseType#RELEASE}).
         *
         * @return The {@link ReleaseType} of the file.
         */
        public ReleaseType getReleaseType() {

            return releaseType;
        }

        /**
         * Returns the location where the file described by the data object can be found under.
         *
         * @return The location where the file can be found under.
         */
        public URI getLocation() {

            return location;
        }

        /**
         * Returns the name of the actual file which can be found under the set file location ({@link #getLocation()}).
         *
         * @return The name of the actual file.
         */
        public String getFileName() {

            return fileName;
        }

    }

}
