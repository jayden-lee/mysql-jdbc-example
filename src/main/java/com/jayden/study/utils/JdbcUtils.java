package com.jayden.study.utils;

import java.sql.*;

public final class JdbcUtils {

    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    public static void closeConnection(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement == null) {
            return;
        }

        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a Connection Instance
     *
     * @param url      JDBC URL
     * @param user     Username
     * @param password Password
     * @return Connection
     * @throws Exception
     */
    public static Connection getConnection(String url, String user, String password) throws Exception {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            throw new Exception("Unable to register driver class", e);
        }

        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Retrieves the database metadata for this connection.
     *
     * @param connection The connection to use to query the database.
     * @return DatabaseMetaData
     */
    public static DatabaseMetaData getDatabaseMetaData(Connection connection) throws Exception {
        DatabaseMetaData databaseMetaData;
        try {
            databaseMetaData = connection.getMetaData();
        } catch (SQLException e) {
            throw new Exception("Unable to read database connection metadata", e);
        }
        return databaseMetaData;
    }
}
