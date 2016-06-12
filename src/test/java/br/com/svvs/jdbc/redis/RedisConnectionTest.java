package br.com.svvs.jdbc.redis;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RedisConnectionTest extends BaseTest {

    private Connection connection;

    @Before
    public void connect() throws Exception {
        TestHelper t = TestHelper.getInstance();
        connection = t.getConnection();
    }

    @After
    public void quit() throws Exception {
        connection.close();
    }

    @Test
    public void validateUnimplementedMethods() throws Exception {
        assertNotSupported(() -> connection.createArrayOf(null, null));
        assertNotSupported(() -> connection.createBlob());
        assertNotSupported(() -> connection.createClob());
        assertNotSupported(() -> connection.createNClob());
        assertNotSupported(() -> connection.createSQLXML());
        assertNotSupported(() -> connection.createStatement(0, 0));
        assertNotSupported(() -> connection.createStatement(0, 0, 0));
        assertNotSupported(() -> connection.createStruct(null, null));
        assertNotSupported(() -> connection.getClientInfo());
        assertNotSupported(() -> connection.getClientInfo(null));
        assertNotSupported(() -> connection.getHoldability());
        assertNotSupported(() -> connection.getTypeMap());
        assertNotSupported(() -> connection.nativeSQL(null));
        assertNotSupported(() -> connection.prepareCall(null, 0, 0));
        assertNotSupported(() -> connection.prepareCall(null, 0, 0, 0));
        assertNotSupported(() -> connection.prepareCall(null));
        assertNotSupported(() -> connection.prepareStatement(null, 0));
        assertNotSupported(() -> connection.prepareStatement(null, (int[])null));
        assertNotSupported(() -> connection.prepareStatement(null, (String[])null));
        assertNotSupported(() -> connection.prepareStatement(null, 0, 0));
        assertNotSupported(() -> connection.prepareStatement(null, 0, 0, 0));
        assertNotSupported(() -> connection.releaseSavepoint(null));
        assertNotSupported(() -> connection.rollback());
        assertNotSupported(() -> connection.rollback(null));
        assertNotSupported(() -> connection.getAutoCommit());
        assertNotSupported(() -> connection.setAutoCommit(false));
        assertNotSupported(() -> connection.setCatalog(null));
        assertNotSupported(() -> connection.setHoldability(0));
        assertNotSupported(() -> connection.setReadOnly(false));
        assertNotSupported(() -> connection.setSavepoint());
        assertNotSupported(() -> connection.setSavepoint(null));
        assertNotSupported(() -> connection.setTransactionIsolation(0));
        assertNotSupported(() -> connection.setSchema(null));
        assertNotSupported(() -> connection.getSchema());
        assertNotSupported(() -> connection.abort(null));
        assertNotSupported(() -> connection.setNetworkTimeout(null, 0));
        assertNotSupported(() -> connection.getNetworkTimeout());
        assertNotSupported(() -> connection.isWrapperFor(null));
        assertNotSupported(() -> connection.unwrap(null));
    }
}
