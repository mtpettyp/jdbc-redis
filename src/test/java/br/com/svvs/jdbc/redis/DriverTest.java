package br.com.svvs.jdbc.redis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class DriverTest {

    @Test
    public void testLoadDriver() {
        try {
            Class.forName("br.com.svvs.jdbc.redis.RedisDriver");
        } catch (ClassNotFoundException e) {
            fail("Could not load driver!");
        }
    }

    @Test
    public void testGetDriverForUrl() {
        try {
            Driver d = DriverManager.getDriver("jdbc:redis:");
            assertTrue(
                    "Not a instance of br.com.svvs.jdbc.redis.Driver, same URL already registered?",
                    d instanceof br.com.svvs.jdbc.redis.RedisDriver);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAcceptsURL() {
        Driver d = new br.com.svvs.jdbc.redis.RedisDriver();
        try {
            assertTrue(
                    "Valid URL returned false!",
                    d.acceptsURL("jdbc:redis:")
            );
            assertTrue(
                    "Valid URL returned false!",
                    d.acceptsURL("jdbc:redis://")
            );
            assertTrue(
                    "Valid URL returned false!",
                    d.acceptsURL("jdbc:redis://localhost")
            );
            assertTrue(
                    "Valid URL returned false!",
                    d.acceptsURL("jdbc:redis://localhost:6379")
            );
            assertTrue(
                    "Valid URL returned false!",
                    d.acceptsURL("jdbc:redis://localhost:6379/0")
            );
            assertTrue(
                    "Valid URL returned false!",
                    d.acceptsURL("jdbc:redis://localhost/0")
            );
            assertFalse(
                    "Invalid URL returned true!",
                    d.acceptsURL("jdbc:invalid")
            );
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConnect() {
        try {
            assertNotNull(DriverManager.getConnection("jdbc:redis://localhost:6379/0"));
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetMajorVersion() {
        assertTrue(new br.com.svvs.jdbc.redis.RedisDriver().getMajorVersion() >= 0);
    }

    @Test
    public void testGetMinorVersion() {
        assertTrue(new br.com.svvs.jdbc.redis.RedisDriver().getMinorVersion() >= 0);
    }

    //TODO: implement this test
    public void testGetPropertyInfo() {
        fail("Not yet implemented");
    }

    @Test
    public void testJdbcCompliant() {
        assertFalse(new br.com.svvs.jdbc.redis.RedisDriver().jdbcCompliant());
    }

}
