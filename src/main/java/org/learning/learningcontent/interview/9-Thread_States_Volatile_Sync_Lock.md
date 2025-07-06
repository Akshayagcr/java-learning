# Java Concurrency Deep Dive

This guide covers three essential topics in Java concurrency:

1. States of a Thread
2. `volatile` Keyword
3. `synchronized` vs `Lock`

---

## 1️⃣ States of a Thread

Java threads go through a well-defined lifecycle. These states are defined in `java.lang.Thread.State` enum.

### 🔄 Thread Lifecycle States

| State             | Description |
|-------------------|-------------|
| `NEW`             | Thread is created but not started yet. |
| `RUNNABLE`        | Thread is ready to run and waiting for CPU time. |
| `BLOCKED`         | Waiting to acquire a monitor lock (another thread is holding it). |
| `WAITING`         | Waiting indefinitely for another thread to perform a specific action. |
| `TIMED_WAITING`   | Waiting for a limited time (`sleep`, `join(timeout)`, `wait(timeout)`). |
| `TERMINATED`      | Thread has completed execution or was terminated due to an error. |

### ✅ Example:
```java
Thread t = new Thread(() -> System.out.println("Running"));
System.out.println(t.getState()); // NEW
t.start();
System.out.println(t.getState()); // RUNNABLE or TERMINATED (if fast)
```

---

## 2️⃣ `volatile` Keyword

### 🔹 Purpose:
- Ensures **visibility** of changes to variables across threads.
- Prevents threads from **caching variables locally**.

### ✅ Use Case:
```java
volatile boolean flag = true;

Thread t = new Thread(() -> {
    while (flag) {
        // busy-wait
    }
});
t.start();

// Another thread
flag = false; // Volatile makes this immediately visible to t
```

### ⚠️ Limitations:
- Does **not guarantee atomicity** (e.g., `count++` is not thread-safe).
- Does **not lock**; suitable only for simple signaling or state flags.

### ✅ When to Use:
- For visibility between threads.
- For boolean flags or status fields where atomic updates aren't required.

---

## 3️⃣ `synchronized` vs `Lock`

Both are used to implement **mutual exclusion** in concurrent programs but differ in capabilities.

---

### 🔸 `synchronized` Keyword

| Feature              | Description |
|----------------------|-------------|
| Simplicity           | Built-in, easy to use |
| Locking Mechanism    | Implicit monitor lock |
| Reentrancy           | ✔️ Allowed |
| Interrupt Support    | ❌ Not interruptible |
| Timeout Support      | ❌ No timeout |
| Fairness             | ❌ No fairness guarantee |

### ✅ Examples:
Block-level:
```java
synchronized(this) {
    // critical section
}
```
Method-level:
```java
public synchronized void increment() {
    count++;
}
```

---

### 🔸 `Lock` Interface (from `java.util.concurrent.locks`)

| Feature              | Description |
|----------------------|-------------|
| Flexibility          | ✔️ Explicit locking and unlocking |
| Try Lock             | ✔️ `tryLock()` with optional timeout |
| Interruptible        | ✔️ `lockInterruptibly()` |
| Read/Write Locks     | ✔️ via `ReadWriteLock` |
| Fairness             | ✔️ Optional fairness constructor |
| Manual Unlocking     | Required – must call `unlock()` in `finally` block |

### ✅ Example:
```java
Lock lock = new ReentrantLock();

lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

---

## 🆚 Comparison Table: `synchronized` vs `Lock`

| Feature                  | `synchronized`           | `Lock`                        |
|--------------------------|--------------------------|-------------------------------|
| Lock Type                | Intrinsic (monitor)      | Explicit (`ReentrantLock`)    |
| Unlocking                | Automatic                | Manual                        |
| Interruptible Lock       | ❌ No                    | ✔️ Yes                        |
| Timeout Support          | ❌ No                    | ✔️ `tryLock(timeout)`         |
| Fairness                 | ❌ No                    | ✔️ Optional                   |
| Read/Write Separation    | ❌ No                    | ✔️ `ReadWriteLock`            |
| Reentrancy               | ✔️ Yes                   | ✔️ Yes                        |
| Error Prone              | Low                      | Higher (manual unlock needed) |
| Performance              | Good for simple locking  | Better for advanced control   |

---

## ✅ When to Use What?

| Scenario                                 | Prefer |
|------------------------------------------|--------|
| Basic mutual exclusion                   | `synchronized` |
| Require timeout, interruption, fairness  | `Lock`         |
| Read-heavy scenarios                     | `ReadWriteLock` |
| Fine-grained control or multiple locks   | `Lock`         |
| Avoiding deadlocks with `tryLock()`      | `Lock`         |

---
