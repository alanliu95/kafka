package alan.kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SimStatus extends SysStatus {

	private Random ra;

	public SimStatus(String deviceId) {
		super(deviceId);
		this.ra = new Random();
	}

	public void readStatus() {
		setMem(ra.nextFloat() * 100);
		setCpu(ra.nextFloat() * 100);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 设置日期格式
		setTs(df.format(new Date()));// new Date()为获取当前系统时间，也可使用当前时间戳
	}

}
