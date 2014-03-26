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

package com.quartercode.quarterbukkit.api.query;

import java.net.URL;
import org.json.simple.JSONArray;

/**
 * The query exception is thrown by a {@link ServerModsAPIQuery} if something goes wrong there.
 * It stores the type of error that occurred, along with the catched {@link Throwable} object.
 * 
 * @see ServerModsAPIQuery
 * @see QueryExceptionType
 */
public class QueryException extends Exception {

    private static final long serialVersionUID = -5960397138979837038L;

    /**
     * The types of exceptions that can be described using a query exception.
     * 
     * @see QueryException
     */
    public static enum QueryExceptionType {

        /**
         * The request {@link URL} isn't in the correct format.
         */
        MALFORMED_URL,
        /**
         * Can't open a connection to the request {@link URL}.
         */
        CANNOT_OPEN_CONNECTION,
        /**
         * Can't read the response the server mods api may return.
         */
        CANNOT_READ_RESPONSE,
        /**
         * The api key provided in QuarterBukkit's configuration was rejected by the server mods api.
         */
        INVALID_API_KEY,
        /**
         * The response of the server mods api isn't a valid {@link JSONArray}.
         */
        INVALID_RESPONSE;

    }

    private final QueryExceptionType type;
    private final ServerModsAPIQuery query;
    private final String             requestUrl;

    /**
     * Creates a new query exception with the given type, the {@link ServerModsAPIQuery} which caused the exception and the request url which was used.
     * 
     * @param type The type of error which occurred.
     * @param query The {@link ServerModsAPIQuery} instance which caused the exception on execution.
     * @param requestUrl The url which was used to send a GET request to the server mods api.
     */
    public QueryException(QueryExceptionType type, ServerModsAPIQuery query, String requestUrl) {

        this.type = type;
        this.query = query;
        this.requestUrl = requestUrl;
    }

    /**
     * Creates a new query exception with the given type, the {@link ServerModsAPIQuery}, the request url which was used and a wrapped {@link Throwable}.
     * 
     * @param type The type of error which occurred.
     * @param query The {@link ServerModsAPIQuery} instance which caused the exception on execution.
     * @param requestUrl The url which was used to send a GET request to the server mods api.
     * @param cause The {@link Throwable} which caused the query exception.
     */
    public QueryException(QueryExceptionType type, ServerModsAPIQuery query, String requestUrl, Throwable cause) {

        super(cause);

        this.type = type;
        this.query = query;
        this.requestUrl = requestUrl;
    }

    /**
     * Returns the type of error which occurred as a {@link QueryExceptionType}.
     * 
     * @return The type of error which occurred.
     */
    public QueryExceptionType getType() {

        return type;
    }

    /**
     * Returns the {@link ServerModsAPIQuery} instance which caused the exception on execution.
     * 
     * @return The query object which was executed and caused the exception.
     */
    public ServerModsAPIQuery getQuery() {

        return query;
    }

    /**
     * Returns the url which was used to send a GET request to the server mods api.
     * 
     * @return The request url which was used by the given {@link ServerModsAPIQuery}.
     */
    public String getRequestUrl() {

        return requestUrl;
    }

}
