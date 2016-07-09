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

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.quartercode.quarterbukkit.api.query.SearchQuery.Project.ProjectStage;

/**
 * The search query can be used to search the server mods api for projects which contain a given character sequence.
 * The class internally uses the {@link ServerModsAPIQuery}.
 *
 * @see ServerModsAPIQuery
 */
public class SearchQuery {

    private final String search;

    /**
     * Creates a new search query which may search for the given character sequence.
     *
     * @param search The character sequence the search query may search for.
     */
    public SearchQuery(String search) {

        this.search = search;
    }

    /**
     * Returns the character sequence the search query may search for.
     *
     * @return The search string to search for.
     */
    public String getSearch() {

        return search;
    }

    /**
     * Searches for the set character sequence ({@link #getSearch()}) and returns the found {@link Project}s.
     *
     * @return The {@link Project}s the search query found.
     * @throws QueryException Something goes wrong while querying the server mods api.
     */
    public List<Project> execute() throws QueryException {

        String query = "projects?search=" + search;
        JSONArray result = new ServerModsAPIQuery(query).execute();

        List<Project> projects = new ArrayList<Project>();
        for (Object resultEntry : result) {
            if (resultEntry instanceof JSONObject) {
                JSONObject entry = (JSONObject) resultEntry;

                int id = Integer.parseInt(entry.get("id").toString());
                String name = entry.get("name").toString();
                String slug = entry.get("slug").toString();
                ProjectStage stage = ProjectStage.valueOf(entry.get("stage").toString().toUpperCase());
                projects.add(new Project(id, name, slug, stage));
            }
        }

        return projects;
    }

    /**
     * A project represents a project on BukkitDev and contains its id, slug and name, as well as its current stage.
     * It can be retrieved using the {@link SearchQuery} class.
     */
    public static class Project {

        /**
         * The current stage of the project (e.g. alpha, release, inactive).
         */
        public static enum ProjectStage {

            /**
             * There's no downloadable plugin file for this project yet, but the developers wanted to present their concept anyway.
             */
            PLANNING,
            /**
             * The latest version of the project is an alpha release which typically has the core features of a plugin but often lacks in stability.
             */
            ALPHA,
            /**
             * The latest version of the project is a beta release which typically has most of the features of a plugin and is fairly stable.
             */
            BETA,
            /**
             * The latest version of the project is a final release which typically can be used in production and has every planned feature implemented, as well as a very high stability.
             */
            RELEASE,
            /**
             * There were no updates on the project for a long time. It is likely that the developers stopped working on it.
             */
            INACTIVE,
            /**
             * The former developers of the project marked it as officialy abandoned. That means that there is very little chance that the plugin will continue to exist.
             */
            ABANDONED;

        }

        private final int          id;
        private final String       name;
        private final String       slug;
        private final ProjectStage stage;

        /**
         * Creates a new project with the given information.
         *
         * @param id The id the project has on BukkitDev.
         * @param name The name of the project.
         * @param slug The slug which can be used to view the project using the url {@code http://dev.bukkit.org/server-mods/<slug>}.
         * @param stage The current stage of the project (see {@link ProjectStage}).
         */
        public Project(int id, String name, String slug, ProjectStage stage) {

            this.id = id;
            this.name = name;
            this.slug = slug;
            this.stage = stage;
        }

        /**
         * Returns the id the project has on BukkitDev.
         *
         * @return The id of the project.
         */
        public int getId() {

            return id;
        }

        /**
         * Returns the name of the project.
         *
         * @return The name of the project.
         */
        public String getName() {

            return name;
        }

        /**
         * Returns the slug which can be used to view the project using the url {@code http://dev.bukkit.org/server-mods/<slug>}.
         *
         * @return The slug of the project.
         */
        public String getSlug() {

            return slug;
        }

        /**
         * Returns the current stage of the project (see {@link ProjectStage}).
         *
         * @return The current stage of the project.
         */
        public ProjectStage getStage() {

            return stage;
        }

    }

}
