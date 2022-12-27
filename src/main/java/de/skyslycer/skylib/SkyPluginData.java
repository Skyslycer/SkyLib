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

import java.nio.file.Path;
import javax.annotation.Nullable;

public class SkyPluginData {

    private final @Nullable Path messages;
    private final @Nullable Integer polymartId;
    private final @Nullable Integer spigotId;
    private final boolean actions;
    private final boolean packets;

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
         */
        public Builder actions(boolean actions) {
            this.actions = actions;
            return this;
        }

        /**
         * Whether packets are enabled.
         * @param packets Whether packets are enabled
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
