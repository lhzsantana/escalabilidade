package br.com.escalabilidade.queue.events.impl;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.queue.events.ExampleEvent;

public class TransferEvent extends ExampleEvent {

	public TransferEvent(){
		
	}
	
	private Account origin;
	private Account destination;
	private double value;
	
	public TransferEvent(Account origin, Account destination, double value) {
		this.origin=origin;
		this.destination=destination;
		this.value=value;		
	}

	public Account getOrigin() {
		return origin;
	}

	public void setOrigin(Account origin) {
		this.origin = origin;
	}

	public Account getDestination() {
		return destination;
	}

	public void setDestination(Account destination) {
		this.destination = destination;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
