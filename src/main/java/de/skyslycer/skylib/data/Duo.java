package de.skyslycer.skylib.data;

/**
 * A mapper for two values.
 * @param <F> The first value
 * @param <S> The second value
 */
public class Duo<F, S> {

    private final F first;
    private final S second;

    /**
     * Create a new duo.
     * @param first The first value
     * @param second The second value
     */
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
     * Build a duo.
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
