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

public class CheckResult {

    private final String version;
    private final String url;
    private final PluginPlatform platform;

    public CheckResult(String version, String url, PluginPlatform platform) {
        this.version = version;
        this.url = url;
        this.platform = platform;
    }

    /**
     * Get the new version.
     * @return The new version
     */
    public String version() {
        return version;
    }

    /**
     * Get the plugin url.
     * @return The plugin url
     */
    public String url() {
        return url;
    }

    /**
     * Get the update platform.
     * @return The update platform
     */
    public PluginPlatform platform() {
        return platform;
    }

}
