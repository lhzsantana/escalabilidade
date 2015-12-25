package br.com.escalabilidade.queue.events;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table
public abstract class ExampleEvent {
	
	@PrimaryKey
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	
	
}
