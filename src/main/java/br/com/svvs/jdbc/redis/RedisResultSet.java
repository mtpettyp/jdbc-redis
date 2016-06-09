package br.com.svvs.jdbc.redis;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class RedisResultSet implements ResultSet {

    private boolean isClosed;
    protected int position = -1;
    protected String[] result;

    public RedisResultSet(final String[] result) {
        this.result = result;
    }

    @Override
    public boolean absolute(int row) throws SQLException {
        return false;
    }

    @Override
    public void afterLast() throws SQLException {
    }

    @Override
    public void beforeFirst() throws SQLException {
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
    }

    @Override
    public void clearWarnings() throws SQLException {
    }

    @Override
    public void close() throws SQLException {
    }

    @Override
    public void deleteRow() throws SQLException {
    }

    @Override
    public int findColumn(String columnName) throws SQLException {
        return 0;
    }

    @Override
    public boolean first() throws SQLException {
        return false;
    }

    @Override
    public Array getArray(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getArray");
    }

    @Override
    public Array getArray(String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getArray");
    }

    @Override
    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getAsciiStream");
    }

    @Override
    public InputStream getAsciiStream(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getAsciiStream");
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        try {
            return new BigDecimal(this.result[this.position]);
        } catch(NumberFormatException e) {
            throw new SQLException("Can't convert " + this.result[this.position] + " to BigDecimal.");
        }
    }

    @Override
    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return this.getBigDecimal(0);
    }

    @Override
    public BigDecimal getBigDecimal(int columnIndex, int scale)
            throws SQLException {
        try {
            return new BigDecimal(this.result[this.position]).setScale(scale);
        } catch(NumberFormatException e) {
            throw new SQLException("Can't convert " + this.result[this.position] + " to BigDecimal.");
        }
    }

    @Override
    public BigDecimal getBigDecimal(String columnName, int scale)
            throws SQLException {
        return this.getBigDecimal(0, scale);
    }

    @Override
    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBinaryStream");
    }

    @Override
    public InputStream getBinaryStream(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBinaryStream");
    }

    @Override
    public Blob getBlob(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBlob");
    }

    @Override
    public Blob getBlob(String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBlob");
    }

    @Override
    public boolean getBoolean(int columnIndex) throws SQLException {
        String r = this.result[this.position];
        if(r.equals("0") || r.equals("false")) {
            return false;
        } else if (r.equals("1") || r.equals("true")) {
            return true;
        } else {
            throw new SQLException("Don't know how to convert " + r + " into a boolean.");
        }
    }

    @Override
    public boolean getBoolean(String columnName) throws SQLException {
        return this.getBoolean(0);
    }

    /**
     * Will return the first byte of current row value. If the result has more
     * bytes only the first will be returned.
     */
    @Override
    public byte getByte(int columnIndex) throws SQLException {
        if(this.result[this.position] != null) {
            return this.result[this.position].getBytes()[0];
        } else {
            return 0;
        }
    }

    @Override
    public byte getByte(String columnName) throws SQLException {
        return this.getByte(0);
    }

    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        if(this.result[this.position] != null) {
            return this.result[this.position].getBytes();
        } else {
            return null;
        }
    }

    @Override
    public byte[] getBytes(String columnName) throws SQLException {
        return this.getBytes(0);
    }

    @Override
    public Reader getCharacterStream(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getCharacterStream");
    }

    @Override
    public Reader getCharacterStream(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getCharacterStream");
    }

    @Override
    public Clob getClob(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getClob");
    }

    @Override
    public Clob getClob(String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getClob");
    }

    @Override
    public int getConcurrency() throws SQLException {
        return ResultSet.CONCUR_READ_ONLY;
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new SQLFeatureNotSupportedException("getCursorName");
    }

    @Override
    public Date getDate(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public Date getDate(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public Date getDate(String columnName, Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public double getDouble(int columnIndex) throws SQLException {
        return Double.parseDouble(this.result[this.position]);
    }

    @Override
    public double getDouble(String columnName) throws SQLException {
        return this.getDouble(0);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return ResultSet.FETCH_FORWARD;
    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public float getFloat(int columnIndex) throws SQLException {
        return Float.parseFloat(this.result[this.position]);
    }

    @Override
    public float getFloat(String columnName) throws SQLException {
        return this.getFloat(0);
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException("getHoldability");
    }

    @Override
    public int getInt(int columnIndex) throws SQLException {
        return Integer.parseInt(this.result[this.position]);
    }

    @Override
    public int getInt(String columnName) throws SQLException {
        return this.getInt(0);
    }

    @Override
    public long getLong(int columnIndex) throws SQLException {
        return Long.parseLong(this.result[this.position]);
    }

    @Override
    public long getLong(String columnName) throws SQLException {
        return this.getLong(0);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException("getMetaData");
    }

    @Override
    public Reader getNCharacterStream(int arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNCharacterStream");
    }

    @Override
    public Reader getNCharacterStream(String arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNCharacterStream");
    }

    @Override
    public NClob getNClob(int arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNClob");
    }

    @Override
    public NClob getNClob(String arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNClob");
    }

    @Override
    public String getNString(int arg0) throws SQLException {
        return this.result[this.position];
    }

    @Override
    public String getNString(String arg0) throws SQLException {
        return getNString(0);
    }

    @Override
    public Object getObject(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject");
    }

    @Override
    public Object getObject(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject");
    }

    @Override
    public Object getObject(int i, Map<String, Class<?>> map)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject");
    }

    @Override
    public Object getObject(String colName, Map<String, Class<?>> map)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject");
    }

    @Override
    public Ref getRef(int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRef");
    }

    @Override
    public Ref getRef(String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRef");
    }

    @Override
    public int getRow() throws SQLException {
        return this.position + 1;
    }

    @Override
    public RowId getRowId(int arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRowId");
    }

    @Override
    public RowId getRowId(String arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRowId");
    }

    @Override
    public SQLXML getSQLXML(int arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSQLXML");
    }

    @Override
    public SQLXML getSQLXML(String arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSQLXML");
    }

    @Override
    public short getShort(int columnIndex) throws SQLException {
        return Short.parseShort(this.result[this.position]);
    }

    @Override
    public short getShort(String columnName) throws SQLException {
        return this.getShort(0);
    }

    @Override
    public Statement getStatement() throws SQLException {
        return null;
    }

    @Override
    public String getString(int columnIndex) throws SQLException {
        return this.result[this.position];
    }

    @Override
    public String getString(String columnName) throws SQLException {
        return this.result[this.position];
    }

    @Override
    public Time getTime(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Time getTime(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Time getTime(String columnName, Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public Timestamp getTimestamp(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public Timestamp getTimestamp(int columnIndex, Calendar cal)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public Timestamp getTimestamp(String columnName, Calendar cal)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public int getType() throws SQLException {
        return ResultSet.TYPE_FORWARD_ONLY;
    }

    @Override
    public URL getURL(int columnIndex) throws SQLException {
        try {
            return new URL(this.result[this.position]);
        } catch (MalformedURLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public URL getURL(String columnName) throws SQLException {
        return this.getURL(0);
    }

    @Override
    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getUnicodeStream");
    }

    @Override
    public InputStream getUnicodeStream(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getUnicodeStream");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void insertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("insertRow");
    }

    @Override
    public boolean isAfterLast() throws SQLException {
        return position >= result.length;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        //TODO: valid for SCAN
        return position < 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
    }

    @Override
    public boolean isFirst() throws SQLException {
        //TODO: valid for SCAN
        return position == 0;
    }

    @Override
    public boolean isLast() throws SQLException {
        return position == result.length - 1;
    }

    @Override
    public boolean last() throws SQLException {
        throw new SQLFeatureNotSupportedException("last");
    }

    @Override
    public void moveToCurrentRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("moveToCurrentRow");
    }

    @Override
    public void moveToInsertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("moveToInsertRow");
    }

    @Override
    public boolean next() throws SQLException {
        if (position < result.length - 1) {
            position++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean previous() throws SQLException {
        throw new SQLFeatureNotSupportedException("previous");
    }

    @Override
    public void refreshRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("refreshRow");
    }

    @Override
    public boolean relative(int rows) throws SQLException {
        throw new SQLFeatureNotSupportedException("relative");
    }

    @Override
    public boolean rowDeleted() throws SQLException {
        throw new SQLFeatureNotSupportedException("rowDeleted");
    }

    @Override
    public boolean rowInserted() throws SQLException {
        throw new SQLFeatureNotSupportedException("rowInserted");
    }

    @Override
    public boolean rowUpdated() throws SQLException {
        throw new SQLFeatureNotSupportedException("rowUpdated");
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchDirection");
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchSize");
    }

    @Override
    public void updateArray(int columnIndex, Array x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateArray");
    }

    @Override
    public void updateArray(String columnName, Array x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateArray");
    }

    @Override
    public void updateAsciiStream(int arg0, InputStream arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(String arg0, InputStream arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(int columnIndex, InputStream x, int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(String columnName, InputStream x, int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(int arg0, InputStream arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(String arg0, InputStream arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateBigDecimal(int columnIndex, BigDecimal x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBigDecimal");
    }

    @Override
    public void updateBigDecimal(String columnName, BigDecimal x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBigDecimal");
    }

    @Override
    public void updateBinaryStream(int arg0, InputStream arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(String arg0, InputStream arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(String columnName, InputStream x, int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(int arg0, InputStream arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(String arg0, InputStream arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBlob(int columnIndex, Blob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(String columnName, Blob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(int arg0, InputStream arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(String arg0, InputStream arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(int arg0, InputStream arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(String arg0, InputStream arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBoolean(int columnIndex, boolean x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBoolean");
    }

    @Override
    public void updateBoolean(String columnName, boolean x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBoolean");
    }

    @Override
    public void updateByte(int columnIndex, byte x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateByte");
    }

    @Override
    public void updateByte(String columnName, byte x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateByte");
    }

    @Override
    public void updateBytes(int columnIndex, byte[] x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBytes");
    }

    @Override
    public void updateBytes(String columnName, byte[] x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBytes");
    }

    @Override
    public void updateCharacterStream(int arg0, Reader arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(String arg0, Reader arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(int columnIndex, Reader x, int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(String columnName, Reader reader,
            int length) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(int arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(String arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateClob(int columnIndex, Clob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(String columnName, Clob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(int arg0, Reader arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(String arg0, Reader arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(int arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(String arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateDate(int columnIndex, Date x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDate");
    }

    @Override
    public void updateDate(String columnName, Date x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDate");
    }

    @Override
    public void updateDouble(int columnIndex, double x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDouble");
    }

    @Override
    public void updateDouble(String columnName, double x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDouble");
    }

    @Override
    public void updateFloat(int columnIndex, float x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateFloat");
    }

    @Override
    public void updateFloat(String columnName, float x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateFloat");
    }

    @Override
    public void updateInt(int columnIndex, int x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateInt");
    }

    @Override
    public void updateInt(String columnName, int x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateInt");
    }

    @Override
    public void updateLong(int columnIndex, long x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateLong");
    }

    @Override
    public void updateLong(String columnName, long x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateLong");
    }

    @Override
    public void updateNCharacterStream(int arg0, Reader arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream");
    }

    @Override
    public void updateNCharacterStream(String arg0, Reader arg1)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream");
    }

    @Override
    public void updateNCharacterStream(int arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream");
    }

    @Override
    public void updateNCharacterStream(String arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream");
    }

    @Override
    public void updateNClob(int arg0, NClob arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(String arg0, NClob arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(int arg0, Reader arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(String arg0, Reader arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(int arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(String arg0, Reader arg1, long arg2)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNString(int arg0, String arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNString");
    }

    @Override
    public void updateNString(String arg0, String arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNString");
    }

    @Override
    public void updateNull(int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNull");
    }

    @Override
    public void updateNull(String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNull");
    }

    @Override
    public void updateObject(int columnIndex, Object x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject");
    }

    @Override
    public void updateObject(String columnName, Object x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject");
    }

    @Override
    public void updateObject(int columnIndex, Object x, int scale)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject");
    }

    @Override
    public void updateObject(String columnName, Object x, int scale)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject");
    }

    @Override
    public void updateRef(int columnIndex, Ref x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRef");
    }

    @Override
    public void updateRef(String columnName, Ref x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRef");
    }

    @Override
    public void updateRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRow");
    }

    @Override
    public void updateRowId(int arg0, RowId arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRowId");
    }

    @Override
    public void updateRowId(String arg0, RowId arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRowId");
    }

    @Override
    public void updateSQLXML(int arg0, SQLXML arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateSQLXML");
    }

    @Override
    public void updateSQLXML(String arg0, SQLXML arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateSQLXML");
    }

    @Override
    public void updateShort(int columnIndex, short x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateShort");
    }

    @Override
    public void updateShort(String columnName, short x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateShort");
    }

    @Override
    public void updateString(int columnIndex, String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateString");
    }

    @Override
    public void updateString(String columnName, String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateString");
    }

    @Override
    public void updateTime(int columnIndex, Time x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTime");
    }

    @Override
    public void updateTime(String columnName, Time x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTime");
    }

    @Override
    public void updateTimestamp(int columnIndex, Timestamp x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTimestamp");
    }

    @Override
    public void updateTimestamp(String columnName, Timestamp x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTimestamp");
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.result[this.position] == null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor");
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap");
    }

    @Override
    public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
        return null;
    }

    @Override
    public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
        return null;
    }

}
