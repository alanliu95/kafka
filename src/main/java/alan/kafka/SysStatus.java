package alan.kafka;

import java.util.*;
import java.text.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SysStatus {
	public static void  main(String Args[]){
		//SysStatus.getCpuUsage();
		//SysStatus.getMemUsage();
		SysStatus.getStatus();
	}
	public static String getStatus() {
		float cpuUsage = 0;
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
			
		}
		idleUsage = Float.parseFloat(str);
//		System.out.println(idleUsage);
		cpuUsage = 100 - idleUsage;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		System.out.println(date);
		System.out.println("CpuUsage:"+cpuUsage);
		
		long memUsed = 0;
		long memTotal = 0;
		float memUsage = 0;
		
		try{
			Process p = rt.exec(memCmd);
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			str = in.readLine();
		}catch(Exception e){
			
		}
		
		String[] mems = str.split(" ");
		memTotal = Long.parseLong(mems[0]);
		memUsed = Long.parseLong(mems[1]);
		memUsage = (float) memUsed / memTotal * 100;
		System.out.println("MemUsage:"+memUsage);
		return "hello";
		
	}
	public static float getCpuUsage() {
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
	
	public static void getCPUMEMByPID(){
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
	
	public static float getMemUsage() {
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