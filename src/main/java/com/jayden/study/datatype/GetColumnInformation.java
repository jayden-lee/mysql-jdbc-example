package com.jayden.study.datatype;

import com.jayden.study.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Get Column Information from ResultSet MetaData
 *
 * @author jayden-lee
 */
public class GetColumnInformation {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);
            executeTestQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    private static void executeTestQuery() throws SQLException {
        String sql = "select * from sakila.address";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            List<ColumnInfo> columnInfos = getColumnInfoList(resultSet.getMetaData());

            for(ColumnInfo columnInfo : columnInfos) {
                System.out.println(columnInfo);
            }

        } catch (SQLException e) {
            throw e;
        }
    }

    private static List<ColumnInfo> getColumnInfoList(ResultSetMetaData resultSetMetaData) throws SQLException {
        List<ColumnInfo> columnInfos = new ArrayList<>();

        int columnCount = resultSetMetaData.getColumnCount();

        for (int index = 1; index <= columnCount; index++) {
            columnInfos.add(new ColumnInfo(resultSetMetaData, index));
        }

        return columnInfos;
    }
}

class ColumnInfo {

    String columnName;
    String columnLabel;
    int columnType;
    int displaySize;
    int precision;
    boolean autoIncrement;
    boolean nullable;

    public ColumnInfo(ResultSetMetaData rsmd, int index) throws SQLException {
        this.columnName = rsmd.getColumnName(index);
        this.columnLabel = rsmd.getColumnLabel(index);
        this.columnType = rsmd.getColumnType(index);
        this.displaySize = rsmd.getColumnDisplaySize(index);
        this.precision = rsmd.getPrecision(index);
        this.autoIncrement = rsmd.isAutoIncrement(index);
        this.nullable = rsmd.isNullable(index) == 1;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", columnLabel='" + columnLabel + '\'' +
                ", columnType=" + columnType +
                ", displaySize=" + displaySize +
                ", precision=" + precision +
                ", autoIncrement=" + autoIncrement +
                ", nullable=" + nullable +
                '}';
    }
}
