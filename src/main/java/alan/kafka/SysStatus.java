package alan.kafka;

//public class SysStatus {
//	private String devToken;
//	private String ts;
//	private float cpu;
//	private float mem;
//	public  void readStatus() {} //有问题
//	
//	public SysStatus(String devToken, String ts, float cpu, float mem) {
//		this.devToken = devToken;
//		this.ts = ts;
//		this.cpu = cpu;
//		this.mem = mem;
//	}
//
//	public String getDevToken() {
//		return devToken;
//	}
//
//	public void setDevToken(String devToken) {
//		this.devToken = devToken;
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
//		return cpu;
//	}
//
//	public void setCpu(float cpu) {
//		this.cpu = cpu;
//	}
//
//	public float getMem() {
//		return mem;
//	}
//
//	public void setMem(float mem) {
//		this.mem = mem;
//	}
//
////	public SysStatus(String devToken) {
////		this.devToken=devToken;
////	}
//
//}

public class SysStatus {
	private String devToken;
	private String ts;
	private float cpu;
	private float mem;
	
	public  void readStatus() {} //有问题
	

	public SysStatus() {
		
	}
	
	public SysStatus(String devToken) {
		this.devToken = devToken;
	}
	
	public SysStatus(String devToken, String ts, float cpu, float mem) {
		
		this.devToken = devToken;
		this.ts = ts;
		this.cpu = cpu;
		this.mem = mem;
	}


	public String getDevToken() {
		return devToken;
	}

	public void setDevToken(String devToken) {
		this.devToken = devToken;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public float getCpu() {
		return cpu;
	}

	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	public float getMem() {
		return mem;
	}

	public void setMem(float mem) {
		this.mem = mem;
	}

	@Override
	public String toString() {
		return "SysStatus{" +
				"devToken='" + devToken + '\'' +
				", ts='" + ts + '\'' +
				", cpu=" + cpu +
				", mem=" + mem +
				'}';
	}
}
