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

package de.skyslycer.skylib.util;

import com.github.retrooper.packetevents.util.Vector3d;
import org.bukkit.Location;

public class VectorUtil {

    /**
     * Get the vector from a location.
     * @param location The Bukkit location
     * @return The vector
     */
    public static Vector3d fromLocation(Location location) {
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Get a vector with everything set to 0.
     * @return A null vector
     */
    public static Vector3d zeroVector() {
        return new Vector3d(0d, 0d, 0d);
    }

}
