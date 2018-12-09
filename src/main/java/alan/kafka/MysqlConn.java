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
		MysqlConn mysql = new MysqlConn();
		// mysql.insertRecord();
		mysql.selectRecord();
		mysql.disconn();
	}

	private Connection con;

	public MysqlConn() {
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名
		String url = "jdbc:mysql://alannnn.tpddns.cn:3306/cloud";
		String user = "root";
		String password = "alan";
		
		try {
			// 加载驱动程序
			Class.forName(driver);
			// 1.getConnection()方法，连接MySQL数据库！！
			con = DriverManager.getConnection(url, user, password);
			if (!con.isClosed())
				System.out.println("Succeeded connecting to the Database!");
		} catch (ClassNotFoundException e) {
			// 数据库驱动类异常处理
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			// 数据库连接失败异常处理
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			System.out.println("数据库数据成功获取！！");
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

	public void selectRecord() {
		try {
			// 2.创建statement类对象，用来执行SQL语句！！
			Statement statement = con.createStatement();
			// 要执行的SQL语句
			String sql = "select * from emp";
			// 3.ResultSet类，用来存放获取的结果集！！
			ResultSet rs = statement.executeQuery(sql);
			System.out.println("-----------------");
			System.out.println("执行结果如下所示:");
			System.out.println("-----------------");
			System.out.println("姓名" + "\t" + "职称");
			System.out.println("-----------------");

			String job = null;
			String id = null;
			while (rs.next()) {
				// 获取stuname这列数据
				job = rs.getString("job");
				// 获取stuid这列数据
				id = rs.getString("ename");

				// 输出结果
				System.out.println(id + "\t" + job);
			}
			rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void insertRecord(LinuxStatus record) {
		PreparedStatement psql;
		// 预处理添加数据，其中有两个参数--“？”
		try {
			psql = con.prepareStatement("insert into sysStatus (ts,cpu,mem) " + "values(?,?,?)");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = simpleDateFormat.parse(record.getts());
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