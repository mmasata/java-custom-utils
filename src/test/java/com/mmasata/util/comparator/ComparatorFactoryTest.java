package com.mmasata.util.comparator;

import com.mmasata.util.comparator.enums.Order;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComparatorFactoryTest {

    @Test
    void alphabeticalOrderStringComparator() {
        Comparator<String> comparator = ComparatorFactory.alphabeticalOrderStringComparator();
        assertTrue(comparator instanceof AlphabeticalOrderStringComparator);
    }

    @Test
    void integerComparatorComparator_asc() {
        Comparator<Integer> comparator = ComparatorFactory.integerComparatorComparator(Order.ASC);
        assertTrue(comparator instanceof AscOrderIntegerComparator);
        assertFalse(comparator instanceof DescOrderIntegerComparator);
    }

    @Test
    void integerComparatorComparator_desc() {
        Comparator<Integer> comparator = ComparatorFactory.integerComparatorComparator(Order.DESC);
        assertTrue(comparator instanceof DescOrderIntegerComparator);
        assertFalse(comparator instanceof AscOrderIntegerComparator);
    }

}
