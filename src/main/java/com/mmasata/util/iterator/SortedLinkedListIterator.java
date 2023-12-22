package com.mmasata.util.iterator;

import com.mmasata.util.model.Node;

import java.util.Iterator;

/**
 * Custom iterator implementation for SortedLinkedList
 *
 * @param <T>
 */
public class SortedLinkedListIterator<T> implements Iterator<T> {

    private Node<T> current;

    public SortedLinkedListIterator(Node<T> head) {
        this.current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        var value = current.getValue();
        current = current.getNext();
        return value;
    }

}
