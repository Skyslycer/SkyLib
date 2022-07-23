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

import com.google.gson.JsonObject;

public class PolymartPluginUpdater extends PluginUpdater {

    public PolymartPluginUpdater(int pluginId) {
        super(pluginId, PluginPlatform.POLYMART);
    }

    @Override
    public String parse(JsonObject object) {
        return object.getAsJsonObject("response").getAsJsonObject("resource").getAsJsonObject("updates").getAsJsonObject("latest").get("version").getAsString();
    }

}
