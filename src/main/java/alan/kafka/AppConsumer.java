package alan.kafka;

import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class AppConsumer {
	private static final Logger logger = LogManager.getLogger(AppConsumer.class);

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
		props.put("group.id", "consumer");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		MysqlConn mysql = new MysqlConn();
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
		consumer.subscribe(Arrays.asList("status"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				// System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
				// record.key(), record.value());
				try {
					SysStatus json = mapper.readValue(record.value(), SysStatus.class); // 有问题
					mysql.insertRecord(json);

				} catch (Exception e) {
					logger.debug("jackson transformation or inserting mysql failed");
					e.printStackTrace();
					System.exit(-1);

				}
			}
		}
	}

}
