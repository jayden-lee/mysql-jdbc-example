package com.jayden.study.metadata;

import com.jayden.study.utils.JdbcUtils;

import java.sql.*;

/**
 * Extracting MySQL Database Metadata Information
 *
 * @author jayden-lee
 */
public class DatabaseMetaDataInfo {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);
            printDatabaseMetaData(JdbcUtils.getDatabaseMetaData(connection));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    /**
     * Print Database MetaData Information
     *
     * @param databaseMetaData DatabaseMetaData
     * @throws SQLException
     */
    private static void printDatabaseMetaData(DatabaseMetaData databaseMetaData) throws SQLException {
        System.out.println("Product Name : " + databaseMetaData.getDatabaseProductName());               // MySQL
        System.out.println("Database Major Version : " + databaseMetaData.getDatabaseMajorVersion());    // 5
        System.out.println("Database Minor Version : " + databaseMetaData.getDatabaseMinorVersion());    // 7
        System.out.println("Driver Name : " + databaseMetaData.getDriverName());                         // MySQL Connection/J
        System.out.println("Driver Major Version : " + databaseMetaData.getDriverMajorVersion());        // 8
        System.out.println("Driver Minor Version : " + databaseMetaData.getDriverMinorVersion());        // 0
        System.out.println("ReadOnly : " + databaseMetaData.isReadOnly());                               // false
        System.out.println("Support Transaction : " + databaseMetaData.supportsTransactions());          // true
        System.out.println("Support Savepoint : " + databaseMetaData.supportsSavepoints());              // true
        System.out.println("Support Select For Update : " + databaseMetaData.supportsSelectForUpdate()); // true
        System.out.println("Support Stored Procedure : " + databaseMetaData.supportsStoredProcedures()); // true
        System.out.println("Identifier Quote String : " + databaseMetaData.getIdentifierQuoteString());  // `
    }
}
