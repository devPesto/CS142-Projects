/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Tiles
 *
 * Description: TileManager stores and handles Tile instances that are
 * displayed on graphical user interface (GUI). An empty ArrayList is initialized
 * and new Tiles are added, then drawn to that list when the program is initially
 * ran. TileManager also has added functionality that handles raising,
 * lowering and removing and shuffling tiles from the GUI.
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TileManager {
    private ArrayList<Tile> tiles;

    /**
     * Default constructor for the TileManager class
     */
    public TileManager() {
        this.tiles = new ArrayList<>();
    }

    /**
     * Adds a tile to {@link #tiles}
     *
     * @param tile the {@link Tile} instance to add
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    /**
     * Draws all {@link Tile} instances in {@link #tiles}
     *
     * @param graphics instance of {@link Graphics}
     */
    public void drawAll(Graphics graphics) {
        for (Tile tile : tiles) {
            tile.draw(graphics);
        }
    }

    /**
     * Raises the topmost {@link Tile} that occurs at the clicked
     * location by moving it to the end of {@link #tiles}
     *
     * @param x The x co-ordinate where the mouse click occurred
     * @param y The y co-ordinate where the mouse click occurred
     */
    public void raise(int x, int y) {
        Tile tile = removeCickedTile(x, y);
        if (tile != null) {
            tiles.add(tile);
        }
    }

    /**
     * Lowers the topmost {@link Tile} that occurs at the clicked
     * location by moving it to the beginning of {@link #tiles}
     *
     * @param x The x co-ordinate where the mouse click occurred
     * @param y The y co-ordinate where the mouse click occurred
     */
    public void lower(int x, int y) {
        Tile tile = removeCickedTile(x, y);
        if (tile != null) {
            tiles.add(0, tile);
        }
    }

    /**
     * Deletes the topmost {@link Tile} that occurs at the clicked
     * location by removing it from {@link #tiles}
     *
     * @param x The x co-ordinate where the mouse click occurred
     * @param y The y co-ordinate where the mouse click occurred
     */
    public void delete(int x, int y) {
        removeCickedTile(x, y);
    }

    /**
     * Deletes all {@link Tile} instances that occur on the mouse click
     * location
     *
     * @param x The x co-ordinate where the mouse click occurred
     * @param y The y co-ordinate where the mouse click occurred
     */
    public void deleteAll(int x, int y) {
        tiles.removeIf(tile -> isInside(tile, x, y));
    }

    /**
     * Shuffles the tile list and its positions (fitted in the frame)
     *
     * @param width  The maximum width where shapes can be moved
     * @param height The maximum height where shapes can be moved
     */
    public void shuffle(int width, int height) {
        Collections.shuffle(tiles);
        Random random = new Random();
        for (Tile tile : tiles) {
            int x = random.nextInt(width - tile.getWidth() + 1);
            int y = random.nextInt(height - tile.getHeight() + 1);
            tile.setX(x);
            tile.setY(y);
        }
    }

    /**
     * Helper function to determine if a point is within a rectangle
     *
     * @param tile The tile to check the co-ordinate point against
     * @param x    The x co-ordinate
     * @param y    The y co-ordinate
     * @return {@code true} if within, otherwise {@code false}
     */
    private boolean isInside(Tile tile, int x, int y) {
        int x2 = tile.getX() + tile.getWidth();
        int y2 = tile.getY() + tile.getHeight();
        return x >= tile.getX() && x <= x2 && y >=
                tile.getY() && y <= y2;
    }

    /**
     * Helper method that removes the topmost tile clicked from
     * the {@link #tiles} list.
     *
     * @param x x-coordinate of the clicked tile
     * @param y y-coordinate of the clicked tile
     * @return The tile that was removed, otherwise null if no tile was clicked
     * @see #raise(int, int)
     * @see #lower(int, int)
     * @see #delete(int, int)
     */
    private Tile removeCickedTile(int x, int y) {
        for (int i = tiles.size() - 1; i >= 0; i--) {
            Tile tile = tiles.get(i);
            if (isInside(tile, x, y)) {
                tiles.remove(i);
                return tile;
            }
        }
        return null;
    }
}
