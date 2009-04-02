package br.com.svvs.jdbc.redis;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

public class RedisDriver implements Driver {
	
	// those are public so user can peek, but all are final.
	public static final String JDBC_URL = "jdbc:redis:";
	
	public static final String DEFAULT_HOST = "localhost";
	public static final int    DEFAULT_PORT = 6379;
	public static final int    DEFAULT_DBNB = 0;
	
	public static final int MAJOR_VERSION = 0;
	public static final int MINOR_VERSION = 1;
	
	static {
		try {
			DriverManager.registerDriver(new RedisDriver());
		} catch (SQLException e) {
			throw new RuntimeException("Can't register redis JDBC driver!");
		}
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return url.toLowerCase().startsWith(JDBC_URL);
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		if(!this.acceptsURL(url)) {
			throw new SQLException("Invalid URL: " + url);
		} else {
			// remove prefix so we can use URI parsing.
			String raw_url = url.replaceFirst("jdbc:","");
			try {
				
				URI uri = new URI(raw_url);
				
				String host = uri.getHost();
				int    port = uri.getPort();
				String path = uri.getPath();
				
				// solve defaults...
				if(host != null && port != -1 && path != null) {
					int dbnb = Integer.parseInt(path.substring(1));// FIXME: better handle path parsing.
					return RedisConnectionFactory.getConnection(host, port, dbnb, info);
				} else if (host != null && port != -1) {
					return RedisConnectionFactory.getConnection(host, port, DEFAULT_DBNB, info);
				} else if (host != null) {
					return RedisConnectionFactory.getConnection(host, DEFAULT_PORT, DEFAULT_DBNB, info);
				} else {
					return RedisConnectionFactory.getConnection(DEFAULT_HOST, DEFAULT_PORT, DEFAULT_DBNB, info);
				}
					
			} catch (URISyntaxException e) {
				throw new SQLException("Could not parse JDBC URL: " + url);
			}
		}
	}

	@Override
	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	@Override
	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		// still not compliant
		return false;
	}

}
