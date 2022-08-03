import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Jiacheng Zhang
 * @userid jzhang3283
 * @GTID 903743074
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should't be null!");
        } else {
            for (T t : data) {
                if (t == null) {
                    throw new IllegalArgumentException("At least one of the element in data is null!");
                } else {
                    add(t);
                }
            }
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should't be null!");
        } else {
            root = recursivelyAdd(root, data);
        }
    }

    /**
     *
     * @param current the current node
     * @param data the data to be added
     * @return node
     */
    private AVLNode<T> recursivelyAdd(AVLNode<T> current, T data) {
        AVLNode<T> node = null;
        if (current == null) {
            this.size++;
            node = new AVLNode<T>(data);
        } else {
            if (current.getData().compareTo(data) == 0) {
                node = current;
            } else if (current.getData().compareTo(data) > 0) {
                current.setLeft(recursivelyAdd(current.getLeft(), data));
            } else {
                current.setRight(recursivelyAdd(current.getRight(), data));
            }
            updating(current);
            node = rebalance(current);
        }
        return node;
    }

    /**
     *
     * @param current the current node
     */
    private void updating(AVLNode<T> current) {
        int leftH = 0;
        int rightH = 0;
        if ((current.getLeft() == null) && (current.getRight() == null)) {
            leftH = -1;
            rightH = -1;
        }
        if (current.getLeft() == null) {
            leftH = -1;
        } else {
            leftH = current.getLeft().getHeight();
        }
        if (current.getRight() == null) {
            rightH = -1;
        } else {
            rightH = current.getRight().getHeight();
        }
        current.setBalanceFactor(leftH - rightH);
        current.setHeight(Math.max(leftH, rightH) + 1);
    }

    /**
     * right-left or left-right
     * @param current the current node
     * @return current node
     */
    private AVLNode<T> rebalance(AVLNode<T> current) {
        if (current.getBalanceFactor() < -1) {
            if (current.getRight().getBalanceFactor() == 1) {
                current.setRight(rightRotate(current.getRight()));
            }
            current = leftRotate(current);
        } else if (current.getBalanceFactor() > 1) {
            if (current.getLeft().getBalanceFactor() == -1) {
                current.setLeft(leftRotate(current.getLeft()));
            }
            current = rightRotate(current);
        }
        return current;
    }

    /**
     *
     * @param a rotation node
     * @return b node
     */
    private AVLNode<T> leftRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        updating(a);
        updating(b);
        return b;
    }

    /**
     *
     * @param a rotation node
     * @return b node
     */
    private AVLNode<T> rightRotate(AVLNode<T> a) {
        AVLNode<T> b = a.getLeft();
        a.setLeft(b.getRight());
        b.setRight(a);
        updating(a);
        updating(b);
        return b;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        AVLNode<T> removedData = new AVLNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("The data should't be null!");
        } else {
            this.size--;
            root = recursivelyRemove(root, data, removedData);
            return removedData.getData();
        }
    }

    /**
     *
     * @param current the current node
     * @param data the data to remove from the tree
     * @param removedData the node holding the removed data 
     * @return node rebalance node
     */
    private AVLNode<T> recursivelyRemove(AVLNode<T> current, T data, AVLNode<T> removedData) {
        if (current == null) {
            throw new NoSuchElementException("The data is not found.");
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(recursivelyRemove(current.getLeft(), data, removedData));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(recursivelyRemove(current.getRight(), data, removedData));
        } else {
            removedData.setData(current.getData());
            if (current.getLeft() == null) {
                return current.getRight();
            } else if (current.getRight() == null) {
                return current.getLeft();
            } else {
                AVLNode<T> temp = new AVLNode<>(null);
                current.setRight(successor(current.getRight(), temp));
                current.setData(temp.getData());
            }
        }
        updating(current);
        return rebalance(current);
    }

    /**
     *
     * @param current the current node
     * @param temp temp node
     * @return rebalance node
     */
    private AVLNode<T> successor(AVLNode<T> current, AVLNode<T> temp) {
        AVLNode<T> node = null;
        if (current.getLeft() == null) {
            temp.setData(current.getData());
            current.setLeft(null);
            node = current.getRight();
        } else {
            current.setLeft(successor(current.getLeft(), temp));
            updating(current);
            node = rebalance(current);
        }
        return node;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should't be null!");
        } else {
            return recursivelyGet(root, data);
        }
    }

    /**
     *
     * @param current the current node
     * @param data the data to search for in the tree
     * @return the data should be removed
     */
    private T recursivelyGet(AVLNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("The data is not found!");
        } else if (current.getData().compareTo(data) == 0) {
            return current.getData();
        } else if (current.getData().compareTo(data) > 0) {
            return recursivelyGet(current.getLeft(), data);
        } else {
            return recursivelyGet(current.getRight(), data);
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data should't be null!");
        } else {
            return recursivelyFind(root, data);
        }
    }

    /**
     *
     * @param current the current node
     * @param data the data to search for in the tree
     * @return whether or not the parameter is contained within the tree
     */
    private boolean recursivelyFind(AVLNode<T> current, T data) {
        if (current == null) {
            return false;
        } else if (current.getData().compareTo(data) == 0) {
            return true;
        } else if (current.getData().compareTo(data) > 0) {
            return recursivelyFind(current.getLeft(), data);
        } else {
            return recursivelyFind(current.getRight(), data);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        this.size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightTraversal(root);
    }

    /**
     *
     * @param current the current node
     * @return the height of the root of the tree
     */
    private int heightTraversal(AVLNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return Math.max(heightTraversal(current.getLeft()), 
                    heightTraversal(current.getRight())) + 1;
        }
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}