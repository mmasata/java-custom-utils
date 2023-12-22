package com.mmasata.util.helper;

import com.mmasata.util.comparator.ComparatorFactory;
import com.mmasata.util.model.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortedLinkedListHelperTest {

    private static final String HEAD_VALUE = "AAA";
    private static final String SECOND_VALUE = "BBB";
    private static final String THIRD_VALUE = "CCC";

    private final SortedLinkedListHelper helper = new SortedLinkedListHelper();

    @Test
    void findNodeByValue() {
        var head = prepareData();

        assertNull(helper.findNodeByValue(head, "DDD"));
        assertNotNull(helper.findNodeByValue(head, SECOND_VALUE));
    }

    @Test
    void findNodeByIndex() {
        var head = prepareData();

        assertThrows(IndexOutOfBoundsException.class, () -> helper.findNodeByIndex(head, -1));

        var actual = helper.findNodeByIndex(head, 0);
        assertNotNull(actual);
        assertEquals(HEAD_VALUE, actual.getValue());
    }

    @Test
    void performDelete() {
        var head = prepareData();
        var newHead = helper.performDelete(head, head);

        assertNotSame(head, newHead);
        assertNotNull(newHead);
        assertEquals(SECOND_VALUE, newHead.getValue());
    }

    @Test
    void performInsert() {
        var oldHead = new Node<>(SECOND_VALUE);
        var newHead = helper.performInsert(oldHead, ComparatorFactory.alphabeticalOrderStringComparator(), HEAD_VALUE);

        assertNotSame(oldHead, newHead);
        assertNotNull(newHead);
        assertEquals(HEAD_VALUE, newHead.getValue());
        assertEquals(oldHead, newHead.getNext());
    }

    @Test
    void performClear() {
        var head = prepareData();
        helper.performClear(head);

        assertNull(head.getValue());
        assertNull(head.getNext());
        assertNull(head.getPrev());
    }

    @Test
    void convertToArray() {
        var head = prepareData();
        Object[] array = helper.convertToArray(head, 3);

        assertEquals(array.length, 3);
        assertEquals(array[0], HEAD_VALUE);
        assertEquals(array[1], SECOND_VALUE);
        assertEquals(array[2], THIRD_VALUE);
    }

    private Node<String> prepareData() {
        //data preparation
        var head = new Node<>(HEAD_VALUE);
        var secondNode = new Node<>(SECOND_VALUE);
        var thirdNode = new Node<>(THIRD_VALUE);

        head.setNext(secondNode);
        secondNode.setPrev(head);
        secondNode.setNext(thirdNode);
        thirdNode.setPrev(secondNode);

        return head;
    }

}
