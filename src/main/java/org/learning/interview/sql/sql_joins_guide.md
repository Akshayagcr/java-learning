
# 📘 SQL Joins Explained with Examples

A SQL JOIN is used to combine rows from two or more tables based on a related column (usually a foreign key). Joins help extract meaningful relationships across tables in a relational database.

---

## 🗃 Sample Tables

**Table: `Employees`**

| emp_id | name   | dept_id |
|--------|--------|---------|
| 1      | Alice  | 10      |
| 2      | Bob    | 20      |
| 3      | Carol  | 10      |
| 4      | David  | NULL    |

**Table: `Departments`**

| dept_id | dept_name    |
|---------|--------------|
| 10      | HR           |
| 20      | Engineering  |
| 30      | Marketing    |

---

## 🔹 1. INNER JOIN

Returns only rows where there is a match in both tables.

```sql
SELECT e.name, d.dept_name
FROM Employees e
INNER JOIN Departments d ON e.dept_id = d.dept_id;
```

**Result:**

| name  | dept_name   |
|-------|-------------|
| Alice | HR          |
| Bob   | Engineering |
| Carol | HR          |

---

## 🔹 2. LEFT JOIN (LEFT OUTER JOIN)

Returns all rows from the left table and the matched rows from the right table. Unmatched right side columns will contain NULL.

```sql
SELECT e.name, d.dept_name
FROM Employees e
LEFT JOIN Departments d ON e.dept_id = d.dept_id;
```

**Result:**

| name  | dept_name   |
|-------|-------------|
| Alice | HR          |
| Bob   | Engineering |
| Carol | HR          |
| David | NULL        |

---

## 🔹 3. RIGHT JOIN (RIGHT OUTER JOIN)

Returns all rows from the right table and the matched rows from the left table. Unmatched left side columns will contain NULL.

```sql
SELECT e.name, d.dept_name
FROM Employees e
RIGHT JOIN Departments d ON e.dept_id = d.dept_id;
```

**Result:**

| name  | dept_name   |
|-------|-------------|
| Alice | HR          |
| Bob   | Engineering |
| Carol | HR          |
| NULL  | Marketing   |

---

## 🔹 4. FULL OUTER JOIN

Returns all records when there is a match in either left or right table. If there is no match, the result will contain NULLs.

⚠️ Not directly supported in MySQL, but can be simulated using `UNION`.

```sql
SELECT e.name, d.dept_name
FROM Employees e
LEFT JOIN Departments d ON e.dept_id = d.dept_id

UNION

SELECT e.name, d.dept_name
FROM Employees e
RIGHT JOIN Departments d ON e.dept_id = d.dept_id;
```

**Result:**

| name  | dept_name   |
|-------|-------------|
| Alice | HR          |
| Bob   | Engineering |
| Carol | HR          |
| David | NULL        |
| NULL  | Marketing   |

---

## 🔹 5. CROSS JOIN

Returns the Cartesian product of the two tables. Every row from the first table is joined with every row from the second.

```sql
SELECT e.name, d.dept_name
FROM Employees e
CROSS JOIN Departments d;
```

**Result:** (partial, total 12 rows)

| name  | dept_name   |
|-------|-------------|
| Alice | HR          |
| Alice | Engineering |
| Alice | Marketing   |
| Bob   | HR          |
| Bob   | Engineering |
| Bob   | Marketing   |
| ...   | ...         |

---

## 🔹 6. SELF JOIN

A SELF JOIN is a regular join where a table is joined with itself. Useful for hierarchical relationships like employees and managers.

**Modified Table: `Employees` with manager_id**

| emp_id | name   | manager_id |
|--------|--------|------------|
| 1      | Alice  | NULL       |
| 2      | Bob    | 1          |
| 3      | Carol  | 1          |
| 4      | David  | 2          |

```sql
SELECT e.name AS Employee, m.name AS Manager
FROM Employees e
LEFT JOIN Employees m ON e.manager_id = m.emp_id;
```

**Result:**

| Employee | Manager |
|----------|---------|
| Alice    | NULL    |
| Bob      | Alice   |
| Carol    | Alice   |
| David    | Bob     |

---

## 🔸 Summary Table

| Join Type         | Description                                      |
|-------------------|--------------------------------------------------|
| INNER JOIN        | Only matched rows from both tables               |
| LEFT JOIN         | All rows from left, matched from right           |
| RIGHT JOIN        | All rows from right, matched from left           |
| FULL OUTER JOIN   | All rows from both tables                        |
| CROSS JOIN        | Cartesian product (all combinations)             |
| SELF JOIN         | A table joined with itself (alias required)      |

---

This complete guide covers all essential join types with clear syntax and output. Use this reference for interviews, SQL learning, and day-to-day development.
