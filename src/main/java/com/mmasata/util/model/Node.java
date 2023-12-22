package com.mmasata.util.model;

/**
 * Container for generic entry in custom collections.
 * <p>
 * Since Node is used for the optimized SkipList, it keeps a collection of next and previous so that it can more efficiently jump by multiple indexes when searching.
 *
 * @param <T> Generic value of the collection
 */
public class Node<T> {

    private T value;

    private Node<T> prev;
    private Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public Node(T value,
                Node<T> prev,
                Node<T> next) {

        this.value = value;
        this.prev = prev;
        this.next = next;
    }


    public T getValue() {
        return value;
    }

    public Node<T> getPrev() {
        return prev;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }

    public void setNext(Node<T> next) {
        this.next = next;
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

