
# How `HashMap` Works in Java – In-Depth Explanation

---

## 🧱 1. Internal Structure of HashMap
- `HashMap` is a part of Java’s `java.util` package.
- It stores **key-value pairs**.
- Internally uses an **array of buckets**, where each bucket is a `Node<K, V>` (before Java 8) or `TreeNode<K, V>` (after Java 8 under certain conditions).

```java
transient Node<K,V>[] table; // Array of buckets
```

Each entry in the `HashMap` is stored in a `Node` object:
```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    V value;
    Node<K,V> next;
}
```

---

## 🔢 2. Hashing Mechanism

When you call `put(key, value)`, here's what happens:

1. **Hash Calculation**:
   ```java
   int hash = hash(key);
   ```
   `HashMap` uses a hash function that spreads the hash to avoid poor distribution:
   ```java
   static final int hash(Object key) {
       int h;
       return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
   }
   ```

2. **Index Calculation**:
   ```java
   int index = (n - 1) & hash;
   ```
   where `n` is the array length. This ensures the index falls within bounds.

---

## 🤝 3. Handling Collisions

A collision occurs when two keys have the same bucket index.

### Java 7 and earlier:
- Uses **Linked List** in each bucket.
- Performance degrades to O(n) if many keys land in the same bucket.

### Java 8 and later:
- Uses **Linked List**, but if the list becomes too long (`> TREEIFY_THRESHOLD`, usually 8), it converts to a **Red-Black Tree** for O(log n) performance.

---

## 🧼 4. Resizing (Rehashing)

When the number of entries exceeds the **load factor threshold**:
- Default load factor = 0.75
- Default capacity = 16

So, resizing occurs when 16 × 0.75 = 12 entries are inserted.

On resizing:
- A new array (2× size) is created.
- All existing entries are **rehashed** and reinserted.

This is a costly operation — try to set an appropriate initial capacity if you know the size in advance.

---

## ❓ 5. How `null` Key and Values Work

### `null` key:
- Allowed in `HashMap`.
- Only **one** `null` key is permitted.
- It is always mapped to **index 0**, as:
  ```java
  hash(null) = 0
  ```

Example:
```java
map.put(null, "value"); // stored at index 0
```

### `null` values:
- Allowed in `HashMap`.
- Multiple keys can map to `null` values.

Example:
```java
map.put("a", null);
map.put("b", null);
```

---

## ⚙️ 6. `get()` Method Logic

When you do `map.get(key)`:

1. Calculate hash and index.
2. Traverse the chain (linked list or tree) at that index.
3. Use `equals()` to match the key.
4. Return value if found; else `null`.

---

## ⏱️ 7. Time Complexity

| Operation   | Average Case | Worst Case |
|-------------|--------------|------------|
| `get()`     | O(1)         | O(n) or O(log n) (tree) |
| `put()`     | O(1)         | O(n) or O(log n) |
| `remove()`  | O(1)         | O(n) or O(log n) |

---

## 🚫 8. Thread Safety

- **Not thread-safe** by default.
- Use `Collections.synchronizedMap()` or `ConcurrentHashMap` for thread-safe operations.

---

## ✅ Summary

| Feature            | Supported in `HashMap` |
|--------------------|------------------------|
| Null key           | ✅ Yes (only one)       |
| Null values        | ✅ Yes (multiple)       |
| Maintains order    | ❌ No                   |
| Thread-safe        | ❌ No                   |
| Allows duplicates  | ❌ No (unique keys)     |
| Backed by          | Array + Linked List / Tree |
