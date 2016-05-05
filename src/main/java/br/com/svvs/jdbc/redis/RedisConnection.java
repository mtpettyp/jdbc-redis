package br.com.svvs.jdbc.redis;

import java.io.IOException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class RedisConnection implements java.sql.Connection {

    private RedisIO io = null;
    private boolean isClosed = true;

    private final String NOT_SUPPORTED = "This operation is not supported by Redis or this driver. (%s)";

    public RedisConnection(RedisIO io, Properties info) throws SQLException {

        if(io == null)
            throw new RuntimeException("Null RedisIO handler.");
        this.io = io;

        // we got a connection, let's try to authenticate
        if(info != null && info.getProperty("password") != null &&
                info.getProperty("password").length() > 0) {
            try {
                String response = this.io.sendRaw(RedisProtocol.AUTH.createMsg(info.getProperty("password")));
                RedisProtocol.AUTH.parseMsg(response); // will throw RedisResultException if pass is invalid.
            } catch (IOException e) {
                throw new SQLException(e);
            } catch (RedisParseException e) {
                throw new SQLException(e);
            } catch (RedisResultException e) {
                throw new SQLException("Could not authenticate with Redis.");
            }
        }

        this.isClosed = false;
    }

    @Override
    public void clearWarnings() throws SQLException {
        // no op.
    }

    /**
     * Issues a QUIT command to Redis server and closes any socket opened.
     * No operations should be done in the connection object after call
     * this method.
     */
    @Override
    public void close() throws SQLException {
        this.isClosed = true;
        try {
            this.io.sendRaw(RedisProtocol.QUIT.createMsg(null));
            this.io.close();
        } catch (RedisParseException e) {
            throw new SQLException(e);
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    /**
     * Send a SAVE command to Redis server.
     */
    @Override
    public void commit() throws SQLException {
        this.checkConnection();
        try {
            this.io.sendRaw(RedisProtocol.SAVE.createMsg(null));
        } catch (IOException e) {
            throw new SQLException(e);
        } catch (RedisParseException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createArrayOf(Ljava/lang/String;[Ljava/lang/Object;)Ljava/sql/Array;"));
    }

    @Override
    public Blob createBlob() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createBlob()Ljava/sql/Blob;"));
    }

    @Override
    public Clob createClob() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createClob()Ljava/sql/Clob;"));
    }

    @Override
    public NClob createNClob() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createNClob()Ljava/sql/NClob;"));
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createSQLXML()Ljava/sql/SQLXML;"));
    }

    @Override
    public Statement createStatement() throws SQLException {
        return new RedisStatement(this);
    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createStatement(II)Ljava/sql/Statement;"));
    }

    @Override
    public Statement createStatement(int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createStatement(III)Ljava/sql/Statement;"));
    }

    @Override
    public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("createStruct(Ljava/lang/String;[Ljava/lang/Object;)Ljava/sql/Struct;"));
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }

    @Override
    public String getCatalog() throws SQLException {
        this.checkConnection(); // as API spec says throw exception if conn is closed.
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("getClientInfo()Ljava/util/Properties;"));
    }

    @Override
    public String getClientInfo(String arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("getClientInfo(Ljava/lang/String;)Ljava/lang/String;"));
    }

    @Override
    public int getHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("getHoldability()I"));
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("getMetaData()Ljava/sql/DatabaseMetaData;"));
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return Connection.TRANSACTION_NONE;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return this.isClosed;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public boolean isValid(int arg0) throws SQLException {
        return this.isClosed;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("nativeSQL(Ljava/lang/String;)Ljava/lang/String;"));
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareCall(Ljava/lang/String;)Ljava/sql/CallableStatement;"));
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareCall(Ljava/lang/String;II)Ljava/sql/CallableStatement;"));
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareCall(Ljava/lang/String;III)Ljava/sql/CallableStatement;"));
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new RedisPreparedStatement(sql, this);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareStatement(Ljava/lang/String;I)Ljava/sql/PreparedStatement;"));
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareStatement(Ljava/lang/String;[I)Ljava/sql/PreparedStatement;"));
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareStatement(Ljava/lang/String;[Ljava/lang/String;)Ljava/sql/PreparedStatement;"));
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareStatement(Ljava/lang/String;II)Ljava/sql/PreparedStatement;"));
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("prepareStatement(Ljava/lang/String;III)Ljava/sql/PreparedStatement;"));
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("releaseSavepoint(Ljava/sql/Savepoint;)V"));
    }

    @Override
    public void rollback() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("rollback()V"));
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("rollback(Ljava/sql/Savepoint;)V"));
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        if(autoCommit == false)
            throw new SQLFeatureNotSupportedException(unsupportedMethod("setAutoCommit(Z)V"));
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
    }

    @Override
    public void setClientInfo(Properties arg0) throws SQLClientInfoException {
    }

    @Override
    public void setClientInfo(String arg0, String arg1)
            throws SQLClientInfoException {
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("setReadOnly(Z)V"));
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("setSavepoint()Ljava/sql/Savepoint;"));
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("setSavepoint(Ljava/lang/String;)Ljava/sql/Savepoint;"));
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    }

    @Override
    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("unwrap(Ljava/lang/Class;)Ljava/lang/Object;"));
    }

    protected String msgToServer(String redisMsg) throws SQLException {

        this.checkConnection(); // check if we can send the message.

        try {
            return this.io.sendRaw(redisMsg);
        } catch (IOException e) {
            this.isClosed = true;
            throw new SQLException(e.getMessage());
        }
    }

    private void checkConnection() throws SQLException {
        if(this.isClosed())
            throw new SQLException("Connection with Redis is closed");
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("setSchema(Ljava/lang/String;)V"));
    }

    @Override
    public String getSchema() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("getSchema()Ljava/lang/String;"));
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("abort(Ljava/util/concurrent/Executor;)V"));
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("setNetworkTimeout(Ljava/util/concurrent/Executor;I)V"));
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException(unsupportedMethod("getNetworkTimeout()I"));
    }

    private String unsupportedMethod(final String method) {
        return String.format(NOT_SUPPORTED, method);
    }

}
