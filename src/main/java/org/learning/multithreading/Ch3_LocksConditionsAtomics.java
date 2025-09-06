package org.learning.multithreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Ch3_LocksConditionsAtomics {
    /*
        Locks & condition
        Atomics
        ThreadLocal
     */
    {
        Lock reentrantLock = new ReentrantLock();
        Condition firstCondition = reentrantLock.newCondition();

        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
    }
}
