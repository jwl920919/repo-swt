package Common.Helper;

import java.sql.*;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBHelper {
	private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);
	private static final String driverName = "org.postgresql.Driver";
	private static final String jdbcUrl = "jdbc:postgresql://192.168.1.81:5432/";
	private static final String dbName = "ipm_db";
	private static final String dbId = "postgres";
	private static final String dbPwd = "shinwoo123!";

	private Connection conn = null;
	private Statement stat = null;
	private PreparedStatement pstat = null;
	private ResultSet rs = null;
	static {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
	}
	// static {
	// try {
	// Class.forName("org.postgresql.Driver");
	// Connection conn = getConnection();
	// Statement stat = conn.createStatement();
	// // ResultSet rs = stat.executeQuery("select count(*) AS total from
	// // user;");
	// // int total = rs.getInt("total");
	// // if (total <= 0) {
	// // stat.executeUpdate("insert into user values
	// // ('admin','smartipt','admin','','',null)");
	// // }
	// //
	// // close(rs);
	// // close(stat);
	// // commit(conn);
	// // close(conn);
	//
	// } catch (ClassNotFoundException cnfe) {
	// logger.error(cnfe.getMessage());
	// } catch (SQLException sqle) {
	// logger.error(sqle.getMessage());
	// }
	// }

	/**
	 * <p>
	 * PostgreSQL connection 개체를 리턴한다.
	 * </p>
	 * 
	 * @param
	 * @return Connection
	 **/
	public Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(MessageFormat.format("{0}{1}", jdbcUrl, dbName), dbId, dbPwd);
			con.setAutoCommit(false);

		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		}

		return con;
	}

	/**
	 * <p>
	 * PostgreSQL executeQuery를 호출하고 ResultSet 결과 값을 리턴한다.
	 * </p>
	 * 
	 * @param String
	 *            sql
	 * @return ResultSet
	 **/
	public ResultSet executeQuery(String sql) {
		try {
			conn = DriverManager.getConnection(MessageFormat.format("{0}{1}", jdbcUrl, dbName), dbId, dbPwd);
			conn.setAutoCommit(false);
			stat = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stat.executeQuery(sql);
			// close(rs);
			// close(stat);
			// commit(conn);
			// close(conn);

		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		}

		return rs;
	}
	

	/**
	 * <p>
	 * PostgreSQL executeUpdate를 호출하고 int 결과 값을 리턴한다.
	 * </p>
	 * 
	 * @param String
	 *            sql
	 * @return int
	 **/
	public int executeUpdate(String sql) {
		int req = 0;
		try {
			conn = DriverManager.getConnection(MessageFormat.format("{0}{1}", jdbcUrl, dbName), dbId, dbPwd);
			conn.setAutoCommit(false);
			stat = conn.createStatement();
			req = stat.executeUpdate(sql);
			// close(stat);
			// commit(conn);
			// close(conn);

		} catch (SQLException sqle) {
			if (conn != null)
				rollback(conn);
			logger.error(sqle.getMessage());
		}

		return req;
	}
	

	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		}
	}

	private void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		}
	}

	private void close(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException sqle) {
			logger.error(sqle.getMessage());
		}
	}

	public void commit(Connection con) {
		try {
			con.commit();
			logger.info("commit success");
		} catch (SQLException sqle) {
			logger.error("commit fail : " + sqle.getMessage());
		}
	}

	public void rollback(Connection con) {
		try {
			con.rollback();
			logger.info("rollback success");
		} catch (SQLException sqle) {
			logger.error("rollback fail : " + sqle.getMessage());
		}
	}

}
