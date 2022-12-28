package de.skyslycer.skylib.util;

import java.util.stream.IntStream;

/**
 * Utility class for math.
 */
public class MathUtil {

    private static final double[] SIN_TABLE = IntStream.rangeClosed(0, 360).mapToDouble(i -> Math.sin(Math.toRadians(i))).toArray();

    private static final double[] COS_TABLE = IntStream.rangeClosed(0, 360).mapToDouble(i -> Math.cos(Math.toRadians(i))).toArray();

    /**
     * Get the corresponding sin value from an angle.
     * @param angle The angle for the sin
     * @return The sin of the angle
     */
    public static double sin(int angle) {
        return SIN_TABLE[angle];
    }

    /**
     * Get the corresponding cos value from an angle.
     * @param angle The angle for the cos
     * @return The cos of the angle
     */
    public static double cos(int angle) {
        return COS_TABLE[angle];
    }

}
