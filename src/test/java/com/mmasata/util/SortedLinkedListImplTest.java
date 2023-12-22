package com.mmasata.util;

import com.mmasata.util.comparator.ComparatorFactory;
import com.mmasata.util.comparator.enums.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This tests covers real-world use of the resulting SortedLinkedList.
 */
class SortedLinkedListImplTest {

    @MethodSource(value = "integerListInsertionDataProvider")
    @ParameterizedTest
    void integerList_insertion(List<Integer> expectedOrder, Order order, List<Integer> valuesToAdd) {
        var comparator = ComparatorFactory.integerComparatorComparator(order);
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts(comparator);
        assertTrue(sortedLinkedList.isEmpty());

        sortedLinkedList.addAll(valuesToAdd);
        assertFalse(sortedLinkedList.isEmpty());

        commonValuesAssertions(expectedOrder, sortedLinkedList);
    }

    @MethodSource(value = "stringListInsertionDataProvider")
    @ParameterizedTest
    void stringList_insertion_defaultComparator(List<String> expectedOrder, List<String> valuesToAdd) {
        SortedLinkedList<String> sortedLinkedList = SortedLinkedListImpl.createForStrings();
        assertTrue(sortedLinkedList.isEmpty());

        sortedLinkedList.addAll(valuesToAdd);
        assertFalse(sortedLinkedList.isEmpty());

        commonValuesAssertions(expectedOrder, sortedLinkedList);
    }

    @MethodSource(value = "integerListRemoveDataProvider")
    @ParameterizedTest
    void sortedLinkedList_delete(List<Integer> expectedOrder, List<Integer> valuesToAdd, List<Integer> valuesToDelete) {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        assertTrue(sortedLinkedList.isEmpty());

        sortedLinkedList.addAll(valuesToAdd);
        assertFalse(sortedLinkedList.isEmpty());

        sortedLinkedList.removeAll(valuesToDelete);
        commonValuesAssertions(expectedOrder, sortedLinkedList);
    }

    @Test
    void remove_emptyLinkedListAgain() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        assertTrue(sortedLinkedList.isEmpty());

        sortedLinkedList.add(999);
        assertFalse(sortedLinkedList.isEmpty());

