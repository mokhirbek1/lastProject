package main.project.movie.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import static main.project.movie.controller.message.LogMessage.*;

public class ConnectionPool {
    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            logger.fatal(ERROR_REGISTR_DRIVER_LOG);
            throw new RuntimeException(ERROR_REGISTR_DRIVER_LOG,e);
        }
    }
    private static ConnectionPool instance = new ConnectionPool();
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> busyConnections;
    private static final int POOL_SIZE = 8;
    private static final String URL_TO_DB = "jdbc:mysql://localhost:3306/movieproject";
    private static final String USER = "user";
    private static final String ROOT = "root";
    private static final String PASSWORD = "password";
    private static final String DB_PASSWORD = "1234";
    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() {
        Properties properties = new Properties();
        String url = URL_TO_DB;
        properties.put(USER, ROOT);
        properties.put(PASSWORD, DB_PASSWORD);
        freeConnections = new LinkedBlockingDeque<>(POOL_SIZE);
        busyConnections = new LinkedBlockingDeque<>(POOL_SIZE);

        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection proxyConnection;
            try {
                proxyConnection = new ProxyConnection(DriverManager.getConnection(url, properties));
            } catch (SQLException sqlException) {
                logger.fatal(FATAL_REGISTRATION_LOG, sqlException);
                throw new ExceptionInInitializerError(sqlException);
            }
            freeConnections.add(proxyConnection);
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException interruptedException) {
            logger.error(ERROR_GETTING_CONNECTION_LOG, interruptedException);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (connection instanceof ProxyConnection /*connection1*/ && (busyConnections.remove(connection))) {
                freeConnections.put((ProxyConnection) connection);
            }
        } catch (InterruptedException interruptedException) {
            logger.error(ERROR_RELEASING_CONNECTION_LOG, interruptedException);
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException interruptedException) {
                logger.error(ERROR_DESTROYING_CONNECTION_LOG, interruptedException);
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> deregisterDrivers());
    }

}
