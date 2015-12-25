package br.com.escalabilidade.transaction;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.escalabilidade.entity.User;
import br.com.escalabilidade.queue.events.ExampleEvent;
import br.com.escalabilidade.queue.events.impl.PaymentEvent;
import br.com.escalabilidade.queue.events.impl.TransferEvent;
import br.com.escalabilidade.transaction.impl.Database;

@Component
public class Cassandra implements Database {

	final static Logger logger = Logger.getLogger(Cassandra.class);
	
	Session session;

    @Autowired
	public Cassandra(Session session) {
		this.session=session;
		
		try{
			session.execute("CREATE TABLE IF NOT EXISTS transact (uid uuid primary key, event varchar) ");
		}catch(Exception e){
			logger.error(e);
		}
	}

	@Override
	public void register(Transaction transaction) {
		
		ObjectMapper mapper = new ObjectMapper();

		String cql;
		try {
			cql = String.format("INSERT INTO transact (uid,event) " +
					"VALUES (now(), '%s')",
					mapper.writeValueAsString(transaction.getEvent()));
			session.execute(cql);
			
		} catch (JsonProcessingException e) {
			logger.error(e);
		}
		
	}

	@Override
	public Set<Transaction> getByDate(Date date) throws Exception {
		throw new Exception("Not implemented yet");
	}

	@Override
	public Set<Transaction> getByDateAndUser(Date date, User user) throws Exception {
		throw new Exception("Not implemented yet");
	}

	@Override
	public Set<Transaction> getAll() {
		
		logger.info("Getting all the transactions");
		
		ResultSet results = session.execute("SELECT * FROM transactions");
		
		Set<Transaction> transactions = new HashSet<Transaction>();
		
		for (Row row : results) {
			
			ObjectMapper mapper = new ObjectMapper();
			
			ExampleEvent event = null;
			
        	try{
				event = mapper.readValue(row.getString(2), PaymentEvent.class);

        	}catch(Exception e1){

        		try{
					event = mapper.readValue(row.getString(2), TransferEvent.class);
					
        		}catch(Exception e2){
        			logger.error(e1);
        			logger.error(e2);
				}
        	}
        	
			transactions.add(new Transaction(event));
		}
		
		return transactions;
	}

}
