/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Critters
 *
 * Aardvarks are displayed as a red '%' and always scratch in a fight and always eat.
 * The move in a zigzag pattern depending on the walkSouth variable
 */

import java.awt.*;

public class Aardvark extends Critter {
    private boolean walkSouth;
    private boolean northSouth;

    /**
     * Parameterized constructor for Aardvark
     *
     * @param walkSouth boolean that determines the direction of the zigzag
     */
    public Aardvark(boolean walkSouth) {
        this.walkSouth = walkSouth;
        this.northSouth = false;
    }

    /**
     * Overridden #eat() method
     *
     * @return true
     */
    @Override
    public boolean eat() {
        return true;
    }

    /**
     * Overridden #fight(String) method
     *
     * @return {@link Critter.Attack#SCRATCH}
     */
    @Override
    public Attack fight(String opponent) {
        return Attack.SCRATCH;
    }

    /**
     * Overridden #getColor method
     *
     * @return {@link Color#RED}
     */
    @Override
    public Color getColor() {
        return Color.RED;
    }

    /**
     * @return The next move alternating between East and South/North (depending on {@link #walkSouth})
     */
    @Override
    public Direction getMove() {
        if (!northSouth) {
            northSouth = true;
            return Direction.EAST;
        } else {
            northSouth = false;
            return walkSouth ? Direction.SOUTH : Direction.NORTH;
        }
    }

    /**
     * @return The "%" symbol
     */
    @Override
    public String toString() {
        return "%";
    }
}
