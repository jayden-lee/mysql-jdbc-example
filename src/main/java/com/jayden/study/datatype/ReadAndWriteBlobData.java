package com.jayden.study.datatype;

import com.jayden.study.utils.JdbcUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.*;

/**
 * Reading and Writing BLOB data
 *
 * @author jayden-lee
 */
public class ReadAndWriteBlobData {
    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Connection connection;

    public static void main(String[] args) {
        try {
            connection = JdbcUtils.getConnection(url, user, password);

            readingBlobData();

            writingBlobData();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    /**
     * Reading a BLOB data type
     *
     * @throws SQLException
     * @throws IOException
     */
    private static void readingBlobData() throws SQLException, IOException {
        // picture column is blob data type
        String sql = "select picture from sakila.staff";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Blob blob = resultSet.getBlob(1);

                // NULL Check
                if (!resultSet.wasNull()) {
                    InputStream inputStream = blob.getBinaryStream();
                    System.out.println(IOUtils.toString(inputStream, Charset.defaultCharset()));
                }
            }
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Writing a BLOB data type
     *
     * create table testdb.blob_test (
     *      picture longblob not null
     * )
     *
     * @throws Exception
     */
    private static void writingBlobData() throws Exception {
        String sql = "insert into testdb.blob_test values (?)";

        File file = new File(ReadAndWriteBlobData.class.getClass().getResource("/image.png").toURI());
        FileInputStream inputStream = FileUtils.openInputStream(file);

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBinaryStream(1, inputStream);

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " rows affected");
        } catch (SQLException e) {
            throw e;
        }
    }
}
