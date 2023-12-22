# Usage

## Import dependency

- Add library to pom.xml

```xml

<dependency>
    <groupId>com.mmasata.library</groupId>
    <artifactId>custom-utils</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Provided functionality

- **SortedLinkedList**
    - Generic, but through the private constructor and exposed static methods can be set only for Integer and String
    - Integer - Without inserting Comparator the default AscOrderIntegerComparator is used
    - String - String - Without inserting Comparator the default AlphabeticalOrderStringComparator is used
- **Comparators**
    - The library offers several built-in Comparators
        - AlphabeticalOrderStringComparator
        - AscOrderIntegerComparator
        - DescOrderIntegerComparator
    - You can of course create your own Comparator and pass it to the data structure at initialization

## Example

```java
import com.mmasata.util.SortedLinkedList;
import com.mmasata.util.SortedLinkedListImpl;

import java.util.List;
import java.util.function.Function;

public class ExampleClass {

    public static void main(String[] args) {
        SortedLinkedList<Integer> linkedList = SortedLinkedListImpl.createForInts();

        //add to list
        linkedList.add(3);
        linkedList.add(2);
        linkedList.add(1);
        //now should be ordered -> 1,2,3

        //retrieve size (number of records inside of list)
        var size = linkedList.size();

        //remove from list
        linkedList.remove(2);
        //now should be ordered -> 1,3

        //it is possible to use foreach or stream constructs
        linkedList.forEach(value -> System.out.println(value));
        linkedList.stream().map(value -> value).toList();

        //it is possible to verify existence of values inside of list
        linkedList.contains(1);
        linkedList.containsAll(List.of(1, 2, 3));

        //it is possible to retrieve data by index
        var value = linkedList.get(0);

        //it is possible to set new value at specified index
        linkedList.set(0, 22); //NOTICE: New value will be resorted to keep sorted whole structure
    }
}
```

# Operations asymptotic time

| Operation | Asymptotic time |                                                                                                                     Description |
|-----------|:---------------:|--------------------------------------------------------------------------------------------------------------------------------:|
| add       |      O(n)       |                                         It has to search through the records until it finds where value according to Comparator |
| size      |      O(1)       |                                                      The size keeps the List internally, no need to go through all the records. |
| remove    |      O(n)       |                                                                             Must find record to delete, must go through records |
| contains  |      O(n)       |                                                        It must go through the records to determine the existence of the element |
| get       |      O(n)       |                                                                                    Traverses n elements to find the given index |
| set       |      O(n)       | Finds a record on a given index, then removes it and adds it with a new value (a combination of get, remove and add operations) |

# Potential ideas for future optimizations

- Using SkipList instead of LinkedList
    - A SkipList is a probabilistic data structure that can hold references to more distant neighbors on either side.
      This allows it to jump more efficiently.
    - On the other hand, the disadvantage is that it is more memory intensive and also that in the case of
      SortedLinkedList, reordering multi-level references would be complicated.
- HashSet for storing values that are somewhere in LinkedList
    - Theoretically, we could optimize the contains operation to O(1)
    - On the other hand, there is again the disadvantage that the data structure would be more memory complex
- Make a data structure based on primitive int instead of autoboxing values (Integer)
  - Autoboxing of values is a standard thing in Java, but the object takes more memory than a primitive value
  - There are several disadvantages
    - Could not apply Java generics without autoboxing (Generics can only work with objects)
    - Implementations for int and String would be different, it would not be possible to reuse the code so much. This would lead to duplication.
    - NOTICE: I definitely do not recommend this optimization, only a theoretical consideration