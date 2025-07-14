
# ✅ Java Records – Deep Copy, Equals, and HashCode Behavior for Arrays, Lists, and Custom Objects

Java **records** are a concise, immutable data carrier introduced in Java 14 (finalized in Java 16). While they offer powerful value-based semantics, their behavior with **mutable fields like arrays, collections, and custom objects** needs to be understood clearly.

This guide answers:

1. 👉 Does a Java record create a **deep copy** of array, list, or custom object fields?
2. 👉 Does a Java record include array, list, or custom object fields in **equals()** and **hashCode()**?

---

## ❓ 1. Does a Java Record Create a Deep Copy of Arrays, Lists, or Custom Objects?

### ❌ No, Java Records Do **Not** Perform Deep Copy

Java records automatically store **references** to the objects passed into their constructor. There is **no deep copy** of:

- Arrays (e.g., `int[]`)
- Collections (e.g., `List<String>`)
- Custom objects (e.g., `Address`)

---

### 🔍 Example

```java
record Employee(String name, List<String> skills, int[] scores, Address address) {}
record Address(String city) {}
```

```java
List<String> skills = new ArrayList<>(List.of("Java"));
int[] scores = new int[]{100, 90};
Address address = new Address("Pune");

Employee e1 = new Employee("Akshay", skills, scores, address);
Employee e2 = new Employee(e1.name(), e1.skills(), e1.scores(), e1.address());

// Modify mutable fields after creation
skills.add("Docker");
scores[0] = 60;

System.out.println(e1.skills()); // [Java, Docker]
System.out.println(e1.scores()[0]); // 60
```

➡️ Changes to `skills` or `scores` affect the record instance `e1`.  
✅ This proves that records **store shallow references**, not deep copies.

---

## ✅ If You Need Deep Copy

You must create new copies manually:

```java
Employee e2 = new Employee(
    e1.name(),
    new ArrayList<>(e1.skills()),         // Copy of List
    Arrays.copyOf(e1.scores(), e1.scores().length), // Copy of array
    new Address(e1.address().city())      // Copy of custom object
);
```

---

## ❓ 2. Does a Java Record Include Arrays, Lists, and Custom Objects in `equals()` and `hashCode()`?

### ✅ Yes, But with Important Caveats

Java records **automatically override** `equals()` and `hashCode()` using **all declared fields**, but behavior depends on the **type** of those fields.

---

### 🔍 Field-Wise Behavior Summary

| Field Type       | Included in `equals()` / `hashCode()` | Based on Content? | Notes |
|------------------|----------------------------------------|-------------------|-------|
| `int`, `String`, `boolean` | ✅ Yes | ✅ Yes | Works correctly |
| `List`, `Map`, `Set`        | ✅ Yes | ✅ Yes | Uses `List.equals()` and `List.hashCode()` (mutable!) |
| `int[]`, `String[]`, etc.   | ✅ Yes | ❌ No  | Uses object identity (`==`) and memory address in `hashCode()` |
| Custom Objects              | ✅ Yes | ⚠️ Depends | Only works if `equals()` and `hashCode()` are properly overridden |

---

### ⚠️ Pitfall with Arrays

```java
record Data(int[] scores) {}

int[] a1 = new int[]{90, 80};
int[] a2 = new int[]{90, 80};

Data d1 = new Data(a1);
Data d2 = new Data(a2);

System.out.println(d1.equals(d2)); // false!
System.out.println(d1.hashCode() == d2.hashCode()); // false
```

➡️ Arrays are compared by reference, not content. This breaks value-based semantics.

---

### ✅ How to Fix Arrays in Records

Manually override `equals()` and `hashCode()` using `Arrays.equals()` and `Arrays.hashCode()`:

```java
record Data(int[] scores) {
    @Override
    public boolean equals(Object o) {
        return o instanceof Data d && Arrays.equals(scores, d.scores);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(scores);
    }
}
```

---

## ✅ Summary Table

| Field Type       | Deep Copied in Constructor? | Used in `equals()` and `hashCode()`? | Content-Based? | Safe for Value Objects? |
|------------------|-----------------------------|----------------------------------------|----------------|--------------------------|
| `int`, `String`  | ✅ Yes (by value)           | ✅ Yes                                 | ✅ Yes         | ✅ Yes                   |
| `List`, `Map`    | ❌ No (reference only)      | ✅ Yes                                 | ✅ Yes         | ⚠️ Only if not mutated   |
| `int[]`, `String[]` | ❌ No (reference only)   | ✅ Yes                                 | ❌ No          | ❌ Must override manually |
| Custom Object    | ❌ No (reference only)      | ✅ Yes                                 | ⚠️ If overridden | ⚠️ Check implementation   |

---

## ✅ Best Practices

- ✅ Use only **immutable fields** (e.g., `String`, `int`, records) in records.
- ❌ Avoid using arrays or mutable collections unless you're managing copies and overrides manually.
- ✅ Use defensive copies for collections or arrays inside record constructor.
- ✅ If using arrays, override `equals()` and `hashCode()` with `Arrays.equals()` and `Arrays.hashCode()`.

---

## ✅ Conclusion

- Java **records do not perform deep copy** of array, list, or custom object fields.
- Java **records do include all fields** in `equals()` and `hashCode()`, but:
  - Arrays use object identity (`==`)
  - Lists and Maps use content-based equals/hashCode (but are mutable!)
  - Custom objects must override `equals()` and `hashCode()` properly

> For records to behave as true value objects, always ensure fields are immutable or deep-copied and hash-consistent.
