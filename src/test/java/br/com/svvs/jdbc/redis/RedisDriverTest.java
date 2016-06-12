package br.com.svvs.jdbc.redis;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class RedisDriverTest extends BaseTest {

    @Test
    public void testGetDriverForUrl() throws Exception {
        Driver d = DriverManager.getDriver("jdbc:redis:");
        assertTrue(d instanceof RedisDriver);
    }

    @Test
    public void testConnect() throws Exception {
        assertNotNull(DriverManager.getConnection("jdbc:redis:///"));
        assertNotNull(DriverManager.getConnection("jdbc:redis://localhost"));
        assertNotNull(DriverManager.getConnection("jdbc:redis://localhost:6379"));
        assertNotNull(DriverManager.getConnection("jdbc:redis://localhost:6379/0"));
        assertNotNull(DriverManager.getConnection("jdbc:redis://localhost/0"));
        assertSQLException(() -> DriverManager.getConnection("jdbc:redis://localhost/my_db"));
    }

    @Test(expected = SQLException.class)
    public void testInvalidUrl() throws Exception {
        new RedisDriver().connect("jdbc:invalid", null);
    }

    @Test
    public void testGetMajorVersion() {
        assertEquals(0, new RedisDriver().getMajorVersion() );
    }

    @Test
    public void testGetMinorVersion() {
        assertTrue(new RedisDriver().getMinorVersion() >= 0);
    }

    @Test
    public void jdbcCompliant() {
        assertFalse(new RedisDriver().jdbcCompliant());
    }

    @Test
    public void propertyInfo() throws Exception {
        assertArrayEquals(new DriverPropertyInfo[]{}, new RedisDriver().getPropertyInfo(null, null));
    }

    @Test
    public void validateUnimplementedMethods() throws Exception {
        assertNotSupported(() -> new RedisDriver().getParentLogger());
    }

}
