package com.mmasata.util;

import java.util.Collection;
import java.util.Comparator;

public interface SortedLinkedList<T> extends Collection<T> {

    /**
     * @return Returns the comparator used to sort the records in the collection.
     */
    Comparator<T> comparator();

    /**
     * @param index Searched index
     * @return Returns an entry at the specified index
     */
    T get(int index);

    /**
     * Sets a new value on the given index
     *
     * @param index Searched index
     * @param value New value
     */
    void set(int index, T value);
}
