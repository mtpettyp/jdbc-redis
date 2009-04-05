

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RedisStatementSample {

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

		conn.commit();
		conn.close();
		
	}
}
