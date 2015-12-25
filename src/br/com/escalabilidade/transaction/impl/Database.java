package br.com.escalabilidade.transaction.impl;

import java.util.Date;
import java.util.Set;

import br.com.escalabilidade.entity.User;
import br.com.escalabilidade.transaction.Transaction;

public interface Database {

	public void register(Transaction transaction);

	public Set<Transaction> getByDate(Date date) throws Exception;
	
	public Set<Transaction> getByDateAndUser(Date date, User user) throws Exception;
	
	public Set<Transaction> getAll();
}
