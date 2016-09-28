package br.com.svvs.jdbc.redis;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RedisCommandProcessor {

    private RedisCommandProcessor() {
    }

    public static ResultSet runCommand(final RedisConnection connection, final String statement)
            throws SQLException, RedisParseException, RedisResultException {

        RedisCommand command = extractCommand(statement);
        Object response = connection.msgToServer(statement + "\r\n");
        return command.getResponse().processResponse(connection, statement, response);
    }


    private static RedisCommand extractCommand(final String statement) throws RedisParseException {

        String[] keywords = statement.trim().split(" ", 2);

        try {
            return RedisCommand.valueOf(RedisCommand.class, keywords[0].toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new RedisParseException("Command not recognized.");
        }
    }
}
