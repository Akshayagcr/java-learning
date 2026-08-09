# 🧮 SQL `WHERE` Clause – All Operators, Expressions, and Advanced Use Cases

The `WHERE` clause is used in SQL to **filter rows** before they are selected, grouped, updated, or deleted. It allows conditional logic using various operators and expressions.

---

## 🔹 1. Comparison Operators

| Operator | Description              | Example                      | Notes                                       |
|----------|--------------------------|------------------------------|---------------------------------------------|
| =        | Equal to                 | `WHERE salary = 50000`       | Works for numbers, strings, and dates       |
| <> or != | Not equal to             | `WHERE status <> 'active'`   | `<>` is standard SQL; `!=` is widely used   |
| >        | Greater than             | `WHERE age > 30`             |                                              |
| <        | Less than                | `WHERE age < 18`             |                                              |
| >=       | Greater than or equal    | `WHERE price >= 1000`        |                                              |
| <=       | Less than or equal       | `WHERE score <= 90`          |                                              |

> 🔸 Tip: Use string literals with quotes: `WHERE name = 'John'`

---

## 🔹 2. Logical Operators

Used to combine multiple `WHERE` conditions.

| Operator | Description                 | Example                                          |
|----------|-----------------------------|--------------------------------------------------|
| AND      | All conditions must be true | `WHERE age > 18 AND city = 'Pune'`               |
| OR       | At least one must be true   | `WHERE role = 'Admin' OR role = 'Manager'`       |
| NOT      | Negates a condition         | `WHERE NOT (status = 'inactive')`                |

> 🔸 Use parentheses `()` to group conditions and control evaluation order.

---

## 🔹 3. BETWEEN Operator

Filters values within a **range** (inclusive).

```sql
SELECT * FROM Orders
WHERE amount BETWEEN 100 AND 500;
```

Equivalent to:

```sql
WHERE amount >= 100 AND amount <= 500
```

> 📝 Works for numeric, date, and string ranges.

---

## 🔹 4. IN / NOT IN Operator

Checks if a value is in a specified list (set).

```sql
SELECT * FROM Employees
WHERE department IN ('HR', 'Sales', 'IT');
```

```sql
SELECT * FROM Products
WHERE category NOT IN ('Obsolete', 'Deprecated');
```

> 🔸 Avoid `NOT IN` with NULLs in the list — it can produce unexpected results.

---

## 🔹 5. LIKE / NOT LIKE (Pattern Matching)

Performs pattern-based filtering using wildcards:

| Wildcard | Description                  |
|----------|------------------------------|
| `%`      | Zero or more characters      |
| `_`      | Exactly one character        |

```sql
SELECT * FROM Customers WHERE name LIKE 'A%';       -- Starts with A
SELECT * FROM Emails WHERE email LIKE '%@gmail.com'; -- Ends with @gmail.com
SELECT * FROM Products WHERE code LIKE '_X%';       -- Second letter is X
```

> 🔸 Use `ILIKE` for case-insensitive match in PostgreSQL.

---

## 🔹 6. IS NULL / IS NOT NULL

Used to check for null or missing values.

```sql
SELECT * FROM Users WHERE phone IS NULL;
SELECT * FROM Users WHERE email IS NOT NULL;
```

> 🔸 `= NULL` does not work. Always use `IS NULL` syntax.

---

## 🔹 7. EXISTS / NOT EXISTS

Used to check whether a **subquery returns any rows**.

```sql
SELECT name FROM Customers c
WHERE EXISTS (
  SELECT 1 FROM Orders o WHERE o.customer_id = c.id
);
```

```sql
SELECT name FROM Customers c
WHERE NOT EXISTS (
  SELECT 1 FROM Orders o WHERE o.customer_id = c.id
);
```

> 🔸 EXISTS is often more performant than IN for subqueries.

---

## 🔹 8. ANY / ALL / SOME (Quantified Subqueries)

Compare a value with **multiple values returned by a subquery**.

```sql
-- Greater than any price in the subquery
SELECT * FROM Products
WHERE price > ANY (
  SELECT price FROM Products WHERE category = 'Discounted'
);

-- Greater than all prices in the subquery
SELECT * FROM Products
WHERE price > ALL (
  SELECT price FROM Products WHERE category = 'Low-End'
);
```

> 🔸 `SOME` is a synonym for `ANY`.

---

## 🔹 9. Arithmetic Expressions in WHERE

Use arithmetic operations to apply conditions on derived expressions.

```sql
SELECT * FROM Orders
WHERE (unit_price * quantity) > 1000;
```

> 🔸 Avoid overuse in indexed columns as it may disable index optimization.

---

## 🔹 10. WHERE with Dates

```sql
-- Exact match
SELECT * FROM Events WHERE event_date = '2024-01-01';

-- Range
SELECT * FROM Events
WHERE event_date BETWEEN '2024-01-01' AND '2024-12-31';
```

> 🕒 Dates should be in format `'YYYY-MM-DD'`. Quoting required.

---

## 🔹 11. WHERE with Subqueries

```sql
SELECT name FROM Employees
WHERE department_id = (
  SELECT id FROM Departments WHERE name = 'Engineering'
);
```

> 🔸 Scalar subqueries must return only 1 row, else it throws an error.

---

## 🔹 12. WHERE with CASE (limited support)

```sql
SELECT * FROM Employees
WHERE 1 = CASE WHEN age > 40 THEN 1 ELSE 0 END;
```

> ⚠️ Rarely used; avoid unless needed in complex logic. Use `OR`/`AND` instead.

---

## ✅ Summary Table of WHERE Clause Capabilities

| Category            | Operators / Keywords                                |
|---------------------|-----------------------------------------------------|
| Comparison          | `=`, `!=`, `<>`, `>`, `<`, `>=`, `<=`               |
| Logical             | `AND`, `OR`, `NOT`                                  |
| Pattern Matching    | `LIKE`, `NOT LIKE`, `%`, `_`                        |
| Range               | `BETWEEN ... AND ...`                               |
| Set Matching        | `IN (...)`, `NOT IN (...)`                          |
| Null Checking       | `IS NULL`, `IS NOT NULL`                            |
| Subquery Checking   | `EXISTS`, `NOT EXISTS`, `= (SELECT ...)`, `IN (...)`|
| Quantified Subquery | `> ANY`, `< ALL`, `= SOME`                          |
| Arithmetic          | Expressions like `(price * quantity) > 1000`       |
| Date Filtering      | `= 'YYYY-MM-DD'`, `BETWEEN`                         |

---

This guide covers all valid operations supported in the `WHERE` clause in **standard SQL**, including **best practices**, **edge cases**, and **performance tips**. Ideal for both learning and interviews!
