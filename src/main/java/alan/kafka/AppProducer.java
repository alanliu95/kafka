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
			logger.error("not found producer.propeties");
			System.exit(-1);
		}
		try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(" parse failed: producer.propeties.");
			System.exit(-1);			
		}

		int interval = 0;
		try {
			interval = Integer.parseInt(props.getProperty("interval"));
		} catch (NumberFormatException e) {
			logger.error("interval value must be numeric.");
			System.exit(-1);
		}
		String deviceId = props.getProperty("device.token");
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
