package org.learning.multithreading;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

public class ConcurrencyBasics {

    // Concurrency primitive
    {
        // volatile, synchronize
        var obj = new Object();
        try {
            obj.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        obj.notify();
        obj.notifyAll();
    }

    // Executor service, Runnable, Callable, Future, CompletableFuture
    {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Runnable runnable = () -> {};
        Callable<Integer> callable = () -> 1;
    }

    // Locks, Latches, Barriers, Semaphore, Phaser
    {
        Lock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        CountDownLatch countDownLatch = new CountDownLatch(2);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, System.out::println);

        Semaphore semaphore = new Semaphore(3);

        Phaser phaser = new Phaser(3);
    }

    // Atomics, ThreadLocal, Exchanger
    {
        AtomicInteger i = new AtomicInteger(10);
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        Exchanger exchanger = new Exchanger<Integer>();
    }

    // Concurrent collections
    {
        BlockingQueue<Integer> q = new ArrayBlockingQueue<>(10);
        BlockingDeque<Integer> d = new LinkedBlockingDeque<>();
        TransferQueue<Integer> tq = new LinkedTransferQueue<>();
        ConcurrentMap<Integer, Integer> cm = new ConcurrentHashMap<>();
        ConcurrentNavigableMap<Integer, Integer> cnm = new ConcurrentSkipListMap<>();
    }
}
