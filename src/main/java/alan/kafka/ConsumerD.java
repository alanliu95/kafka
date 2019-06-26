package alan.kafka;

import com.alan.Util.ThreadAnalysis;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
//import java.util.concurrent.FutureTask;

public class ConsumerD implements Runnable {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        SysStatus status = new SysStatus();
        ConsumerD consumerD = new ConsumerD(status, mapper);
        Thread thread = new Thread(consumerD);
        ThreadAnalysis.getThreadInfo();
        thread.start();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            logger.info("延时失败");
        }
        thread.interrupt();
        logger.info("Thread main finished.");
        ThreadAnalysis.getThreadInfo();
        while (true) ;

    }

    private static final Logger logger = LogManager.getLogger(ConsumerD.class);
    private Properties props;
    private KafkaConsumer<String, String> consumer;
    private SysStatus status;
    private ObjectMapper mapper;

    public ConsumerD(SysStatus status, ObjectMapper mapper) {
        this.status = status;
        this.mapper = mapper;
        this.props = new Properties();
        InputStream in = ConsumerD.class.getResourceAsStream("/consumer.properties");
        if (in == null) {
            logger.error("could find consumer.propeties");
            System.exit(-1);
        }
        try {
            this.props.load(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("consumer.propeties contains errors.");
            System.exit(-1);
        }
        this.consumer = new KafkaConsumer<>(this.props);
        this.consumer.subscribe(Arrays.asList(props.getProperty("topic")));

    }

    //FutureTask Lock
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ConsumerRecords<String, String> records;
//            records = this.consumer.poll(100);
            try {
                records = this.consumer.poll(100);
            }catch (Exception e){
                logger.debug("get interrupted");
                continue;
            }
            for (ConsumerRecord<String, String> record : records) {
                // System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(),
                // record.key(), record.value());
                try {
                    SysStatus sysStatus = this.mapper.readValue(record.value(), SysStatus.class); // 有问题
                    logger.info("get record:" + sysStatus);
                } catch (Exception e) {
                    logger.error("jackson transformation failed");
                    e.printStackTrace();
                    System.exit(-1);

                }
            }
        }
        consumer.close();

        //System.out.println(""+Thread.currentThread());
    }
}
