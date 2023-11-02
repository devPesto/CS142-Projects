/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Critters
 *
 * Salmon are unique in that they try to run away from Tigers and Cat by
 * attempting to move either East or West, otherwise they will randomly move north
 * or south. They never eat and will only forfeit if they fight tigers or cats,
 * otherwise they scratch. They are pink in color and change to orange if they are
 * near a cat or tiger and are displayed as an '#'
 */

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Salmon extends Critter {
    private static final List<String> SYMBOLS = List.of("<", "^", ">", "V");
    private Random rand;

    /**
     * Default constructor of salmon
     */
    public Salmon() {
        this.rand = new Random();
    }

    /**
     * Overridden #fight(String) method
     *
     * @return {@link Critter.Attack#SCRATCH} unless against a Tiger or Cat in which they will
     * {@link Critter.Attack#FORFEIT}
     */
    @Override
    public Attack fight(String opponent) {
        return !isCat(opponent) ? Attack.SCRATCH : Attack.FORFEIT;
    }

    /**
     * @return {@link Critter.Direction#EAST} or {@link Critter.Direction#WEST} if
     * a cat or tiger is nearby, otherwise it will move randomly
     * {@link Critter.Direction#NORTH} or {@link Critter.Direction#SOUTH}.
     */
    @Override
    public Direction getMove() {
        String east = getNeighbor(Direction.EAST);
        String west = getNeighbor(Direction.WEST);
        if (isCatNearby()) {
            if (isCat(east) && !isCat(west)) {
                return Direction.WEST;
            } else if (isCat(west) && !isCat(east)) {
                return Direction.EAST;
            } else {
                return Direction.CENTER;
            }
        }
        return rand.nextBoolean() ? Direction.NORTH : Direction.SOUTH;
    }

    /**
     * Overridden #getColor method
     *
     * @return {@link Color#PINK} if no cats are nearby, otherwise they
     * become {@link Color#ORANGE}
     */
    @Override
    public Color getColor() {
        return !isCatNearby() ? Color.PINK : Color.ORANGE;
    }

    /**
     * @return true if a cat or tiger nearby, otherwise false
     */
    private boolean isCatNearby() {
        Direction[] dir = Direction.values();
        for (int i = 0; i < dir.length - 1; i++) {
            String neighbour = getNeighbor(dir[i]);
            if (isCat(neighbour)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param opponent the opponent or nearby opponent
     * @return true if the opponent is a cat or tiger, otherwise false
     */
    private boolean isCat(String opponent) {
        return SYMBOLS.contains(opponent);
    }

    /**
     * @return #
     */
    @Override
    public String toString() {
        return "#";
    }
}
