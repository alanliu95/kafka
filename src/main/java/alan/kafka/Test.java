package alan.kafka;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    private static Logger logger =        
    LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
    public static void main(String[] args) throws Exception {
        logger.info("{}以及{}","张三","李四");    
    }

}
//public static void  main(String Args[]){
//	//SysStatus.getCpuUsage();
//	//SysStatus.getMemUsage();
//	String jsonStr=" ";
//	SysStatus s=new SimStatus();
//	s.readStatus();
//	
//	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
//	try {
//		jsonStr=mapper.writeValueAsString(s);
//		System.out.println(jsonStr);
//	} catch (IOException e) {
//		e.printStackTrace();
//		System.out.println("json format error");
//		return;
//	}		
//}


//props.put("bootstrap.servers", "alannnn.tpddns.cn:9092");
//props.put("acks", "all");
//props.put("retries", 1);
//props.put("batch.size", 16384);
//props.put("linger.ms", 1);
//props.put("buffer.memory", 33554432);
//props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");