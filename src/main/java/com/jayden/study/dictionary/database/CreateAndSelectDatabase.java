package com.jayden.study.dictionary.database;

import com.jayden.study.utils.JdbcUtils;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creating and Selecting a Database
 *
 * @author jayden-lee
 */
public class CreateAndSelectDatabase {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    private static Type type = Type.SQL;

    public enum Type {
        JDBC_API,
        SQL
    }

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);

            printCurrentDatabase();

            createDatabase();

            selectDatabase();

            printCurrentDatabase();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    private static void createDatabase() throws SQLException {
        System.out.println("Create a testdb2 Database");

        String sql = "CREATE DATABASE testdb2";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    private static void selectDatabase() throws SQLException {
        System.out.println("Select a testdb2 Database");

        if (Type.JDBC_API == type) {
            connection.setCatalog("testdb2");

        } else if (Type.SQL == type) {
            String sql = "USE testdb2";

            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    private static void printCurrentDatabase() throws SQLException {
        String sql = "SELECT DATABASE()";
        String databaseName = null;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                databaseName = resultSet.getString(1);
            }
        }

        System.out.println("Current Database: " + databaseName);
    }
}
