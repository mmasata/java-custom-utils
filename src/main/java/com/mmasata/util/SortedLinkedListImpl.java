package com.mmasata.util;

import com.mmasata.util.comparator.AlphabeticalOrderStringComparator;
import com.mmasata.util.comparator.AscOrderIntegerComparator;
import com.mmasata.util.exception.MethodNotImplementedException;
import com.mmasata.util.helper.SortedLinkedListHelper;
import com.mmasata.util.iterator.SortedLinkedListIterator;
import com.mmasata.util.model.Node;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

/**
 * Custom implementation of the LinkedList data structure that also follows sorting rules.
 * <p>
 * The sorting rules are set via the Comparator interface.
 * <p>
 * In addition to this implementation, a set of built-in Comparators is also provided for use. If a different sorting rule is needed, it can be created and passed to the data structure.
 *
 * @param <T>
 */
@NoArgsConstructor(access = PRIVATE)
public class SortedLinkedListImpl<T> implements SortedLinkedList<T> {

    private Comparator<T> comparator;

    private SortedLinkedListHelper helper;

    private Node<T> head;

    private int size = 0;


    /**
     * Static method constructing data structure for Strings
     *
     * @return Returns an instance of SortedLinkedList
     */
    public static SortedLinkedListImpl<String> createForStrings() {
        return createForStrings(new AlphabeticalOrderStringComparator());
    }

    /**
     * Static method constructing data structure for Strings
     * Using a custom comparator.
     *
     * @return Returns an instance of SortedLinkedList
     */
    public static SortedLinkedListImpl<String> createForStrings(Comparator<String> customComparator) {
        var sortedLinkedList = new SortedLinkedListImpl<String>();
        sortedLinkedList.comparator = customComparator;
        sortedLinkedList.helper = new SortedLinkedListHelper();
        return sortedLinkedList;
    }

    /**
     * Static method constructing data structure for Integers
     *
     * @return Returns an instance of SortedLinkedList
     */
    public static SortedLinkedListImpl<Integer> createForInts() {
        return createForInts(new AscOrderIntegerComparator());
    }

    /**
     * Static method constructing data structure for Strings
     * Using a custom comparator.
     *
     * @return Returns an instance of SortedLinkedList
     */
    public static SortedLinkedListImpl<Integer> createForInts(Comparator<Integer> customComparator) {
        var sortedLinkedList = new SortedLinkedListImpl<Integer>();
        sortedLinkedList.comparator = customComparator;
        sortedLinkedList.helper = new SortedLinkedListHelper();
        return sortedLinkedList;
    }

    /**
     * @return Returns the number of items in SortedLinkedList
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if SortedLinkedList is empty (has no record in it)
     *
     * @return Returns true if it has no records. Otherwise, returns false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param index Searched index
     * @return Returns an entry at the specified index
     */
    @Override
    public T get(int index) {
        var node = helper.findNodeByIndex(head, index);
        return node.getValue();
    }

    /**
     * Sets a new value on the given index
     *
     * @param index Searched index
     * @param value New value
     */
    @Override
    public void set(int index, T value) {
        var node = helper.findNodeByIndex(head, index);

        //to keep ordered we need to reorder
        head = helper.performDelete(head, node);
        head = helper.performInsert(head, comparator, value);
    }

    /**
     * Add new record to the SortedLinkedList
     *
     * @param t element whose presence in this collection is to be ensured
     * @return Returns true if the addition to SortedLinkedList was successful
     */
    @Override
    public boolean add(T t) {
        head = helper.performInsert(head, comparator, t);
        size++;
        return true;
    }

    /**
     * Add new Collection of records to the SortedLinkedList
     *
     * @param c collection containing elements to be added to this collection
     * @return Returns true if adding to SortedLinkedList was successful for all elements
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return c.stream().allMatch(this::add);
    }

    /**
     * Checks if the item exists in the SortedLinkedList. If yes, it returns true, otherwise false.
     *
     * @param o Element whose presence in this collection is to be tested
     * @return Returns a boolean about the existence of an item in SortedLinkedList
     */
    @Override
    public boolean contains(Object o) {
        var searched = helper.findNodeByValue(head, o);
        return searched != null;
    }

    /**
     * Checks if the all items in the Collection exists in the SortedLinkedList. If yes, it returns true, otherwise false.
     *
     * @param c collection to be checked for containment in this collection
     * @return Returns a boolean about the existence of an item in SortedLinkedList
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    /**
     * Attempts to find an entry in SortedLinkedList. If it finds it, then deletes it.
     *
     * @param o element to be removed from this collection, if present
     * @return Returns true if the record was deleted, otherwise returns false and the record does not exist in SortedLinkedList
     */
    @Override
    public boolean remove(Object o) {
        var searched = helper.findNodeByValue(head, o);
        if (searched == null) {
            return false;
        }

        head = helper.performDelete(head, searched);
        size--;
        return true;
    }

    /**
     * Attempts to find each entry in the Collection in SortedLinkedList. If it finds it, then deletes it.
     *
     * @param c collection containing elements to be removed from this collection
     * @return Returns true if the records was deleted, otherwise returns false.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return c.stream().allMatch(this::remove);
    }

    /**
     * Sets the value of all elements to null and finally clears the entire SortedLinkedList
     */
    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        helper.performClear(head);
        head = null;
        size = 0;
    }

    /**
     * @return Returns the comparator used to sort the records in the collection.
     */
    @Override
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * @return Returns iterator for SortedLinkedList
     */
    @Override
    public Iterator<T> iterator() {
        return new SortedLinkedListIterator<>(head);
    }

    @Override
    public Object[] toArray() {
        return helper.convertToArray(head, size);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        SortedLinkedList.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return SortedLinkedList.super.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return SortedLinkedList.super.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return SortedLinkedList.super.parallelStream();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return SortedLinkedList.super.removeIf(filter);
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        throw new MethodNotImplementedException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new MethodNotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new MethodNotImplementedException();
    }

}
