# Java Concurrency: Runnable, Callable, Future, CompletableFuture with ExecutorService

Java provides a rich concurrency API to run tasks asynchronously. This includes:

- `Runnable`
- `Callable`
- `Future`
- `CompletableFuture`
- `ExecutorService`

---

## 🔹 `Runnable`

- Represents a task that **does not return** a result.
- Has a single method: `run()`.

### ✅ Example:
```java
Runnable task = () -> System.out.println("Running in thread: " + Thread.currentThread().getName());
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.submit(task);
executor.shutdown();
```

---

## 🔹 `Callable<V>`

- Represents a task that **returns a result** and can throw exceptions.
- Has a single method: `call()`.

### ✅ Example:
```java
Callable<String> task = () -> {
    Thread.sleep(1000);
    return "Result from " + Thread.currentThread().getName();
};

ExecutorService executor = Executors.newFixedThreadPool(1);
Future<String> future = executor.submit(task);
String result = future.get(); // Blocks until result is available
executor.shutdown();
System.out.println(result);
```

---

## 🔹 `Future<V>`

- Represents the result of an asynchronous computation.
- Returned by submitting `Callable` or `Runnable` to `ExecutorService`.

### ✅ Key Methods:
| Method          | Description                      |
|----------------|----------------------------------|
| `get()`        | Waits for task completion and returns result |
| `get(timeout)` | Waits up to timeout              |
| `isDone()`     | Checks if task is completed      |
| `cancel()`     | Cancels the task                 |

### ❗ Limitation:
- `get()` blocks until the result is available.
- No chaining, combining, or async result handling.

---

## 🔹 `CompletableFuture<T>`

- Introduced in Java 8.
- Represents a **future** result that can be **manually completed**, or created from a task.
- Supports **non-blocking**, **chained** operations.

### ✅ Creating with `supplyAsync()`:
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return "Hello from " + Thread.currentThread().getName();
});
future.thenAccept(result -> System.out.println("Result: " + result));
```

### ✅ Chaining Tasks:
```java
CompletableFuture.supplyAsync(() -> 5)
    .thenApply(x -> x * 2)
    .thenApply(x -> "Result: " + x)
    .thenAccept(System.out::println);
```

### ✅ Combining Tasks:
```java
CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 10);
CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 20);

CompletableFuture<Integer> combined = future1.thenCombine(future2, Integer::sum);
combined.thenAccept(System.out::println); // 30
```

---

## 🔄 `Future` vs `CompletableFuture`

| Feature                  | `Future`                    | `CompletableFuture`            |
|--------------------------|-----------------------------|--------------------------------|
| Result Retrieval         | `get()` blocks               | Async and non-blocking options |
| Task Chaining            | ❌ Not supported             | ✔️ `thenApply`, `thenAccept`, etc |
| Combination of Results   | ❌ Not supported             | ✔️ `thenCombine`, `allOf`, etc |
| Manual Completion        | ❌ No                        | ✔️ Yes (`complete()`)          |
| Exception Handling       | Basic (`get()` throws)      | Advanced (`exceptionally()`)   |
| Introduced In            | Java 5                      | Java 8                         |

---

## 🧪 Example: Using `ExecutorService` with `CompletableFuture`

```java
ExecutorService executor = Executors.newFixedThreadPool(2);

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    try { Thread.sleep(1000); } catch (InterruptedException e) {}
    return "Async result";
}, executor);

future.thenAccept(System.out::println); // Non-blocking

executor.shutdown();
```

---

## ✅ When to Use What?

| Use Case                          | Preferred Option        |
|----------------------------------|-------------------------|
| Fire-and-forget task             | `Runnable`              |
| Task returns result              | `Callable` + `Future`   |
| Chaining and combining tasks     | `CompletableFuture`     |
| Advanced async flow control      | `CompletableFuture`     |
| Legacy API or simple usage       | `Future`                |

---