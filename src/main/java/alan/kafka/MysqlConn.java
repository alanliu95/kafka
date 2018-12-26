package alan.kafka;

import java.sql.Connection;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class MysqlConn {
	public static void main(String args[]) {
//		MysqlConn mysql = new MysqlConn();
//		 mysql.insertRecord();
//		mysql.selectRecord();
//		mysql.disconn();
	}
	private String tableId;
	private Connection con;

	public MysqlConn(String tableId) {
		this.tableId=tableId;  //需要判断是否为空
		
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
			System.out.println("can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("connection succeed");
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
		String sql="insert into "+this.tableId+"(ts,cpu,mem)"; 
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
			e.printStackTrace();
		}
	}
}