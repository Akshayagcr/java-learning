package org.learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.learning.model.Interval;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 *  ***** Interfaces :- Implementations
 * Collection :- None
 * List :- ArrayList & LinkedList
 * Set :- HashSet & TreeSet
 * Map :- HashMap & TreeMap
 * Queue :- LinkedList & PriorityQueue
 * Deque :- ArrayDeque & LinkedList
 *
 * ***** Legacy Classes :- not to be used anymore.
 * Vector, HashTable, Stack, Enumeration(Predecessor of Iterable)
 *
 */
class CollectionsTest {

    /**
     * Collection represents a group of elements. It is not sequenced.
     * It is the root interface of collections framework.
     *
     *  Iterable -> Collection
     *
     *  Every Implementation of Collection interface generally provides two constructor
     *  1: No argument constructor 2: constructor which takes other collection as input.
     */
    @DisplayName("Collection")
    @Test
    void testCollection(){
        Collection<Integer> a = new ArrayList<>();

        // CREATE : Adding elements
        a.add(1); a.add(2); a.add(3); a.add(4); a.add(5);

        // READ : Check membership
        assertThat(a.contains(1)).isTrue();

        // General operations
        assertThat(a.size()).isEqualTo(5);
        assertThat(a.isEmpty()).isFalse();

        // DELETE : remove, clear
        a.remove(1);
        a.removeIf(ele -> ele % 2 != 0);
        assertThat(a).contains(2, 4);

        a.clear();
        assertThat(a).isEmpty();

        // It also supports Set operations. As it represents a group of elements.
        Collection<Integer> s1;
        Collection<Integer> s2;

        // Union
        s1 = new ArrayList<>(List.of(1, 2, 3));
        s2 = new ArrayList<>(List.of(3, 4, 5));
        s1.addAll(s2);
        assertThat(s1).contains(1, 2, 3, 4, 5);

        // Difference
        s1 = new ArrayList<>(List.of(1, 2, 3));
        s2 = new ArrayList<>(List.of(3, 4, 5));
        s1.removeAll(s2);
        assertThat(s1).contains(1, 2);

        // Inclusion (Membership)
        s1 = new ArrayList<>(List.of(1, 2, 3));
        s2 = new ArrayList<>(List.of(3, 4, 5));
        assertThat(s1.containsAll(s2)).isFalse();

        // intersection
        s1 = new ArrayList<>(List.of(1, 2, 3));
        s2 = new ArrayList<>(List.of(3, 4, 5));
        s1.retainAll(s2);
        assertThat(s1).contains(3);

        /*
            Iteration on collections
                1: If we just need to read then use for each loop
                2: If we need to remove elements while iterating use remove method of iterator
                3: If we directly try to modify collection while iterating using method like collection.remove
                then we get ConcurrentModificationException
         */
    }

    /**
     * SequencedCollection gives following functionality to collection
     *  1. Operation on both end of container.
     *  2. Reversed view of the collection.
     */
    @DisplayName("SequencedCollection")
    @Test
    void testSequencedCollection(){
        SequencedCollection<Integer> a = new ArrayList<>(List.of(1, 2, 3));

        a.addFirst(0); a.addLast(4);

        assertThat(a.getFirst()).isEqualTo(0);
        assertThat(a.getLast()).isEqualTo(4);

        a.removeFirst(); a.removeLast();
        assertThat(a).contains(1, 2, 3);

        assertThat(a.reversed()).contains(3, 2, 1);
    }

    /**
     *  Collection -> SequencedCollection -> List
     *  List interface brings two functionality in plain collections
     *  1: Encounter order :- Element appear in same order in which they were inserted
     *  2: Indexing :- we can access elements using index
     */
    @DisplayName("ArrayList")
    @Test
    void testArrayList(){
        List<Integer> a = new ArrayList<>();

        // Operation on single index
        a.add(2);
        a.add(0, 1);
        assertThat(a).contains(1, 2);

        a.set(1, 3);
        assertThat(a.get(1)).isEqualTo(3);

        /*
            There are two overload of the remove function
            1. remove(index)  2. remove(Object)
         */
        a.remove(1);
        assertThat(a).contains(1);
    }

