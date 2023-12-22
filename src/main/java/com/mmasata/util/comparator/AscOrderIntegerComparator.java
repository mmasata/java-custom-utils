package com.mmasata.util.comparator;

import java.util.Comparator;

/**
 * Comparator for sorting numbers from smallest to largest number
 */
public class AscOrderIntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {

        if (o1 == null && o2 == null) {
            return 0;
        }

        if (o1 == null) {
            return -1;
        }

        if (o2 == null) {
            return 1;
        }

        return o1 - o2;
    }

}
