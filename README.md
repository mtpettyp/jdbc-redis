[![Build Status](https://travis-ci.org/mtpettyp/jdbc-redis.svg?branch=master)](https://travis-ci.org/mtpettyp/jdbc-redis)



jdbc-redis is an effort to implement the JDBC API for Redis. 

## Note 

Redis isn't a relational database, so jdbc-redis doesn't implement all JDBC API functionality. If a function is not available a SQLFeatureNotSupportedException will be thrown. 

## Examples

### Getting a connection:

```java
Class.forName("br.com.svvs.jdbc.redis.RedisDriver");  
Connection conn = DriverManager.getConnection("jdbc:redis://localhost");
```
The format of the JDBC URL is as follows:

```
jdbc:redis://<host>:<port>/<db_number>
```


### Setting and Getting a value:

```java
Statement statement = conn.createStatement();

statement.execute("set my_first_key my first value");
statement.execute("get my_first_key");

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

ResultSet r = statement.executeQuery("get my_key");

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
| APPEND | :x: |
| AUTH | :x: |
| BGREWRITEAOF | :x: |
| BGSAVE | :x: |
| BITCOUNT | :x: |
| BITFIELD | :x: |
| BITOP | :x: |
| BITPOS | :x: |
| BLPOP | :x: |
| BRPOP | :x: |
| BRPOPLPUSH | :x: |
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
| DBSIZE | :x: |
| DEBUG OBJECT | :x: |
| DEBUG SEGFAULT | :x: |
| DECR | :x: |
| DECRBY | :x: |
| DEL | :x: |
| DISCARD | :x: |
| DUMP | :x: |
| ECHO | :x: |
| EVAL | :x: |
| EVALSHA | :x: |
| EXEC | :x: |
| EXISTS | :x: |
| EXPIRE | :x: |
| EXPIREAT | :x: |
| FLUSHALL | :x: |
| FLUSHDB | :x: |
| GEOADD | :x: |
| GEODIST | :x: |
| GEOHASH | :x: |
| GEOPOS | :x: |
| GEORADIUS | :x: |
| GEORADIUSBYMEMBER | :x: |
| GET | :x: |
| GETBIT | :x: |
| GETRANGE | :x: |
| GETSET | :x: |
| HDEL | :x: |
| HEXISTS | :x: |
| HGET | :x: |
| HGETALL | :x: |
| HINCRBY | :x: |
| HINCRBYFLOAT | :x: |
| HKEYS | :x: |
| HLEN | :x: |
| HMGET | :x: |
| HMSET | :x: |
| HSCAN | :x: |
| HSET | :x: |
| HSETNX | :x: |
| HSTRLEN | :x: |
| HVALS | :x: |
| INCR | :x: |
| INCRBY | :x: |
| INCRBYFLOAT | :x: |
| INFO | :x: |
| KEYS | :x: |
| LASTSAVE | :x: |
| LINDEX | :x: |
| LINSERT | :x: |
| LLEN | :x: |
| LPOP | :x: |
| LPUSH | :x: |
| LPUSHX | :x: |
| LRANGE | :x: |
| LREM | :x: |
| LSET | :x: |
| LTRIM | :x: |
| MGET | :x: |
| MIGRATE | :x: |
| MONITOR | :x: |
| MOVE | :x: |
| MSET | :x: |
| MSETNX | :x: |
| MULTI | :x: |
| OBJECT | :x: |
| PERSIST | :x: |
| PEXPIRE | :x: |
| PEXPIREAT | :x: |
| PFADD | :x: |
| PFCOUNT | :x: |
| PFMERGE | :x: |
| PING | :x: |
| PSETEX | :x: |
| PSUBSCRIBE | :x: |
| PTTL | :x: |
| PUBLISH | :x: |
| PUBSUB | :x: |
| PUNSUBSCRIBE | :x: |
| QUIT | :x: |
| RANDOMKEY | :x: |
| READONLY | :x: |
| READWRITE | :x: |
| RENAME | :x: |
| RENAMENX | :x: |
| RESTORE | :x: |
| ROLE | :x: |
| RPOP | :x: |
| RPOPLPUSH | :x: |
| RPUSH | :x: |
| RPUSHX | :x: |
| SADD | :x: |
| SAVE | :x: |
| SCAN | :x: |
| SCARD | :x: |
| SCRIPT DEBUG | :x: |
| SCRIPT EXISTS | :x: |
| SCRIPT FLUSH | :x: |
| SCRIPT KILL | :x: |
| SCRIPT LOAD | :x: |
| SDIFF | :x: |
| SDIFFSTORE | :x: |
| SELECT | :x: |
| SET | :x: |
| SETBIT | :x: |
| SETEX | :x: |
| SETNX | :x: |
| SETRANGE | :x: |
| SHUTDOWN | :x: |
| SINTER | :x: |
| SINTERSTORE | :x: |
| SISMEMBER | :x: |
| SLAVEOF | :x: |
| SLOWLOG | :x: |
| SMEMBERS | :x: |
| SMOVE | :x: |
| SORT | :x: |
| SPOP | :x: |
| SRANDMEMBER | :x: |
| SREM | :x: |
| SSCAN | :x: |
| STRLEN | :x: |
| SUBSCRIBE | :x: |
| SUNION | :x: |
| SUNIONSTORE | :x: |
| SYNC | :x: |
| TIME | :x: |
| TTL | :x: |
| TYPE | :x: |
| UNSUBSCRIBE | :x: |
| UNWATCH | :x: |
| WAIT | :x: |
| WATCH | :x: |
| ZADD | :x: |
| ZCARD | :x: |
| ZCOUNT | :x: |
| ZINCRBY | :x: |
| ZINTERSTORE | :x: |
| ZLEXCOUNT | :x: |
| ZRANGE | :x: |
| ZRANGEBYLEX | :x: |
| ZRANGEBYSCORE | :x: |
| ZRANK | :x: |
| ZREM | :x: |
| ZREMRANGEBYLEX | :x: |
| ZREMRANGEBYRANK | :x: |
| ZREMRANGEBYSCORE | :x: |
| ZREVRANGE | :x: |
| ZREVRANGEBYLEX | :x: |
| ZREVRANGEBYSCORE | :x: |
| ZREVRANK | :x: |
| ZSCAN | :x: |
| ZSCORE | :x: |
| ZUNIONSTORE | :x: |



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
