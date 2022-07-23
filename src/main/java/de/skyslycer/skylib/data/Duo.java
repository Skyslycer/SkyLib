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

package de.skyslycer.skylib.data;

public class Duo<F, S> {

    private final F first;
    private final S second;

    public Duo(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Get the first value.
     * @return The first value
     */
    public F first() {
        return first;
    }

    /**
     * Get the second value.
     * @return The second value
     */
    public S second() {
        return second;
    }

    /**
     * Build a dou.
     * @param first First value
     * @param second Second value
     * @param <F> First value type
     * @param <S> Second value type
     * @return Built dou
     */
    public static <F, S> Duo<F, S> build(F first, S second) {
        return new Duo<>(first, second);
    }

}
