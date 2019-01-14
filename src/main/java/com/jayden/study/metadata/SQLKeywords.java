package com.jayden.study.metadata;

import com.jayden.study.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Get all SQL Keywords
 *
 * @author jayden-lee
 */
public class SQLKeywords {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);
            printSQLKeywords(JdbcUtils.getDatabaseMetaData(connection));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    /**
     * Print SQL Keywords
     *
     * @param databaseMetaData DatabaseMetaData
     * @throws SQLException
     */
    private static void printSQLKeywords(DatabaseMetaData databaseMetaData) throws SQLException {
        String sqlKeywords = databaseMetaData.getSQLKeywords();
        String[] arrSqlKeywords = sqlKeywords.split(",");

        System.out.println("##SQL Keywords##\n");

        for(String sqlKeyword : arrSqlKeywords) {
            System.out.println(sqlKeyword);
        }
    }
}
