package com.mmasata.util.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Container for generic entry in custom collections.
 * <p>
 * Since Node is used for the optimized SkipList, it keeps a collection of next and previous so that it can more efficiently jump by multiple indexes when searching.
 *
 * @param <T> Generic value of the collection
 */
@Data
@AllArgsConstructor
public class Node<T> {

    private T value;

    private Node<T> prev;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    /**
     * Cleans all data inside the container
     */
    public void clear() {
        prev = null;
        next = null;
        value = null;
    }

}

