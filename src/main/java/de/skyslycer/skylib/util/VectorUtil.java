package de.skyslycer.skylib.util;

import com.github.retrooper.packetevents.util.Vector3d;
import org.bukkit.Location;

/**
 * Utility class for vectors.
 */
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
