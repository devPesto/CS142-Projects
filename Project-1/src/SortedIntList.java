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

import java.util.Arrays;
import java.util.NoSuchElementException;

public class SortedIntList {
    // a constant storing our initial size
    public static final int DEFAULT_CAPACITY = 10;
    private int[] elementData;
    private int size;
    private boolean unique;

    /**
     * Initializes a {@link SortedIntList} with the given capacity and unique
     * flag that manages duplicate values (true means no duplicates, false
     * means duplicates are allowed).
     *
     * @param unique   The flag that determines whether or not duplicates are allowed
     * @param capacity The initial capacity of the internal array
     * @throws IllegalArgumentException if the capacity is negative
     */
    public SortedIntList(boolean unique, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive: " + capacity);
        }
        elementData = new int[capacity];
        size = 0;
        this.unique = unique;
    }

    /**
     * Initializes a {@link SortedIntList} with the given capacity where
     * unique is set to false (duplicates elements are allowed)
     *
     * @param capacity The initial capacity of the internal array
     * @throws IllegalArgumentException if the capacity is negative
     */
    public SortedIntList(int capacity) {
        this(false, capacity);
    }

    /**
     * Initializes a {@link SortedIntList} with the given unique flag where
     * the capacity is set to the {@link #DEFAULT_CAPACITY}
     *
     * @param unique The flag that determines whether duplicates are allowed
     */
    public SortedIntList(boolean unique) {
        this(unique, DEFAULT_CAPACITY);
    }

    /**
     * Initializes a {@link SortedIntList} where duplicates elements are
     * allowed and the capacity is set to the {@link #DEFAULT_CAPACITY}
     */
    public SortedIntList() {
        this(false, DEFAULT_CAPACITY);
    }

    /**
     * @return the index of the value using binary search
     */
    public int indexOf(int value) {
        return Arrays.binarySearch(elementData, 0, size, value);
    }

    /**
     * Inserts the given value into the list where the order is maintained.
     * If the value exists and duplicates are not allowed, no change occurs
     *
     * @param value The new value to insert
     */
    public void add(int value) {
        int index = indexOf(value);
        if (index < 0 || !unique) {
            int n = Math.max((-(index + 1)), index);
            add(n, value);
        }
    }

    /**
     * Inserts the given value into the list at the given index.
     * Pre: 0 <= index <= size, throws IndexOutOfBoundsException otherwise
     *
     * @deprecated
     */
    private void add(int index, int value) {
        checkIndex(index, 0, size); // ok to add at size (end of list)
        ensureCapacity();
        for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }
        elementData[index] = value;
        size++;
    }

    /**
     * Removes the value at the given index.
     *
     * @param index The index of the element to remove
     * @throws IndexOutOfBoundsException If index doesn't meet the condition: 0 <= index < size
     */
    public void remove(int index) {
        checkIndex(index, 0, size - 1);
        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[index + 1];
        }
        size--;
    }

    // Returns the value at the given index.
    // Pre: 0 <= index < size, throws IndexOutOfBoundsException otherwise
    public int get(int index) {
        checkIndex(index, 0, size);
        return elementData[index];
    }

    // returns the number of elements in the list
    public int size() {
        return size;
    }

    // returns true if the list is empty and false otherwise
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return the smallest value in the list
     */
    public int min() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list contains no values");
        }
        return elementData[0];
    }

    /**
     * @return the largest value in the list
     */
    public int max() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list contains no values");
        }
        return elementData[size - 1];
    }

    /**
     * Accessor method for {@link #unique}
     *
     * @return whether the list allows duplicate elements or not
     */
    public boolean getUnique() {
        return unique;
    }

    /**
     * Mutator method for {@link #unique}. If {@link #unique} is set
     * to {@code true} then duplicate values are removed.
     */
    public void setUnique(boolean unique) {
        // Only remove duplicates if unique is changed from false -> true
        if (!getUnique() && unique) {
            removeDuplicates();
        }

        this.unique = unique;
    }

    // Returns a String representation of the list consisting of the elements
    // in order, separated by commas and enclosed in square brackets.
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            String output = "[" + elementData[0];
            for (int i = 1; i < size; i++) {
                output += ", " + elementData[i];
            }
            return output + "]";
        }
    }

    // Increases the capacity if needed to ensure that it can hold at
    // least the number of elements specified.
    private void ensureCapacity() {
        // double in size until large enough
        if (size == elementData.length) {
            elementData = Arrays.copyOf(elementData, 2 * size);
        }
    }

    // If the given index is outside the given bounds, throws an
    // IndexOutOfBoundsException.
    private void checkIndex(int index, int min, int max) {
        if (index < min || index > max) {
            throw new IndexOutOfBoundsException("index: " + index);
        }
    }

    /**
     * Helper method that removes duplicate elements
     * from the list
     */
    private void removeDuplicates() {
        if (size > 1) {
            int index = 0;
            for (int i = 1; i < size; i++) {
                if (elementData[i] != elementData[i - 1]) {
                    index++;
                    elementData[index] = elementData[i];
                }
            }
            size = index + 1;
        }
    }
}