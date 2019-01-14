package com.jayden.study.transaction;

import com.jayden.study.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC Commit and Rollback
 *
 * @author jayden-lee
 */
public class CommitAndRollback {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);

            // change auto commit status
            connection.setAutoCommit(false);

            // Execute update Query
            updateQuery();

            // Commit
            connection.commit();

        } catch (Exception e) {
            try {
                // Rollback
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    private static void updateQuery() throws SQLException {
        String sql = "INSERT INTO testdb.employee VALUES (150, 'jayden-lee', 'IT', 4)";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
    }
}
