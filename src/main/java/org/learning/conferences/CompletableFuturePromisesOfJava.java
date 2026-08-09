package org.learning.conferences;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/*
    https://www.youtube.com/watch?v=9ueIL0SwEWI
 */
public class CompletableFuturePromisesOfJava {

    /*

        JavaScript promise
            1. Has three states 1. resolve, 2. reject and 3. pending
                resolved and Rejected are permanant state. once in that state it does not changes
            2. Has data and error channel
            3. error treated as data
            4. easily chainable

        CompletableFuture in java are same as Promise in JavaScript


            CompletableFuture has stages
            stage is a section of a pipeline
            each stage take CompletableFuture and returns a CompletableFuture

            CompletableFuture introduced in Java 8

            Executes the code in ForkJoinPool

            CompletableFuture can have at most one data or an error
     */

    void main() throws ExecutionException, InterruptedException {
        // Create CompletableFuture. It is a supplier.
        CompletableFuture<Integer> comfOne = CompletableFuture
                .supplyAsync(() -> 1);  // We can also pass our custom pool

        // Equivalent to map in stream
        CompletableFuture<Integer> comfTwo = comfOne.thenApply(data -> data * 2);

        // It is a consumer and equivalent to foreach in steams
        CompletableFuture<Void> comfThree = comfTwo.thenAccept(data -> IO.println(data));

        // comfThree.thenApply(data -> IO.println(data)); As comfThree is CompletableFuture of void we cannot use thenApply
        // However we can use Runnable by using theRun()
        CompletableFuture<Void> comfFour = comfThree.thenRun(() -> IO.println("done"));


        /*
            Get result from CompletableFuture but get() is a blocking call
         */
        comfOne.get();
        comfOne.getNow(123); // If value is present return it or else return the default value

        /*
            Thread of execution depends on the computation result
            i.e. if result is already available then there is no need to switch thread main thread executes
            if result is not available then computation is handled to a different thread

            CompletableFuture<Void> gives us the information whether the operation was success or not

            We can create a pipeline which can be trigerred later
         */
        CompletableFuture<Integer> cf = new CompletableFuture<>();

        cf
                .thenApply(data -> data * 2)
                .thenApply(data -> data * 4)
                .thenAccept(data -> IO.println(data));

        cf.complete(2);

        /*
            Handle exception
                All methods starting with then....() are success method
                If a completable future succeeds it goes to nearest then....() method
                exceptionally() method is used to handle exception
                    If exception happens we find nearest exceptionally method

         */
        var cf2 = new CompletableFuture<Integer>();

        cf2.exceptionally(throwable -> handleOne(throwable))
                .thenApply(data -> data * 2)
                .exceptionally(throwable -> handleTwo(throwable))
                .thenApply(data -> data * 3)
                .thenAccept(data -> IO.println(data));

        /*
            First it goes to handleOne which fails to handle exception.
            so it goes directly to handleTwo skipping between processing stages i.e. multiply by 2 step
            and later the processing continues from multiplying 3
         */
        cf2.completeExceptionally(new RuntimeException("Source exception"));

        /*
            Succeed on time:
            How long should we wait in pending state for a CompletableFuture
            below code wait until timeout for CompletableFuture to resolve if it does not resolve then the pipeline is triggered with default value.
            cf.completeOnTimeout("Default value", 5, TimeUnit.SECONDS);

            If future does not resolve withing the provided timeout period then throw TimeoutException
            cf.orTimeout(5, TimeUnit.SECONDS);

            Combine: Trigger two async operation together and then wait for both to complete.
                on-completion apply the merge function to result of both futures.
                It like zip operator where we zip the result together

            cfOne.thenCombine(getSecondCompletableFuture(), (resultOne, resultTwo) -> new combinedResult(resultOne, resultTwo))
                    .thenApply(data -> process(data))

            In JavaScript then(... fun()) always return a promise
            So if function return data it is wrapped in a promise. but if function return promise it is returned as it is
            Due to dynamic nature of JS. But in java it's a problem as if a method returns a CompletableFuture then it will be wrapped in another CompletableFuture
            To solve above issue there exists two method thenApply/thenAccept for data and thenCompose for CompletableFuture

            Compose: Use when method returns CompletableFuture.

                Bad example:-
                cfOne.thenApply(data -> getAnotherCf(data))
                        .thenApply(data -> IO.print(data)) // Here data is actually CompletableFuture !!!!!

                Fixed example:-
                cfOne.thenCompose(data -> getAnotherCf(data))
                        .thenApply(data -> IO.print(data))

            We need to used thenApply when we have data and use thenCompose when we have CompletableFuture
            i.e. use compose when we have chOne whose data we take and pass it to method whose result is also CF and the need to wait for result of that CF in next sage
            It like flatmap in stream
         */

    }

    private static Integer handleOne(Throwable throwable) {
        IO.println("Inside handleOne");
        throw new RuntimeException("Fail to handle");
    }

    private static Integer handleTwo(Throwable throwable) {
        IO.println("Inside handleTwo");
        return 2;
    }

}
