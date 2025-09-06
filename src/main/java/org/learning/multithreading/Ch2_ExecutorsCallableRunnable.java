package org.learning.multithreading;

import java.util.concurrent.*;

public class Ch2_ExecutorsCallableRunnable {

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("Run");
        }
    };

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

    {
        Future<Integer> future = cachedThreadPool.submit(callable);
        future.isDone();

        try {
            future.get();
            future.get(123, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }
}
