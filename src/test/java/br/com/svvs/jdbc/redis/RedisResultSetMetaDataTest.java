package br.com.svvs.jdbc.redis;

import static java.sql.ResultSetMetaData.columnNoNulls;
import static java.sql.Types.NVARCHAR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSetMetaData;
import java.sql.Types;

import org.junit.Test;

public class RedisResultSetMetaDataTest extends BaseTest {

    private ResultSetMetaData metadata = new RedisResultSetMetaData();

    @Test
    public void checkValues() throws Exception {

        assertEquals(1, metadata.getColumnCount());
        assertFalse(metadata.isAutoIncrement(0));
        assertTrue(metadata.isCaseSensitive(0));
        assertFalse(metadata.isSearchable(0));
        assertFalse(metadata.isCurrency(0));
        assertEquals(columnNoNulls, metadata.isNullable(0));
        assertFalse(metadata.isSigned(0));
        assertEquals(1024, metadata.getColumnDisplaySize(0));
        assertEquals("", metadata.getColumnLabel(0));
        assertEquals("", metadata.getColumnName(0));
        assertEquals("", metadata.getSchemaName(0));
        assertEquals(1024, metadata.getPrecision(0));
        assertEquals(0, metadata.getScale(0));
        assertEquals("", metadata.getTableName(0));
        assertEquals("", metadata.getCatalogName(0));
        assertEquals(NVARCHAR, metadata.getColumnType(0));
        assertEquals("String", metadata.getColumnTypeName(0));
        assertTrue(metadata.isReadOnly(0));
        assertFalse(metadata.isWritable(0));
        assertEquals(false, metadata.isDefinitelyWritable(0));
        assertEquals("java.lang.String", metadata.getColumnClassName(0));
    }

    @Test
    public void validateUnimplementedMethods() throws Exception {
        assertNotSupported(() -> metadata.unwrap(null));
        assertNotSupported(() -> metadata.isWrapperFor(null));
    }
}
