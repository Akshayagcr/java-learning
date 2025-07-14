# 🔒 Signaling in Java Using Lock and Condition (Detailed Guide with Examples)

This guide covers the use of `Lock` and `Condition` from `java.util.concurrent.locks` in Java for thread signaling — a powerful alternative to traditional `synchronized`, `wait()`, and `notify()` mechanisms. This approach is useful when you need precise control over multiple threads waiting for different conditions.

## 🧠 Why Not Just Use wait/notify?

Before Java 5, thread signaling used:

synchronized (lock) {
while (!condition) {
lock.wait();
}
lock.notify();
}

However, this has limitations:

- Only one wait set per lock object
- No fairness or interrupt handling
- notify() can randomly wake threads that don’t meet the condition
- No support for multiple waiting conditions per lock

To overcome this, Java provides:

- `Lock`: an explicit locking mechanism
- `Condition`: a signaling mechanism associated with a `Lock`

These are part of `java.util.concurrent.locks`.

## ✅ Core Concepts

### Lock

An interface offering greater flexibility than `synchronized`. The most used implementation is `ReentrantLock`.

Example:

Lock lock = new ReentrantLock();

### Condition

A condition variable that threads can await or signal. You can create multiple conditions for the same lock:

Condition condition = lock.newCondition();

Each `Condition` acts like a separate wait set. Threads can wait using `await()` and signal using `signal()` or `signalAll()`.

## ⚙️ Basic Usage

### Waiting Thread

lock.lock();
try {
while (!someCondition) {
condition.await(); // releases lock, waits to be signaled
}
// proceed with work
} finally {
lock.unlock();
}

### Signaling Thread

lock.lock();
try {
// update shared state
condition.signal(); // or condition.signalAll()
} finally {
lock.unlock();
}

## 💡 Key Methods of Condition

- await() — causes the current thread to wait
- awaitNanos(nanosTimeout), await(time, unit) — wait with timeout
- awaitUninterruptibly() — wait ignoring interrupts
- signal() — wakes one waiting thread
- signalAll() — wakes all waiting threads

## 🔄 Lock vs Synchronized

| Feature                         | synchronized + wait/notify | Lock + Condition         |
|----------------------------------|-----------------------------|---------------------------|
| Multiple condition queues        | ❌ No                       | ✅ Yes                    |
| Interruptible wait               | ❌ No                       | ✅ Yes                    |
| Timeout support                  | ✅ Limited                  | ✅ Full                   |
| Explicit unlocking               | ❌ Implicit                 | ✅ Required               |
| Fairness support                 | ❌ No                       | ✅ With ReentrantLock(true) |

## 🧪 Full Example: Custom BlockingQueue Using Lock and Condition

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.*;

public class BlockingQueue<T> {
private final Queue<T> queue = new LinkedList<>();
private final int capacity;
private final Lock lock = new ReentrantLock();
private final Condition notFull = lock.newCondition();
private final Condition notEmpty = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await(); // wait until not full
            }
            queue.add(item);
            notEmpty.signal(); // signal consumer
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await(); // wait until not empty
            }
            T item = queue.poll();
            notFull.signal(); // signal producer
            return item;
        } finally {
            lock.unlock();
        }
    }
}

## 🛡️ Best Practices

- Always use await() in a loop:
  while (!condition) {
  condition.await();
  }

- Always unlock in a finally block:
  lock.lock();
  try {
  // critical section
  } finally {
  lock.unlock();
  }

- Use signalAll() if multiple threads may be waiting for the same condition.
- Prefer multiple Condition instances if different threads block on different criteria (e.g., producers vs consumers).

## 🔍 Advanced Usage Tips

- Use ReentrantLock(true) for fair FIFO locking:
  Lock lock = new ReentrantLock(true);

- Prefer await(time, unit) for bounded waiting when applicable.

- You can replace wait/notifyAll-based blocking data structures (queues, pools) using Lock + Condition for higher performance and clarity.

## 🧭 Summary

Lock and Condition provide a more flexible, scalable, and readable way to coordinate threads than the traditional synchronized block with wait/notify.

Use Lock + Condition when:
- You need multiple independent condition queues
- You require fairness, interruptibility, or timeouts
- You want to build custom concurrent data structures like bounded queues, semaphores, etc.

This is the recommended signaling approach in modern concurrent Java.
