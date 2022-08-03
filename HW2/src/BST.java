import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
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
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
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
     * @param data the data to add
     * @return current node
     */
    private BSTNode<T> recursivelyAdd(BSTNode<T> current, T data) {
        BSTNode<T> node = null;
        if (current == null) {
            this.size++;
            node = new BSTNode<T>(data);
            return node;
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(recursivelyAdd(current.getLeft(), data));
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(recursivelyAdd(current.getRight(), data));
        } else {
            node = current;
            return node;
        }
        return current;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        BSTNode<T> removedData = new BSTNode<T>(data);
        if (data == null) {
            throw new IllegalArgumentException("The data should't be null!");
        } else {
            this.size--;
            root = recursivelyRemove(root, data, removedData);
        }
        return removedData.getData();
    }

    /**
     *
     * @param current the current node
     * @param data the data to remove
     * @param removedData the node to hold the removed data
     * @return the current node
     */
    private BSTNode<T> recursivelyRemove(BSTNode<T> current, T data, BSTNode<T> removedData) {
        if (current == null) {
            throw new NoSuchElementException("The data is not in the tree!");
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
                BSTNode<T> temp = new BSTNode<>(null);
                current.setLeft(predecessor(current.getLeft(), temp));
                current.setData(temp.getData());
            }
        }
        return current;
    }

    /**
     *
     * @param current the current node
     * @param temp the temp node
     * @return the current node
     */
    private BSTNode<T> predecessor(BSTNode<T> current, BSTNode<T> temp) {
        if (current.getRight() == null) {
            temp.setData(current.getData());
            return current.getLeft();
        } else {
            current.setRight(predecessor(current.getRight(), temp));
            return current;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
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
     * @param data the data to get
     * @return the data in the tree equal to the parameter
     */
    private T recursivelyGet(BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("The data is not in the tree!");
        } else if (current.getData().compareTo(data) == 0) {
            return current.getData();
        } else if (current.getData().compareTo(data) > 0) {
            return recursivelyGet(current.getLeft(), data);
        } else {
            return recursivelyGet(current.getRight(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
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
     * @param data the data to find
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean recursivelyFind(BSTNode<T> current, T data) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorder = new ArrayList<T>();
        preorderTraversal(root, preorder);
        return preorder;
    }

    /**
     *
     * @param current the current node
     * @param list a list hold the data by preorder traversal
     */
    private void preorderTraversal(BSTNode<T> current, List<T> list) {
        if (current != null) {
            list.add(current.getData());
            preorderTraversal(current.getLeft(), list);
            preorderTraversal(current.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorder = new ArrayList<T>();
        inorderTraversal(root, inorder);
        return inorder;
    }

    /**
     *
     * @param current the current node
     * @param list a list hold the data by inorder traversal
     */
    private void inorderTraversal(BSTNode<T> current, List<T> list) {
        if (current != null) {
            inorderTraversal(current.getLeft(), list);
            list.add(current.getData());
            inorderTraversal(current.getRight(), list);
        }
    }
    
    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorder = new ArrayList<T>();
        postorderTraversal(root, postorder);
        return postorder;
    }

    /**
    *
    * @param current the current node
    * @param list a list hold the data by postorder traversal
    */
    private void postorderTraversal(BSTNode<T> current, List<T> list) {
        if (current != null) {
            postorderTraversal(current.getLeft(), list);
            postorderTraversal(current.getRight(), list);
            list.add(current.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<T>();
        Queue<BSTNode<T>> queueNodes = new LinkedList<BSTNode<T>>();
        if (root != null) {
            queueNodes.add(root);
        }
        while (!queueNodes.isEmpty()) {
            BSTNode<T> current = queueNodes.remove();
            if (current == null) {
                return list;
            }
            if (current.getLeft() != null && current.getRight() != null) {
                queueNodes.add(current.getLeft());
                queueNodes.add(current.getRight());
            } else if (current.getLeft() != null) {
                queueNodes.add(current.getLeft());
            } else if (current.getRight() != null) {
                queueNodes.add(current.getRight());
            }
            list.add(current.getData());
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightTraversal(root);
    }

    /**
     *
     * @param current the current node
     * @return the height of the tree
     */
    private int heightTraversal(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return Math.max(heightTraversal(current.getLeft()), 
                    heightTraversal(current.getRight())) + 1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        this.size = 0;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
