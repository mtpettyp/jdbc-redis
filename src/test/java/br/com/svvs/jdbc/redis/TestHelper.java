package br.com.svvs.jdbc.redis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class TestHelper {

    private final String propFile = "config";
    private final static TestHelper instance = new TestHelper();

    private ResourceBundle bundle;

    private TestHelper() {
        try {
            Class.forName("br.com.svvs.jdbc.redis.RedisDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Can't load driver class: " + e.getMessage());
        }
        bundle = ResourceBundle.getBundle(propFile);
    }

    public static TestHelper getInstance() {
        return instance;
    }

    public String get(String propName) {
        return instance.bundle.getString(propName);
    }

    public String getConnectionString() {
        String connUrl = "jdbc:redis://" +
            instance.get("host") + ":" +
            instance.get("port") + "/" +
            instance.get("dbnb");
        return connUrl;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.getConnectionString());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
