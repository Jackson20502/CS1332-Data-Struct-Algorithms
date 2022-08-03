import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class LinearProbingHashMapStudentTest1 {

    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> directory;

    @Before
    public void setUp() {
        directory = new LinearProbingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {
        assertEquals(directory.getTable().length, 13);
        assertEquals(directory.size(), 0);
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor2() {
        directory = new LinearProbingHashMap<>(1);
        assertEquals(directory.getTable().length, 1);
        assertEquals(directory.size(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutException() {
        directory.put(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutException2() {
        directory.put(123, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutException3() {
        directory.put(null, "Exception");
    }

    @Test(timeout = TIMEOUT)
    public void testResizeInPut() {
        directory = new LinearProbingHashMap(2);
        directory.put(1, "A");
        assertEquals(directory.size(), 1);
        assertEquals(directory.getTable().length, 2);
        directory.put(2, "B");
        assertEquals(directory.size(), 2);
        assertEquals(directory.getTable().length, 5);
        directory.put(1, "C");
        directory.put(2, "D");
        directory.put(3, "E");
        assertEquals(directory.size(), 3);
        directory.put(4, "F");
        assertEquals(directory.size(), 4);
        assertEquals(directory.getTable().length, 11);
    }

    @Test(timeout = TIMEOUT)
    public void testPut2() {
        assertEquals(null, directory.put(10, "10"));
        assertEquals(1, directory.size());
    }

    @Test(timeout = TIMEOUT)
    public void testPutForRemoved() {
        directory = new LinearProbingHashMap(10);
        assertEquals(null, directory.put(1, "A"));
        assertEquals(null, directory.put(2, "B"));
        assertEquals(null, directory.put(11, "C"));
        assertEquals(null, directory.put(12, "D"));
        assertEquals(4, directory.size());
        assertEquals("B", directory.remove(2));
        assertEquals(true, directory.getTable()[2].isRemoved());
        assertEquals(3, directory.size());
        assertEquals(null, directory.put(2, "F"));
        assertEquals(false, directory.getTable()[2].isRemoved());
        assertEquals(4, directory.size());
    }
}