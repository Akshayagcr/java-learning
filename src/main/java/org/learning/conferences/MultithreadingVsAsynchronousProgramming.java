package org.learning.conferences;

/**
 * Multithreading vs Asynchronous Programming: The Architectural Shift by Venkat Subramaniam : https://www.youtube.com/watch?v=GY7aFOjlHtI
 */
public class MultithreadingVsAsynchronousProgramming {

    /*
        java 1 : threads
        java 5 : Executor service : Pool induced deadlock
        java 7 : ForkJoinPool : solves pool induced deadlock by work stealing

        Structure of imperative style parallel code is completely different from imperative style sequential code

        Parallel vs concurrent
        parallel : Tasks can execute at exact same time
        concurrent : Task executes alternately. but cannot execute at exact same time

        Asynchronous : non-blocking call

        When a task request data. It needs to wait for the data
        And when a task waits for data. the thread also waits for the data

        blocking vs non-blocking is about the thread of execution


        blocking vs non-blocking is an architectural concern as it directly affects scalability i.e. no of request you can serve. if we use blocking code then if we need to scale
        then we need to use a cluster of machines which further complicates the architecture. and the reason is that thread blocks waiting and we can only have a small number of threads per machine

        java 8 : Streams : functional style parallel code is same as functional style sequential code stream() vs stream().parallel()


        Asynchronous programming

        var completeAbleFuture = CompletableFuture.supplyAsync(() -> blockingCall)

        completeAbleFuture.thenApply(data -> processData)
        .theAccept(processedData -> performAction)

        java 8 : CompletableFuture : Functional style asynchronous code is same as functional style synchronous code


        Functional programming is awesome when your functions are pure and have no side effects

        If your code has side effects and exception, functional programming is not ideal

        java 21 : virtual threads  structure of imperative style synchronous code is equal to imperative style asynchronous code

        From java 21, java has two types of threads 1: carrier threads(OS threads) 2: virtual threads(Created and maintained JVM)
        We can have n number of virtual thread which gets mounted and unmounted on carries/os threads
     */
}
