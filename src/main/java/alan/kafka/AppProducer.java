package alan.kafka;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.kafka.clients.producer.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import java.util.concurrent.FutureTask;

public class AppProducer implements Callback {
	private static final Logger logger = LogManager.getLogger(AppProducer.class);

	public static void main(String[] args){
		Properties props = new Properties();
		InputStream in = AppProducer.class.getResourceAsStream("/producer.properties");
		if (in == null) {
			logger.error("could find producer.propeties");
			System.exit(-1);
		}
		try {
			props.load(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("producer.propeties contains errors.");
			System.exit(-1);			
		}
		int interval = 0;
		try {
			interval = Integer.parseInt(props.getProperty("interval"));
		} catch (NumberFormatException e) {
			logger.error("interval value must be numeric.");
			System.exit(-1);
		}
		String deviceId = props.getProperty("device.id");
		if (deviceId == null) {
			logger.error("miss deviceId field");
			System.exit(-1);
		}
		SysStatus sysStatus = new SimStatus(deviceId);
		ObjectMapper mapper = new ObjectMapper();
		logger.info("properties:" + props);
		// logger.info("topic:" + props.getProperty("topic"));
		Producer<String, String> producer = new KafkaProducer<>(props);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				logger.info("shut down,clear the resourses");
				producer.close();
			}
		});
		ProducerRecord<String, String> record;
		int key = 0;
		String jsonStr;
		AppProducer app = new AppProducer();
		while (true) {			
			sysStatus.readStatus();
			try {
				jsonStr = mapper.writeValueAsString(sysStatus);
				logger.debug("message:" + jsonStr);
				record = new ProducerRecord<String, String>(props.getProperty("topic"), Integer.toString(key), jsonStr);
				producer.send(record, app);
				// getThreadInfo();
				key++;
				Thread.sleep(interval);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info("json-formatting failed");
				System.exit(-1);
			} catch(InterruptedException e) {
				e.printStackTrace();
				logger.info("process was interrupted");
				System.exit(-1);
			}

		}
	}

	@SuppressWarnings("unused")
	private static String transferLongToDate(Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(millSec);
		return sdf.format(date);
	}

	@SuppressWarnings("unused")
	private static void getThreadInfo() {
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		while (currentGroup.getParent() != null) {
			// 返回此线程组的父线程组
			currentGroup = currentGroup.getParent();
		}
		// 此线程组中活动线程的估计数
		int noThreads = currentGroup.activeCount();
		Thread[] lstThreads = new Thread[noThreads];
		// 把对此线程组中的所有活动子组的引用复制到指定数组中。
		currentGroup.enumerate(lstThreads);
		for (Thread thread : lstThreads) {
			System.out.println("线程数量：" + noThreads + " 线程id：" + thread.getId() + " 线程名称：" + thread.getName() + " 线程状态："
					+ thread.getState());
		}
		return;

	}

	public void onCompletion(RecordMetadata metadata, Exception e) {
		if (e != null) {
			logger.error("failed to send the message");
			e.printStackTrace();
			System.exit(-1); // 出现异常退出
		} else {
			// getThreadInfo();
			logger.debug("message was sent");
			logger.debug("The partition offset of the record sent: " + metadata.offset());
		}
	}
}
