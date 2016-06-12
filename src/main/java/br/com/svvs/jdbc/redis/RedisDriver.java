package br.com.svvs.jdbc.redis;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class RedisDriver implements Driver {

    private static final String JDBC_URL = "jdbc:redis:";

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 6379;
    private static final int DEFAULT_DBNB = 0;

    private static final int MAJOR_VERSION = 0;
    private static final int MINOR_VERSION = 1;

    static {
        try {
            DriverManager.registerDriver(new RedisDriver());
        } catch (SQLException e) {
            throw new RuntimeException("Can't register Redis JDBC driver", e);
        }
    }

    @Override
    public boolean acceptsURL(final String url) throws SQLException {
        return url.toLowerCase().startsWith(JDBC_URL);
    }

    @Override
    public Connection connect(final String url, final Properties info) throws SQLException {

        if(!this.acceptsURL(url)) {
            throw new SQLException("Invalid URL: " + url);
        } else {
            // remove prefix so we can use URI parsing.
            String rawUrl = url.replaceFirst("jdbc:","");
            String host = DEFAULT_HOST;
            int port = DEFAULT_PORT;
            int dbnb = DEFAULT_DBNB;
            try {

                URI uri = new URI(rawUrl);

                host = uri.getHost() != null ? uri.getHost() : DEFAULT_HOST;
                port = uri.getPort() != -1 ? uri.getPort() : DEFAULT_PORT;

                dbnb = DEFAULT_PORT;

                if (uri.getPath() != null && uri.getPath().length() > 1) {
                    dbnb = Integer.parseInt(uri.getPath().substring(1));
                }

            } catch (URISyntaxException |
                     NumberFormatException e) {
                throw new SQLException("Could not parse JDBC URL: " + url, e);
            }

            return RedisConnectionFactory.getConnection(host, port, dbnb, info);

        }
    }

    @Override
    public int getMajorVersion() {
        return MAJOR_VERSION;
    }

    @Override
    public int getMinorVersion() {
        return MINOR_VERSION;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info)
            throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("getParentLogger");
    }
}
