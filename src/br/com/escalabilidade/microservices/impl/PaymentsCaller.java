package br.com.escalabilidade.microservices.impl;

import org.apache.log4j.Logger;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Bill;
import br.com.escalabilidade.microservices.Caller;

public class PaymentsCaller extends Caller {

	final static Logger logger = Logger.getLogger(PaymentsCaller.class);
	
	public static void pay(Account account, Bill bill){
		logger.info("Calling microservice for payment");		
	}
}
