package de.skyslycer.skylib.util;

import org.bukkit.Color;

/**
 * Utility class for colors.
 */
public class ColorUtil {

    /**
     * Converts a color to a hex string.
     * @param color The color
     * @return The hex string
     */
    public static String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

}
