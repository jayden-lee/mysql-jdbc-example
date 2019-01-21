package com.jayden.study.connection;

import com.jayden.study.utils.JdbcUtils;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
 * Connecting to remote MySQL through SSH using Java
 *
 * @author jayden-lee
 */
public class SshTunnelConnection {
    // db information
    private static String host = "dbHost";
    private static int port = 3306;
    private static String databaseName = "mysql";
    private static final String user = "user";
    private static final String password = "password";

    // ssh information
    private static final String sshHost = "sshHost";
    private static final int sshPort = 22;
    private static final String sshUser = "user";
    private static final String sshPassword = "password";

    private static Connection connection;

    // random number
    private static int localPort = 12345;

    private static Session session;
    private static SshAuthType sshAuthType = SshAuthType.PASSWORD;

    public enum SshAuthType {
        PASSWORD,
        PUBLIC_KEY
    }

    public static void main(String[] args) {
        try {
            // ssh connect
            prepareSshTunnel();

            connection = JdbcUtils.getConnection(getJdbcUrl(), user, password);

            executeTestQuery();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            JdbcUtils.closeConnection(connection);

            if (session != null) {
                // close ssh session
                session.disconnect();
            }
        }
    }

    private static void prepareSshTunnel() throws Exception {
        JSch jSch = new JSch();

        if (SshAuthType.PUBLIC_KEY == sshAuthType) {
            // key file byte data and passphrase
            jSch.addIdentity(UUID.randomUUID().toString(), null, null, null);
        }

        session = jSch.getSession(sshUser, sshHost, sshPort);
        session.setConfig("StrictHostKeyChecking", "no");

        if (SshAuthType.PASSWORD == sshAuthType) {
            session.setConfig("PreferredAuthentications", "password,keyboard-interactive");
            session.setPassword(sshPassword);

        } else {
            session.setConfig("PreferredAuthentications", "publickey");
        }

        session.connect();
        System.out.println("Connected");

        int allocatedLocalPort = session.setPortForwardingL(localPort, host, port);
        System.out.println("localhost:" + allocatedLocalPort + " -> " + host + ":" + port);
    }

    private static String getJdbcUrl() {
        return "jdbc:mysql://localhost:" + localPort + "/" + databaseName;
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
