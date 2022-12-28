package de.skyslycer.skylib.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for enums.
 */
public class EnumUtil {

    /**
     * Get all different enum values that match the ones in the list.
     * @param raw The string values
     * @param clazz The enum
     * @param <T> The enum
     * @return The mapped list
     */
    public static <T extends Enum<T>> List<T> getAllPossibilities(List<String> raw, Class<T> clazz) {
        List<T> newList = new ArrayList<>();
        raw.forEach(it -> {
            try {
                newList.add(T.valueOf(clazz, it));
            } catch (IllegalArgumentException ignored) {
            }
        });
        return newList;
    }

}
