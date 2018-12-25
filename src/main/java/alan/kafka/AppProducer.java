package alan.kafka;

import java.io.IOException;
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

	public static void main(String[] args) throws IOException, InterruptedException {

		SysStatus s = new SimStatus();
		ObjectMapper mapper = new ObjectMapper();
		Properties props = new Properties();

//		props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
//		props.put("acks", "all");
//		props.put("retries", 1);
//		props.put("batch.size", 16384);
//		props.put("linger.ms", 1);
//		props.put("buffer.memory", 33554432);
//		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		 props.load(AppProducer.class.getResourceAsStream("/producer.properties"));
		 logger.info("properties:" + props);
		 logger.info("topic:" + props.getProperty("topic"));
		Producer<String, String> producer = new KafkaProducer<>(props);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				logger.info("Shuting down ...");
				producer.close();
			}
		});
		ProducerRecord<String, String> record;

		int key = 0;
		String jsonStr;
		while (true) {
			s.readStatus();
			jsonStr = mapper.writeValueAsString(s);
			logger.info("message:" + jsonStr);
			record = new ProducerRecord<String, String>(props.getProperty("topic"), Integer.toString(key), jsonStr);
			producer.send(record, new AppProducer());
			// getThreadInfo();
			key++;
			Thread.sleep(3000);
		}
	}
	private static String transferLongToDate(Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(millSec);
		return sdf.format(date);
	}

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
			e.printStackTrace();
			System.exit(-1);
		} else {
			// getThreadInfo();
			logger.debug("message was sent");
			logger.debug("The offset of the record we just sent is: " + metadata.offset());
		}
	}

}
