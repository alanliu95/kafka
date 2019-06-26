package alan.kafka;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class AppConsumer {
	private static final Logger logger = LogManager.getLogger(AppConsumer.class);

	public static void main(String[] args) {
		Properties props = new Properties();
		InputStream in = AppProducer.class.getResourceAsStream("/consumer.properties");
		if (in == null) {
			logger.error("could find consumer.propeties");
			System.exit(-1);
		}
		try {
			props.load(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("consumer.propeties contains errors.");
			System.exit(-1);
		}
		KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props);
		MysqlConn mysql = new MysqlConn(props);
		ObjectMapper mapper = new ObjectMapper();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					Thread.sleep(200);
					System.out.println("Shuting down ...");
					// some cleaning up code...
					consumer.close();
					mysql.disconn();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		String topic=props.getProperty("topic");
		logger.debug("topic:"+topic);
		consumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				// System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
				// record.key(), record.value());
				try {
					SysStatus sysStatus = mapper.readValue(record.value(), SysStatus.class); // 有问题
					logger.debug("got record:"+sysStatus);
					mysql.insertRecord(sysStatus);

				} catch (Exception e) {
					logger.debug("jackson transformation or inserting mysql failed");
					e.printStackTrace();
					System.exit(-1);

				}
			}
		}
	}

}
