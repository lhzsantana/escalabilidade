package br.com.escalabilidade.transaction.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.escalabilidade.transaction.Transaction;

@Component
public class Transactioner {

	private final Database database;

    @Autowired
	public Transactioner(Database database){
    	this.database=database;
	}
	
	public void register(Transaction transaction){
		database.register(transaction);
	}
}
