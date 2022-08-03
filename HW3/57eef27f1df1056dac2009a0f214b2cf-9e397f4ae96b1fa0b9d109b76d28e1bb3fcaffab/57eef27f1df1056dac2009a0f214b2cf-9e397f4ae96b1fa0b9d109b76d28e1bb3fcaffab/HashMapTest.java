import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HashMapTest {

    private static final int TIMEOUT = 200;
    private LinearProbingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new LinearProbingHashMap<>();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullKey() {
        map.put(null, "A");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void putNullValue() {
        map.put(1, null);
    }

    @Test(timeout = TIMEOUT)
    public void putRemovedKey() {
        map.put(1, "A");
        map.remove(1);
        map.put(1, "B");
        assertEquals(1, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "B");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putProbingKey() {
        map.put(1, "A");
        map.put(14, "B");
        assertEquals(2, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(14, "B");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putLoopingKey() {
        //cracked test
        map.put(0, "0");
        map.put(1, "1");
        map.put(12, "12");
        map.put(25, "25");
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "0");
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[2] = new LinearProbingMapEntry<>(25, "25");
        expected[12] = new LinearProbingMapEntry<>(12, "12");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void putOutOfOrder() {
        map.put(8, "8");
        map.put(10, "10");
        map.put(21, "21");
        map.put(9, "9");
        map.put(11, "11");
        map.put(34, "34");
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(34, "34");
        expected[8] = new LinearProbingMapEntry<>(8, "8");
        expected[9] = new LinearProbingMapEntry<>(21, "21");
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(9, "9");
        expected[12] = new LinearProbingMapEntry<>(11, "11");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void removeNullKey() {
        map.put(1, "A");
        map.put(14, "B");
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeSameHash() {
        map.put(1, "A");
        map.put(2, "B");
        map.remove(14);
        assertEquals(2, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "A");
        expected[2] = new LinearProbingMapEntry<>(2, "B");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeFlaggedKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.remove(2);
        map.remove(2);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void removeNonexistentKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.remove(3);
    }

    @Test(timeout = TIMEOUT)
    public void removeOverRemoved() {
        //cracked test
        map.put(1, "1");
        map.put(14, "14");
        map.put(27, "27");
        map.remove(14);
        map.remove(27);
        assertEquals(1, map.size());
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[2] = new LinearProbingMapEntry<>(14, "14");
        expected[3] = new LinearProbingMapEntry<>(27, "27");
        expected[2].setRemoved(true);
        expected[3].setRemoved(true);
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void removeOverLoop() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        map.remove(23);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<>(0, "0");
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[2] = new LinearProbingMapEntry<>(23, "23");
        expected[2].setRemoved(true);
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(11, "11");
        expected[12] = new LinearProbingMapEntry<>(12, "12");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void getNullKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getNonexistentKey() {
        map.put(1, "A");
        map.put(2, "B");
        map.get(3);
    }

    @Test(timeout = TIMEOUT)
    public void getOverRemoved() {
        map.put(1, "1");
        map.put(14, "14");
        map.put(27, "27");
        map.remove(14);
        assertEquals("27", map.get(27));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void getRemoved() {
        map.put(1, "1");
        map.put(14, "14");
        map.put(27, "27");
        map.remove(14);
        map.get(14);
    }

    @Test(timeout = TIMEOUT)
    public void getOverLoop() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        assertEquals("23", map.get(23));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void containsKeyNull() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyLooped() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        assertTrue(map.containsKey(23));
    }

    @Test(timeout = TIMEOUT)
    public void containsKeyIterateOverRemoved() {
        map.put(12, "12");
        map.put(11, "11");
        map.put(10, "10");
        map.put(1, "1");
        map.put(0, "0");
        map.put(23, "23");
        map.remove(0);
        map.remove(1);
        map.remove(12);
        assertTrue(map.containsKey(23));
    }

    @Test(timeout = TIMEOUT)
    public void doubleResize() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[(LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1) * 2 + 1];
        for (int i = 0; i < 20; i++) {
            map.put(i, i + "");
            expected[i] = new LinearProbingMapEntry<>(i, i + "");
        }
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeRemovedHandling() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        for (int i = 0; i < 10; i++) {
            map.put(i, i + "");
            expected[i] = new LinearProbingMapEntry<>(i, i + "");
            if (i == 4) {
                map.remove(i);
                expected[i] = null;
            }
        }
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeRehash() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        for (int i = 10; i < 19; i++) {
            map.put(i, i + "");
            expected[i] = new LinearProbingMapEntry<>(i, i + "");
        }
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeOutOfOrder() {
        map.put(8, "8");
        map.put(10, "10");
        map.put(21, "21");
        map.put(9, "9");
        map.put(11, "11");
        map.put(34, "34");
        map.put(23, "23");
        map.put(35, "35");
        assertEquals(13, map.getTable().length);
        map.put(7, "7");
        assertEquals(27, map.getTable().length);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[7] = new LinearProbingMapEntry<>(34, "34");
        expected[8] = new LinearProbingMapEntry<>(35, "35");
        expected[9] = new LinearProbingMapEntry<>(8, "8");
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(9, "9");
        expected[12] = new LinearProbingMapEntry<>(11, "11");
        expected[13] = new LinearProbingMapEntry<>(7, "7");
        expected[21] = new LinearProbingMapEntry<>(21, "21");
        expected[23] = new LinearProbingMapEntry<>(23, "23");
        assertEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void resizeComprehensive() {
        map.put(8, "8");
        map.put(10, "10");
        map.put(21, "21");
        map.put(9, "9");
        map.put(11, "11");
        map.put(34, "34");
        map.put(23, "23");
        map.put(35, "35");
        map.remove(35);
        map.put(1, "1");
        assertEquals(13, map.getTable().length);
        map.put(7, "7");
        assertEquals(27, map.getTable().length);
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY * 2 + 1];
        expected[1] = new LinearProbingMapEntry<>(1, "1");
        expected[7] = new LinearProbingMapEntry<>(34, "34");
        expected[8] = new LinearProbingMapEntry<>(8, "8");
        expected[9] = new LinearProbingMapEntry<>(9, "9");
        expected[10] = new LinearProbingMapEntry<>(10, "10");
        expected[11] = new LinearProbingMapEntry<>(11, "11");
        expected[12] = new LinearProbingMapEntry<>(7, "7");
        expected[21] = new LinearProbingMapEntry<>(21, "21");
        expected[23] = new LinearProbingMapEntry<>(23, "23");
        assertEquals(expected, map.getTable());
        assertTrue(!map.containsKey(35));
        map.remove(11);
        expected[11].setRemoved(true);
        map.put(37, "37");
        expected[11] = new LinearProbingMapEntry<>(37, "37");
        assertEquals(expected, map.getTable());
    }
}
