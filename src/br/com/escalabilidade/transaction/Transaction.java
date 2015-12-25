package br.com.escalabilidade.transaction;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import org.springframework.data.elasticsearch.annotations.Document;

import br.com.escalabilidade.queue.events.ExampleEvent;

@Table
@Document(indexName = "transactions", type = "transaction", shards = 1, replicas = 0, refreshInterval = "-1")
public class Transaction {

	private ExampleEvent event;

	@PrimaryKey
	@Id
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Transaction(ExampleEvent event) {
		this.event=event;
	}

	public ExampleEvent getEvent() {
		return event;
	}

	public void setEvent(ExampleEvent event) {
		this.event = event;
	}	
}
