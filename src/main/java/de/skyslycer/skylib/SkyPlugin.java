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

package de.skyslycer.skylib;

import com.tchristofferson.configupdater.ConfigUpdater;
import de.skyslycer.skylib.message.MessageHandler;
import de.skyslycer.skylib.updater.PluginPlatform;
import de.skyslycer.skylib.updater.PluginUpdater;
import de.skyslycer.skylib.updater.PolymartPluginUpdater;
import de.skyslycer.skylib.updater.SpigotPluginUpdater;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

public abstract class SkyPlugin extends JavaPlugin {

    private @Nullable MessageHandler handler;
    private final List<PluginUpdater> updaters = new ArrayList<>();

    @Override
    public void onLoad() {
        if (data().packets()) {
            // PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            // PacketEvents.getAPI().load();
            // PacketEvents.getAPI().getSettings().checkForUpdates(false);
        }
        if (data().polymartId() != null) {
            updaters.add(new PolymartPluginUpdater(data().polymartId()));
        }
        if (data().spigotId() != null) {
            updaters.add(new SpigotPluginUpdater(data().spigotId()));
        }
        load();
    }

    @Override
    public void onEnable() {
        if (data().packets()) {
            // PacketEvents.getAPI().init();
        }
        if (data().messages() != null) {
            this.handler = new MessageHandler(this, data().messages());
        }
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> updaters.forEach(updater -> {
            var check = updater.check(this);
            if (check != null) {
                newUpdate(check.version(), check.url(), check.platform());
            }
        }), 20 * 60, 20 * 60 * 60);
        enable();
    }

    @Override
    public void onDisable() {
        disable();
        // PacketEvents.getAPI().terminate();
    }

    /**
     * Register new plugin listeners.
     * @param listener The listeners to register
     */
    public void registerListener(Listener... listener) {
        for (var entry : listener) {
            Bukkit.getPluginManager().registerEvents(entry, this);
        }
    }

    /**
     * Log a severe error using the plugin's logger.
     * @param error The error message to display
     */
    public void logSevere(String error) {
        logSevere(error, null);
    }

    /**
     * Log a severe error using the plugin's logger.
     * @param error The error message to display
     * @param exception The exception coming along it
     */
    public void logSevere(String error, @Nullable Exception exception) {
        this.getLogger().severe(error);
        if (exception != null) {
            exception.printStackTrace();
        }
    }

    /**
     * Load a configuration file and update it. Destination and jar-included file must have the same name.
     * @param path The path to the file
     * @param clazz The class
     * @param ignoredSections Ignored sections for the updater
     * @return The loaded class
     */
    public <T> T loadConfig(Path path, Class<T> clazz, String... ignoredSections) {
        try {
            if (!Files.exists(path)) {
                Files.copy(getResource(path.getFileName().toString()), path);
            }
            ConfigUpdater.update(this, path.getFileName().toString(), path.toFile(), ignoredSections);
            var loader = YamlConfigurationLoader.builder()
                    .path(path)
                    .build();
            return loader.load().get(clazz);
        } catch (IOException exception) {
            logSevere("Could not load the configuration (please report this to the developers)! The plugin will shut down now.", exception);
            return null;
        }
    }

    /**
     * Get the current message handler.
     * @return Current message handler
     */
    public @Nullable MessageHandler handler() {
        return handler;
    }

    /**
     * Action on plugin load.
     */
    public void load() {

    }

    /**
     * Action on plugin enable.
     */
    public void enable() {

    }

    /**
     * Action on plugin disable.
     */
    public void disable() {

    }

    /**
     * The plugin data the parent class should use.
     * @return The plugin data
     */
    public abstract SkyPluginData data();

    /**
     * Action on new update.
     * @param version The new version
     * @param link The link to the resource
     * @param platform The platform
     */
    public void newUpdate(String version, String link, PluginPlatform platform) {

    }

}
