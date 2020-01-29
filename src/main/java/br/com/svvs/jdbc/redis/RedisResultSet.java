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
import java.util.Arrays;
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
    public boolean next() throws SQLException {
        checkIfClosed();
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
    public boolean isAfterLast() throws SQLException {
        checkIfClosed();
        return position >= result.length;
    }

    @Override
    public boolean isBeforeFirst() throws SQLException {
        checkIfClosed();
        //TODO: valid for SCAN
        return position < 0;
    }

    @Override
    public boolean isFirst() throws SQLException {
        checkIfClosed();
        //TODO: valid for SCAN
        return position == 0;
    }

    @Override
    public boolean first() throws SQLException {
        checkIfClosed();
        position = 0;
        return result.length > 0;
    }

    @Override
    public boolean isLast() throws SQLException {
        checkIfClosed();
        return position == result.length - 1;
    }

    @Override
    public boolean last() throws SQLException {
        position = result.length - 1;
        return result.length > 0;
    }

    @Override
    public boolean absolute(final int row) throws SQLException {
        checkIfClosed();
        return false;
    }

    @Override
    public boolean relative(final int rows) throws SQLException {
        throw new SQLFeatureNotSupportedException("relative");
    }

    @Override
    public void afterLast() throws SQLException {
        checkIfClosed();
        position = result.length;
    }

    @Override
    public void beforeFirst() throws SQLException {
        checkIfClosed();
        position = -1;
    }

    @Override
    public void cancelRowUpdates() throws SQLException {
        throw new SQLFeatureNotSupportedException("cancelRowUpdates");
    }

    @Override
    public void clearWarnings() throws SQLException {
        checkIfClosed();
    }

    @Override
    public void close() throws SQLException {
        isClosed = true;
    }

    @Override
    public void deleteRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("deleteRow");
    }

    @Override
    public int findColumn(final String columnName) throws SQLException {
        checkIfClosed();
        return 0;
    }

    @Override
    public Array getArray(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getArray");
    }

    @Override
    public Array getArray(final String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getArray");
    }

    @Override
    public InputStream getAsciiStream(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getAsciiStream");
    }

    @Override
    public InputStream getAsciiStream(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getAsciiStream");
    }

    @Override
    public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
        checkIfClosed();
        try {
            return new BigDecimal(getString(0));
        } catch(NumberFormatException e) {
            throw new SQLException("Can't convert " + getString(0) + " to BigDecimal.");
        }
    }

    @Override
    public BigDecimal getBigDecimal(final String columnName) throws SQLException {
        return getBigDecimal(0);
    }

    @Override
    public BigDecimal getBigDecimal(final int columnIndex, final int scale)
            throws SQLException {
        checkIfClosed();
        try {
            return new BigDecimal(getString(0)).setScale(scale);
        } catch(NumberFormatException e) {
            throw new SQLException("Can't convert " + getString(0) + " to BigDecimal.");
        }
    }

    @Override
    public BigDecimal getBigDecimal(final String columnName, final int scale)
            throws SQLException {
        return getBigDecimal(0, scale);
    }

    @Override
    public InputStream getBinaryStream(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBinaryStream");
    }

    @Override
    public InputStream getBinaryStream(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBinaryStream");
    }

    @Override
    public Blob getBlob(final int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBlob");
    }

    @Override
    public Blob getBlob(final String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getBlob");
    }

    @Override
    public boolean getBoolean(final int columnIndex) throws SQLException {
        checkIfClosed();
        String r = getString(0);
        if(r.equals("0") || r.equals("false")) {
            return false;
        } else if (r.equals("1") || r.equals("true")) {
            return true;
        } else {
            throw new SQLException("Don't know how to convert " + r + " into a boolean.");
        }
    }

    @Override
    public boolean getBoolean(final String columnName) throws SQLException {
        return getBoolean(0);
    }

    /**
     * Will return the first byte of current row value. If the result has more
     * bytes only the first will be returned.
     */
    @Override
    public byte getByte(final int columnIndex) throws SQLException {
        checkIfClosed();
        if(getString(0) != null) {
            return getString(0).getBytes()[0];
        } else {
            return 0;
        }
    }

    @Override
    public byte getByte(final String columnName) throws SQLException {
        return getByte(0);
    }

    @Override
    public byte[] getBytes(final int columnIndex) throws SQLException {
        checkIfClosed();
        if(getString(0) != null) {
            return getString(0).getBytes();
        } else {
            return null;
        }
    }

    @Override
    public byte[] getBytes(final String columnName) throws SQLException {
        return getBytes(0);
    }

    @Override
    public Reader getCharacterStream(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getCharacterStream");
    }

    @Override
    public Reader getCharacterStream(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getCharacterStream");
    }

    @Override
    public Clob getClob(final int i) throws SQLException {
        throw new SQLFeatureNotSupportedException("getClob");
    }

    @Override
    public Clob getClob(final String colName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getClob");
    }

    @Override
    public int getConcurrency() throws SQLException {
        checkIfClosed();
        return ResultSet.CONCUR_READ_ONLY;
    }

    @Override
    public String getCursorName() throws SQLException {
        throw new SQLFeatureNotSupportedException("getCursorName");
    }

    @Override
    public Date getDate(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public Date getDate(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public Date getDate(final String columnName, final Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getDate");
    }

    @Override
    public double getDouble(final int columnIndex) throws SQLException {
        String value = getString(0);

        if (value == null) {
            return 0;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid value for getDouble() - " + value);
        }
    }

    @Override
    public double getDouble(final String columnName) throws SQLException {
        return getDouble(0);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        checkIfClosed();
        return ResultSet.TYPE_SCROLL_INSENSITIVE;
    }

    @Override
    public int getFetchSize() throws SQLException {
        checkIfClosed();
        return result.length;
    }

    @Override
    public float getFloat(final int columnIndex) throws SQLException {
        String value = getString(0);

        if (value == null) {
            return 0;
        }

        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid value for getFloat() - " + value);
        }
    }

    @Override
    public float getFloat(final String columnName) throws SQLException {
        return getFloat(0);
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException("getHoldability");
    }

    @Override
    public int getInt(final int columnIndex) throws SQLException {
        String value = getString(0);

        if (value == null) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid value for getInt() - " + value);
        }
    }

    @Override
    public int getInt(final String columnName) throws SQLException {
        checkIfClosed();
        return getInt(0);
    }

    @Override
    public long getLong(final int columnIndex) throws SQLException {
        String value = getString(0);

        if (value == null) {
            return 0;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid value for getLong() - " + value);
        }
    }

    @Override
    public long getLong(final String columnName) throws SQLException {
        return getLong(0);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
    	String[] result = this.result;
        return new ResultSetMetaData() {
			
			@Override
			public <T> T unwrap(Class<T> iface) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isWrapperFor(Class<?> iface) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isWritable(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSigned(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isSearchable(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isReadOnly(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public int isNullable(int column) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean isDefinitelyWritable(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isCurrency(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isCaseSensitive(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAutoIncrement(int column) throws SQLException {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String getTableName(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getSchemaName(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getScale(int column) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getPrecision(int column) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getColumnTypeName(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getColumnType(int column) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getColumnName(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getColumnLabel(int column) throws SQLException {
				// TODO Auto-generated method stub
				return "result";
			}
			
			@Override
			public int getColumnDisplaySize(int column) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getColumnCount() throws SQLException {
				if(result == null || result.length <= 0)
					return 0;
				return 1;
			}
			
			@Override
			public String getColumnClassName(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getCatalogName(int column) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		};
    }

    @Override
    public Reader getNCharacterStream(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNCharacterStream");
    }

    @Override
    public Reader getNCharacterStream(final String columnName) throws SQLException {
        return getNCharacterStream(0);
    }

    @Override
    public NClob getNClob(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getNClob");
    }

    @Override
    public NClob getNClob(final String columnName) throws SQLException {
        return getNClob(0);
    }

    @Override
    public String getNString(final int columnIndex) throws SQLException {
        checkIfClosed();
        return result[position];
    }

    @Override
    public String getNString(final String columnName) throws SQLException {
        return getNString(0);
    }

    @Override
    public Object getObject(final int columnIndex) throws SQLException {
    	return Arrays.asList(this.result);
    }

    @Override
    public Object getObject(final String columnName) throws SQLException {
        return getObject(0);
    }

    @Override
    public Object getObject(final int columnIndex, final Map<String, Class<?>> map)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getObject");
    }

    @Override
    public Object getObject(final String columnName, final Map<String, Class<?>> map)
            throws SQLException {
        return getObject(0, map);
    }

    @Override
    public Ref getRef(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRef");
    }

    @Override
    public Ref getRef(final String columnName) throws SQLException {
        return getRef(0);
    }

    @Override
    public int getRow() throws SQLException {
        checkIfClosed();
        return this.position + 1;
    }

    @Override
    public RowId getRowId(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getRowId");
    }

    @Override
    public RowId getRowId(final String columnName) throws SQLException {
        return getRowId(0);
    }

    @Override
    public SQLXML getSQLXML(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getSQLXML");
    }

    @Override
    public SQLXML getSQLXML(final String columnName) throws SQLException {
        return getSQLXML(0);
    }

    @Override
    public short getShort(final int columnIndex) throws SQLException {
        String value = getString(0);

        if (value == null) {
            return 0;
        }

        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            throw new SQLException("Invalid value for getShort() - " + value);
        }
    }

    @Override
    public short getShort(final String columnName) throws SQLException {
        return getShort(0);
    }

    @Override
    public Statement getStatement() throws SQLException {
        checkIfClosed();
        //TODO: implement
        return null;
    }

    @Override
    public String getString(final int columnIndex) throws SQLException {
        checkIfClosed();

        if (isAfterLast() || isBeforeFirst()) {
            throw new SQLException("columnIndex is not valid");
        }

        return result[position];
    }

    @Override
    public String getString(final String columnName) throws SQLException {
        return getString(0);
    }

    @Override
    public Time getTime(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Time getTime(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Time getTime(final String columnName, final Calendar cal) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTime");
    }

    @Override
    public Timestamp getTimestamp(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public Timestamp getTimestamp(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public Timestamp getTimestamp(final int columnIndex, final Calendar cal)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public Timestamp getTimestamp(final String columnName, final Calendar cal)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("getTimestamp");
    }

    @Override
    public int getType() throws SQLException {
        checkIfClosed();
        return ResultSet.TYPE_SCROLL_INSENSITIVE;
    }

    @Override
    public URL getURL(final int columnIndex) throws SQLException {
        checkIfClosed();
        try {
            return new URL(result[position]);
        } catch (MalformedURLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public URL getURL(final String columnName) throws SQLException {
        return getURL(0);
    }

    @Override
    public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("getUnicodeStream");
    }

    @Override
    public InputStream getUnicodeStream(final String columnName) throws SQLException {
        throw new SQLFeatureNotSupportedException("getUnicodeStream");
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        checkIfClosed();
        return null;
    }

    @Override
    public void insertRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("insertRow");
    }

    @Override
    public boolean isClosed() throws SQLException {
        return isClosed;
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
    public void refreshRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("refreshRow");
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
    public void setFetchDirection(final int direction) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchDirection");
    }

    @Override
    public void setFetchSize(final int rows) throws SQLException {
        throw new SQLFeatureNotSupportedException("setFetchSize");
    }

    @Override
    public void updateArray(final int columnIndex, final Array x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateArray");
    }

    @Override
    public void updateArray(final String columnName, final Array x) throws SQLException {
        updateArray(0, x);
    }

    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(final String columnIndex, final InputStream x)
            throws SQLException {
        updateAsciiStream(0, x);
    }

    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(final String columnName, final InputStream x, final int length)
            throws SQLException {
        updateAsciiStream(0, x, length);
    }

    @Override
    public void updateAsciiStream(final int columnIndex, final InputStream x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateAsciiStream");
    }

    @Override
    public void updateAsciiStream(final String columnName, final InputStream x, final long length)
            throws SQLException {
        updateAsciiStream(0, x, length);
    }

    @Override
    public void updateBigDecimal(final int columnIndex, final BigDecimal x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBigDecimal");
    }

    @Override
    public void updateBigDecimal(final String columnName, final BigDecimal x)
            throws SQLException {
        updateBigDecimal(0, x);
    }

    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(final String columnName, final InputStream x)
            throws SQLException {
        updateBinaryStream(0, x);
    }

    @Override
    public void updateBinaryStream(int columnIndex, InputStream x, int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(final String columnName, final InputStream x, final int length)
            throws SQLException {
        updateBinaryStream(0, x, length);
    }

    @Override
    public void updateBinaryStream(final int columnIndex, final InputStream x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBinaryStream");
    }

    @Override
    public void updateBinaryStream(final String columnName, final InputStream x, final long length)
            throws SQLException {
        updateBinaryStream(0, x, length);
    }

    @Override
    public void updateBlob(final int columnIndex, final Blob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(final String columnName, final Blob x) throws SQLException {
        updateBlob(0, x);
    }

    @Override
    public void updateBlob(final int columnIndex, final InputStream x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(final String columnName, final InputStream x) throws SQLException {
        updateBlob(0, x);
    }

    @Override
    public void updateBlob(final int columnIndex, final InputStream x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBlob");
    }

    @Override
    public void updateBlob(final String columnName, final InputStream x, final long length)
            throws SQLException {
        updateBlob(0, x, length);
    }

    @Override
    public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBoolean");
    }

    @Override
    public void updateBoolean(final String columnName, final boolean x) throws SQLException {
        updateBoolean(0, x);
    }

    @Override
    public void updateByte(final int columnIndex, final byte x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateByte");
    }

    @Override
    public void updateByte(final String columnName, final byte x) throws SQLException {
        updateByte(0, x);
    }

    @Override
    public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateBytes");
    }

    @Override
    public void updateBytes(final String columnName, final byte[] x) throws SQLException {
        updateBytes(0, x);
    }

    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(final String columnName, final Reader x)
            throws SQLException {
        updateCharacterStream(0, x);
    }

    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final int length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(final String columnName, final Reader reader,
            final int length) throws SQLException {
        updateCharacterStream(0, reader, length);
    }

    @Override
    public void updateCharacterStream(final int columnIndex, final Reader x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateCharacterStream");
    }

    @Override
    public void updateCharacterStream(final String columnName, final Reader x, final long length)
            throws SQLException {
        updateCharacterStream(0, x, length);
    }

    @Override
    public void updateClob(final int columnIndex, final Clob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(final String columnName, final Clob x) throws SQLException {
        updateClob(0, x);
    }

    @Override
    public void updateClob(final int columnIndex, final Reader x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(final String columnName, final Reader x) throws SQLException {
        updateClob(0, x);
    }

    @Override
    public void updateClob(final int columnIndex, final Reader x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateClob");
    }

    @Override
    public void updateClob(final String columnName, final Reader x, final long length)
            throws SQLException {
        updateClob(0, x, length);
    }

    @Override
    public void updateDate(final int columnIndex, final Date x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDate");
    }

    @Override
    public void updateDate(final String columnName, final Date x) throws SQLException {
        updateDate(0, x);
    }

    @Override
    public void updateDouble(final int columnIndex, final double x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateDouble");
    }

    @Override
    public void updateDouble(final String columnName, final double x) throws SQLException {
        updateDouble(0, x);
    }

    @Override
    public void updateFloat(final int columnIndex, final float x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateFloat");
    }

    @Override
    public void updateFloat(final String columnName, final float x) throws SQLException {
        updateFloat(0, x);
    }

    @Override
    public void updateInt(final int columnIndex, final int x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateInt");
    }

    @Override
    public void updateInt(final String columnName, final int x) throws SQLException {
        updateInt(0, x);
    }

    @Override
    public void updateLong(final int columnIndex, final long x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateLong");
    }

    @Override
    public void updateLong(final String columnName, final long x) throws SQLException {
        updateLong(0, x);
    }

    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream");
    }

    @Override
    public void updateNCharacterStream(final String columnName, final Reader x)
            throws SQLException {
        updateNCharacterStream(0, x);
    }

    @Override
    public void updateNCharacterStream(final int columnIndex, final Reader x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNCharacterStream");
    }

    @Override
    public void updateNCharacterStream(final String columnName, final Reader x, final long length)
            throws SQLException {
        updateNCharacterStream(0, x, length);
    }

    @Override
    public void updateNClob(final int columnIndex, final NClob x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(final String columnName, final NClob x) throws SQLException {
        updateNClob(0, x);
    }

    @Override
    public void updateNClob(final int columnIndex, final Reader x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(final String columnName, final Reader x) throws SQLException {
        updateNClob(0, x);
    }

    @Override
    public void updateNClob(final int columnIndex, final Reader x, final long length)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNClob");
    }

    @Override
    public void updateNClob(final String columnName, final Reader x, final long length)
            throws SQLException {
        updateNClob(0, x, length);
    }

    @Override
    public void updateNString(final int columnIndex, final String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNString");
    }

    @Override
    public void updateNString(final String columnName, final String x) throws SQLException {
        updateNString(0, x);
    }

    @Override
    public void updateNull(final int columnIndex) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateNull");
    }

    @Override
    public void updateNull(final String columnName) throws SQLException {
        updateNull(0);
    }

    @Override
    public void updateObject(final int columnIndex, final Object x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject");
    }

    @Override
    public void updateObject(final String columnName, final Object x) throws SQLException {
        updateObject(0, x);
    }

    @Override
    public void updateObject(final int columnIndex, final Object x, final int scaleOrLength)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateObject");
    }

    @Override
    public void updateObject(final String columnName, final Object x, final int scaleOrLength)
            throws SQLException {
        updateObject(0, x, scaleOrLength);
    }

    @Override
    public void updateRef(final int columnIndex, final Ref x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRef");
    }

    @Override
    public void updateRef(final String columnName, final Ref x) throws SQLException {
        updateRef(0, x);
    }

    @Override
    public void updateRow() throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRow");
    }

    @Override
    public void updateRowId(final int columnIndex, final RowId x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateRowId");
    }

    @Override
    public void updateRowId(final String columnName, final RowId x) throws SQLException {
        updateRowId(0, x);
    }

    @Override
    public void updateSQLXML(final int columnIndex, final SQLXML x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateSQLXML");
    }

    @Override
    public void updateSQLXML(final String columnName, final SQLXML x) throws SQLException {
        updateSQLXML(0, x);
    }

    @Override
    public void updateShort(final int columnIndex, final short x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateShort");
    }

    @Override
    public void updateShort(final String columnName, final short x) throws SQLException {
        updateShort(0, x);
    }

    @Override
    public void updateString(final int columnIndex, final String x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateString");
    }

    @Override
    public void updateString(final String columnName, final String x) throws SQLException {
        updateString(0, x);
    }

    @Override
    public void updateTime(final int columnIndex, final Time x) throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTime");
    }

    @Override
    public void updateTime(final String columnName, final Time x) throws SQLException {
        updateTime(0, x);
    }

    @Override
    public void updateTimestamp(final int columnIndex, final Timestamp x)
            throws SQLException {
        throw new SQLFeatureNotSupportedException("updateTimestamp");
    }

    @Override
    public void updateTimestamp(final String columnName, final Timestamp x)
            throws SQLException {
        updateTimestamp(0, x);
    }

    @Override
    public boolean wasNull() throws SQLException {
        checkIfClosed();
        return result[position] == null;
    }

    @Override
    public boolean isWrapperFor(final Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("isWrapperFor");
    }

    @Override
    public <T> T unwrap(final Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException("unwrap");
    }

    @Override
    public <T> T getObject(final int columnIndex, final Class<T> type) throws SQLException {
        return null;
    }

    @Override
    public <T> T getObject(final String columnLabel, final Class<T> type) throws SQLException {
        return null;
    }

    private void checkIfClosed() throws SQLException {
        if (isClosed()) {
            throw new SQLException();
        }
    }

}
