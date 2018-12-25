package alan.kafka;

import java.util.*;
import org.apache.kafka.clients.consumer.*;
import org.codehaus.jackson.map.ObjectMapper;

public class AppConsumer {
	
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
		consumer.subscribe(Arrays.asList("SysStatus"));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
				try {
					LinuxStatus json = mapper.readValue(record.value(), LinuxStatus.class);
					mysql.insertRecord(json);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	@SuppressWarnings("unused")
	private Consumer<String, String> consumer;

	public AppConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(props);
	}

}
