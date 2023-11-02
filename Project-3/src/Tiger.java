/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Critters
 *
 * Tigers share the similarity with cats in how they are displayed as either
 * a '^', '>', 'V' or '<' depending on its last move. They become hungry when they
 * are created and when they fight but become full when they eat. They pounce in
 * fights, unless the opponent is an aardvark in which they roar. The move in a
 * clockwise square moving three times in each direction.
 */

import java.awt.*;

public class Tiger extends Cat {
    private boolean hungry;

    /**
     * Parameterized constructor for Tiger
     */
    public Tiger() {
        this.hungry = true;
    }

    /**
     * Overridden #eat() method. Set the hunger to true if previously false
     *
     * @return true if the tiger was hungry, false otherwise.
     */
    @Override
    public boolean eat() {
        if (hungry) {
            this.hungry = false;
            return true;
        }
        return false;
    }

    /**
     * Overridden #fight(String) method
     *
     * @return {@link Critter.Attack#ROAR} at aardvarks, otherwise it {@link Critter.Attack#POUNCE}
     */
    @Override
    public Attack fight(String opponent) {
        this.hungry = true;
        return super.fight(opponent);
    }

    /**
     * Overridden #getColor method
     *
     * @return {@link Color#BLACK}
     */
    @Override
    public Color getColor() {
        return Color.BLACK;
    }
}
