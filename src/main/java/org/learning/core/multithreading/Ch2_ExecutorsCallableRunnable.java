package org.learning.core.multithreading;

import java.util.List;
import java.util.concurrent.*;

public class Ch2_ExecutorsCallableRunnable {

    // Since java 1 : runnable
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("Run");
        }
    };

    // Since java 5 : callable
    Callable<Integer> callable = new Callable<Integer>() {
        @Override
        public Integer call() throws Exception {
            return 0;
        }
    };

    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    ExecutorService workStealingPool = Executors.newWorkStealingPool();

    ExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
    ExecutorService newVirtualThreadPerTaskExecutor = Executors.newVirtualThreadPerTaskExecutor();

    {
        Future<Integer> future = cachedThreadPool.submit(callable);
        future.isDone();

        try {
            future.get();   // Blocking !!!!!!
            future.get(123, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    // ************ CompletableFuture ************
    {
        /*
            Creating future
         */

        // Below can be used in testcases
        CompletableFuture<Integer> completedFuture = CompletableFuture.completedFuture(10);
        CompletableFuture<Integer> failedFuture = CompletableFuture.failedFuture(new RuntimeException());

        // Create CompletableFuture after performing some action
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            IO.println("Preformed so action");
        });
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            IO.println("Preformed so action");
            return 10;
        });

        // Create CompletableFuture pipeline
        CompletableFuture<Integer> completableFuturePipeline = new CompletableFuture<>();
        completableFuturePipeline
                .thenApply(data -> data * 2)
                .thenApply(data -> data * 4)
                .thenAccept(data -> IO.println(data));

        completableFuturePipeline.complete(10);
        completableFuturePipeline.completeExceptionally(new RuntimeException());

        /*
            CompletableFuture API and IMP methods
         */
        // Core success methods 1. thenApply(Function), 2. thenAccept(Consumer) 3. thenRun(Runnable)
        CompletableFuture<Integer> r1 = getCompletableFuture(1)
                .thenApply(data -> data * 2); // Function

        CompletableFuture<Void> r2 = getCompletableFuture(1)
                .thenAccept(data -> IO.println(data)); // Consumer

        CompletableFuture<Void> r3 = getCompletableFuture(1)
                .thenRun(() -> {
                    IO.println("Preformed so action");
                }); // Runnable

        // thenCompose is flatMap. It unwraps the nested future, producing a flat chain
        CompletableFuture<Integer> r4 = getCompletableFuture(10)
                .thenCompose(data -> incrementAndGetCompletableFuture(data))
                .thenCompose(data -> doubleAndGetCompletableFuture(data));

        /*
            Both transformation methods
         */
        CompletableFuture<Integer> r5 = getCompletableFuture(10)
                .thenCombine(getCompletableFuture(20), (data1, data2) -> data1 + data2);

        CompletableFuture<Void> r6 = getCompletableFuture(10)
                .thenAcceptBoth(getCompletableFuture(20), (data1, data2) -> IO.println(data1 + data2));

        CompletableFuture<Void> r7 = getCompletableFuture(10)
                .runAfterBoth(getCompletableFuture(20), () -> IO.println("Done processing both"));

        /*
            Either transformation methods
         */
        CompletableFuture<Integer> r8 = getCompletableFuture(10)
                .applyToEither(getCompletableFuture(20), (data) -> data * data);

        CompletableFuture<Void> r9 = getCompletableFuture(10)
                .acceptEither(getCompletableFuture(20), (data) -> IO.println(data));

        CompletableFuture<Void> r10 = getCompletableFuture(10)
                .runAfterEither(getCompletableFuture(20), () -> IO.println("Done processing both"));

        /*
            Combining multiple with allOf and anyOf
         */

        var listOfFutures = List.of(getCompletableFuture(10),
                getCompletableFuture(20),
                getCompletableFuture(30));

        CompletableFuture<Void> combined = CompletableFuture.allOf(listOfFutures.toArray(new CompletableFuture[0]));

        // Handle result asynchronously
        CompletableFuture<List<Integer>> combinedResult = combined.thenApply(_ -> {
            return listOfFutures.stream()
                    .map(CompletableFuture::join)   // safe — all are complete at this point
                    .toList();
        });

        // Get result back from CompletableFuture
        List<Integer> result = combinedResult.join(); // Blocking !!!!!!!!
        // We can also use combinedResult.get() which is also blocking and also throws Checked exception!!!

        // Any one which succeeds first will be the result
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(listOfFutures.toArray(new CompletableFuture[0]));

        /*
            exceptionally method is used for exceptional handling
            handle method take both data and throwable.

            There are variant of all method
                1. Async:- where the computation will run in a different thread
                2. Takes custom executor as a parameter

            TODO: Refer conferences.CompletableFuturePromisesOfJava for more.
         */
    }

    private static CompletableFuture<Integer> getCompletableFuture(int data) {
        return CompletableFuture.supplyAsync(() -> data);
    }

    private static CompletableFuture<Integer> incrementAndGetCompletableFuture(int data) {
        return CompletableFuture.supplyAsync(() -> data + 1);
    }

    private static CompletableFuture<Integer> doubleAndGetCompletableFuture(int data) {
        return CompletableFuture.supplyAsync(() -> data * 2);
    }
}
