package alan.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class MysqlConn {
	private static final Logger logger = LogManager.getLogger(MysqlConn.class);
	public static void main(String args[]) {
//		MysqlConn mysql = new MysqlConn();
//		mysql.insertRecord();
//		mysql.selectRecord();
//		mysql.disconn();
	}
	private Connection con;

	public MysqlConn(Properties props) {
		String driver = props.getProperty("driver"); // 驱动程序名
		String url = props.getProperty("url"); // URL指向要访问的数据库名
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		logger.info(""+props);
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 1.getConnection()方法，连接MySQL数据库！！
			this.con = DriverManager.getConnection(url, user, password);
//			if (!con.isClosed())
//				System.out.println("Succeeded connecting to the Database!");
		} catch (ClassNotFoundException e) {
			// 数据库驱动类异常处理
			logger.error("can`t find the Driver!");
			e.printStackTrace();
			System.exit(-1);
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			logger.error("establishing connection failed");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void disconn() {
		try {
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void insertRecord(SysStatus record) {
		PreparedStatement psql;
		// 预处理添加数据，其中有两个参数--“？”
		String sql="insert into "+"records(dev_id, ts, cpu, mem) ";
		try {
			psql = con.prepareStatement(sql+ "values(?,?,?,?)");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = simpleDateFormat.parse(record.getTs());
			psql.setInt(1,1 );//需从devices中查询获取
			psql.setTimestamp(2,new Timestamp(date.getTime()) );
			psql.setFloat(3, record.getCpu());
			psql.setFloat(4, record.getMem());
			psql.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("insert operation failed");
			e.printStackTrace();
			System.exit(-1);
		}
		logger.debug("insert finish");
	}
}