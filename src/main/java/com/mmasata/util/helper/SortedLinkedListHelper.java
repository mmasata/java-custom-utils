package com.mmasata.util.helper;

import com.mmasata.util.model.Node;

import java.util.Comparator;

/**
 * Helper class for processing algorithms over custom data structure SortedLinkedList
 */
public class SortedLinkedListHelper {

    /**
     * Searches SortedLinkedList and tries to find the given value. If it does not find it, it returns null.
     *
     * @param value Searched value
     * @return Node of the searched value or null
     */
    public <T> Node<T> findNodeByValue(Node<T> head,
                                       Object value) {

        var currentNode = head;
        while (currentNode != null) {

            if (value != null && value.equals(currentNode.getValue())) {
                return currentNode;
            }

            if (value == null && currentNode.getValue() == null) {
                return currentNode;
            }

            currentNode = currentNode.getNext();
        }

        return null;
    }

    /**
     * @param head  First SortedLinkedList Node
     * @param index Search index
     * @param <T>   Generic value stored in Node
     * @return Returns the Node at the given index. If the Node does not exist on that index, then this is an IndexOutOfBoundsException
     */
    public <T> Node<T> findNodeByIndex(Node<T> head,
                                       int index) {

        //index must be at least 0
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }

        var current = head;
        var currentIndex = 0;

        while (current != null) {

            if (index == currentIndex) {
                return current;
            }

            current = current.getNext();
            currentIndex++;
        }

        throw new IndexOutOfBoundsException();
    }

    /**
     * Deletes the Node from SortedLinkedList and fixes the neighbor bindings.
     *
     * @param head First SortedLinkedList Node
     * @param node Node to delete
     * @return Returns new head after delete
     */
    public <T> Node<T> performDelete(Node<T> head,
                                     Node<T> node) {

        //node to delete is also a head -> delete and set new head
        if (head == node) {
            var newHead = node.getNext();

            //there is chance, that deleted head was also the only element -> after removing is linked list empty again
            if (newHead != null) {
                newHead.setPrev(null);
                head.clear();
            }

            return newHead;
        }


        //node to delete is on end
        if (node.getNext() == null) {
            var newLastNode = node.getPrev();

            newLastNode.setNext(null);
            node.clear();
            return head;
        }

        //otherwise node to delete is somewhere in the middle of Linked List
        var leftNeighbour = node.getPrev();
        var rightNeighbour = node.getNext();

        leftNeighbour.setNext(rightNeighbour);
        rightNeighbour.setPrev(leftNeighbour);
        node.clear();
        return head;
    }


    /**
     * Adds an entry in SortedLinkedList to the correct location and fixes the neighbor bindings
     *
     * @param head       First SortedLinkedList Node
     * @param comparator Comparator for comparing individual records
     * @param newValue   Value to insert
     * @param <T>        Generic value stored in Node
     * @return Returns new head after insertion
     */
    public <T> Node<T> performInsert(Node<T> head,
                                     Comparator<T> comparator,
                                     T newValue) {

        //head is empty -> value become head
        if (head == null) {
            return new Node<>(newValue);
        }

        var currentNode = head;
        while (true) {
            var satisfied = comparator.compare(newValue, currentNode.getValue()) <= 0;

            //if is equal or less, and we are on head, then become new head
            if (satisfied && currentNode == head) {
                var newHead = new Node<>(newValue, null, head);
                head.setPrev(newHead);
                return newHead;
            }

            //if is equal or less we can add to this position
            if (satisfied) {
                var leftNeighbour = currentNode.getPrev();
                var rightNeighbour = currentNode;
                var newNode = new Node<>(newValue, leftNeighbour, rightNeighbour);

                //now fix the chain
                leftNeighbour.setNext(newNode);
                rightNeighbour.setPrev(newNode);
                return head;
            }

            //reach end we can insert
            if (currentNode.getNext() == null) {
                currentNode.setNext(new Node<>(newValue, currentNode, null));
                return head;
            }

            currentNode = currentNode.getNext();
        }
    }

    /**
     * Sets the value of all elements to null and finally clears the entire SortedLinkedList
     *
     * @param head First SortedLinkedList Node
     * @param <T>  Generic value stored in Node
     */
    public <T> void performClear(Node<T> head) {
        Node<T> currentNode = head;
        Node<T> nextNode;

        while (currentNode != null) {
            nextNode = currentNode.getNext();
            currentNode.clear();
            currentNode = nextNode;
        }
    }

    /**
     * Converts SortedLinkedList to an Object array
     *
     * @param head First SortedLinkedList Node
     * @param size Number of records in SortedLinkedList
     * @param <T>  Generic value stored in Node
     * @return Returns an array of Objects
     */
    public <T> Object[] convertToArray(Node<T> head,
                                       int size) {

        var array = new Object[size];
        var currentNode = head;

        for (var idx = 0; idx < size; idx++) {
            array[idx] = currentNode.getValue();
            currentNode = currentNode.getNext();
        }

        return array;
    }

}
