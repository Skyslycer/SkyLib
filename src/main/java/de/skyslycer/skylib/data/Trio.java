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

/**
 * A data class for three values.
 * @param <F> The first value
 * @param <S> The second value
 * @param <T> The third value
 */
public class Trio<F, S, T> {

    private final F first;
    private final S second;
    private final T third;

    /**
     * Create a new trio.
     * @param first The first value
     * @param second The second value
     * @param third The third value
     */
    public Trio(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
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
     * Get the third value.
     * @return The third value
     */
    public T third() {
        return third;
    }

    /**
     * Build a trio.
     * @param first First value
     * @param second Second value
     * @param third Third value
     * @param <F> First value type
     * @param <S> Second value type
     * @param <T> Third value type
     * @return Built trio
     */
    public static <F, S, T> Trio<F, S, T> build(F first, S second, T third) {
        return new Trio<>(first, second, third);
    }

}
