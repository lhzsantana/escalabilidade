package br.com.escalabilidade.services;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Balance;
import br.com.escalabilidade.entity.Bill;
import br.com.escalabilidade.microservices.exceptions.NoLimitException;

public interface BankServices {

	public void transfers(Account origin, Account destination, double value) throws NoLimitException;

	public void payments(Account account, Bill bill) throws NoLimitException;

	public Balance checkBalances(Account account) throws Exception;
}
