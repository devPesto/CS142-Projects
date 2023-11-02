/*
 * Name: Amir Seman
 * Instructor: Allison Obourn
 * Course: CS 142, Fall 2023
 * Project: SortedIntList
 *
 * Description: An SortedIntList stores an ordered list of integers.
 * It starts with a length of 0 and increases in size whenever an element is
 * added. When an element is added in a spot where another element already
 * is, the element currently there and all after it move over to make room.
 * Likewise, when an element is removed the list adjusts itself so that
 * there are no empty indexes.
 */


import java.util.*;
import java.util.stream.Collectors;

public class SortedIntListTest {
    private static final int MAX_INT_VALUE = 15;
    private static final int EXPECTED_SHUFFLE_ADDITION = 5;

    public static void main(String[] args) {
        test(25, false, 15);
        test(5, true, 23);
        test(1, false, 0);
    }

    public static void test(int initialCapacity, boolean unique, int k) {
        System.out.println("\nTesting SortedList(" + unique + ", " + initialCapacity + ") with " + k + " elements");
        SortedIntList l = new SortedIntList(unique, initialCapacity);
        fill(l, k);

        // Check if list follows non-descending order
        test("Ascending order test", testSorted(l));

        // Check if list has the same size
        test("Array size test", testSize(l, k));

        // Check if duplicates are allowed in both unique and non-unique lists
        if (l.getUnique()) {
            test("Duplicates in non-unique", testDuplicatesInNonUnique(l));
        } else {
            test("Duplicates in unique", testDuplicatesInUnique(l));
        }

        // Check min and max
        test("Min/Max", testMinMax(l));
    }

    private static void test(String title, boolean testPassed) {
        System.out.println("> " + title);
        System.out.println(testPassed ? "::\tPASSED" : "::\tFAILED");
    }

    /**
     * Fills the list with a k number of elements
     *
     * @param l instance of the list
     * @param k number of elements to add
     */
    private static void fill(SortedIntList l, int k) {
        Random r = new Random();
        Set<Integer> uniqueValues = new HashSet<>();

        while (uniqueValues.size() < k) {
            int next = r.nextInt(2 * MAX_INT_VALUE + 1) - MAX_INT_VALUE;

            // If the number is not in the list
            if (l.indexOf(next) < 0) {
                uniqueValues.add(next);
                l.add(next);
            }
        }
        System.out.println("List: " + l + "\n");
    }

    /**
     * Test case for the size of the list. Ensures that the size is incremented
     * when values are added
     *
     * @param l            instance of the list
     * @param expectedSize the expected size of the list
     * @return true if the size is equal, otherwise false
     */
    private static boolean testSize(SortedIntList l, int expectedSize) {
        return l.size() == expectedSize;
    }

    /**
     * Test case for the ordering of the list. Ensures list is sorted
     * a non-descending order.
     *
     * @param l instance of the list
     * @return true if order is valid
     */
    private static boolean testSorted(SortedIntList l) {
        if (l.size() == 1) {
            return true;
        }

        for (int i = 0; i < l.size() - 1; i++) {
            if (l.get(i) > l.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Test case for duplicate values. Ensures that duplicates are removed when the list
     *
     * @param l instance of the list
     * @return true if the list contains no duplicates.
     */
    private static boolean testDuplicatesInUnique(SortedIntList l) {
        // Create ArrayList and remove duplicates
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            list.add(l.get(i));
        }
        list = list.stream().sorted().distinct().collect(Collectors.toList());

        // Set our SortedIntList to unique
        l.setUnique(true);

        // Compare each corresponding index if values are equal
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) != list.get(i)) {
                System.out.println("Does not match at index" + i);
                System.out.println("SortedIntList (unique): " + l);
                System.out.println("Sorted ArrayList: " + list);
                return false;
            }
        }
        return true;
    }

    /**
     * Test case for duplicate values. Ensure that duplicates can be added when
     * the unique flag is disabled by comparing the size before and after adding duplicates
     *
     * @param l instance of the list
     * @return true if the size of the list is equal to the sum of the number of duplicates
     * added and the old size of the list
     */
    private static boolean testDuplicatesInNonUnique(SortedIntList l) {
        l.setUnique(false);

        int oldSize = l.size();
        for (int i = 0; i < EXPECTED_SHUFFLE_ADDITION; i++) {
            int n = new Random().nextInt(l.size());
            l.add(n);
        }

        return l.size() == (oldSize + EXPECTED_SHUFFLE_ADDITION);
    }

    /**
     * Test case for the min and max methods. Ensures that the min/max methods
     * are the same as the actual min/max (obtained through normal iteration)
     * @param l instance of the list
     * @return true if the min and max are equal to the calculated values, otherwise false
     */
    private static boolean testMinMax(SortedIntList l) {
        // One or no values
        if (l.size() <= 1) {
            return true;
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) < min) {
                min = l.get(i);
            }

            if (l.get(i) > max) {
                max = l.get(i);
            }
        }

        return max == l.max() && min == l.min();
    }
}