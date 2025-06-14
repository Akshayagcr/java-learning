
# How `TreeMap` Works in Java – In-Depth Explanation

---

## 🌳 1. What is a `TreeMap`?

- `TreeMap<K,V>` is a **Map implementation** based on a **Red-Black Tree**, a self-balancing Binary Search Tree (BST).
- It maintains **sorted order** of keys (ascending by default or custom via `Comparator`).
- It is part of the `java.util` package and implements `NavigableMap<K,V>` and `SortedMap<K,V>`.

---

## 🧱 2. Internal Structure

- Uses a **Red-Black Tree** to store entries (`Entry<K,V>` objects).
- Each node maintains:
  - `key`
  - `value`
  - `left`, `right`, `parent` pointers
  - `color` (RED or BLACK)

### Red-Black Tree Properties:
1. Every node is either red or black.
2. The root is always black.
3. No two red nodes appear consecutively.
4. Every path from a node to null has the same number of black nodes.
5. New insertions are red by default and balanced by rotations.

---

## 🔁 3. How Operations Work

### `put(K key, V value)`:
- Performs a **binary search** using `compareTo()` or `Comparator`.
- If the key exists: replaces the value.
- If not: inserts a new node and then **rebalances** the tree to maintain Red-Black properties.

### `get(K key)`:
- Binary search tree traversal using comparison logic.

### `remove(K key)`:
- Searches for the node.
- Removes it using standard BST deletion logic.
- Rebalances tree to preserve Red-Black properties.

---

## 🧭 4. Key Ordering

- Default: natural ordering (`Comparable`) — e.g., `String`, `Integer`.
- Custom: via `Comparator<K>` passed in the constructor.
- All keys **must be mutually comparable**.

```java
TreeMap<Integer, String> map = new TreeMap<>();
TreeMap<Person, String> map = new TreeMap<>(Comparator.comparing(Person::getAge));
```

---

## ⚠️ 5. Null Handling

| Aspect         | Allowed? | Notes |
|----------------|----------|-------|
| Null key       | ❌ No     | Throws `NullPointerException` on `put(null, value)` |
| Null values    | ✅ Yes    | Multiple keys can map to null values |

Reason: Keys are compared during insertion, and `null` cannot be compared using `compareTo()`.

---

## ⏱️ 6. Time Complexity

| Operation | Time Complexity |
|-----------|------------------|
| `get()`   | O(log n)         |
| `put()`   | O(log n)         |
| `remove()`| O(log n)         |

---

## 🔄 7. Iteration Order

- **Always in ascending key order**.
- You can use `descendingMap()` for reverse iteration.
- Supports subviews like:
  - `headMap(toKey)`
  - `tailMap(fromKey)`
  - `subMap(fromKey, toKey)`

---

## 🧵 8. Thread Safety

- `TreeMap` is **not synchronized**.
- Use `Collections.synchronizedSortedMap()` or external synchronization.

---

## 🔍 9. TreeMap vs HashMap

| Feature         | `TreeMap`              | `HashMap`            |
|------------------|-------------------------|------------------------|
| Order            | Sorted (ascending)     | No order              |
| Implementation   | Red-Black Tree         | Hash Table            |
| Performance      | O(log n)               | O(1) average, O(n) worst |
| Null Key         | ❌ Not allowed          | ✅ One null key allowed |
| Null Values      | ✅ Allowed              | ✅ Allowed             |
| Thread-safe      | ❌ No                  | ❌ No                  |
