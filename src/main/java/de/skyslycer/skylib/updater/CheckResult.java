package de.skyslycer.skylib.updater;

/**
 * This class contains information about the result of a version check.
 */
public class CheckResult {

    private final String version;
    private final String url;
    private final PluginPlatform platform;

    /**
     * Create a new check result.
     * @param version The latest version of the plugin.
     * @param url The url to the resource
     * @param platform The platform of the resource
     */
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
