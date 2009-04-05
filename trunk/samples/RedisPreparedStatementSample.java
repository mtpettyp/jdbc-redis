import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RedisPreparedStatementSample {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName("br.com.svvs.jdbc.redis.RedisDriver");
		
		Connection conn = DriverManager.getConnection("jdbc:redis://localhost");
		
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

	}

}
