package alan.kafka;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static Logger logger = LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	public static void main(String[] args) throws Exception {
		// logger.info("{}以及{}","张三","李四");
		jsonTest();
	}

	public static void jsonTest() {
		String s = "{\"deviceId\":\"sim1\",\"ts\":\"2018-12-26 15:50:47.324\",\"cpuUsage\":65.93164,\"memUsage\":74.29702}";
		System.out.println(s);
		ObjectMapper mapper = new ObjectMapper();
		try {
			SysStatus json = mapper.readValue(s, SysStatus.class); // 有问题
//			SysStatus json = mapper.readValue(s, SysStatus.class); // 有问题
			System.out.println(json.getDevToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
// public static void main(String Args[]){
// //SysStatus.getCpu();
// //SysStatus.getMem();
// String jsonStr=" ";
// SysStatus s=new SimStatus();
// s.readStatus();
//
// ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
// try {
// jsonStr=mapper.writeValueAsString(s);
// System.out.println(jsonStr);
// } catch (IOException e) {
// e.printStackTrace();
// System.out.println("json format error");
// return;
// }
// }

// props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
// props.put("acks", "all");
// props.put("retries", 1);
// props.put("batch.size", 16384);
// props.put("linger.ms", 1);
// props.put("buffer.memory", 33554432);
// props.put("key.serializer",
// "org.apache.kafka.common.serialization.StringSerializer");
// props.put("value.serializer",
// "org.apache.kafka.common.serialization.StringSerializer");

// public void selectRecord() {
// try {
// // 2.创建statement类对象，用来执行SQL语句！！
// Statement statement = con.createStatement();
// // 要执行的SQL语句
// String sql = "select * from emp";
// // 3.ResultSet类，用来存放获取的结果集！！
// ResultSet rs = statement.executeQuery(sql);
// System.out.println("-----------------");
// System.out.println("执行结果如下所示:");
// System.out.println("-----------------");
// System.out.println("姓名" + "\t" + "职称");
// System.out.println("-----------------");
//
// String job = null;
// String id = null;
// while (rs.next()) {
// // 获取stuname这列数据
// job = rs.getString("job");
// // 获取stuid这列数据
// id = rs.getString("ename");
//
// // 输出结果
// System.out.println(id + "\t" + job);
// }
// rs.close();
// } catch (Exception e) {
// // TODO: handle exception
// e.printStackTrace();
// }
// }


//class SysStatus1 {
//	private String deviceId;
//	private String ts;
//	private float cpuUsage;
//	private float memUsage;
//	
//	public  void readStatus() {} //有问题
//	
//
//	public SysStatus1() {
//		
//	}
//	
//
//	public SysStatus1(String deviceId, String ts, float cpuUsage, float memUsage) {
//		
//		this.deviceId = deviceId;
//		this.ts = ts;
//		this.cpuUsage = cpuUsage;
//		this.memUsage = memUsage;
//	}
//
//
//	public String getDevToken() {
//		return deviceId;
//	}
//
//	public void setDevToken(String deviceId) {
//		this.deviceId = deviceId;
//	}
//
//	public String getTs() {
//		return ts;
//	}
//
//	public void setTs(String ts) {
//		this.ts = ts;
//	}
//
//	public float getCpu() {
//		return cpuUsage;
//	}
//
//	public void setCpu(float cpuUsage) {
//		this.cpuUsage = cpuUsage;
//	}
//
//	public float getMem() {
//		return memUsage;
//	}
//
//	public void setMem(float memUsage) {
//		this.memUsage = memUsage;
//	}
//
//	
//
//}


