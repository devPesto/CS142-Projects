/**
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: Mountain Pass
 * <p>
 * This program reads in a csv file containing elevation data from a survey of land. This data is then read into a 2D
 * array and converted into a grid of locations the highest and lowest points are gathered. Then a path is generated
 * starting from a random y coordinate from the west-most side, to the east side ensuring that a path with the least
 * elevation change is computed and printed. The total and steepest changes are recorded as well and printed. A graphic showing the
 * collected information is also generated and presented.
 */

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MountainPass {
    private static final int[][] EMPTY = new int[0][0];
    private static final Random rnd = new Random();
    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private static final int GRID = 1;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("File name? ");

        File file = new File(console.next());
        int[][] data = readData(file);

        // Get the peaks
        Location[] peaks = getPeaks(data);
        System.out.println("Mountain Peak: " + peaks[0]);
        System.out.println("Lowest Point: " + peaks[1]);

        // Output the elevation data
        List<Location> path = getElevationPath(data);
        int steepest = 0, total = 0;
        Location curr;
        for (int i = 1; i < path.size(); i++) {
            Location prev = path.get(i - 1);
            curr = path.get(i);

            int delta = prev.getAbsDelta(curr);
            total += delta;
            steepest = Math.max(steepest, delta);
        }
        System.out.println("Total elevation: " + total);
        System.out.println("Steepest: " + steepest);

        // Draw the graphic
        drawGraphic(path, peaks, data);
    }

    /**
     * Reads the data from the file, line by line, adding it to a 2D array
     *
     * @param file The CSV file to read the data from
     * @return A new {@link int[][]} containing the elevation data
     */
    private static int[][] readData(File file) {
        try {
            Scanner sc = new Scanner(file);
            int[] grid = parseIntArray(sc.nextLine().split(","));
            int[][] data = new int[grid[1]][grid[0]];

            int i = 0;
            while (sc.hasNextLine()) {
                int[] row = parseIntArray(sc.nextLine().split(","));
                System.arraycopy(row, 0, data[i], 0, row.length);
                i++;
            }
            return data;
        } catch (FileNotFoundException e) {
            System.out.printf("File '%s' could not be found%n", file.getName());
            System.exit(0);
        }
        return EMPTY;
    }

    /**
     * Determines the coordinates of highest and lowest peaks of the mountain data
     *
     * @param data The 2D array representing the mountain data from the csv file
     * @return an array of locations that contains the high and low peak respectively
     */
    private static Location[] getPeaks(int[][] data) {
        Location max = new Location(0, 0, data[0][0]);
        Location min = new Location(0, 0, data[0][0]);

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                Location loc = new Location(j, i, data[i][j]);
                if (loc.compareTo(max) > 0)
                    max = loc;

                if (loc.compareTo(min) < 0)
                    min = loc;
            }
        }

        return new Location[]{max, min};
    }

    /**
     * Calculates and prints the elevation path using a greedy fill algorithm with a bias towards moving forward in the
     * case where moving up or down has equal cost.
     *
     * @param data The 2D array representing the mountain data from the csv file
     * @return A list of {@link Location} representing each walk on the elevation path
     */
    private static List<Location> getElevationPath(int[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        ArrayList<Location> path = new ArrayList<>();

        System.out.print("Elevation Path: ");
        int rndRow = rnd.nextInt(rows);
        path.add(new Location(0, rndRow, data[rndRow][0]));

        while (path.size() < cols) {
            Location last = path.get(path.size() - 1);

            // Calculate the elevation differences with the three possible next steps
            Location fwd = last.offset(1, 0, data);
            Location fwdUp = (last.getY() < rows - 1) ? last.offset(1, 1, data) : null;
            Location fwdDown = (last.getY() > 0) ? last.offset(1, -1, data) : null;

            int fwdDelta = last.getAbsDelta(fwd);
            int upDelta = (fwdUp != null) ? last.getAbsDelta(fwdUp) : Integer.MAX_VALUE;
            int downDelta = (fwdDown != null) ? last.getAbsDelta(fwdDown) : Integer.MAX_VALUE;

            // Choose the direction based on the specified criteria
            Location nextLocation;
            if (fwdDelta <= upDelta && fwdDelta <= downDelta) {
                nextLocation = fwd;
            } else if (upDelta < fwdDelta && upDelta <= downDelta) {
                nextLocation = fwdUp;
            } else if (downDelta < fwdDelta) {
                nextLocation = fwdDown;
            } else {
                // If there is a tie between up and down, randomly choose
                nextLocation = rnd.nextBoolean() ? fwdUp : fwdDown;
            }
            path.add(nextLocation);
            System.out.print(nextLocation + (path.size() < cols ? ", " : " \n"));
        }
        return path;
    }

    /**
     * Draws a graphic showing the elevation changes in grayscale. The elevation path is shown in green and the peak
     * represented as a red dot.
     *
     * @param path  The elevation path generated from {@link #getElevationPath(int[][])}
     * @param peaks The high and low peaks from {@link #getPeaks(int[][])}
     * @param data  The 2D array representing the mountain data from the csv file
     */
    public static void drawGraphic(List<Location> path, Location[] peaks, int[][] data) {
        Location min = peaks[1];
        Location max = peaks[0];
        DrawingPanel panel = new DrawingPanel(WIDTH, HEIGHT);
        Graphics g = panel.getGraphics();

        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                Location loc = new Location(i, j, data[j][i]);
                Color color;
                if (loc.equals(max)) {
                    color = Color.RED;
                } else if (path.contains(loc)) {
                    color = Color.GREEN;
                } else {
                    int c = 255 - (255 * loc.compareTo(min) / max.compareTo(min));
                    color = new Color(c, c, c);
                }
                g.setColor(color);
                g.fillRect(i * GRID, HEIGHT - (j * GRID), GRID, GRID);
            }
        }
    }

    /**
     * Helper method that converts a {@link String[]} to an {@link int[]}
     *
     * @param array The {@link String[]}
     * @return A new {@link int[]}
     */
    private static int[] parseIntArray(String[] array) {
        int n = array.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = Integer.parseInt(array[i]);
        }
        return result;
    }
}