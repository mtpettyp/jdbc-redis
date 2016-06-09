package br.com.svvs.jdbc.redis;

import java.sql.SQLException;

public class RedisCursorResultSet extends RedisResultSet {

    private final RedisConnection connection;
    private int cursor;
    private  final String command;

    public RedisCursorResultSet(final RedisConnection connection,
            final int cursor, final String command, final String[] results) {
        super(results);
        this.cursor = cursor;
        this.command = command;
        this.connection = connection;
    }

    @Override
    public boolean next() throws SQLException {
        boolean next = super.next();

        if (!next && cursor != 0) {

            try {
                RedisCursorResultSet newResultSet
                    = (RedisCursorResultSet)RedisCommandProcessor.runCommand(connection,
                        command.replaceFirst("\\d+", String.valueOf(cursor)));

                position = 0;
                cursor = newResultSet.getCursor();
                result = newResultSet.getResult();
                newResultSet.close();
                return true;
            } catch (RedisParseException |
                     RedisResultException e) {
                throw new SQLException(e);
            }
        }

        return next;

    }

    public int getCursor() {
        return cursor;
    }

    public String[] getResult() {
        return result;
    }
}
