package org.learning.core.multithreading;

/*
    happens before relation : If a thread updates a shared variable then the updates performed by -
    first thread will be visible to other threads
 */
public class Ch1_ConcurrencyPrimitives {

    /*
        Updates to volatile field are visible to other threads
     */
    volatile String var;

    /*
        Only one thread is allowed at a time to enter critical section.
     */
    synchronized void synchronizedMethod(){

        // synchronized block
        synchronized (this){
        }
    }

    Object conditionOne = new Object();

    void testCondition(){
        try {
            // Multiple threads can be blocked/waiting
            conditionOne.wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Move one thread from waiting to runnable
        conditionOne.notify();

        // Move all thread from waiting to runnable
        conditionOne.notifyAll();
    }

    void testThread(){
        /*
            Thread state
            NEW             : Thread which has not started
            RUNNABLE        : Thread which is running
            BLOCKED         : Thread waiting for monitor lock
            WAITING         : Thread waiting for another thread to perform action
            TIMED_WAITING   : Thread waiting for another thread only for a specified time period
            TERMINATED      : Thread which has exited

         */
        var threadState = Thread.State.NEW;

        try {
            // causes current executing thread to sleep
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Thread t1 = new Thread(()-> System.out.println("Hello"));

        try {
            // Causes current thread wait for thread t1 to complete or wait maximum for 1000 milli sec
            t1.join(1000L);
            // Causes current thread wait for thread t1 to complete
            t1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
