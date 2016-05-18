package br.com.svvs.jdbc.redis;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public final class RESPDecoder {

    private static final char RESP_SIMPLE_STRING = '+';
    private static final char RESP_ERROR = '-';
    private static final char RESP_INTEGER = ':';
    private static final char RESP_BULK_STRING = '$';
    private static final char RESP_ARRAY = '*';

    private RESPDecoder() {
    }

    public static Object decode(final String response) throws RedisResultException {

        if (response == null || response.length() == 0) {
            return null;
        }

        return decode(CharBuffer.wrap(response, 0, response.length()));
    }

    private static Object decode(final CharBuffer buffer) throws RedisResultException {

        char type = buffer.get();

        switch(type) {
        case RESP_SIMPLE_STRING:
        case RESP_INTEGER:
            return parseSimpleString(buffer);
        case RESP_BULK_STRING:
            return parseBulkString(buffer);
        case RESP_ERROR:
            return parseError(buffer);
        case RESP_ARRAY:
            return parseArray(buffer);
        default:
            return null;
        }
    }


    private static String parseSimpleString(final CharBuffer buffer) {
        return readToTerminator(buffer);
    }

    private static String parseBulkString(final CharBuffer buffer) {
        int length = parseLength(buffer);

        if (length >= 0) {

            char[] data = new char[length];
            buffer.get(data);

            buffer.get();
            buffer.get();

            return new String(data);
        } else {
            return null;
        }

    }

    private static Object parseArray(final CharBuffer buffer) throws RedisResultException {
        int length = parseLength(buffer);

        if (length >= 0) {
            List<Object> array = new ArrayList<>();

            for (int i = 0; i < length; i++) {
                array.add(decode(buffer));
            }

            return array.toArray();
        } else {
            return null;
        }
    }


    private static String parseError(final CharBuffer buffer) throws RedisResultException {
        throw new RedisResultException(readToTerminator(buffer));
    }

    private static String readToTerminator(final CharBuffer buffer) {
        char c;
        StringBuilder response = new StringBuilder();
        while ((c = buffer.get()) != '\r') {
            response.append(c);
        }

      //TODO: check that is '\n'
        buffer.get();

        return response.toString();
    }

    private static int parseLength(final CharBuffer buffer) {
        int length;
        try {
            length = Integer.valueOf(readToTerminator(buffer));
        } catch (Exception e) {
            length = -1;
        }

        return length;
    }
}
