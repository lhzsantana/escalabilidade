package br.com.escalabilidade.queue.events.impl;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Bill;
import br.com.escalabilidade.queue.events.ExampleEvent;

public class PaymentEvent extends ExampleEvent {

	public PaymentEvent(){
		
	}
	
	private Account account;
	private Bill bill;
	
	public PaymentEvent(Account account, Bill bill) {
		this.account=account;
		this.setBill(bill);
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
}
