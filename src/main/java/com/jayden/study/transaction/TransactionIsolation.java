package com.jayden.study.transaction;

import com.jayden.study.utils.JdbcUtils;

import java.sql.*;

/**
 * Transaction Isolation Levels
 *
 * @author jayden-lee
 */
public class TransactionIsolation {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public enum TxIsolationLevel {
        NONE("NONE", 0),
        READ_UNCOMMITTED("READ UNCOMMITTED", 1),
        READ_COMMITTED("READ COMMITTED", 2),
        REPEATABLE_READ("REPEATABLE READ", 4),
        SERIALIZABLE("SERIALIZABLE", 8);

        private String name;
        private int level;

        TxIsolationLevel(String name, int level) {
            this.name = name;
            this.level = level;
        }

        public String getName() {
            return name;
        }

        public int getLevel() {
            return level;
        }
    }

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);

            for (TxIsolationLevel txIsolationLevel : TxIsolationLevel.values()) {
                changeTxIsolationLevel(txIsolationLevel);
                printTxIsolationInfo(txIsolationLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    private static void changeTxIsolationLevel(TxIsolationLevel txIsolationLevel) throws SQLException {
        String sql = "SET SESSION TRANSACTION ISOLATION LEVEL " + txIsolationLevel.getName();
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static void printTxIsolationInfo(TxIsolationLevel txIsolationLevel) throws SQLException {
        System.out.println(txIsolationLevel.getName() + ", " + connection.getTransactionIsolation());
    }
}
