import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * This is a basic set of unit tests for Sorting.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class SortingStudentTest {

    private static final int TIMEOUT = 200;
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private ComparatorPlus<TeachingAssistant> comp;

    @Before
    public void setUp() {
        /*
            Unsorted Names:
                index 0: Alvin
                index 1: Lisa
                index 2: Sera
                index 3: Tahsin
                index 4: Hannah
                index 5: Avery
                index 6: Wendy
                index 7: Sheza
                index 8: Hanrui
                index 9: Carolyn
         */

        /*
            Sorted Names:
                index 0: Alvin
                index 1: Avery
                index 2: Carolyn
                index 3: Hannah
                index 4: Hanrui
                index 5: Lisa
                index 6: Sera
                index 7: Sheza
                index 8: Tahsin
                index 9: Wendy
         */

        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alvin"); //0
        tas[1] = new TeachingAssistant("Lisa"); //5
        tas[2] = new TeachingAssistant("Sera"); //6
        tas[3] = new TeachingAssistant("Tahsin"); //8
        tas[4] = new TeachingAssistant("Hannah"); //3
        tas[5] = new TeachingAssistant("Avery"); //1
        tas[6] = new TeachingAssistant("Wendy"); //9
        tas[7] = new TeachingAssistant("Sheza"); //7
        tas[8] = new TeachingAssistant("Hanrui"); //4
        tas[9] = new TeachingAssistant("Carolyn"); //2
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[5];
        tasByName[2] = tas[9];
        tasByName[3] = tas[4];
        tasByName[4] = tas[8];
        tasByName[5] = tas[1];
        tasByName[6] = tas[2];
        tasByName[7] = tas[7];
        tasByName[8] = tas[3];
        tasByName[9] = tas[6];

        comp = TeachingAssistant.getNameComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 30 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSort() {
        Sorting.cocktailSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 33 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 24 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, 28, 58, 84, 20, 122, -85, 3};
        int[] sortedArray = new int[] {-85, 3, 20, 28, 54, 58, 84, 122};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }

    @Test(timeout = TIMEOUT)
    public void testQuickSort() {
        Sorting.quickSort(tas, comp, new Random(234));
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
            comp.getCount() <= 30 && comp.getCount() != 0);
    }
    
    //---
    @Test//---
    public void crazyRadix() {
        int[] arr = new int[]{Integer.MIN_VALUE, 0,1,1,-1,-1,4,-4,16,64,-256,1024,-4096,Integer.MIN_VALUE};
        int[] arr2 = new int[]{Integer.MIN_VALUE, 0,1,1,-1,-1,4,-4,16,64,-256,1024,-4096,Integer.MIN_VALUE};
        Sorting.lsdRadixSort(arr);
        Arrays.sort(arr2);
        if(!Arrays.equals(arr, arr2)) System.out.println("Your radixSort could not sort an array with Integer.MIN.");
    }
    
    @Test(timeout = TIMEOUT)
    public void testEmpty() {
        TeachingAssistant[] empty = new TeachingAssistant[0];
        Sorting.insertionSort(empty, comp);
        Sorting.cocktailSort(empty, comp);
        Sorting.mergeSort(empty, comp);
        Sorting.quickSort(empty, comp, new Random());
        Sorting.lsdRadixSort(new int[0]);
    }

    @Test(timeout = TIMEOUT)
    public void simpleTestStability() {
        tas = new TeachingAssistant[2];
        TeachingAssistant avery1 = new TeachingAssistant("Avery");
        TeachingAssistant avery2 = new TeachingAssistant("Avery");
        tas[0] = avery1;
        tas[1] = avery2;
        Sorting.insertionSort(tas, comp);
        assertSame("relative order not preserved in insertion sort", tas[0], avery1);
        assertSame("relative order not preserved in insertion sort", tas[1], avery2);
        Sorting.cocktailSort(tas, comp);
        assertSame("relative order not preserved in cocktail sort", tas[0], avery1);
        assertSame("relative order not preserved in cocktail sort", tas[1], avery2);
        Sorting.mergeSort(tas, comp);
        assertSame("relative order not preserved in merge sort", tas[0], avery1);
        assertSame("relative order not preserved in merge sort", tas[1], avery2);
    }

    @Test(timeout = TIMEOUT)
    public void testAdaptiveInsertion() {
        Sorting.insertionSort(tasByName, comp);
        assertTrue("number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testAdaptiveCocktail() {
        Sorting.cocktailSort(tasByName, comp);
        assertTrue("number of comparisons: " + comp.getCount(),
                comp.getCount() <= 9 && comp.getCount() != 0);
    }

    @Test(timeout = TIMEOUT)
    public void testLSDEdgeCases() {
        int[] unsortedArray = new int[] {Integer.MAX_VALUE, 119, -119, Integer.MIN_VALUE, 0, -1, 1, 19, -19};
        int[] sortedArray = new int[] {Integer.MIN_VALUE, -119, -19, -1, 0, 1, 19, 119, Integer.MAX_VALUE};
        Sorting.lsdRadixSort(unsortedArray);
        assertArrayEquals(sortedArray, unsortedArray);
    }
    
    @Test(timeout = TIMEOUT)
    public void testInsertionSortStable() {
        tas = new TeachingAssistant[4];
        TeachingAssistant firstCaroline = new TeachingAssistant("Caroline");
        TeachingAssistant secondCaroline = new TeachingAssistant("Caroline");
        tas[0] = new TeachingAssistant("Sera");
        tas[1] = firstCaroline;
        tas[2] = new TeachingAssistant("Alvin");
        tas[3] = secondCaroline;

        Sorting.insertionSort(tas, comp);
        assertNotSame(firstCaroline, secondCaroline);
        assertEquals(new TeachingAssistant("Alvin"), tas[0]);
        assertSame(firstCaroline, tas[1]);
        assertSame(secondCaroline, tas[2]);
        assertEquals(new TeachingAssistant("Sera"), tas[3]);
    }

    @Test(timeout = TIMEOUT)
    public void testCocktailSortStable() {
        tas = new TeachingAssistant[4];
        TeachingAssistant firstCaroline = new TeachingAssistant("Caroline");
        TeachingAssistant secondCaroline = new TeachingAssistant("Caroline");
        tas[0] = new TeachingAssistant("Sera");
        tas[1] = firstCaroline;
        tas[2] = new TeachingAssistant("Alvin");
        tas[3] = secondCaroline;

          Sorting.cocktailSort(tas, comp);
          assertNotSame(firstCaroline, secondCaroline);
          assertEquals(new TeachingAssistant("Alvin"), tas[0]);
          assertSame(firstCaroline, tas[1]);
          assertSame(secondCaroline, tas[2]);
          assertEquals(new TeachingAssistant("Sera"), tas[3]);
      }

      @Test(timeout = TIMEOUT)
      public void testMergeSortStable() {
          tas = new TeachingAssistant[4];
          TeachingAssistant firstCaroline = new TeachingAssistant("Caroline");
          TeachingAssistant secondCaroline = new TeachingAssistant("Caroline");
          tas[0] = new TeachingAssistant("Sera");
          tas[1] = firstCaroline;
          tas[2] = new TeachingAssistant("Alvin");
          tas[3] = secondCaroline;

          Sorting.mergeSort(tas, comp);
          assertNotSame(firstCaroline, secondCaroline);
          assertEquals(new TeachingAssistant("Alvin"), tas[0]);
          assertSame(firstCaroline, tas[1]);
          assertSame(secondCaroline, tas[2]);
          assertEquals(new TeachingAssistant("Sera"), tas[3]);
      }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            return other instanceof TeachingAssistant
                && ((TeachingAssistant) other).name.equals(this.name);
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}