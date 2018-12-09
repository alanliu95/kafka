package alan.kafka;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.*;
import org.codehaus.jackson.map.ObjectMapper;

public class AppProducer {
	public static void main(String[] args) throws IOException, InterruptedException {
		SysStatus s = new SimStatus();
		ObjectMapper mapper = new ObjectMapper();
		Properties props = new Properties();
		props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
		props.put("acks", "all");
		props.put("retries", 1);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		// 捕获的是什么信号？
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shouting down ...");
				// some cleaning up code...
				producer.close();
			}
		});

		int i = 0;
		String jsonStr;
		while (true) {
			s.readStatus();
			jsonStr = mapper.writeValueAsString(s);
			System.out.println(jsonStr);
			producer.send(new ProducerRecord<String, String>("SysStatus", Integer.toString(i), jsonStr));
			i++;
			Thread.sleep(3000);
		}
	}
}
