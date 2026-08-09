package org.learning.core.multithreading;

import java.util.concurrent.*;

public class Ch4_Synchronizes {

    // CountDownLatch
    {
        CountDownLatch countDownLatch = new CountDownLatch(3); // Initialized with count 3
        try {
            // Threads calling await goes in WAITING state until countdown reaches zero
            countDownLatch.await();
            countDownLatch.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // Decrement count (Once count reaches zero it cannot be reset)
        countDownLatch.countDown();
    }

    // CyclicBarrier
    {
        // Initialize with number of threads that need to co-ordinate and -
        // barrier action which gets triggered once all thread reaches barrier, but before threads are released
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("BarrierAction"));
        try {
            // Threads calling await goes in WAITING state until all thread reaches barrier
            // Once reached all threads are moved to RUNNABLE state and barrier is reset
            cyclicBarrier.await();
            cyclicBarrier.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | BrokenBarrierException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    // Semaphore
    {
        // Initialized with number of permits
        Semaphore semaphore = new Semaphore(10);

        try {
            // Acquire permit to enter critical section
            semaphore.acquire();
            semaphore.acquire(2);
            boolean result = semaphore.tryAcquire();
            boolean result2 = semaphore.tryAcquire(2, 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        semaphore.release();
        semaphore.release(2);
    }

    // Phaser
    {
        Phaser phaser = new Phaser(1);
    }

    // Exchanger
    {
        Exchanger<Integer> exchanger = new Exchanger<>();
    }

}
