# Query

## 1. Retrieve a row count from a ResultSet
```
try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
    resultSet.last();
    int totalRows = resultSet.getRow();
} catch (SQLException e) {
    System.err.println(e);
}
```

or

```
select count(*) from database.table;
```