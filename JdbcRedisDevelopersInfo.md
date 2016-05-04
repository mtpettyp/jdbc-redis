# Introduction #

If you feel like contributing to this project here you will find some information how to.

# Requirements #

JDBC-Redis is developed implementing the interfaces of [JDBC API 4.0](http://java.sun.com/javase/6/docs/technotes/guides/jdbc/) which comes with [Java 1.6](http://java.sun.com/javase/6/). To build and test you will need standard development tools for Java, like [Ant](http://ant.apache.org) and [Junit 4](http://www.junit.org).


# Considerations #

JDBC is a API focused on **relational** databases, but [Redis](http://code.google.com/p/redis) is a key/value database for this reason JDBC-Redis will not implements all functions of JDBC. Some operations can be mapped to similar contexts, like `executeUpdate(String sql)` of `java.sql.Statement` to use the `SETNX` command of Redis which returns a integer (in fact `SETNX` is not like a `UPDATE` on SQL, but this is another discussion).

Another thing to keep in mind is that Redis is **fast**, there's no doubt that a Java interface will be more slow than a C interface. So take in consideration Redis speed when coding. This is not a advise to [Premature Optimization](http://c2.com/cgi/wiki?PrematureOptimization) but keep in mind that speed matters when talking about Redis.

The current specification of Redis have three groups of commands; **bulk commands**, **bulk with parameters commands** and **single line commands**. There's no evidently way to distinct a group from another just looking at it's syntax so we have to declare all commands inside the JDBC-Redis mapping each one with it's group. This command group association is represented with `RedisProtocol` class.


# Technical Details #

Here's a class diagram of JDBC-Redis:

<table><tr><td><a href='http://picasaweb.google.com/lh/photo/ZV2rxO29ctQ3fgOUuYNGZQ?feat=embedwebsite'><img src='http://lh4.ggpht.com/_UFTS2NrETHg/SdLjELJxV-I/AAAAAAAAAMU/h1PuTJRiAlk/s144/JDBC-Redis_Class_Diagram.jpg' /></a></td></tr><tr><td>From <a href='http://picasaweb.google.com/mavcunha/JDBCRedis?feat=embedwebsite'>JDBC-Redis</a></td></tr></table>

JDBC-Redis uses a Java enumeration to define all Redis commands, for each element in this enumeration we implement the `RedisMessageHandler` interface that has two methods; `createMsg(String msg)` and `parseMsg(String msg)`. A SQL like string, for example `set key value`, should be convert to a Redis formatted message, which in this example is `set key 5\r\nvalue\r\n`. A response from Redis will be sent to `parseMsg(String msg)` and converted in String array, this array will be the basis for `RedisResultSet`. `RedisResultSet` takes a String array and convert it's elements as the client calls to get each line returned.

For a simple view how a message is handled here is a sequence diagram:

<table><tr><td><a href='http://picasaweb.google.com/lh/photo/vt9xQ3WvfNSlLdVu3JKOKQ?feat=embedwebsite'><img src='http://lh4.ggpht.com/_UFTS2NrETHg/SdLjE3m-KXI/AAAAAAAAAMY/E2Blz_HL9rI/s144/JDBC-Redis_Message_Parsing.jpg' /></a></td></tr><tr><td>From <a href='http://picasaweb.google.com/mavcunha/JDBCRedis?feat=embedwebsite'>JDBC-Redis</a></td></tr></table>

