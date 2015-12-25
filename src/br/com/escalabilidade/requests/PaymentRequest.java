package br.com.escalabilidade.requests;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Bill;

public class PaymentRequest {

	private Account account;
	private Bill bill;
	
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
