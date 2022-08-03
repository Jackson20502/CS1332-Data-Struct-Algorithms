import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayStack.
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
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayStack.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the data to the top of the stack.
     *
     * Must be O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
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
     * Removes and returns the data from the top of the stack.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        T temp = null;
        if (backingArray[0] == null) {
            throw new NoSuchElementException("The stack is empty!");
        } else {
            temp = backingArray[size - 1];
            backingArray[size - 1] = null;
        }
        this.size--;
        return temp;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     *
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException("The stack is empty!");
        }
        return backingArray[size - 1];
    }

    /**
     * Returns the backing array of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}