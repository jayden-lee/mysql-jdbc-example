package com.jayden.study.connection;

import com.jayden.study.utils.CommonUtils;
import com.jayden.study.utils.JdbcUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Connecting to MySQL via an Encrypted Connection using SSL
 *
 * @author jayden-lee
 */
public class EncryptedConnection {

    /**
     * MySQL SSL Mode Type
     */
    public enum SslMode {
        PREFERRED, REQUIRED, VERIFY_CA, VERIFY_IDENTITY, DISABLED;
    }

    private static final String url = "jdbc:mysql://localhost:3306/mysql";
    private static final String user = "user";
    private static final String password = "password";

    private static Properties properties = new Properties();

    private static Connection connection;

    // SSL Mode Default value 'PREFERRED'
    private static SslMode sslMode = SslMode.VERIFY_CA;

    private static final Path KEY_STORE_DIRECTORY_PATH = CommonUtils.getBaseDir();

    private static final String KEY_STORE_FILE_SUFFIX = ".jks";

    private static final String DEFAULT_KEY_STORE_PASSWORD = "";

    public static void main(String[] args) {
        try {
            // Setting connection properties
            buildProperties();

            connection = JdbcUtils.getConnection(url, properties);

            // Execute Test Query
            executeTestQuery();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);
        }
    }

    private static void buildProperties() throws Exception {
        // Username and Password
        properties.put("user", user);
        properties.put("password", password);

        properties.put("characterEncoding", "UTF-8");

        // JDBC Error "Value '0000-00-00' can not be represented as java.sql.Timestamp"
        properties.put("zeroDateTimeBehavior", "convertToNull");

        // SSL Mode
        properties.put("sslMode", sslMode.name());

        if (SslMode.VERIFY_CA == sslMode || SslMode.VERIFY_IDENTITY == sslMode) {
            FileUtils.forceMkdir(KEY_STORE_DIRECTORY_PATH.toFile());

            Path keyStoreFilePath = KEY_STORE_DIRECTORY_PATH.resolve("KEY_STORE_FILE_NAME" + KEY_STORE_FILE_SUFFIX);

            createKeyStoreFile(keyStoreFilePath);

            properties.put("trustCertificateKeyStoreUrl", keyStoreFilePath.toUri().toURL().toString());
        }
    }

    private static void createKeyStoreFile(Path keyStoreFilePath) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);

        String certData = IOUtils.toString(EncryptedConnection.class.getClass().getResourceAsStream("/ca.pem"), "UTF-8");

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(IOUtils.toInputStream(certData, "UTF-8"));
        keyStore.setCertificateEntry("ca-cert", certificate);

        try (FileOutputStream fos = new FileOutputStream(keyStoreFilePath.toFile())) {
            keyStore.store(fos, DEFAULT_KEY_STORE_PASSWORD.toCharArray());
        }
    }

    private static void executeTestQuery() throws Exception {
        String sql = "select * from sakila.address";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            JdbcUtils.printResultData(resultSet);
        } catch (Exception e) {
            throw e;
        }
    }
}
