# The First SET/GET #

**JDBC-Redis** is a effort to implement the JDBC API to interact with [Redis Database](http://code.google.com/p/redis). Here's the most basic example, first we set a key called **my\_first\_key** to the value **my first value** and then get the value back.


```
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RedisSample {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		
		Class.forName("br.com.svvs.jdbc.redis.RedisDriver");
		
		Connection conn = DriverManager.getConnection("jdbc:redis://localhost");
		
		Statement statement = conn.createStatement();
		
		statement.execute("set my_first_key my first value");
		
		statement.execute("get my_first_key");
		
		ResultSet r = statement.getResultSet();
		while(r.next()) {
			System.out.println(">" + r.getString(0) +  "<");
		}
		
	}
}
```