package com.mmasata.util.comparator;

import com.mmasata.util.comparator.enums.Order;

import java.util.Comparator;

/**
 * Factory class for creating built-in library comparators.
 */
public class ComparatorFactory {

    /**
     * @return Returns Comparator for alphabetical sorting of Strings
     */
    public static Comparator<String> alphabeticalOrderStringComparator() {
        return new AlphabeticalOrderStringComparator();
    }

    /**
     * @param order Direction of number order
     * @return Returns a comparator for sorting numbers
     */
    public static Comparator<Integer> integerComparatorComparator(Order order) {
        return order == Order.ASC
                ? new AscOrderIntegerComparator()
                : new DescOrderIntegerComparator();
    }

}
