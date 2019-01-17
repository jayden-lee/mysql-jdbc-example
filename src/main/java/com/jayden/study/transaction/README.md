# Transaction

# 1. Transaction Isolation Levels

- READ UNCOMMITTED
- READ COMMITTED
- REPEATABLE READ
- SERIALIZABLE

### 1. READ UNCOMMITTED

### 2. READ COMMITTED

### 3. REPEATABLE READ

### 4. SERIALIZABLE

## Change Transaction Isolation Levels

```
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;  
```

<hr>


# 2. START TRANSACTION, COMMIT, and ROLLBACK Syntax

These statements provide control over use of transactions:

- <b>START TRANSACTION</b> or <b>BEGIN</b> start a new transaction.

- <b>COMMIT</b> commits the current transaction, making its changes permanent.

- <b>ROLLBACK</b> rolls back the current transaction, canceling its changes.

- <b>SET autocommit</b> disables or enables the default autocommit mode for the current session.

<b>By default, MySQL runs with autocommit mode enabled</b>. This means that as soon as you execute a statement that 
updates (modifies) a table, MySQL stores the update on disk to make it permanent. The change cannot be rolled back.

To disable autocommit mode implicitly for a single series of statements, use the <b>START TRANSACTION</b> statement:

```
START TRANSACTION;
// update statement
COMMIT;
```

With <b>START TRANSACTION</b>, autocommit remains disabled until you end the transaction with <b>COMMIT</b> or <b>ROLLBACK</b>.
The autocommit mode then reverts to its previous state.

<hr>

# 3. Show Current Active Transaction

```
SELECT 
    COUNT(1) AS count
FROM
    INFORMATION_SCHEMA.INNODB_TRX
WHERE
    trx_mysql_thread_id = CONNECTION_ID();
```

## References
- https://dev.mysql.com/doc/refman/8.0/en/commit.html