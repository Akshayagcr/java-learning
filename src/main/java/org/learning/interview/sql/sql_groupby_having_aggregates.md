
# 📊 SQL GROUP BY, HAVING, and Aggregate Functions – Complete Guide

In SQL, the `GROUP BY` clause is used to group rows based on common values in specified columns, typically combined with aggregate functions to perform operations like sum, count, average, etc. The `HAVING` clause is used to filter records after grouping, based on aggregate results.

---

## 🗃 Sample Table: `Sales`

| id | customer | region | amount | sale_date  |
|----|----------|--------|--------|------------|
| 1  | Alice    | West   | 300    | 2023-07-01 |
| 2  | Bob      | East   | 150    | 2023-07-02 |
| 3  | Alice    | West   | 200    | 2023-07-03 |
| 4  | Carol    | East   | 450    | 2023-07-04 |
| 5  | Dave     | North  | 120    | 2023-07-05 |
| 6  | Alice    | West   | 250    | 2023-07-06 |

---

## 🔹 GROUP BY

Used to group rows by one or more columns, especially before applying aggregate functions.

```sql
SELECT customer, SUM(amount) AS total_spent
FROM Sales
GROUP BY customer;
```

**Result:**

| customer | total_spent |
|----------|-------------|
| Alice    | 750         |
| Bob      | 150         |
| Carol    | 450         |
| Dave     | 120         |

---

## 🔸 HAVING

Used to filter grouped results, typically after using `GROUP BY` and aggregate functions.

```sql
SELECT customer, SUM(amount) AS total_spent
FROM Sales
GROUP BY customer
HAVING SUM(amount) > 200;
```

**Result:**

| customer | total_spent |
|----------|-------------|
| Alice    | 750         |
| Carol    | 450         |

---

## 🔍 SQL Aggregate Functions

### ✅ 1. SUM(column)
Returns the total sum of a numeric column.

```sql
SELECT region, SUM(amount) AS total_sales
FROM Sales
GROUP BY region;
```

| region | total_sales |
|--------|-------------|
| West   | 750         |
| East   | 600         |
| North  | 120         |

---

### ✅ 2. AVG(column)
Returns the average of numeric values.

```sql
SELECT customer, AVG(amount) AS avg_purchase
FROM Sales
GROUP BY customer;
```

| customer | avg_purchase |
|----------|--------------|
| Alice    | 250.00       |
| Bob      | 150.00       |
| Carol    | 450.00       |
| Dave     | 120.00       |

---

### ✅ 3. COUNT(*), COUNT(column)
Counts rows or non-null values.

```sql
SELECT region, COUNT(*) AS total_orders
FROM Sales
GROUP BY region;
```

| region | total_orders |
|--------|--------------|
| West   | 3            |
| East   | 2            |
| North  | 1            |

---

### ✅ 4. MAX(column)
Returns the maximum value in a group.

```sql
SELECT region, MAX(amount) AS max_sale
FROM Sales
GROUP BY region;
```

| region | max_sale |
|--------|----------|
| West   | 300      |
| East   | 450      |
| North  | 120      |

---

### ✅ 5. MIN(column)
Returns the minimum value in a group.

```sql
SELECT region, MIN(amount) AS min_sale
FROM Sales
GROUP BY region;
```

| region | min_sale |
|--------|----------|
| West   | 200      |
| East   | 150      |
| North  | 120      |

---

## ⚙️ Combining GROUP BY + HAVING + ORDER BY

```sql
SELECT region, SUM(amount) AS total_sales
FROM Sales
GROUP BY region
HAVING SUM(amount) > 200
ORDER BY total_sales DESC;
```

**Result:**

| region | total_sales |
|--------|-------------|
| West   | 750         |
| East   | 600         |

---

## 📌 Key Points

- `GROUP BY` groups rows for each unique value in specified columns.
- `HAVING` filters the result set **after** aggregation.
- `WHERE` is used to filter rows **before** grouping.
- `ORDER BY` is used to sort grouped results.
- You can group by multiple columns using: `GROUP BY region, customer`.

---

This guide covers everything you need to know about `GROUP BY`, `HAVING`, and aggregate functions in SQL with examples, results, and best practices.
