package alan.kafka;

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

public class MysqlConn {
	private static final Logger logger = LogManager.getLogger(AppProducer.class);
	public static void main(String args[]) {
//		MysqlConn mysql = new MysqlConn();
//		mysql.insertRecord();
//		mysql.selectRecord();
//		mysql.disconn();
	}
	private Connection con;

	public MysqlConn() {
		
		String driver = "com.mysql.jdbc.Driver"; // 驱动程序名		
		String url = "jdbc:mysql://alannnn.tpddns.cn:3306/cloud"; // URL指向要访问的数据库名
		String user = "root";
		String password = "alan";
		//String tableName;
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
		String sql="insert into "+record.getDeviceId()+"(ts,cpu,mem)"; 
		try {
			psql = con.prepareStatement(sql+ "values(?,?,?)");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = simpleDateFormat.parse(record.getTs());
			psql.setTimestamp(1,new Timestamp(date.getTime()) );
			psql.setFloat(2, record.getCpuUsage());
			psql.setFloat(3, record.getMemUsage());
			//System.out.println(x);
			psql.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("insert operation failed");
			e.printStackTrace();
			System.exit(-1);
		}
	}
}