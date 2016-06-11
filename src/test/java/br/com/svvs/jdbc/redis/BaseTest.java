package br.com.svvs.jdbc.redis;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public abstract class BaseTest {

    public SQLFeatureNotSupportedException assertNotSupported(final ExceptionThrowing block) {
        return assertThrows(SQLFeatureNotSupportedException.class, block);
    }

    public SQLException assertSQLException(final ExceptionThrowing block) {
        return assertThrows(SQLException.class, block);
    }

    protected static <X extends Throwable> X assertThrows(final Class<X> exceptionClass, final ExceptionThrowing block) {
        try {
            block.run();
        } catch (Throwable ex) {
            if (exceptionClass.isInstance(ex))
                return exceptionClass.cast(ex);
        }
        fail("Failed to throw expected exception");
        return null;
    }

    interface ExceptionThrowing {
        void run() throws Exception;
    }
}
