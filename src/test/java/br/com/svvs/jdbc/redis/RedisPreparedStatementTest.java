package br.com.svvs.jdbc.redis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class RedisPreparedStatementTest {

    public static final String MOCK_RESPONSE = "mockResult";

    private RedisConnection connection;

    @Before
    public void before () throws Exception {
        connection = Mockito.mock(RedisConnection.class);
        when(connection.msgToServer(any(String.class))).thenReturn(MOCK_RESPONSE);
    }

    @Test
    public void executeQueryWithoutParameters() {
        try {
            RedisPreparedStatement stmt = new RedisPreparedStatement("get {\"id\":10}", connection);
            assertTrue(stmt.execute());
            assertEquals("get {\"id\":10}", stmt.sql);
            assertResultSetContainsMockedResponse(stmt);
        }catch (Exception e){
            Assert.fail("Failed with exception");
        }
    }
    @Test
    public void executeQueryWithIntegerParameter() {
        try {
            RedisPreparedStatement stmt = new RedisPreparedStatement("get {\"id\":?}", connection);
            stmt.setInt(1, 1000);
            assertTrue(stmt.execute());
            assertEquals("get {\"id\":1000}", stmt.sql);
            assertResultSetContainsMockedResponse(stmt);
        }catch (Exception e){
            Assert.fail("Failed with exception");
        }
    }

    @Test
    public void executeQueryWithNullParameter() {
        try {
            RedisPreparedStatement stmt = new RedisPreparedStatement("get {\"id\":?}", connection);
            stmt.setString(1, null);
            assertTrue(stmt.execute());
            assertEquals("get {\"id\":null}", stmt.sql);
            assertResultSetContainsMockedResponse(stmt);
        }catch (Exception e){
            Assert.fail("Failed with exception");
        }
    }

    @Test
    public void executeQueryWithIntAndStringParameters() {
        try {
            RedisPreparedStatement stmt = new RedisPreparedStatement("get {\"id\":?,type:\"?\"}", connection);
            stmt.setInt(1, 1000);
            stmt.setString(2, "test");
            assertTrue(stmt.execute());
            assertEquals("get {\"id\":1000,type:\"test\"}", stmt.sql);
            assertResultSetContainsMockedResponse(stmt);
        }catch (Exception e){
            Assert.fail("Failed with exception");
        }
    }

    @Test
    public void executeQueryWithQuoteReplacementString() {
        try {
            RedisPreparedStatement stmt = new RedisPreparedStatement("get {\"id\":?,type:\"?\"}", connection);
            stmt.setInt(1, 1000);
            stmt.setString(2, "te$t");
            assertTrue(stmt.execute());
            assertEquals("get {\"id\":1000,type:\"te$t\"}", stmt.sql);
            assertResultSetContainsMockedResponse(stmt);
        }catch (Exception e){
            Assert.fail("Failed with exception");
        }
    }

    private void assertResultSetContainsMockedResponse(RedisPreparedStatement stmt) throws SQLException {
        ResultSet resultSet = stmt.getResultSet();
        resultSet.next();
        assertNotNull(resultSet);
        assertEquals(MOCK_RESPONSE, resultSet.getString(0));
    }
}
