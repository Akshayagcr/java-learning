package org.learning.conferences;

/**
 * Java Streams vs Reactive Streams: Which, When, How, and Why? by Venkat Subramaniam : https://www.youtube.com/watch?v=kG2SEcl1aMM
 */
class StreamsVsReactiveStreams {

    /**
     * Before java 8 we programmed in imperative and object-oriented style.
     * Java 8 introduction functional style of programming with features like lambdas and stream
     *
     * Functional programming =  declarative style + functional composition + lazy evaluation
     *
     * For lazy evaluation depends on function purity.
     * Pure function does not have side effect, they are idempotent regardless of how many times -
     * we run the function with same input we get same output.
     * When using stream API we need to make sure that that our functions should are pure.
     *
     * Example : impure function
     * we try to add element to an arrayList in a forEach() terminal operation. That external arrayList acts as shared mutable state which will case thread issue when we convert stream() to parallelSteam() instead we should use reduce() or collect()
     * We should embrace immutability as mutability might make function impure which has direct effect on lazy evaluation as lazy evaluation relies on purity of function
     * Using collectors make our life easy as they take care of functioning correctly in concurrent scenarios also
     *
     * Drawbacks of streams :
     * 1. Single pass over stream
     * 2. Cannot fork a stream i.e. divide in two or more different flow or single terminal operation
     * 3. No standard way of exception handling as ***exception as concept of imperative programming
     *
     * Reactive programming = function programming++
     *
     * Reactive programming is built on function composition and lazy evaluation and *** takes abstraction even further
     *
     *  Java Streams                            Reactive streams
     *  similarities -------------------------------------------------------
     *  1. pipeline                             pipeline
     *  2. push data                            push data
     *  4. Lazy                                 Lazy
     *  5. Zero, one, or more data              Zero, one, or more data
     *  Differences----------------------------------------------------------
     *  1. only data channel                    Three channels (Data, error, complete)
     *  2. Exceptions : good luck !!            Error is like data (Deal with it downstream)
     *  3. Sequential vs Parallel               Synchronous vs Asynchronous
     *  4. Single pipeline                      Multiple tree of subscribers
     *
     *
     *  Reactive streams        vs      Completable future
     *  ---------------------------------------------------
     *  1.Zero, one, more data          Zero or one data
     *  2. Three channels               Two channels
     *
     */

}
