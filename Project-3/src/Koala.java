/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Critters
 *
 * Koalas are displayed as the value of their hunger, otherwise zero. Their
 * color changes based on if they are still hungry, otherwise white. In a fight,
 * they would always scratch if hungry, otherwise pounce. They move in a random
 */

import java.awt.*;
import java.util.Random;

public class Koala extends Critter {
    private Random rand;
    private int hunger;
    private int dirCount;
    private Direction lastDirection;

    /**
     * Parameterized constructor for Koala
     *
     * @param hunger the number of times the koala can eat
     */
    public Koala(int hunger) {
        this.hunger = hunger;
        this.rand = new Random();
        this.dirCount = 0;
    }

    /**
     * Overridden #eat() method
     *
     * @return true if {@link #hunger} is greater than 0, otherwise false
     */
    @Override
    public boolean eat() {
        this.hunger--;
        return hunger >= 0;
    }

    /**
     * Overridden #fight(String) method
     *
     * @return {@link Critter.Attack#SCRATCH} if still hungry, otherwise {@link Attack#POUNCE}
     */
    @Override
    public Attack fight(String opponent) {
        return wouldEat() ? Attack.SCRATCH : Attack.POUNCE;
    }

    /**
     * Overridden #getColor method
     *
     * @return {@link Color#GRAY} if still hungry, otherwise {@link Color#WHITE}
     */
    @Override
    public Color getColor() {
        return wouldEat() ? Color.GRAY : Color.WHITE;
    }

    /**
     * @return the {@link #lastDirection} 5 times (while incrementing {@link #dirCount})
     * before selecting a new random direction and resetting the count
     */
    @Override
    public Direction getMove() {
        Direction[] dir = Direction.values();
        if (dirCount < 4 && lastDirection != null) {
            dirCount++;
        } else {
            int index = rand.nextInt(dir.length - 1); // Exclude center
            dirCount = 0;
            lastDirection = dir[index];
        }
        return lastDirection;
    }

    /**
     * @return The hunger count as a symbol, otherwise 0
     */
    @Override
    public String toString() {
        return String.valueOf(Math.max(hunger, 0));
    }

    /**
     * @return true if the koala would eat, otherwise false. Used as a helper method
     * without affecting the value of {@link #hunger}
     */
    private boolean wouldEat() {
        return hunger - 1 >= 0;
    }
}
