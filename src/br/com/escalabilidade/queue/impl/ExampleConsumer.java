package br.com.escalabilidade.queue.impl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.escalabilidade.microservices.impl.PaymentsCaller;
import br.com.escalabilidade.microservices.impl.TransfersCaller;
import br.com.escalabilidade.queue.events.impl.PaymentEvent;
import br.com.escalabilidade.queue.events.impl.TransferEvent;
import br.com.escalabilidade.queue.exceptions.EventException;
import kafka.serializer.StringDecoder;
import scala.Tuple2;

@EnableAutoConfiguration
@ComponentScan
public class ExampleConsumer {

	final static Logger logger = Logger.getLogger(ExampleProducer.class);
	
    public static JavaStreamingContext jssc;
    	
    @Value("${brokerList}")
    private String brokerList;
 
    @Value("${topic}")
    private String topic;
    
    public ExampleConsumer(){

		SparkConf conf = new SparkConf()
				.setAppName("BankConsumer")
				//.setMaster("spark://192.168.56.1:7077")
				.setMaster("local[*]")				
				.setJars(new String[]{"target/banking-0.0.1-SNAPSHOT-jar-with-dependencies.jar"})
				.set("spark.akka.frameSize", "40");

        jssc = new JavaStreamingContext(conf, Durations.seconds(10));
    }
    
    @PostConstruct
    public void consumeString() {
    	
        HashSet<String> topicsSet = new HashSet<>(Arrays.asList(topic.split(",")));
        HashMap<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", brokerList);
 
        JavaPairInputDStream<String, String> messages =
                KafkaUtils.createDirectStream(
                        jssc,
                        String.class,
                        String.class,
                        StringDecoder.class,
                        StringDecoder.class,
                        kafkaParams,
                        topicsSet
                );
 
        JavaDStream<String> events =
                messages.map(
                        (Function<Tuple2
                        <String, String>,
                        String>) Tuple2::_2);
 
        events.foreachRDD((v1, v2) -> {
            v1.foreach((x) -> {

        		logger.info("--------------------------");
        		logger.info("--------------------------");
        		logger.info("New event " + x);
        		logger.info("--------------------------");
        		logger.info("--------------------------");
        		
        		ObjectMapper mapper = new ObjectMapper();
        		
            	try{
            		logger.info("--------------------------");
            		logger.info("--------------------------");
            		logger.info("Payment Event");
            		logger.info("--------------------------");
            		logger.info("--------------------------");
					PaymentEvent paymentEvent = mapper.readValue(x, PaymentEvent.class);
	
					PaymentsCaller.pay(
							paymentEvent.getAccount(), 
							paymentEvent.getBill()
					);
					
            	}catch(JsonMappingException e1){

            		logger.error(e1);
            		
            		logger.info("--------------------------");
            		logger.info("--------------------------");
            		logger.info("Transfer Event");
            		logger.info("--------------------------");
            		logger.info("--------------------------");
            		
            		try{
						TransferEvent transferEvent = mapper.readValue(x, TransferEvent.class);
						
						TransfersCaller.transfer(
								transferEvent.getOrigin(), 
								transferEvent.getDestination(), 
								transferEvent.getValue()
						);
            		}catch(JsonMappingException e2){

    					throw new EventException("Unknow event");
            		}
            	}
            	
            });
 
            return null;
        });
 
        jssc.start();
        jssc.awaitTermination();
    }
}