    @DisplayName("Convenience factory method for List")
    @Test
    void testFactoryMethodList(){

        /*
            Arrays.asList creates list wrapper around the array.
            we can update array/List elements but operations which change size of list are not supported
         */
        Integer[] arr = {1, 2, 3};
        List<Integer> l = Arrays.asList(arr);
        assertThatThrownBy(() -> l.add(4)).isInstanceOf(UnsupportedOperationException.class);
        l.set(2, 4);
        assertThat(l).contains(1, 2, 4);

        /*
            List.of creates an unmodifiable List. elements cannot be updated as well as changed
         */
        List<Integer> a = List.of(1, 2, 3);
        assertThatThrownBy(() -> a.add(4)).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> a.set(0, 10)).isInstanceOf(UnsupportedOperationException.class);
    }

    /**
     * Collection -> Set
     */
    @DisplayName("HashSet")
    @Test
    void testHashSet(){
        Set<Integer> s = new HashSet<>(Set.of(1, 2));

        s.add(3);

        assertThat(s.contains(3)).isTrue();

        s.remove(3);
        assertThat(s).contains(1, 2);
    }

    /**
     * Collection -> SequencedCollection -> SequencedSet -> SortedSet -> NavigableSet
     */
    @DisplayName("TreeSet")
    @Test
    void testTreeSet(){

        Set<Interval> s1 = Set.of(
                new Interval(10, 15),
                new Interval(5, 16),
                new Interval(5, 4),
                new Interval(1, 2));
        NavigableSet<Interval> s = new TreeSet<>(s1);

        /*
            SortedSet : It adds capability to keep elements in sorted order
            **** headSet, tailSet, subSet methods return a view of the set not a copy
         */
        assertThat(s.first()).isEqualTo(new Interval(1, 2));
        assertThat(s.last()).isEqualTo(new Interval(10, 15));
        assertThat(s.headSet(new Interval(5, 4), true)).containsExactly(new Interval(1, 2), new Interval(5, 4));
        assertThat(s.tailSet(new Interval(5, 16), true)).containsExactly(new Interval(5, 16), new Interval(10, 15));
        assertThat(s.subSet(new Interval(5, 4), true, new Interval(10, 15), true)).containsExactly(new Interval(5, 4), new Interval(5, 16), new Interval(10, 15));

        /*
            NavigableSet : It further adds capability on SortedSet to iterate over element in descending order (descendingIterator(), descendingSet())
            and following method were added
         */
        assertThat(s.ceiling(new Interval(5, 16))).isEqualTo(new Interval(5, 16));
        assertThat(s.floor(new Interval(5, 4))).isEqualTo(new Interval(5, 4));
        assertThat(s.higher(new Interval(5, 16))).isEqualTo(new Interval(10, 15));
        assertThat(s.lower(new Interval(5, 4))).isEqualTo(new Interval(1, 2));
    }

    /**
     *
     */
    @DisplayName("HashMap")
    @Test
    void testHashMap(){
        Map<Integer, String> m = new HashMap<>();
        m.put(1, "Akshay");
        m.put(2, "Rathod");
        m.put(1, "Kishor");

        assertThat(m).containsExactly(Map.entry(1, "Kishor"), Map.entry(2, "Rathod"));

        assertThat(m.containsKey(1)).isTrue();

        m.remove(1);
        assertThat(m).containsExactly(Map.entry(2, "Rathod"));

        /*
            Best way to iterate over map is by getting entrySet rather than getting key and then fetching its corresponding value.
         */
        assertThat(m.entrySet()).isEqualTo(Set.of(Map.entry(2, "Rathod")));
    }

    /**
     *  Map -> SequencedMap -> SortedMap -> NavigableMap
     */
    @DisplayName("TreeMap")
    @Test
    void testTreeMap(){
        NavigableMap<Integer, String> m = new TreeMap<>();
        /*
            https://dev.java/learn/api/collections-framework/sorted-maps/
         */
    }

    @DisplayName("Convenience factory method for Set and Map")
    @Test
    void testFactoryMethodSetMap(){
        Set<Integer> s = Set.of(1, 2, 3);
        assertThatThrownBy(() -> s.add(4)).isInstanceOf(UnsupportedOperationException.class);

        Map<Integer, String> m = Map.ofEntries(Map.entry(1, "Akshay"), Map.entry(2, "Rathod"));
        assertThatThrownBy(() -> m.put(1, "Akshit")).isInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> m.replace(1, "Akshit")).isInstanceOf(UnsupportedOperationException.class);

        Map<Integer, String> m2 = Map.of(1, "Akshay");
        assertThat(m2).contains(Map.entry(1, "Akshay"));
    }

    /**
     *  Collection -> Queue
     *  Queue does not implement SequencedCollection as elements are not ordered as the queue uses heap. Only guaranteed ordered is when we remove the element.
     *
     *  Operations              insert, remove, examine
     * Throws exception     :-  add, remove, element
     * Return special value :-  offer, poll, peek
     */
    @DisplayName("PriorityQueue")
    @Test
    void testPriorityQueue(){
        Queue<Integer> q = new PriorityQueue<>();

        q.add(22); q.add(205); q.add(1);

        assertThat(q.size()).isEqualTo(3);
        assertThat(q.isEmpty()).isFalse();

        assertThat(q.peek()).isEqualTo(1);
        q.remove();
        assertThat(q.peek()).isEqualTo(22);
        q.remove();
        assertThat(q.peek()).isEqualTo(205);
        q.remove();

        assertThatThrownBy(() -> q.remove()).isInstanceOf(NoSuchElementException.class);
        assertThat(q.peek()).isNull();
    }

    /**
     *  Collection -> SequencedCollection -> Deque
     *
     *  ArrayDeque is efficient compared to LinkedList which also implements Deque. Due to following reasons
     *  1: Pointer chasing : As nodes in LinkedList are stored in random location it causes frequent cash misses.
     *  2: Memory consumption : It is high in case of LinkedList as it also need to store the links.
     *
     *  Throws exception        :- addxx(), removexx(), getxx()
     *  Return special value    :- offerxx(), pollxx(), peekxx()
     *  where xx = first, last
     */
    @DisplayName("ArrayDeque")
    @Test
    void testArrayDeque(){
        Deque<Integer> q = new ArrayDeque<>();

        q.addFirst(1); q.addLast(2);

        assertThat(q.getFirst()).isEqualTo(1);
        assertThat(q.getLast()).isEqualTo(2);

        q.removeFirst(); q.removeLast();
        assertThat(q).isEmpty();
    }

    /**
     * LinkedHashMap & LinkedHashSet work as usual HashMap and HashSet,
     * but they also contain a LinkedList structure which maintains insertion order
     *
     * Collection -> SequencedCollection -> SequencedSet -> LinkedHashSet
     */
    @DisplayName("LinkedHashMap & LinkedHashSet")
    @Test
    void testLinkedHashMapLinkedHashSet(){
        LinkedHashSet<Integer> s = new LinkedHashSet<>(List.of(3, 2, 1));

        Iterator<Integer> it = s.iterator();

        assertThat(it.next()).isEqualTo(3);
        assertThat(it.next()).isEqualTo(2);
        assertThat(it.next()).isEqualTo(1);
    }

}
