package org.learning.conferences;

import java.util.List;
import java.util.stream.Stream;

/*
    https://www.youtube.com/watch?v=0hQvWIdwnw4&t=5064s

    Part 1: Parallel streams: 0 to 1:34:15
    part 2: Completable Future: 1:34:15 to end
 */
public class ParallelAndAsynchronousProgramming {

    void main() {

        /*
            Functional programming has less complexity and it's easier to parallelize
            In imperative style of code structure of sequential code is very different from concurrent code

            Using streams the structure of sequential code is identical to structure of concurrent code

            mutability and concurrency does not go well together
         */

        List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).parallelStream();    // use when we create the stream and are at source
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).parallel();// use when we are not in source and already stream has been created

        // Moving backwards from terminal operation first operation encountered i.e. sequential or parallel wine
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .parallel()             // No operation as sequential is present closer to terminal operation
                .map(i -> i + 1)
                .sequential()           // Last one wins i.e. whole stream will be executed in sequential mode
                .forEach(System.out::println);

        /*
            Streams vs Reactive streams

            Streams
                1. are about sequential vs parallel
                2. entire pipeline is either sequential or parallel there are no segments

            Reactive streams
                1. are about synchronous vs Asynchronous
                2. if we use    subscribeOn -> no segments
                                observeOn -> segments

            TODO: Continue from 32:00 for parallel streams part.
         */

        // --------------------------------------------------------------------------------------------------
        /*
            CompletableFuture
                Its about asynchronous execution i.e. we want to do something without blocking
            Non blocking
                We call a method and we dont wait for it to complete we are on the move
                Then the question is how do we get the result back from that call

            Java has Future to get result back. but the movement we do future.get() we are blocked

            JavaScript back in time used callbacks for asynchrony but it had problem of callback hell
            So JavaScript world moved to using promises

            TODO: Continue conferences.CompletableFuturePromisesOfJava for more.
         */
    }
}
