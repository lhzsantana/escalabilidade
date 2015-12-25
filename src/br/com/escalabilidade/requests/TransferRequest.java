package br.com.escalabilidade.requests;

import br.com.escalabilidade.entity.Account;

public class TransferRequest {

	private Account origin;
	private Account destination;
	private Double value;
	
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
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
}
