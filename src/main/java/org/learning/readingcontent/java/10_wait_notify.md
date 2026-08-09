# 🔐 Java Thread Synchronization Using synchronized, wait(), notify(), notifyAll() — Full Guide with Examples

This guide covers how to use Java’s intrinsic monitor methods: `synchronized`, `wait()`, `notify()`, and `notifyAll()` for thread communication and coordination. These tools form the foundation of multithreading in Java, enabling safe access to shared resources and inter-thread signaling.

## 📌 synchronized Keyword

`synchronized` is used to enforce **mutual exclusion** — ensuring only one thread executes a block of code or accesses a method at a time for a given object or class.

### Synchronized Instance Method

public synchronized void increment() {
count++;
}

This ensures that only one thread at a time can call `increment()` on the same object.

### Synchronized Block

synchronized (lock) {
// critical section
}

This allows finer control by locking only the needed code using any object as the lock.

### Synchronized Static Method

public static synchronized void staticMethod() {
// class-level lock
}

Locks on the `Class` object, i.e., `ClassName.class`.

## 🧵 wait(), notify(), notifyAll()

These methods are used for **thread signaling** — one thread can pause execution until another thread sends a signal.

They must always be called **inside a synchronized block or method**, and are defined in `java.lang.Object`, not `Thread`.

### wait()

Releases the lock and causes the thread to wait until another thread calls `notify()` or `notifyAll()` on the same object.

### notify()

Wakes up one thread waiting on the object’s monitor.

### notifyAll()

Wakes up all threads waiting on the object’s monitor.

## ⚠️ Rules & Constraints

- You must call `wait()`, `notify()`, or `notifyAll()` only from within a synchronized context.
- All threads must be synchronized on the same monitor object.
- `wait()` releases the lock on the monitor and suspends the thread.
- After `notify()`/`notifyAll()`, awakened threads must reacquire the lock to continue.

## 📦 Classic Example: Producer-Consumer with wait/notify

class SharedQueue {
private final Queue<Integer> queue = new LinkedList<>();
private final int capacity = 5;

    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == capacity) {
            wait(); // wait until space is available
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notify(); // notify a waiting consumer
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // wait until item is available
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notify(); // notify a waiting producer
        return item;
    }
}

class Producer extends Thread {
private final SharedQueue queue;

    public Producer(SharedQueue queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                queue.produce(i);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer extends Thread {
private final SharedQueue queue;

    public Consumer(SharedQueue queue) {
        this.queue = queue;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                queue.consume();
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class WaitNotifyExample {
public static void main(String[] args) {
SharedQueue queue = new SharedQueue();
new Producer(queue).start();
new Consumer(queue).start();
}
}

## 🔄 wait vs sleep

| Feature         | wait()                                | sleep()                          |
|----------------|----------------------------------------|----------------------------------|
| Defined in      | Object                                | Thread                           |
| Monitor lock    | Releases it                           | Holds it                         |
| Used for        | Inter-thread communication            | Pausing a thread                 |
| Must be called from synchronized block | ✅ Yes         | ❌ No                            |

## 🧠 Best Practices

- Always use `wait()` in a loop to re-check the condition after being notified:

while (!condition) {
wait();
}

- Prefer `notifyAll()` over `notify()` when multiple threads could be waiting for different conditions.
- Always synchronize on the same object that is used for `wait()` and `notify()`.

## 🚫 Common Mistakes

- Calling `wait()` or `notify()` outside a synchronized block → throws `IllegalMonitorStateException`.
- Using `notify()` when multiple threads are waiting → can result in missed signals.
- Not re-checking the condition after waking up → causes logic bugs due to spurious wakeups.

## 🔍 Summary

| Keyword / Method | Purpose                                  |
|------------------|------------------------------------------|
| synchronized     | Mutual exclusion                         |
| wait()           | Thread waits and releases monitor lock   |
| notify()         | Wakes one waiting thread                 |
| notifyAll()      | Wakes all waiting threads                |

Java's `synchronized` with `wait()/notify()` is simple but powerful. For complex coordination with multiple conditions or high performance, use `Lock` and `Condition` from `java.util.concurrent.locks`.

Use `synchronized` and monitor methods when:
- Coordination is simple
- Only one condition is needed
- You want simplicity without extra classes
