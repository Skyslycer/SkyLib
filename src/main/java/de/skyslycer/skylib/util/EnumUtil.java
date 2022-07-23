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

import java.util.ArrayList;
import java.util.List;

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
