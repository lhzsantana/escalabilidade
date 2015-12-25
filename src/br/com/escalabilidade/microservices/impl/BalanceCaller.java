package br.com.escalabilidade.microservices.impl;

import org.springframework.stereotype.Component;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Balance;
import br.com.escalabilidade.microservices.Caller;

@Component
public class BalanceCaller extends Caller {

	public boolean checkLimit(Account account, double value){
		return true;
	}
	
	public Balance getBalance(Account account) throws Exception{
		
		Balance balance = new Balance();
		
		balance.setAccount(account);
		
		return balance;
	}
}
