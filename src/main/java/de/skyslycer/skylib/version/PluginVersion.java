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

package de.skyslycer.skylib.version;

import java.util.regex.Pattern;

public class PluginVersion {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+).+");

    private final int major;
    private final int minor;
    private final int patch;

    public PluginVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    /**
     * Check if this version is older than the other.
     * @param other The version to compare
     * @return If this one is older
     */
    public boolean isOlderThan(PluginVersion other) {
        if (major < other.major) {
            return true;
        } else if (minor < other.minor && major <= other.major) {
            return true;
        } else return patch < other.patch && minor <= other.minor && major <= other.major;
    }

    /**
     * Get the matched version from a string.
     * @param version The version string
     * @return The matched version or in case of no match null
     */
    public static PluginVersion fromString(String version) {
        var matcher = PATTERN.matcher(version);
        if (matcher.matches()) {
            return new PluginVersion(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        }
        return null;
    }

}
