package org.learning.core.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class Ch6_VirtualThreads {

    void main() {
        /*
            Many virtual threads run on a platform thread. Whenever a virtual thread blocks,
            it is unmounted, and the platform thread runs another virtual thread.

            Creating virtual thread
            1. newVirtualThreadPerTaskExecutor: runs each task in a separate virtual thread.
         */
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        service.submit(() -> {
            long id = Thread.currentThread().threadId();
            LockSupport.parkNanos(1_000_000_000);
            IO.println(id);
        });
        service.close();

    }
}
