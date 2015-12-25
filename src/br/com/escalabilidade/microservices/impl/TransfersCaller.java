package br.com.escalabilidade.microservices.impl;

import org.apache.log4j.Logger;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.microservices.Caller;

public class TransfersCaller extends Caller {

	final static Logger logger = Logger.getLogger(TransfersCaller.class);
	
	public static void transfer(Account origin, Account destination, double value){
		logger.info("Calling microservice for transfer");		
	}
}
