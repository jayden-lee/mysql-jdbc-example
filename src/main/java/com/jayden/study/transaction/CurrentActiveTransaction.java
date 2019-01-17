package com.jayden.study.transaction;

import com.jayden.study.utils.JdbcUtils;

import java.sql.*;

/**
 * Show Current Active Transaction
 *
 * @author jayden-lee
 */
public class CurrentActiveTransaction {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);

            connection.setAutoCommit(false);

            updateQuery();

            System.out.println("Current Active Transaction : " + isCurrentActiveTransaction());

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    private static void updateQuery() throws SQLException {
        String sql = "INSERT INTO testdb.test_table VALUES ('mahsa')";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static boolean isCurrentActiveTransaction() throws SQLException {
        int transactionCount = 0;

        String sql = "SELECT \n" +
                     "    COUNT(1) AS count\n" +
                     "FROM\n" +
                     "    INFORMATION_SCHEMA.INNODB_TRX\n" +
                     "WHERE\n" +
                     "    trx_mysql_thread_id = CONNECTION_ID()";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            if (resultSet.next()) {
                transactionCount = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw e;
        }

        return transactionCount > 0;
    }
}