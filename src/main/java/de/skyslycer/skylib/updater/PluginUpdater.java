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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.skyslycer.skylib.SkyPlugin;
import de.skyslycer.skylib.version.PluginVersion;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public abstract class PluginUpdater {

    public static final HttpClient CLIENT = HttpClient.newHttpClient();
    public static final Gson GSON = new GsonBuilder().create();

    private final int pluginId;
    private final PluginPlatform platform;

    protected PluginUpdater(int pluginId, PluginPlatform platform) {
        this.pluginId = pluginId;
        this.platform = platform;
    }

    /**
     * Get the plugin id for the platform.
     * @return The platform plugin id
     */
    public int pluginId() {
        return pluginId;
    }

    /**
     * Get the updater platform.
     * @return The platform
     */
    public PluginPlatform platform() {
        return platform;
    }

    /**
     * Check if there is a new update.
     * @param plugin The plugin
     * @return The new version or, if no new version is available, null
     */
    public CheckResult check(SkyPlugin plugin) {
        try {
            var request = CLIENT.send(HttpRequest.newBuilder()
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36 Edg/90.0.818.42")
                    .uri(URI.create(String.format(platform().apiUrl(), pluginId())))
                    .build(), HttpResponse.BodyHandlers.ofInputStream());
            var json = GSON.fromJson(new String(request.body().readAllBytes(), StandardCharsets.UTF_8), JsonObject.class);
            var version = parse(json);
            if (version != null && PluginVersion.fromString(plugin.getDescription().getVersion()).isOlderThan(PluginVersion.fromString(version))) {
                return new CheckResult(version, String.format(platform().url(), pluginId()), platform());
            }
        } catch (IOException | InterruptedException | NullPointerException ignored) { }
        return null;
    }

    /**
     * Parse the returned JsonObject into the latest version.
     * @param object The json the request returned
     * @return A usable version
     */
    public abstract String parse(JsonObject object);

}
