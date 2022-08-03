import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index should be in range 0 to the size of the DoublyLinkedList.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else {
            if (index == 0) {
                addToFront(data);
            } else if (index == size) {
                addToBack(data);
            } else {
                DoublyLinkedListNode<T> addedNode = new DoublyLinkedListNode<T>(data);
                if (index > 0 && index < size / 2) {
                    DoublyLinkedListNode<T> currentNode = head;
                    for (int i = 0; i < index; i++) {
                        currentNode = currentNode.getNext();
                    }
                    addedNode.setPrevious(currentNode.getPrevious());
                    addedNode.setNext(currentNode);
                    currentNode.getPrevious().setNext(addedNode);
                    currentNode.setPrevious(addedNode);
                }
                if (index >= size / 2 && index < size) {
                    DoublyLinkedListNode<T> currentNode = tail;
                    for (int i = size; i > index + 1; i--) {
                        currentNode = currentNode.getPrevious();
                    }
                    addedNode.setPrevious(currentNode.getPrevious());
                    addedNode.setNext(currentNode);
                    currentNode.getPrevious().setNext(addedNode);
                    currentNode.setPrevious(addedNode);
                }
                this.size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else {
            DoublyLinkedListNode<T> addedNode = new DoublyLinkedListNode<T>(data);
            if (head == null || tail == null) {
                head = addedNode;
                tail = addedNode;
            } else {
                addedNode.setPrevious(null);
                addedNode.setNext(head);
                head.setPrevious(addedNode);
                head = addedNode;
            }
        }
        this.size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else {
            DoublyLinkedListNode<T> addedNode = new DoublyLinkedListNode<T>(data);
            if (head == null || tail == null) {
                head = addedNode;
                tail = addedNode;
            } else {
                addedNode.setPrevious(tail);
                addedNode.setNext(null);
                tail.setNext(addedNode);
                tail = addedNode;
            }
        }
        this.size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        DoublyLinkedListNode<T> remove = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be "
                    + "in range 0 to the (size - 1) of the DoublyLinkedList.");
        } else {
            if (index == 0) {
                removeFromFront();
            } else if (index == size - 1) {
                removeFromBack();
            } else {
                if (index > 0 && index < (size - 1) / 2) {
                    DoublyLinkedListNode<T> currentNode = head;
                    for (int i = 0; i < index; i++) {
                        currentNode = currentNode.getNext();
                    }
                    remove = currentNode;
                    currentNode.getPrevious().setNext(currentNode.getNext());
                    currentNode.getNext().setPrevious(currentNode.getPrevious());
                }
                if (index >= (size - 1) / 2 && index < size - 1) {
                    DoublyLinkedListNode<T> currentNode = tail;
                    for (int i = size - 1; i > index; i--) {
                        currentNode = currentNode.getPrevious();
                    }
                    remove = currentNode;
                    currentNode.getPrevious().setNext(currentNode.getNext());
                    currentNode.getNext().setPrevious(currentNode.getPrevious());
                }
                this.size--;
            }
        }
        return remove.getData();
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        DoublyLinkedListNode<T> remove = null;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        } else if (size == 1) {
            remove = head;
            head = null;
            tail = null;
        } else {
            remove = head;
            head.getNext().setPrevious(null);
            head = head.getNext();
        }
        this.size--;
        return remove.getData();
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
        DoublyLinkedListNode<T> remove = null;
        if (size == 0) {
            throw new NoSuchElementException("The list is empty!");
        } else if (size == 1) {
            remove = tail;
            head = null;
            tail = null;
        } else {
            remove = tail;
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        this.size--;
        return remove.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        DoublyLinkedListNode<T> getNode = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index should be "
                    + "in range 0 to the (size - 1) of the DoublyLinkedList.");
        } else if (index == 0) {
            getNode = head;
        } else if (index == size - 1) {
            getNode = tail;
        } else {
            if (index > 0 && index < (size - 1) / 2) {
                DoublyLinkedListNode<T> currentNode = head;
                for (int i = 0; i < index; i++) {
                    currentNode = currentNode.getNext();
                }
                getNode = currentNode;
            }
            if (index >= (size - 1) / 2 && index < size - 1) {
                DoublyLinkedListNode<T> currentNode = tail;
                for (int i = size - 1; i > index; i--) {
                    currentNode = currentNode.getPrevious();
                }
                getNode = currentNode;
            }
        }
        return getNode.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        this.size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        DoublyLinkedListNode<T> remove = null;
        if (data == null) {
            throw new IllegalArgumentException("The data should not be null!");
        } else if (tail.getData() == data) {
            removeFromBack();
        } else {
            DoublyLinkedListNode<T> currentNode = tail.getPrevious();
            int count = 0;
            for (int i = size - 1; i > 0; i--) {
                count++;
                if (currentNode.getData() == data) {
                    remove = currentNode;
                    if (count < size - 1) {
                        currentNode.getPrevious().setNext(currentNode.getNext());
                        currentNode.getNext().setPrevious(currentNode.getPrevious());
                    } else {
                        removeFromFront();
                    }
                    break;
                }
                currentNode = currentNode.getPrevious();
            }
        }
        if (remove == null) {
            throw new NoSuchElementException("Data is not found in the DoublyLinkedList.");
        }
        this.size--;
        return remove.getData();
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] doublyLinkedListArray = (T[]) new Object[size];
        DoublyLinkedListNode<T> currentNode = head;
        for (int i = 0; i < size; i++) {
            doublyLinkedListArray[i] = currentNode.getData();
            currentNode = currentNode.getNext();
        }
        return doublyLinkedListArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
