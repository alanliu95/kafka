package alan.kafka;

import java.util.*;

import org.codehaus.jackson.map.ObjectMapper;

import java.text.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxStatus implements SysStatus {
	private String ts;
	private float cpuUsage;
	private float memUsage;

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
	public LinuxStatus() {
		this.ts = "test";
		this.cpuUsage = 100;
		this.memUsage = 100;
	}
	public static void  main(String Args[]){
		//SysStatus.getCpuUsage();
		//SysStatus.getMemUsage();
		String jsonStr=" ";
		LinuxStatus s=new LinuxStatus();
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
		float idleUsage = 0;
		Runtime rt = Runtime.getRuntime();	
		String[] cpuCmd = { "/bin/sh", "-c","top -b -n 1 | sed -n '3p' | awk '{print $8}'" };
		String[] memCmd = { "/bin/sh", "-c","top -b -n 1 | sed -n '4p' | awk '{print $4,$8}'" };
		BufferedReader in = null;
		String str = "";
		try{
			Process p = rt.exec(cpuCmd);
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			str = in.readLine();
			}catch(Exception e){
				e.printStackTrace();
		}
		idleUsage = Float.parseFloat(str);
//		System.out.println(idleUsage);
		cpuUsage = 100 - idleUsage;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		ts = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//		System.out.println(date);
//		System.out.println("CpuUsage:"+cpuUsage);
		
		long memUsed = 0;
		long memTotal = 0;
	
		try{
			Process p = rt.exec(memCmd);
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			str = in.readLine();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String[] mems = str.split(" ");
		memTotal = Long.parseLong(mems[0]);
		memUsed = Long.parseLong(mems[1]);
		memUsage = (float) memUsed / memTotal * 100;
		//System.out.println("MemUsage:"+memUsage);		
	}
	public static float readCpuUsage() {
		float cpuUsage = 0;
		float idleUsage = 0;
		Runtime rt = Runtime.getRuntime();
		String[] cmd = { "/bin/sh", "-c","top -b -n 1 | sed -n '3p' | awk '{print $8}'" };
		BufferedReader in = null;
		String str = "";
		try{
		Process p = rt.exec(cmd);
		in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		str = in.readLine();
		}catch(Exception e){
			
		}
//		str = str.substring(0,3);
		idleUsage = Float.parseFloat(str);
		System.out.println(idleUsage);
		cpuUsage = 100 - idleUsage;
//		cpuUsage = FormatFloat.formatFloat(cpuUsage);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		System.out.println(date);
//		System.out.println("CpuUsage:");
		System.out.println("CpuUsage:"+cpuUsage);
		return cpuUsage;
	}
	
	public static void readCPUMEMByPID(){
		Runtime rt = Runtime.getRuntime();
		String[] cmd = { "/bin/sh", "-c","top -b -n 1 | sed -n '3p' | awk '{print $0}'"};
		BufferedReader in = null;
		String str = "";
		try{
			Process p = rt.exec(cmd);
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			str = in.readLine();
		}catch(Exception e){
			
		}
	}
	
	public static float readMemUsage() {
		long memUsed = 0;
		long memTotal = 0;
		float memUsage = 0;
		Runtime rt = Runtime.getRuntime();
		String[] cmd = { "/bin/sh", "-c","top -b -n 1 | sed -n '4p' | awk '{print $4,$8}'" };
		BufferedReader in = null;
		String str = "";
		try{
			Process p = rt.exec(cmd);
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			str = in.readLine();
		}catch(Exception e){
			
		}
		
		String[] mems = str.split(" ");
	//	mems[0] = mems[0].substring(0,mems[0].length()-2);
		memTotal = Long.parseLong(mems[0]);
	//	mems[1] = mems[1].substring(0,mems[1].length()-2);
		memUsed = Long.parseLong(mems[1]);
		memUsage = (float) memUsed / memTotal * 100;
//		memUsage = FormatFloat.formatFloat(memUsage);
		System.out.println("MemUsage:"+memUsage);
		return memUsage;
	}

}