        sortedLinkedList.remove(999);
        assertTrue(sortedLinkedList.isEmpty());
    }

    @Test
    void size() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        var toAdd = 10;
        for (var added = 0; added < toAdd; added++) {
            assertEquals(added, sortedLinkedList.size());
            sortedLinkedList.add(999);
        }
    }

    @Test
    void isEmpty() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        assertTrue(sortedLinkedList.isEmpty());

        sortedLinkedList.add(999);
        assertFalse(sortedLinkedList.isEmpty());

        sortedLinkedList.add(999);
        assertFalse(sortedLinkedList.isEmpty());
    }

    @Test
    void get_success() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();

        sortedLinkedList.add(999);
        sortedLinkedList.add(111);
        sortedLinkedList.add(555);

        assertEquals(111, sortedLinkedList.get(0));
        assertEquals(555, sortedLinkedList.get(1));
        assertEquals(999, sortedLinkedList.get(2));
    }

    @Test
    void get_outOfBoundException() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();

        assertThrows(IndexOutOfBoundsException.class, () -> sortedLinkedList.get(0)); //empty head
        assertThrows(IndexOutOfBoundsException.class, () -> sortedLinkedList.get(-1)); //negative index

        sortedLinkedList.add(999);
        assertThrows(IndexOutOfBoundsException.class, () -> sortedLinkedList.get(1)); //empty value inside of chain
    }

    @Test
    void set() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        sortedLinkedList.addAll(List.of(2, 3, 4, 5));

        //change value of 3 to value of 1
        sortedLinkedList.set(1, 1);

        //1 is now the minimum number, so linked list should reorder -> 1 should be head (first)
        assertEquals(1, sortedLinkedList.get(0));

        //at same time size should be same
        assertEquals(4, sortedLinkedList.size());
    }

    @Test
    void contains() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        sortedLinkedList.addAll(List.of(1, 2, 3));

        assertTrue(sortedLinkedList.contains(1));
        assertTrue(sortedLinkedList.contains(2));
        assertTrue(sortedLinkedList.contains(3));

        assertFalse(sortedLinkedList.contains(55));
        assertFalse(sortedLinkedList.contains(0));
    }

    @Test
    void containsAll() {
        SortedLinkedList<Integer> sortedLinkedList = SortedLinkedListImpl.createForInts();
        sortedLinkedList.addAll(List.of(1, 2, 3));

        assertTrue(sortedLinkedList.containsAll(List.of(1)));
        assertTrue(sortedLinkedList.containsAll(List.of(1, 3)));
        assertTrue(sortedLinkedList.containsAll(List.of(1, 2, 3)));

        assertFalse(sortedLinkedList.containsAll(Arrays.asList(null, 1, 2)));
        assertFalse(sortedLinkedList.containsAll(Arrays.asList(4, 1, 2)));
    }

    private <T> void commonValuesAssertions(List<T> expected, SortedLinkedList<T> sortedLinkedList) {
        //check size
        assertEquals(expected.size(), sortedLinkedList.size());

        //check correct order
        var iterator = sortedLinkedList.iterator();
        var index = 0;

        //check via custom iterator
        while (iterator.hasNext()) {
            assertEquals(expected.get(index), iterator.next());
            index++;
        }
    }


    private static Stream<Arguments> integerListInsertionDataProvider() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 5, 7, 9), Order.ASC, List.of(5, 7, 1, 9, 2)), //basic ASC scenario
                Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), Order.ASC, List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)), //reverse ASC scenario
                Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), Order.ASC, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), // already sorted ASC scenario
                Arguments.of(List.of(1, 1, 2, 2, 2, 5, 5, 7, 7, 8, 9), Order.ASC, List.of(1, 7, 2, 1, 5, 2, 8, 2, 5, 7, 9)), //duplicate values ASC scenario
                Arguments.of(Arrays.asList(null, null, null, 1, 2, 5, 7), Order.ASC, Arrays.asList(null, 2, null, 7, 5, null, 1)), //null values ASC scenario
                Arguments.of(List.of(9, 7, 5, 2, 1), Order.DESC, List.of(5, 7, 1, 9, 2)), //basic DESC scenario
                Arguments.of(List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), Order.DESC, List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), //reverse DESC scenario
                Arguments.of(List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), Order.DESC, List.of(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)), // already sorted DESC scenario scenario
                Arguments.of(List.of(9, 8, 7, 7, 5, 5, 2, 2, 2, 1, 1), Order.DESC, List.of(1, 7, 2, 1, 5, 2, 8, 2, 5, 7, 9)), //duplicate values DESC scenario
                Arguments.of(Arrays.asList(7, 5, 2, 1, null, null, null), Order.DESC, Arrays.asList(null, 2, null, 7, 5, null, 1)) //null values DESC scenario
        );
    }

    private static Stream<Arguments> stringListInsertionDataProvider() {
        return Stream.of(
                Arguments.of(List.of("AAA", "AAB", "BBB", "CBA", "CCB", "EEE"), List.of("EEE", "AAA", "BBB", "CCB", "AAB", "CBA")), //basic scenario
                Arguments.of(List.of("AAA", "AAB", "BBB", "CBA", "CCB", "EEE"), List.of("EEE", "CCB", "CBA", "BBB", "AAB", "AAA")), //reverse scenario
                Arguments.of(List.of("AAA", "AAB", "BBB", "CBA", "CCB", "EEE"), List.of("AAA", "AAB", "BBB", "CBA", "CCB", "EEE")), //already sorted scenario
                Arguments.of(Arrays.asList(null, null, null, "AAA", "CCB", "EEE"), Arrays.asList(null, "CCB", null, "AAA", "EEE", null)) //null values ASC scenario
        );
    }

    private static Stream<Arguments> integerListRemoveDataProvider() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 5, 7, 9), List.of(1, 2, 12, 5, 8, 7, 25, 9), List.of(25, 12, 8)), //basic scenario
                Arguments.of(List.of(2, 5, 7, 9), List.of(1, 2, 5, 7, 9), List.of(1)), //remove head (start element) scenario
                Arguments.of(List.of(1, 2, 5, 7), List.of(1, 2, 5, 7, 9), List.of(9)), //remove last element scenario
                Arguments.of(List.of(1, 2, 5, 7), List.of(1, 2, 2, 5, 7, 7, 7), List.of(2, 7, 7)), //remove duplicate value (should remove only one value with specified value)
                Arguments.of(Arrays.asList(null, null, 1, 2, 3), Arrays.asList(null, null, null, null, 1, 2, 3, 4, 5), Arrays.asList(5, null, 4, null)) //remove null value scenario
        );
    }

}
