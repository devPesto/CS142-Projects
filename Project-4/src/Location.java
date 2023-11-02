/**
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Mountain Pass
 * <p>
 * A utility class used to assist in various calculations and comparisons regarding different elevation data
 */

import java.util.Objects;

public class Location implements Comparable<Location> {
    private final int x;
    private final int y;
    private final int elevation;

    /**
     * Parameterized constructor
     *
     * @param x         The x coordinate
     * @param y         The y coordinate
     * @param elevation The elevation at the point location (x, y)
     */
    public Location(int x, int y, int elevation) {
        this.x = x;
        this.y = y;
        this.elevation = elevation;
    }

    /**
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * @return the elevation of the location
     */
    public int getElevation() {
        return elevation;
    }

    /**
     * @param o the other location
     * @return The absolute height difference between this and the given location
     */
    public int getAbsDelta(Location o) {
        return Math.abs(compareTo(o));
    }

    /**
     * @param x the offset of the x location
     * @param y the offset of the y location
     * @return The offset location if it is within bounds
     */
    public Location offset(int x, int y, int[][] data) {
        int offX = this.x + x;
        int offY = this.y + y;
        return new Location(offX, offY, data[offY][offX]);
    }

    /**
     * @param o the object to be compared.
     * @return the difference in elevation between this and the given object
     */
    @Override
    public int compareTo(Location o) {
        return this.getElevation() - o.getElevation();
    }

    /**
     * @return The location in coordinate form
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Test for equality by comparing the x, y and elevation variables
     *
     * @param o the object to be compared
     * @return {@code true} if this object equals the given object, otherwise {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return getX() == location.getX() && getY() == location.getY() && getElevation() == location.getElevation();
    }

    /**
     * @return generated hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getElevation());
    }
}