package alan.kafka;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;

public class SimStatus implements SysStatus {
	private String ts;
	private float cpuUsage;
	private float memUsage;
	private Random ra;
	public void setts(String date) {
		this.ts = date;
	}
	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public void setMemUsage(float memUsage) {
		this.memUsage = memUsage;
	}

	public String getts() {
		return ts;
	}
	public float getCpuUsage() {
		return cpuUsage;
	}
	public float getMemUsage() {
		return memUsage;
	}
	public SimStatus() {
		this.ts = "test";
		this.cpuUsage = 100;
		this.memUsage = 100;
		this.ra =new Random();
	}
	public static void  main(String Args[]){
		//SysStatus.getCpuUsage();
		//SysStatus.getMemUsage();
		String jsonStr=" ";
		SysStatus s=new SimStatus();
		s.readStatus();
		
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		try {
			jsonStr=mapper.writeValueAsString(s);
			System.out.println(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("json format error");
			return;
		}		
	}
	
	public void readStatus(){
		cpuUsage = ra.nextFloat()*100;
		memUsage = ra.nextFloat()*100;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
		ts = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳				
	}


}
