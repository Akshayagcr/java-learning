# Comparator vs Comparable in Java – Detailed Explanation

Java provides two interfaces to allow **custom sorting** of objects:

- `Comparable<T>`
- `Comparator<T>`

Both serve the purpose of comparing objects, but they are used differently.

---

## 🔹 `Comparable<T>` Interface

- Defined in: `java.lang.Comparable`
- Used to define **natural ordering** of objects.
- Class implements `Comparable` and overrides `compareTo()` method.

### ✅ Syntax:
```java
public class Employee implements Comparable<Employee> {
    int id;
    String name;

    @Override
    public int compareTo(Employee other) {
        return Integer.compare(this.id, other.id); // ascending order
    }
}
```

### ✅ Usage:
```java
List<Employee> list = ...;
Collections.sort(list); // Uses compareTo()
```

### ✅ Key Points:
| Feature    | Description                      |
|------------|----------------------------------|
| Method     | `int compareTo(T o)`             |
| Placement  | Inside the class                 |
| Purpose    | Natural/default sorting          |
| Limitations| One ordering per class           |

---

## 🔹 `Comparator<T>` Interface

- Defined in: `java.util.Comparator`
- Used for **custom and multiple orderings**.
- You define separate class or lambda that implements `Comparator`.

### ✅ Syntax:
```java
class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.name.compareTo(e2.name);
    }
}
```

### ✅ Usage:
```java
Collections.sort(list, new NameComparator());
// or using lambda
list.sort((e1, e2) -> e1.name.compareTo(e2.name));
```

### ✅ Key Points:
| Feature    | Description                       |
|------------|-----------------------------------|
| Method     | `int compare(T o1, T o2)`         |
| Placement  | Outside the class                 |
| Purpose    | Custom sorting                    |
| Multiple Sort Orders | ✔️                      |

---

## 🆚 Comparable vs Comparator: Side-by-Side Comparison

| Feature                  | `Comparable`                    | `Comparator`                        |
|--------------------------|----------------------------------|-------------------------------------|
| Package                  | `java.lang`                     | `java.util`                         |
| Method                   | `compareTo(T o)`                | `compare(T o1, T o2)`               |
| Defines                  | Natural/default ordering        | Custom/multiple ordering            |
| Implemented By           | Class itself                    | External class or lambda            |
| Modification Required    | Yes                             | No                                  |
| Use Example              | `Collections.sort(list)`        | `list.sort(comparator)`             |
| Functional Interface     | ❌                              | ✔️                                 |
| Java 8 Enhancements      | N/A                             | `thenComparing()`, `comparing()`    |

---

## 🔄 Example: Sorting by Multiple Criteria using `Comparator`

```java
Comparator<Employee> comp = Comparator.comparing(Employee::getName)
                                      .thenComparingInt(Employee::getId);

list.sort(comp);
```

---

## 🛠️ Best Practices

| Guideline | Reason |
|----------|--------|
| Use `Comparable` when default sorting is fixed | Makes class self-sorting |
| Use `Comparator` for multiple/dynamic orderings | More flexible |
| Use `Comparator.comparing()` (Java 8+) | Clean, readable |
| Keep `compareTo()` consistent with `equals()` | Avoids collection bugs |

---

## 🚨 Hidden Contract: `compareTo()` and `equals()`

> If `compareTo(a, b) == 0`, then ideally `a.equals(b)` should return `true`.

🔸 Violating this can cause incorrect behavior in `TreeSet`, `TreeMap`, etc.

### ✅ Tip:
Ensure fields used in `compareTo()` are also part of `equals()`.

---

## ✅ Summary Table

| Criteria               | `Comparable`           | `Comparator`               |
|------------------------|------------------------|----------------------------|
| Defines                | Natural ordering       | Custom/multiple orderings  |
| Location               | Inside the class       | External or lambda         |
| Method                 | `compareTo()`          | `compare()`                |
| Java 8 Support         | Not functional         | ✔️ Functional interface     |
| Reusability            | One logic per class    | Multiple, reusable         |
| Example Use            | `Collections.sort()`   | `list.sort(comparator)`    |
