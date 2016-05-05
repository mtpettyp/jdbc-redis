[![Build Status](https://travis-ci.org/mtpettyp/jdbc-redis.svg?branch=master)](https://travis-ci.org/mtpettyp/jdbc-redis)

JDBC-Redis README file.

JDBC-Redis is a effort to implement the JDBC API for Redis Database
(http://code.google.com/p/redis). This file provide some information
about JDBC-Redis, you can find more information at 
http://code.google.com/p/jdbc-redis.

* IMPORTANT * 

Redis database isn't a relational database, so JDBC-Redis doen't implement
all JDBC API functionality. If a function is not available a 
SQLFeatureNotSupportedException will be thrown. 

VERSION INFORMATION

JDBC-Redis is still in beta, this means it works but was not tested enough 
to be considered a production release. See the BUGS file to know more about
how to report bugs and help on JDBC-Redis development. Visit the project website
 (http://code.google.com/p/jdbc-redis) to know more. For compatibility between
 JDBC-Redis and Redis database look at the VERSION file.

SAMPLES

Inside the samples/ directory you will find some examples how to use JDBC-Redis.

BUILDING

To build JDBC-Redis you need the JDK 1.6 (http://java.sun.com/javase/6/) or superior and
Ant 1.7 (http://ant.apache.org) or superior. For a UNIX based system you can:

$ cd jdbc-redis/
$ ant

The jar file will be inside the target/ directory. To test the file use the online-tests
target of Ant, first start a Redis instance and then invoke Ant. NOTE: These tests
should not mess with previous saved data, but it's *highly recommended* that your
Redis instance is a a empty one.

1. Start a Redis database instance.
2. Call Ant with "online-tests" as target.

$ ant online-tests


COPYRIGHT/LICENSE 

JDBC-Redis is licensed with BSD-License see http://code.google.com/p/jdbc-redis for
more information and the COPYRIGHT file.
