# ✅ Best Way to Implement Singleton in Java

A Singleton ensures **only one instance** of a class is created and provides a global point of access to it.

---

## 🔹 1. Enum Singleton (✅ Recommended)

```java
public enum Singleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("Doing work");
    }
}
```

### ✅ Advantages:
- Thread-safe by default
- Serialization handled by JVM
- Immune to reflection attacks
- Concise and clean

### ❗ Limitations:
- No lazy initialization with parameters

---

## 🔹 2. Static Inner Class (Bill Pugh Singleton)

```java
public class Singleton {
    private Singleton() {}

    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}
```

### ✅ Advantages:
- Lazy initialized
- Thread-safe without synchronization
- High performance

---

## 🔹 3. Double-Checked Locking (DCL)

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

### ✅ Advantages:
- Lazy and thread-safe
- Avoids locking after instance is initialized

### ⚠️ Requires `volatile` to prevent instruction reordering

---

## 🔹 4. Eager Initialization

```java
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

### ✅ Advantages:
- Thread-safe via JVM class loading
- Simple and effective

### ❗ Drawback:
- Instance created even if not used

---

## 🧠 Summary Comparison

| Feature              | Enum Singleton | Static Holder | DCL              | Eager Initialization |
|----------------------|----------------|---------------|------------------|-----------------------|
| Thread-Safe          | ✔              | ✔             | ✔                | ✔                     |
| Lazy Initialization  | ❌             | ✔             | ✔                | ❌                    |
| Serialization Safe   | ✔              | ❌ (custom)    | ❌ (custom)       | ❌ (custom)           |
| Reflection-Proof     | ✔              | ❌             | ❌                | ❌                    |
| Simplicity           | ✔              | Moderate      | Complex           | ✔                     |

---

## ✅ Final Recommendation

| Use Case                                | Use This Approach       |
|-----------------------------------------|--------------------------|
| Most robust, general-purpose            | `Enum Singleton`         |
| Need lazy init + simplicity             | `Static Inner Class`     |
| Complex requirement, legacy support     | `DCL with volatile`      |
| Always used singleton, simple scenario  | `Eager Initialization`   |

---

## 💡 Bonus Tip

For serialization-safe singletons (non-enum), override:
```java
protected Object readResolve() {
    return getInstance();
}
```

And guard against reflection via:
```java
if (instance != null) throw new RuntimeException("Use getInstance()");
```

---
