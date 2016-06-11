package br.com.svvs.jdbc.redis;

import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.ResultSet;

import org.junit.Test;

public class RedisResultSetTest extends BaseTest {

    @Test
    public void integerMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {"5", "1.1", "c", null});
        rs.next();
        assertEquals(5, rs.getInt(0));
        assertEquals(5, rs.getInt(""));
        rs.next();
        assertSQLException(() -> rs.getInt(0));
        assertSQLException(() -> rs.getInt(""));
        rs.next();
        assertSQLException(() -> rs.getInt(0));
        assertSQLException(() -> rs.getInt(""));
        rs.next();
        assertEquals(0, rs.getInt(0));
        assertEquals(0, rs.getInt(""));
        rs.close();
    }

    @Test
    public void longMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {"5", "1.1", "c", null});
        rs.next();
        assertEquals(5, rs.getLong(0));
        assertEquals(5, rs.getLong(""));
        rs.next();
        assertSQLException(() -> rs.getLong(0));
        assertSQLException(() -> rs.getLong(""));
        rs.next();
        assertSQLException(() -> rs.getLong(0));
        assertSQLException(() -> rs.getLong(""));
        rs.next();
        assertEquals(0, rs.getLong(0));
        assertEquals(0, rs.getLong(""));
        rs.close();
    }

    @Test
    public void shortMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {"5", "1.1", "c", null});
        rs.next();
        assertEquals(5, rs.getShort(0));
        assertEquals(5, rs.getShort(""));
        rs.next();
        assertSQLException(() -> rs.getShort(0));
        assertSQLException(() -> rs.getShort(""));
        rs.next();
        assertSQLException(() -> rs.getShort(0));
        assertSQLException(() -> rs.getShort(""));
        rs.next();
        assertEquals(0, rs.getShort(0));
        assertEquals(0, rs.getShort(""));
        rs.close();
    }

    @Test
    public void doubleMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {"5", "1.1", "c", null});
        rs.next();
        assertEquals(5.0, rs.getDouble(0), 0.001);
        assertEquals(5.0, rs.getDouble(""), 0.001);
        rs.next();
        assertEquals(1.1, rs.getDouble(0), 0.001);
        assertEquals(1.1, rs.getDouble(""), 0.001);
        rs.next();
        assertSQLException(() -> rs.getDouble(0));
        assertSQLException(() -> rs.getDouble(""));
        rs.next();
        assertEquals(0.0, rs.getDouble(0), 0.001);
        assertEquals(0.0, rs.getDouble(""), 0.001);

        rs.close();
    }

    @Test
    public void floatMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {"5", "1.1", "c", null});
        rs.next();
        assertEquals(5.0, rs.getFloat(0), 0.001);
        assertEquals(5.0, rs.getFloat(""), 0.001);
        rs.next();
        assertEquals(1.1, rs.getFloat(0), 0.001);
        assertEquals(1.1, rs.getFloat(""), 0.001);
        rs.next();
        assertSQLException(() -> rs.getFloat(0));
        assertSQLException(() -> rs.getFloat(""));
        rs.next();
        assertEquals(0.0, rs.getFloat(0), 0.001);
        assertEquals(0.0, rs.getFloat(""), 0.001);

        rs.close();
    }

    @Test
    public void positionalMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {"a", "b", "c"});

        assertEquals(TYPE_SCROLL_INSENSITIVE, rs.getFetchDirection());
        assertEquals(3, rs.getFetchSize());
        assertEquals(0, rs.getRow());

        assertTrue(rs.isBeforeFirst());
        assertFalse(rs.isFirst());
        assertFalse(rs.isLast());
        assertFalse(rs.isAfterLast());

        assertTrue(rs.first());
        assertEquals("a", rs.getString(0));
        assertFalse(rs.isBeforeFirst());
        assertTrue(rs.isFirst());
        assertFalse(rs.isLast());
        assertFalse(rs.isAfterLast());

        assertTrue(rs.last());
        assertEquals("c", rs.getString(0));
        assertFalse(rs.isBeforeFirst());
        assertFalse(rs.isFirst());
        assertTrue(rs.isLast());
        assertFalse(rs.isAfterLast());

        rs.afterLast();
        assertFalse(rs.isBeforeFirst());
        assertFalse(rs.isFirst());
        assertFalse(rs.isLast());
        assertTrue(rs.isAfterLast());
        assertSQLException(() -> rs.getString(0));

        rs.beforeFirst();
        assertTrue(rs.isBeforeFirst());
        assertFalse(rs.isFirst());
        assertFalse(rs.isLast());
        assertFalse(rs.isAfterLast());
        assertSQLException(() -> rs.getString(0));

        rs.close();
    }


    @Test
    public void validateUnimplementedMethods() throws Exception {
        ResultSet rs = new RedisResultSet(new String[] {});

        assertNotSupported(() -> rs.cancelRowUpdates());
        assertNotSupported(() -> rs.insertRow());
        assertNotSupported(() -> rs.deleteRow());
        assertNotSupported(() -> rs.getArray(0));
        assertNotSupported(() -> rs.getArray(""));
        assertNotSupported(() -> rs.getDate(0));
        assertNotSupported(() -> rs.getDate(""));
        assertNotSupported(() -> rs.getDate(0, null));
        assertNotSupported(() -> rs.getDate("", null));
        assertNotSupported(() -> rs.getTime(0));
        assertNotSupported(() -> rs.getTimestamp(0));
        assertNotSupported(() -> rs.getTime(""));
        assertNotSupported(() -> rs.getTimestamp(""));
        assertNotSupported(() -> rs.getTime(0, null));
        assertNotSupported(() -> rs.getTime("", null));
        assertNotSupported(() -> rs.getTimestamp(0, null));
        assertNotSupported(() -> rs.getTimestamp("", null));
        assertNotSupported(() -> rs.moveToInsertRow());
        assertNotSupported(() -> rs.updateNull(0));
        assertNotSupported(() -> rs.updateBoolean(0, true));
        assertNotSupported(() -> rs.updateByte(0, (byte)0));
        assertNotSupported(() -> rs.updateShort(0, (short)0));
        assertNotSupported(() -> rs.updateInt(0, 0));
        assertNotSupported(() -> rs.updateLong(0, 0));
        assertNotSupported(() -> rs.updateFloat(0, 0));
        assertNotSupported(() -> rs.updateDouble(0, 0));
        assertNotSupported(() -> rs.updateBigDecimal(0, null));
        assertNotSupported(() -> rs.updateString(0, ""));
        assertNotSupported(() -> rs.updateBytes(0, null));
        assertNotSupported(() -> rs.updateDate(0, null));
        assertNotSupported(() -> rs.updateTime(0, null));
        assertNotSupported(() -> rs.updateTimestamp(0, null));
        assertNotSupported(() -> rs.updateAsciiStream(0, null, 0));
        assertNotSupported(() -> rs.updateBinaryStream(0, null, 0));
        assertNotSupported(() -> rs.updateCharacterStream(0, null, 0));
        assertNotSupported(() -> rs.updateObject(0, null, 0));
        assertNotSupported(() -> rs.updateObject(0, null));
        assertNotSupported(() -> rs.updateNull(""));
        assertNotSupported(() -> rs.updateBoolean("", true));
        assertNotSupported(() -> rs.updateByte("", (byte)0));
        assertNotSupported(() -> rs.updateShort("", (short)0));
        assertNotSupported(() -> rs.updateInt("", 0));
        assertNotSupported(() -> rs.updateLong("", 0));
        assertNotSupported(() -> rs.updateFloat("", 0));
        assertNotSupported(() -> rs.updateDouble("", 0));
        assertNotSupported(() -> rs.updateBigDecimal("", null));
        assertNotSupported(() -> rs.updateString("", ""));
        assertNotSupported(() -> rs.updateBytes("", null));
        assertNotSupported(() -> rs.updateDate("", null));
        assertNotSupported(() -> rs.updateTime("", null));
        assertNotSupported(() -> rs.updateTimestamp("", null));
        assertNotSupported(() -> rs.updateAsciiStream("", null, 0));
        assertNotSupported(() -> rs.updateBinaryStream("", null, 0));
        assertNotSupported(() -> rs.updateCharacterStream("", null, 0));
        assertNotSupported(() -> rs.updateObject("", null, 0));
        assertNotSupported(() -> rs.updateObject("", null));
        assertNotSupported(() -> rs.updateRow());
        assertNotSupported(() -> rs.updateRef(0, null));
        assertNotSupported(() -> rs.updateRef("", null));
        assertNotSupported(() -> rs.updateBlob(0, (Blob)null));
        assertNotSupported(() -> rs.updateBlob("", (Blob)null));
        assertNotSupported(() -> rs.updateClob(0, (Clob)null));
        assertNotSupported(() -> rs.updateClob("", (Clob)null));
        assertNotSupported(() -> rs.updateArray(0, null));
        assertNotSupported(() -> rs.updateArray("", null));
        assertNotSupported(() -> rs.updateRowId(0, null));
        assertNotSupported(() -> rs.updateRowId("", null));
        assertNotSupported(() -> rs.updateNString(0, ""));
        assertNotSupported(() -> rs.updateNString("", ""));
        assertNotSupported(() -> rs.updateNClob(0, (NClob)null));
        assertNotSupported(() -> rs.updateNClob("", (NClob)null));
        assertNotSupported(() -> rs.updateSQLXML(0, null));
        assertNotSupported(() -> rs.updateSQLXML("", null));
        assertNotSupported(() -> rs.updateNCharacterStream(0, null, 0));
        assertNotSupported(() -> rs.updateNCharacterStream("", null, 0));
        assertNotSupported(() -> rs.updateAsciiStream(0, null, (long)0));
        assertNotSupported(() -> rs.updateBinaryStream(0, null, (long)0));
        assertNotSupported(() -> rs.updateCharacterStream(0, null, (long)0));
        assertNotSupported(() -> rs.updateAsciiStream("", null, (long)0));
        assertNotSupported(() -> rs.updateBinaryStream("", null, (long)0));
        assertNotSupported(() -> rs.updateCharacterStream("", null, (long)0));
        assertNotSupported(() -> rs.updateBlob(0, null, 0));
        assertNotSupported(() -> rs.updateBlob("", null, 0));
        assertNotSupported(() -> rs.updateClob(0, null, 0));
        assertNotSupported(() -> rs.updateClob("", null, 0));
        assertNotSupported(() -> rs.updateNClob(0, null, 0));
        assertNotSupported(() -> rs.updateNClob("", null, 0));
        assertNotSupported(() -> rs.updateNCharacterStream(0, null));
        assertNotSupported(() -> rs.updateNCharacterStream("", null));
        assertNotSupported(() -> rs.updateAsciiStream(0, null));
        assertNotSupported(() -> rs.updateBinaryStream(0, null));
        assertNotSupported(() -> rs.updateCharacterStream(0, null));
        assertNotSupported(() -> rs.updateAsciiStream("", null));
        assertNotSupported(() -> rs.updateBinaryStream("", null));
        assertNotSupported(() -> rs.updateCharacterStream("", null));
        assertNotSupported(() -> rs.updateBlob(0, (InputStream)null));
        assertNotSupported(() -> rs.updateBlob("", (InputStream)null));
        assertNotSupported(() -> rs.updateClob(0, (Reader)null));
        assertNotSupported(() -> rs.updateClob("", (Reader)null));
        assertNotSupported(() -> rs.updateNClob(0, (Reader)null));
        assertNotSupported(() -> rs.updateNClob("", (Reader)null));
        assertNotSupported(() -> rs.updateObject(0, null, null, 0));
        assertNotSupported(() -> rs.updateObject("", null, null, 0));
        assertNotSupported(() -> rs.updateObject(0, null, null));
        assertNotSupported(() -> rs.updateObject("", null, null));

        rs.close();
    }
}
