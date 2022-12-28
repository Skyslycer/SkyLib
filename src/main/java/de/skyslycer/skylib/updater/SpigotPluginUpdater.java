package de.skyslycer.skylib.updater;

import com.google.gson.JsonObject;

/**
 * Utility class for getting the latest plugin version on Spigot.
 */
public class SpigotPluginUpdater extends PluginUpdater {

    /**
     * Create a new Spigot plugin updater.
     * @param pluginId The plugin id
     */
    public SpigotPluginUpdater(int pluginId) {
        super(pluginId, PluginPlatform.SPIGOT_MC);
    }

    @Override
    public String parse(JsonObject object) {
        return object.getAsJsonObject("name").getAsString();
    }

}
