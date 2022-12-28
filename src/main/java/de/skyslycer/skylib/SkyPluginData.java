package de.skyslycer.skylib;

import java.nio.file.Path;
import javax.annotation.Nullable;

/**
 * Data class for the plugin.
 */
public class SkyPluginData {

    private final @Nullable Path messages;
    private final @Nullable Integer polymartId;
    private final @Nullable Integer spigotId;
    private final boolean actions;
    private final boolean packets;

    /**
     * Create a new plugin data.
     * @param messages The path to the messages file, if applicable
     * @param polymartId The polymart id, if applicable
     * @param spigotId The spigot id, if applicable
     * @param actions If the plugin uses actions
     * @param packets If the plugin uses packets
     */
    public SkyPluginData(@Nullable Path messages, @Nullable Integer polymartId, @Nullable Integer spigotId, boolean actions, boolean packets) {
        this.messages = messages;
        this.polymartId = polymartId;
        this.spigotId = spigotId;
        this.actions = actions;
        this.packets = packets;
    }

    /**
     * The message path.
     * @return Message path
     */
    public @Nullable Path messages() {
        return messages;
    }

    /**
     * The Polymart plugin id.
     * @return Polymart plugin id
     */
    @Nullable
    public Integer polymartId() {
        return polymartId;
    }

    /**
     * The Spigot plugin id.
     * @return Spigot plugin id
     */
    @Nullable
    public Integer spigotId() {
        return spigotId;
    }

    /**
     * Whether actions are enabled.
     * @return Whether actions are enabled
     */
    public boolean actions() {
        return actions;
    }

    /**
     * Whether packets are enabled.
     * @return Whether packets are enabled
     */
    public boolean packets() {
        return packets;
    }

    /**
     * Builder class for the plugin data.
     */
    public static class Builder {

        private @Nullable Path messages;
        private @Nullable Integer polymartId;
        private @Nullable Integer spigotId;
        private boolean actions = true;
        private boolean packets = true;

        /**
         * The path to the message file. Must be ".properties".
         * @param messages The path
         * @return Current builder
         */
        public Builder messages(@Nullable Path messages) {
            this.messages = messages;
            return this;
        }

        /**
         * Set the Polymart plugin id.
         * @param polymartId Polymart plugin id
         * @return Current builder
         */
        public Builder polymartId(@Nullable Integer polymartId) {
            this.polymartId = polymartId;
            return this;
        }

        /**
         * Set the Spigot plugin id.
         * @param spigotId Spigot plugin id
         * @return Current builder
         */
        public Builder spigotId(@Nullable Integer spigotId) {
            this.spigotId = spigotId;
            return this;
        }

        /**
         * Whether actions are enabled.
         * @param actions Whether actions are enabled
         * @return Current builder
         */
        public Builder actions(boolean actions) {
            this.actions = actions;
            return this;
        }

        /**
         * Whether packets are enabled.
         * @param packets Whether packets are enabled
         * @return Current builder
         */
        public Builder packets(boolean packets) {
            this.packets = packets;
            return this;
        }

        /**
         * Build the data with the given args.
         * @return Built plugin data
         */
        public SkyPluginData build() {
            return new SkyPluginData(messages, polymartId, spigotId, actions, packets);
        }

    }

}
