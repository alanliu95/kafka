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
		MysqlConn mysql = new MysqlConn("sim1");
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
		String s = "\"deviceId\":\"sim1\",\"ts\":\"2018-12-26 15:50:47.324\",\"cpuUsage\":65.93164,\"memUsage\":74.29702";
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
				try {
					
					SysStatus json = mapper.readValue(record.value(), SysStatus.class);  //有问题
					if(json.getDeviceId().equals("sim1"))
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
