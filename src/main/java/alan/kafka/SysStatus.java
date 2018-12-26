package alan.kafka;

//public class SysStatus {
//	private String deviceId;
//	private String ts;
//	private float cpuUsage;
//	private float memUsage;
//	public  void readStatus() {} //有问题
//	
//	public SysStatus(String deviceId, String ts, float cpuUsage, float memUsage) {
//		this.deviceId = deviceId;
//		this.ts = ts;
//		this.cpuUsage = cpuUsage;
//		this.memUsage = memUsage;
//	}
//
//	public String getDeviceId() {
//		return deviceId;
//	}
//
//	public void setDeviceId(String deviceId) {
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
//	public float getCpuUsage() {
//		return cpuUsage;
//	}
//
//	public void setCpuUsage(float cpuUsage) {
//		this.cpuUsage = cpuUsage;
//	}
//
//	public float getMemUsage() {
//		return memUsage;
//	}
//
//	public void setMemUsage(float memUsage) {
//		this.memUsage = memUsage;
//	}
//
////	public SysStatus(String deviceId) {
////		this.deviceId=deviceId;	
////	}
//
//}

public class SysStatus {
	private String deviceId;
	private String ts;
	private float cpuUsage;
	private float memUsage;
	
	public  void readStatus() {} //有问题
	

	public SysStatus() {
		
	}
	
	public SysStatus(String deviceId) {
		this.deviceId=deviceId;
	}
	
	public SysStatus(String deviceId, String ts, float cpuUsage, float memUsage) {
		
		this.deviceId = deviceId;
		this.ts = ts;
		this.cpuUsage = cpuUsage;
		this.memUsage = memUsage;
	}


	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public float getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(float cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public float getMemUsage() {
		return memUsage;
	}

	public void setMemUsage(float memUsage) {
		this.memUsage = memUsage;
	}

	

}
