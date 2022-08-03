import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Jiacheng Zhang
 * @version 1.0
 * @userid jzhang3283
 * @GTID 903743074
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index should be in range 0 to the size of the arraylist.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else if (this.size < backingArray.length) {
            if (index == size) {
                backingArray[index] = data;
            } else {
                for (int i = size; i > index; i--) {
                    backingArray[i] = backingArray[i - 1];
                }
                backingArray[index] = data;
            }
        } else {
            T[] backingArrayTemp = (T[]) new Object[backingArray.length];
            for (int i = 0; i < backingArray.length; i++) {
                backingArrayTemp[i] = backingArray[i];
            }
            this.backingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                backingArray[i] = backingArrayTemp[i];
            }
            addAtIndex(index, data);
        }
        this.size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else if (this.size < backingArray.length) {
            for (int i = size; i > 0; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[0] = data;
        } else {
            T[] backingArrayTemp = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArray.length; i++) {
                backingArrayTemp[i] = backingArray[i];
            }
            backingArray = backingArrayTemp;
            addToFront(data);
        }
        this.size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else if (this.size < backingArray.length) {
            backingArray[size] = data;
        } else {
            T[] backingArrayTemp = (T[]) new Object[backingArray.length];
            for (int i = 0; i < backingArray.length; i++) {
                backingArrayTemp[i] = backingArray[i];
            }
            this.backingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < backingArrayTemp.length; i++) {
                backingArray[i] = backingArrayTemp[i];
            }
            backingArray[size] = data;
        }
        this.size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T temp = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be in range 0 to the (size - 1) of the arraylist.");
        } else if (index == size - 1) {
            temp = backingArray[index];
            backingArray[index] = null;
        } else {
            temp = backingArray[index];
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
        }
        this.size--;
        return temp;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T temp = null;
        if (isEmpty()) {
            throw new NoSuchElementException("The arraylist is empty. There is nothing that can be removed.");
        } else {
            temp = backingArray[0];
            for (int i = 0; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
        }
        this.size--;
        return temp;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        T temp = null;
        if (isEmpty()) {
            throw new NoSuchElementException("The arraylist is empty. There is nothing that can be removed.");
        } else {
            temp = backingArray[size - 1];
            backingArray[size - 1] = null;
        }
        this.size--;
        return temp;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        T temp = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be in range 0 to the (size - 1) of the arraylist.");
        } else {
            temp = backingArray[index];
        }
        return temp;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        boolean empty;
        if (backingArray[0] == null) {
            empty = true;
        } else {
            empty = false;
        }
        return empty;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
