# Java Concurrency Utilities – Complete Guide (Java 8+)

Java's `java.util.concurrent` package offers a rich set of tools to coordinate, synchronize, and parallelize tasks in multithreaded applications.

---

## 🔹 Overview of Utilities

| Utility               | Use Case                                                   |
|-----------------------|------------------------------------------------------------|
| `Semaphore`           | Limit concurrent access (e.g., connection pool)            |
| `CountDownLatch`      | Wait until a set of threads complete                       |
| `CyclicBarrier`       | Wait until multiple threads reach a common barrier         |
| `Phaser`              | Dynamic and reusable version of `CyclicBarrier`            |
| `Exchanger`           | Data exchange between two threads                          |
| `StampedLock`         | Lightweight lock with optimistic reads                     |
| `CompletableFuture`   | Asynchronous and composable task execution (Java 8+)       |

---

## ✅ 1. `Semaphore`

Limits the number of threads accessing a resource.

```java
Semaphore semaphore = new Semaphore(2); // 2 permits

Runnable task = () -> {
    try {
        semaphore.acquire();
        System.out.println(Thread.currentThread().getName() + " acquired");
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    } finally {
        semaphore.release();
        System.out.println(Thread.currentThread().getName() + " released");
    }
};
```

### 🔸 Use Cases:
- Connection limits
- Throttling access to services

---

## ✅ 2. `CountDownLatch`

Waits until a set of threads have completed.

```java
CountDownLatch latch = new CountDownLatch(3);

for (int i = 0; i < 3; i++) {
    new Thread(() -> {
        System.out.println("Worker finished");
        latch.countDown();
    }).start();
}

latch.await(); // Waits until latch count is 0
System.out.println("Main thread proceeds");
```

### 🔸 Use Cases:
- One-time event coordination
- Wait for multiple services to start

---

## ✅ 3. `CyclicBarrier`

Allows multiple threads to wait until all have reached a barrier point.

```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> 
    System.out.println("All reached barrier"));

Runnable task = () -> {
    try {
        System.out.println(Thread.currentThread().getName() + " waiting");
        barrier.await();
        System.out.println(Thread.currentThread().getName() + " passed");
    } catch (Exception e) {}
};
```

### 🔸 Use Cases:
- Parallel algorithms
- Synchronizing phases

---

## ✅ 4. `Phaser`

Advanced barrier that supports dynamic thread registration and reuse across phases.

```java
Phaser phaser = new Phaser(1); // Register main thread

for (int i = 0; i < 3; i++) {
    phaser.register();
    int id = i;
    new Thread(() -> {
        System.out.println("Phase 1: Thread " + id);
        phaser.arriveAndAwaitAdvance();

        System.out.println("Phase 2: Thread " + id);
        phaser.arriveAndDeregister();
    }).start();
}

phaser.arriveAndAwaitAdvance(); // Main thread waits for phase 1
System.out.println("Main thread continues");
```

### 🔸 Use Cases:
- Multi-stage pipeline
- Task grouping across phases

---

## ✅ 5. `Exchanger`

Used for safe data exchange between two threads.

```java
Exchanger<String> exchanger = new Exchanger<>();

new Thread(() -> {
    try {
        String data = "From A";
        String response = exchanger.exchange(data);
        System.out.println("Thread A received: " + response);
    } catch (InterruptedException e) {}
}).start();

new Thread(() -> {
    try {
        String data = "From B";
        String response = exchanger.exchange(data);
        System.out.println("Thread B received: " + response);
    } catch (InterruptedException e) {}
}).start();
```

### 🔸 Use Cases:
- Buffer swapping
- Pipeline stages exchanging results

---

## ✅ 6. `StampedLock` (Java 8)

Advanced locking mechanism that supports optimistic reads.

```java
StampedLock lock = new StampedLock();
long stamp = lock.tryOptimisticRead();
int val = sharedResource;

if (!lock.validate(stamp)) {
    stamp = lock.readLock();
    try {
        val = sharedResource;
    } finally {
        lock.unlockRead(stamp);
    }
}
```

### 🔸 Use Cases:
- High-read/low-write environments
- Replacing `ReadWriteLock` with better performance

---

## ✅ 7. `CompletableFuture` (Java 8)

Asynchronous, non-blocking programming model.

```java
CompletableFuture.supplyAsync(() -> {
    return "Hello";
}).thenApply(str -> str + " World")
  .thenAccept(System.out::println);
```

### 🔸 Key Features:
- Async chaining: `thenApply`, `thenAccept`, `thenRun`
- Combine tasks: `thenCombine`, `allOf`, `anyOf`
- Exception handling: `exceptionally`, `handle`

---

## 🔧 Summary Table

| Utility            | Purpose                                   | Reusable | Blocking |
|--------------------|-------------------------------------------|----------|----------|
| `Semaphore`        | Limit concurrent access                   | ✔        | ✔        |
| `CountDownLatch`   | Wait for events                           | ❌       | ✔        |
| `CyclicBarrier`    | Wait until N threads reach a barrier      | ✔        | ✔        |
| `Phaser`           | Advanced multi-phase coordination         | ✔        | ✔        |
| `Exchanger`        | Thread-to-thread data exchange            | ✔        | ✔        |
| `StampedLock`      | Optimistic and fine-grained locking       | ✔        | ❌       |
| `CompletableFuture`| Async computation and chaining            | ✔        | ❌       |

---

## ✅ When to Use What?

| Problem                                   | Use This Utility       |
|-------------------------------------------|-------------------------|
| Limit number of threads accessing resource| `Semaphore`             |
| Wait for N workers to complete            | `CountDownLatch`        |
| All threads wait for each other           | `CyclicBarrier`         |
| Phased coordination across dynamic threads| `Phaser`                |
| Thread-to-thread buffer/data exchange     | `Exchanger`             |
| Optimized read/write concurrency          | `StampedLock`           |
| Async and reactive programming            | `CompletableFuture`     |

---

## 🔚 Final Thoughts

These utilities help model robust, scalable concurrent systems. Choose based on:
- One-time vs multi-use (`CountDownLatch` vs `CyclicBarrier`)
- Static vs dynamic participation (`CyclicBarrier` vs `Phaser`)
- Blocking vs non-blocking needs
- Task chaining (`CompletableFuture` for async pipelines)

---
