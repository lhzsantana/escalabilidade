package br.com.escalabilidade.queue.impl;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.escalabilidade.queue.events.ExampleEvent;

@Configuration
public class ExampleProducer {
	
	final static Logger logger = Logger.getLogger(ExampleProducer.class);
	
    @Value("${brokerList}")
    private String brokerList;
 
 
    @Value("${topic}")
    private String topic;
 
    private KafkaProducer<String, String> producer;
 
    @PostConstruct
    public void initIt() {
        Properties kafkaProps = new Properties();
 
        kafkaProps.put("bootstrap.servers", brokerList);

        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("acks", "1");
 
        kafkaProps.put("retries", "1");
        kafkaProps.put("linger.ms", 5);
 
        producer = new KafkaProducer<>(kafkaProps);
        
        producer.partitionsFor(topic); 
    }

	public void produce(ExampleEvent event) {

    	logger.info("New bank event");
    	
		ObjectMapper mapper = new ObjectMapper();

        ProducerRecord<String, String> record;
		try {
			record = new ProducerRecord<String, String>(topic, mapper.writeValueAsString(event));
			
	        producer.send(record);
		} catch (JsonProcessingException e) {
			logger.error(e);
		}	
	}
		
	public void close(){		
		producer.close();
	}
}
