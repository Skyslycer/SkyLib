package de.skyslycer.skylib;

import com.github.retrooper.packetevents.PacketEvents;
import com.tchristofferson.configupdater.ConfigUpdater;
import de.skyslycer.skylib.actions.ActionHandler;
import de.skyslycer.skylib.message.MessageHandler;
import de.skyslycer.skylib.updater.PluginPlatform;
import de.skyslycer.skylib.updater.PluginUpdater;
import de.skyslycer.skylib.updater.PolymartPluginUpdater;
import de.skyslycer.skylib.updater.SpigotPluginUpdater;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
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

/**
 * The base class for all plugins using SkyLib.
 */
public abstract class SkyPlugin extends JavaPlugin {

    private @Nullable MessageHandler messageHandler;
    private @Nullable ActionHandler actionHandler;
    private final List<PluginUpdater> updaters = new ArrayList<>();

    @Override
    public void onLoad() {
        if (data().packets()) {
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            PacketEvents.getAPI().load();
            PacketEvents.getAPI().getSettings().checkForUpdates(false);
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
            PacketEvents.getAPI().init();
        }
        if (data().messages() != null) {
            this.messageHandler = new MessageHandler(this, data().messages());
        }
        if (data().actions()) {
            this.actionHandler = new ActionHandler();
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
        if (data().packets()) {
            PacketEvents.getAPI().terminate();
        }
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
     * @param <T> The configuration file type
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
    public @Nullable MessageHandler messageHandler() {
        return messageHandler;
    }

    /**
     * Get the current action handler.
     * @return Current action handler
     */
    public @Nullable ActionHandler actionHandler() {
        return actionHandler;
    }

    /**
     * Action on plugin load.
     */
    public abstract void load();

    /**
     * Action on plugin enable.
     */
    public abstract void enable();

    /**
     * Action on plugin disable.
     */
    public abstract void disable();

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
    public abstract void newUpdate(String version, String link, PluginPlatform platform);

}
