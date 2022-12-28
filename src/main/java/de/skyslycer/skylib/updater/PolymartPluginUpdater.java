package de.skyslycer.skylib.updater;

import com.google.gson.JsonObject;

/**
 * Utility class for getting the latest plugin version on Polymart.
 */
public class PolymartPluginUpdater extends PluginUpdater {

    /**
     * Create a new Polymart plugin updater.
     * @param pluginId The plugin id
     */
    public PolymartPluginUpdater(int pluginId) {
        super(pluginId, PluginPlatform.POLYMART);
    }

    @Override
    public String parse(JsonObject object) {
        return object.getAsJsonObject("response").getAsJsonObject("resource").getAsJsonObject("updates").getAsJsonObject("latest").get("version").getAsString();
    }

}
