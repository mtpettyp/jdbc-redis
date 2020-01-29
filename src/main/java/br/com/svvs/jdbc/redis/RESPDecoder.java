package br.com.svvs.jdbc.redis;

import br.com.svvs.jdbc.redis.response.RedisInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RESPDecoder {

    private static final byte RESP_SIMPLE_STRING = '+';
    private static final byte RESP_ERROR = '-';
    private static final byte RESP_INTEGER = ':';
    private static final byte RESP_BULK_STRING = '$';
    private static final byte RESP_ARRAY = '*';

    public static Object decode(final RedisInputStream is) throws RedisResultException, IOException {
        final byte type = is.readByte(); 
        switch(type) {
            case RESP_SIMPLE_STRING:
            case RESP_INTEGER:
                return parseSimpleString(is);
            case RESP_BULK_STRING:
                return parseBulkString(is);
            case RESP_ERROR:
                return parseError(is);
            case RESP_ARRAY:
                return parseArray(is);
            default:
                return null;
        }
    }

    private static String parseSimpleString(final RedisInputStream is) throws IOException {
        return is.readLine();
    }

    private static String parseBulkString(final RedisInputStream is) throws IOException {

        final int length = is.readIntCrLf();
        if (length == -1) {
            return null;
        }
        
        final byte[] read = new byte[length];
        int offset = 0;
        while (offset < length) {
            final int size = is.read(read, offset, (length - offset));
            if (size == -1) throw new IOException("It seems like server has closed the connection.");
            offset += size;
        }

        // read 2 more bytes for the command delimiter
        is.readByte();
        is.readByte();

        return new String(read);
    }

    private static Object parseArray(final RedisInputStream is) throws RedisResultException, IOException {
        final int num = is.readIntCrLf();
        if (num == -1) {
            return null;
        }
        final List<Object> result = new ArrayList<Object>(num);
        for (int i = 0; i < num; i++) {
            result.add(decode(is));
        }
        return result.toArray();
    }


    private static String parseError(final RedisInputStream is) throws RedisResultException, IOException {
        String message = is.readLine();
        throw new RedisResultException(message);
    }

}
