[![Build Status](https://travis-ci.org/mtpettyp/jdbc-redis.svg?branch=master)](https://travis-ci.org/mtpettyp/jdbc-redis)
[![Coverage Status](https://coveralls.io/repos/github/mtpettyp/jdbc-redis/badge.svg?branch=master)](https://coveralls.io/github/mtpettyp/jdbc-redis?branch=master)

jdbc-redis is an effort to implement the JDBC API for Redis.

## Note

Redis isn't a relational database, so jdbc-redis doesn't implement all JDBC API functionality. If a function is not available a SQLFeatureNotSupportedException will be thrown.

## Examples

### Getting a connection:

```java
Connection connection = DriverManager.getConnection("jdbc:redis://localhost");
```
The format of the JDBC URL is as follows:

```
jdbc:redis://<host>:<port>/<db_number>
```


### Setting and Getting a value:

```java
Statement statement = conn.createStatement();

statement.execute("SET my_first_key my_first_value");
statement.execute("GET my_first_key");

ResultSet r = statement.getResultSet();
while(r.next()) {
    System.out.println(">" + r.getString(0) +  "<");
}

conn.commit();
conn.close();
```

### Using a PreparedStatement:

```java
PreparedStatement statement = conn.prepareStatement("set ? ?");

statement.setString(1, "my_key");
statement.setInt(2, 1771);
statement.execute();

ResultSet r = statement.executeQuery("GET my_key");

while(r.next()) {
    System.out.println(">" + r.getString(0) +  "<");
}

r.close();
conn.close();
```

## Compatibility

jdbc-redis is still in beta, and is current being tested against Redis v3.0.

The following is the list of currently supported statements:

| Command | Implemented |
| ------- | :-----------: |
| APPEND | :white_check_mark: |
| AUTH | :white_check_mark: |
| BGREWRITEAOF | :white_check_mark: |
| BGSAVE | :white_check_mark: |
| BITCOUNT | :white_check_mark: |
| BITFIELD | :white_check_mark: |
| BITOP | :white_check_mark: |
| BITPOS | :white_check_mark: |
| BLPOP | :white_check_mark: |
| BRPOP | :white_check_mark: |
| BRPOPLPUSH | :white_check_mark: |
| CLIENT GETNAME | :x: |
| CLIENT KILL | :x: |
| CLIENT LIST | :x: |
| CLIENT PAUSE | :x: |
| CLIENT REPLY | :x: |
| CLIENT SETNAME | :x: |
| CLUSTER ADDSLOTS | :x: |
| CLUSTER COUNT-FAILURE-REPORTS | :x: |
| CLUSTER COUNTKEYSINSLOT | :x: |
| CLUSTER DELSLOTS | :x: |
| CLUSTER FAILOVER | :x: |
| CLUSTER FORGET | :x: |
| CLUSTER GETKEYSINSLOT | :x: |
| CLUSTER INFO | :x: |
| CLUSTER KEYSLOT | :x: |
| CLUSTER MEET | :x: |
| CLUSTER NODES | :x: |
| CLUSTER REPLICATE | :x: |
| CLUSTER RESET | :x: |
| CLUSTER SAVECONFIG | :x: |
| CLUSTER SET-CONFIG-EPOCH | :x: |
| CLUSTER SETSLOT | :x: |
| CLUSTER SLAVES | :x: |
| CLUSTER SLOTS | :x: |
| COMMAND | :x: |
| COMMAND COUNT | :x: |
| COMMAND GETKEYS | :x: |
| COMMAND INFO | :x: |
| CONFIG GET | :x: |
| CONFIG RESETSTAT | :x: |
| CONFIG REWRITE | :x: |
| CONFIG SET | :x: |
| DBSIZE | :white_check_mark: |
| DEBUG OBJECT | :x: |
| DEBUG SEGFAULT | :x: |
| DECR | :white_check_mark: |
| DECRBY | :white_check_mark: |
| DEL | :white_check_mark: |
| DISCARD | :x: |
| DUMP | :x: |
| ECHO | :x: |
| EVAL | :x: |
| EVALSHA | :x: |
| EXEC | :x: |
| EXISTS | :white_check_mark: |
| EXPIRE | :white_check_mark: |
| EXPIREAT | :white_check_mark: |
| FLUSHALL | :white_check_mark: |
| FLUSHDB | :white_check_mark: |
| GEOADD | :x: |
| GEODIST | :x: |
| GEOHASH | :x: |
| GEOPOS | :x: |
| GEORADIUS | :x: |
| GEORADIUSBYMEMBER | :x: |
| GET | :white_check_mark: |
| GETBIT | :white_check_mark: |
| GETRANGE | :x: |
| GETSET | :white_check_mark: |
| HDEL | :white_check_mark: |
| HEXISTS | :white_check_mark: |
| HGET | :white_check_mark: |
| HGETALL | :white_check_mark: |
| HINCRBY | :white_check_mark: |
| HINCRBYFLOAT | :white_check_mark: |
| HKEYS | :white_check_mark: |
| HLEN | :white_check_mark: |
| HMGET | :white_check_mark: |
| HMSET | :white_check_mark: |
| HSCAN | :white_check_mark: |
| HSET | :white_check_mark: |
| HSETNX | :white_check_mark: |
| HSTRLEN | :white_check_mark: |
| HVALS | :white_check_mark: |
| INCR | :white_check_mark: |
| INCRBY | :white_check_mark: |
| INCRBYFLOAT | :x: |
| INFO | :white_check_mark: |
| KEYS | :white_check_mark: |
| LASTSAVE | :white_check_mark: |
| LINDEX | :white_check_mark: |
| LINSERT | :white_check_mark: |
| LLEN | :white_check_mark: |
| LPOP | :white_check_mark: |
| LPUSH | :white_check_mark: |
| LPUSHX | :white_check_mark: |
| LRANGE | :white_check_mark: |
| LREM | :white_check_mark: |
| LSET | :white_check_mark: |
| LTRIM | :white_check_mark: |
| MGET | :white_check_mark: |
| MIGRATE | :x: |
| MONITOR | :x: |
| MOVE | :white_check_mark: |
| MSET | :white_check_mark: |
| MSETNX | :x: |
| MULTI | :x: |
| OBJECT | :x: |
| PERSIST | :x: |
| PEXPIRE | :x: |
| PEXPIREAT | :white_check_mark: |
| PFADD | :x: |
| PFCOUNT | :x: |
| PFMERGE | :x: |
| PING | :x: |
| PSETEX | :x: |
| PSUBSCRIBE | :x: |
| PTTL | :white_check_mark: |
| PUBLISH | :x: |
| PUBSUB | :x: |
| PUNSUBSCRIBE | :x: |
| QUIT | :white_check_mark: |
| RANDOMKEY | :white_check_mark: |
| READONLY | :x: |
| READWRITE | :x: |
| RENAME | :white_check_mark: |
| RENAMENX | :white_check_mark: |
| RESTORE | :x: |
| ROLE | :x: |
| RPOP | :white_check_mark: |
| RPOPLPUSH | :white_check_mark: |
| RPUSH | :white_check_mark: |
| RPUSHX | :white_check_mark: |
| SADD | :white_check_mark: |
| SAVE | :white_check_mark: |
| SCAN | :white_check_mark: |
| SCARD | :white_check_mark: |
| SCRIPT DEBUG | :x: |
| SCRIPT EXISTS | :x: |
| SCRIPT FLUSH | :x: |
| SCRIPT KILL | :x: |
| SCRIPT LOAD | :x: |
| SDIFF | :white_check_mark: |
| SDIFFSTORE | :white_check_mark: |
| SELECT | :white_check_mark: |
| SET | :white_check_mark: |
| SETBIT | :white_check_mark: |
| SETEX | :x: |
| SETNX | :white_check_mark: |
| SETRANGE | :x: |
| SHUTDOWN | :white_check_mark: |
| SINTER | :white_check_mark: |
| SINTERSTORE | :white_check_mark: |
| SISMEMBER | :white_check_mark: |
| SLAVEOF | :x: |
| SLOWLOG | :x: |
| SMEMBERS | :white_check_mark: |
| SMOVE | :white_check_mark: |
| SORT | :white_check_mark: |
| SPOP | :white_check_mark: |
| SRANDMEMBER | :white_check_mark: |
| SREM | :white_check_mark: |
| SSCAN | :white_check_mark: |
| STRLEN | :white_check_mark: |
| SUBSCRIBE | :x: |
| SUNION | :white_check_mark: |
| SUNIONSTORE | :white_check_mark: |
| SYNC | :x: |
| TIME | :white_check_mark: |
| TTL | :white_check_mark: |
| TYPE | :white_check_mark: |
| UNSUBSCRIBE | :x: |
| UNWATCH | :white_check_mark: |
| WAIT | :x: |
| WATCH | :white_check_mark: |
| ZADD | :white_check_mark: |
| ZCARD | :white_check_mark: |
| ZCOUNT | :white_check_mark: |
| ZINCRBY | :white_check_mark: |
| ZINTERSTORE | :white_check_mark: |
| ZLEXCOUNT | :white_check_mark: |
| ZRANGE | :white_check_mark: |
| ZRANGEBYLEX | :white_check_mark: |
| ZRANGEBYSCORE | :white_check_mark: |
| ZRANK | :white_check_mark: |
| ZREM | :white_check_mark: |
| ZREMRANGEBYLEX | :white_check_mark: |
| ZREMRANGEBYRANK | :white_check_mark: |
| ZREMRANGEBYSCORE | :white_check_mark: |
| ZREVRANGE | :white_check_mark: |
| ZREVRANGEBYLEX | :white_check_mark: |
| ZREVRANGEBYSCORE | :white_check_mark: |
| ZREVRANK | :white_check_mark: |
| ZSCAN | :white_check_mark: |
| ZSCORE | :white_check_mark: |
| ZUNIONSTORE | :white_check_mark: |



## BUILDING

To build jdbc-redis run:

```
mvn install
```

To run the tests you should have a running Redis instance on port 6379.

NOTE: These tests should not mess with previous saved data, but it's *highly recommended* that your Redis instance is a a empty one.

In order to run the jdbc-redis tests against a dockerized version of redis, run

```
mvn test -Pdocker
```


## COPYRIGHT/LICENSE

Copyright (c) 2009, Marco Valtas All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer. Redistributions in binary
form must reproduce the above copyright notice, this list of conditions and
the following disclaimer in the documentation and/or other materials
provided with the distribution. Neither the name of the JDBC-Redis nor
the names of its contributors may be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
DAMAGE.
