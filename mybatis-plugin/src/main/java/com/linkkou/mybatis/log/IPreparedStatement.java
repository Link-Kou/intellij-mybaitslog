package com.linkkou.mybatis.log;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * A <code>IPreparedStatement</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/4/19 16:21</b></p>
 */
public class IPreparedStatement implements PreparedStatement {

    private String value;


    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        value = "Null";
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        value = Boolean.toString(x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        value = Byte.toString(x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        value = Short.toString(x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        value = Integer.toString(x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        value = Long.toString(x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        value = Float.toString(x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        value = Double.toString(x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        value = x.toString();
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        value = String.format("'%s'", x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        value = "……";
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate localDate = x.toLocalDate();
        value = String.format("'%s'", fmt.format(localDate));
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        final LocalTime localTime = x.toLocalTime();
        value = String.format("'%s'", fmt.format(localTime));
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime localDateTime = x.toLocalDateTime();
        value = String.format("'%s'", fmt.format(localDateTime));
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        value = "……";
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        value = "……";
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        value = "……";
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        value = x.toString();
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        value = x.toString();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        value = "……";
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        value = "……";
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        value = "……";
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        value = "……";
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        value = "……";
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate localDate = x.toLocalDate();
        value = String.format("'%s'", fmt.format(localDate));
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        final LocalTime localTime = x.toLocalTime();
        value = String.format("'%s'", fmt.format(localTime));
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final LocalDateTime localDateTime = x.toLocalDateTime();
        value = String.format("'%s'", fmt.format(localDateTime));
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        value = "null";
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        value = x.toString();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        value = x.toString();
    }

    @Override
    public void setNString(int parameterIndex, String str) throws SQLException {
        value = String.format("'%s'", str);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setNClob(int parameterIndex, NClob nClob) throws SQLException {
        value = "……";
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        value = xmlObject.toString();
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        value = x.toString();
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        value = "……";
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        value = "……";
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        value = "……";
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        value = "……";
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        value = "……";
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        value = "……";
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        value = "……";
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        value = "……";
    }

    //region 其他

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;
    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate() throws SQLException {
        return 0;
    }

    @Override
    public void clearParameters() throws SQLException {

    }

    @Override
    public boolean execute() throws SQLException {
        return false;
    }

    @Override
    public void addBatch() throws SQLException {

    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return null;
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        return 0;
    }

    @Override
    public void close() throws SQLException {

    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {

    }

    @Override
    public int getMaxRows() throws SQLException {
        return 0;
    }

    @Override
    public void setMaxRows(int max) throws SQLException {

    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {

    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return 0;
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {

    }

    @Override
    public void cancel() throws SQLException {

    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public void setCursorName(String name) throws SQLException {

    }

    @Override
    public boolean execute(String sql) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return null;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return 0;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return false;
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {

    }

    @Override
    public int getFetchDirection() throws SQLException {
        return 0;
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {

    }

    @Override
    public int getFetchSize() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return 0;
    }

    @Override
    public int getResultSetType() throws SQLException {
        return 0;
    }

    @Override
    public void addBatch(String sql) throws SQLException {

    }

    @Override
    public void clearBatch() throws SQLException {

    }

    @Override
    public int[] executeBatch() throws SQLException {
        return new int[0];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
    //endregion

    public String getValue() {
        return value;
    }
}
