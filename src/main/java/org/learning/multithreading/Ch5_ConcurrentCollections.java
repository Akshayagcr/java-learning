package org.learning.multithreading;

import java.util.concurrent.*;

/*
    A concurrent collection is thread-safe, but not governed by a single exclusion lock. In the particular case of ConcurrentHashMap,
    it safely permits any number of concurrent reads as well as a large number of concurrent writes.
    "Synchronized" classes can be useful when you need to prevent all access to a collection via a single lock,
     at the expense of poorer scalability. In other cases in which multiple threads are expected to access a common collection,
     "concurrent" versions are normally preferable.
*/
public class Ch5_ConcurrentCollections {

    /*
        BlockingQueue:- A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element (take()),
        and wait for space to become available in the queue when storing an element (put(e)).
     */
    {

        /*
            A bounded blocking queue backed by an array.
         */
        BlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(10);

        class DelayObj implements Delayed {

            @Override
            public long getDelay(TimeUnit unit) {
                return 0;
            }

            @Override
            public int compareTo(Delayed o) {
                return 0;
            }
        }
        /*
            1: when the consumer wants to take an element from the queue,
            they can take it only when the delay for that particular element has expired.
            2: DelayQueue will execute getDelay() to find out if that element is allowed to be returned from the queue.
            If the getDelay() method will return zero or a negative number, it means that it could be retrieved from the queue.
            3: We also need to implement the compareTo() method, because the elements in the DelayQueue will be sorted according to the expiration time.
                The item that will expire first is kept at the head of the queue and the element with the highest expiration -
                time is kept at the tail of the queue
         */
        BlockingQueue<DelayObj> delayQueue = new DelayQueue<>();

        /*
            1: An optionally-bounded blocking deque/queue based on linked nodes.
            2: The optional capacity bound constructor argument serves as a way to prevent excessive expansion. -
            The capacity, if unspecified, is equal to Integer.MAX_VALUE.
         */
        BlockingQueue<DelayObj> linkedBlockingDeque = new LinkedBlockingDeque<>();
        BlockingQueue<DelayObj> linkedBlockingQueue = new LinkedBlockingQueue<>();

        // Refer TransferQueue
        BlockingQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();

        /*
            An unbounded blocking queue that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations.
             While this queue is logically unbounded, attempted additions may fail due to resource exhaustion (causing OutOfMemoryError).
             This class does not permit null elements. A priority queue relying on natural ordering also does not permit insertion of non-comparable objects (doing so results in ClassCastException).
         */
        BlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();

        /*
            1: A blocking queue in which each insert operation must wait for a corresponding remove operation by another thread, and vice versa.
            2: A synchronous queue does not have any internal capacity, not even a capacity of one.
            3: The SynchronousQueue only has two supported operations: take() and put(), and both of them are blocking.
            4: when we want to add an element to the queue, we need to call the put() method.
                That method will block until some other thread calls the take() method, signaling that it is ready to take an element.
            5: Although the SynchronousQueue has an interface of a queue, we should think about it as an exchange point for a single element between two threads,
            in which one thread is handing off an element, and another thread is taking that element.
         */
        BlockingQueue<Integer> synchronousQueue = new SynchronousQueue<>();
    }

    /*
        TransferQueue : A BlockingQueue in which producers may wait for consumers to receive elements.
        i.e. when the producer sends a message to the consumer using the transfer() method, the producer will stay blocked until the message is consumed.
     */
    {
        TransferQueue<Integer> linkedTransferQueue = new LinkedTransferQueue<>();
    }

    /*
        BlockingDeque : A Deque that additionally supports blocking operations that wait for the deque to become non-empty when retrieving an element,
        and wait for space to become available in the deque when storing an element.
     */
    {
        /*
            1: An optionally-bounded blocking deque based on linked nodes.
            2: The optional capacity bound constructor argument serves as a way to prevent excessive expansion.
         */
        BlockingDeque<Integer> linkedBlockingDeque = new LinkedBlockingDeque<>();
    }

    /*
        ConcurrentMap : A Map providing thread safety and atomicity guarantees.
     */
    {

        ConcurrentMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        // Refer ConcurrentNavigableMap
        ConcurrentMap<Integer, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();
    }

    /*
        ConcurrentNavigableMap : A ConcurrentMap supporting NavigableMap operations, and recursively so for its navigable sub-maps.
     */
    {

        /*
            A scalable concurrent ConcurrentNavigableMap implementation.
            The map is sorted according to the natural ordering of its keys, or by a Comparator provided at map creation time.
         */
        ConcurrentNavigableMap<Integer, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();
    }
}
