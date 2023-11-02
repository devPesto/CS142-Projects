/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Critters
 *
 * Cats are displayed as a blue '^', '>', 'V' or '<', never eats, and
 * only roars at aardvarks, otherwise it pounces. The move in a clockwise
 * square moving three times in each direction.
 */

import java.awt.*;

public class Cat extends Critter {
    private static final Direction[] ORDERED_DIR = new Direction[]{
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    };
    private int direction;

    /**
     * Parameterized constructor for Cat
     */
    public Cat() {
        this.direction = 0;
    }

    /**
     * Overridden #eat() method
     *
     * @return false
     */
    @Override
    public boolean eat() {
        return false;
    }

    /**
     * Overridden #fight(String) method
     *
     * @return {@link Critter.Attack#ROAR} at aardvarks, otherwise it {@link Critter.Attack#POUNCE}
     */
    @Override
    public Attack fight(String opponent) {
        return opponent.equals("%") ? Attack.ROAR : Attack.POUNCE;
    }

    /**
     * Overridden #getColor method
     *
     * @return {@link Color#BLUE}
     */
    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    /**
     * @return The direction is determined by taking the modulus of, the direction
     * divided by 3, and 4 as an index for #ORDERED_DIR
     */
    @Override
    public Direction getMove() {
        int index = direction / 3 % 4;
        direction++;
        return ORDERED_DIR[index];
    }

    /**
     * @return The '^', '>', 'V' or '<' symbol based on the direction
     */
    @Override
    public String toString() {
        return switch ((direction - 1) / 3 % 4) {
            case 0 -> "^";
            case 1 -> ">";
            case 2 -> "V";
            case 3 -> "<";
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}
