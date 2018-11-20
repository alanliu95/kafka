package alan.kafka;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.*;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Hello world!
 *
 */
public class AppProducer 
{
    public static void main( String[] args ) throws IOException, InterruptedException
    {    	
    	SysStatus s=new SysStatus();
    	ObjectMapper mapper = new ObjectMapper();
    	Properties props = new Properties();
		props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		final Producer<String, String> producer = new KafkaProducer<>(props);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
	        public void run() {
	            try {
	                Thread.sleep(200);
	                System.out.println("Shouting down ...");
	                //some cleaning up code...
	                producer.close();
	            } catch (InterruptedException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }
	    });
		
		int i=0;
		String jsonStr;
		while(true) {
			s.readStatus();
			jsonStr=mapper.writeValueAsString(s);
			producer.send(new ProducerRecord<String, String>("SysStatus", Integer.toString(i), jsonStr));
			i++;
			Thread.sleep(3000);
		}		
    }
}



