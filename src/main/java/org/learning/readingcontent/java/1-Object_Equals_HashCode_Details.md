# Java Object Class Methods, equals() and hashCode() in Detail

## 🔹 Object Class Methods in Java

The `java.lang.Object` class is the **ultimate superclass** of all Java classes. It defines common behavior that every Java object inherits.

### 📜 Key Methods of `Object`:
| Method                        | Description |
|------------------------------|-------------|
| `equals(Object obj)`         | Checks logical equality between two objects. |
| `hashCode()`                 | Returns an integer hash code used in hashing mechanisms like `HashMap`. |
| `toString()`                 | Returns a string representation of the object. |
| `getClass()`                 | Returns the runtime class of the object. |
| `clone()`                    | Returns a copy of the object (if the class implements `Cloneable`). |
| `finalize()`                 | Called by GC before object is destroyed (deprecated). |
| `wait()`, `notify()`, `notifyAll()` | Used for inter-thread communication (monitor methods). |

---

## 🔹 Overriding `equals()` and `hashCode()` – In Depth

### ✅ Overriding `equals(Object obj)`

#### Purpose:
Defines **logical equality**, i.e., whether two objects represent the same logical entity, **not just the same memory location**.

#### 🔁 Default behavior:
```java
public boolean equals(Object obj) {
    return (this == obj);
}
```

#### ✅ Rules to Follow:
1. **Reflexive**: `x.equals(x)` → `true`
2. **Symmetric**: `x.equals(y)` ↔ `y.equals(x)`
3. **Transitive**: `x.equals(y)` && `y.equals(z)` → `x.equals(z)`
4. **Consistent**: Repeated calls return same result
5. **Non-null**: `x.equals(null)` → `false`

#### 🔍 Example Override:
```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Person other = (Person) obj;
    return id == other.id && Objects.equals(name, other.name);
}
```

---

### ✅ Overriding `hashCode()`

#### Purpose:
Returns an **integer hash value** used in hash-based collections like `HashMap`, `HashSet`, and `Hashtable`.

#### 🔁 Contract with `equals()`:
- If `a.equals(b) == true`, then `a.hashCode() == b.hashCode()` **must be true**.
- The reverse is **not required**, but desirable: unequal objects may share the same hash code.

### 🔍 How Hash Code is Calculated for Different Types:

| Type | Calculation Example | Notes |
|------|---------------------|-------|
| `int` | `Integer.hashCode(value)` | Returns the int as-is |
| `long` | `(int)(value ^ (value >>> 32))` | Uses XOR to combine high and low bits |
| `float` | `Float.floatToIntBits(value)` | Converts float to int bits |
| `double` | `Double.hashCode(value)` | Converts double to long bits, then hashes |
| `boolean` | `value ? 1231 : 1237` | Arbitrary primes for true/false |
| `char` | `(int) value` | Unicode value of char |
| `String` | `s[0]*31ⁿ⁻¹ + s[1]*31ⁿ⁻² + ... + s[n-1]` | Polynomial rolling hash |
| `Object[]` | `Arrays.hashCode(array)` | Iterates and hashes each element |
| `Collection` | `Objects.hash(c.toArray())` or `c.stream().mapToInt(Object::hashCode).sum()` | Order-sensitive |

#### 🔁 Examples:

**Primitive:**
```java
int hash = 31 * Integer.hashCode(age) + Boolean.hashCode(isActive);
```

**Object Field:**
```java
int hash = 31 * result + (name != null ? name.hashCode() : 0);
```

**Array:**
```java
int hash = Arrays.hashCode(arr);
```

**Collection:**
```java
int hash = Objects.hash(list); // order-sensitive
```

---

### ✅ Best Practices for `equals()` and `hashCode()`

| ✅ Best Practice | 💡 Explanation |
|------------------|----------------|
| Always override both together | They must be consistent to avoid collection bugs |
| Use `Objects.hash(...)` or IDE generation | Ensures correctness and maintainability |
| Prefer immutable fields | Prevents bugs due to hashCode changes in collections |
| Avoid using mutable fields in hashCode | Mutation can lead to elements being lost in hash-based collections |
| Include all relevant fields used in `equals()` | To keep both methods consistent |
| Use prime multipliers (`31` is common) | Helps in reducing hash collisions |
| Be consistent | Repeated calls should return the same hash unless fields used change |

---

## 🧪 Practical Example

```java
public class Employee {
    private final int id;
    private final String name;
    private final List<String> skills;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Employee other)) return false;
        return id == other.id &&
               Objects.equals(name, other.name) &&
               Objects.equals(skills, other.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, skills); // order-sensitive
    }
}
```

> ⚠️ If `skills` is a mutable list, modifying it after adding to a `HashSet` or `HashMap` can break the collection behavior.

---

## 🚨 What Happens If You Violate the Contract?

### ❌ Overriding `equals()` but not `hashCode()`:
```java
Set<Employee> set = new HashSet<>();
set.add(new Employee(1, "John"));

set.contains(new Employee(1, "John")); // returns false!
```

### ❌ Using mutable fields:
```java
Map<Employee, String> map = new HashMap<>();
Employee e = new Employee(1, "John");
map.put(e, "Engineer");

e.setName("Johnny"); // Changing field used in hashCode
map.get(e); // Might return null
```

---

## 📘 Summary Table

| Concept | Must Overridden | Common Issue if Skipped |
|--------|------------------|--------------------------|
| `equals()` | ✔️ Yes (for logical equality) | Unexpected results in collections |
| `hashCode()` | ✔️ Yes (if `equals` is overridden) | `HashMap`, `HashSet` don't work correctly |
| Collections | Order matters for `List`, not for `Set` | Use consistent hash logic |
| Arrays | Use `Arrays.hashCode()` | Avoid shallow hash code |