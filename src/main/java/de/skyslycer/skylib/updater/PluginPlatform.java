/*
 * Basic library providing basic functionality for Bukkit plugins of mine.
 * Copyright (C) 2022 David (Skyslycer)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.skyslycer.skylib.updater;

/**
 * The platform of a plugin.
 */
public enum PluginPlatform {

    /**
     * The base for plugins on SpigotMC.
     */
    SPIGOT_MC("SpigotMC", "https://www.spigotmc.org/resources/%d/", "https://api.spiget.org/v2/resources/%d/versions/latest"),
    /**
     * The base for plugins on Polymart.
     */
    POLYMART("Polymart", "https://polymart.org/resource/%d", "https://api.polymart.org/v1/getResourceInfo?resource_id=%d");

    private final String name;
    private final String url;
    private final String apiUrl;

    /**
     * Create a new plugin platform.
     * @param name The name of the platform
     * @param url The URL of the platform
     * @param apiUrl The API URL of the platform
     */
    PluginPlatform(String name, String url, String apiUrl) {
        this.name = name;
        this.url = url;
        this.apiUrl = apiUrl;
    }

    /**
     * Get the platform name.
     * @return The platform name
     */
    public String platformName() {
        return name;
    }

    /**
     * Get the plugin url.
     * @return The plugin url
     */
    public String url() {
        return url;
    }

    /**
     * Get the API url.
     * @return The API url
     */
    public String apiUrl() {
        return apiUrl;
    }

}
