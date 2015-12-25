package br.com.escalabilidade.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Balance;
import br.com.escalabilidade.entity.Bill;
import br.com.escalabilidade.microservices.exceptions.NoLimitException;
import br.com.escalabilidade.microservices.impl.BalanceCaller;
import br.com.escalabilidade.queue.events.impl.PaymentEvent;
import br.com.escalabilidade.queue.events.impl.TransferEvent;
import br.com.escalabilidade.queue.impl.ExampleProducer;
import br.com.escalabilidade.services.BankServices;
import br.com.escalabilidade.transaction.Transaction;
import br.com.escalabilidade.transaction.impl.Transactioner;

@Component
public class ExampleServicesImpl implements BankServices{

	final static Logger logger = Logger.getLogger(ExampleServicesImpl.class);
	
    private final BalanceCaller balance;
    private final ExampleProducer producer;
    private final Transactioner transactioner;
    
    @Autowired
    public ExampleServicesImpl(
    		BalanceCaller balance, 
    		ExampleProducer producer,
    		Transactioner transactioner
    		) {
        this.balance = balance;
        this.producer = producer;
        this.transactioner = transactioner;
    }
    
	/**
	 * This method responsibility is to transfer a value between two accounts. Instead 
	 * of calling the microservice directly, it will queue a transfer event.
	 *
	 * @param Account origin
	 * @param Account destination
	 * @param double value
	 * 
	 * @return void
	 * @throws NoLimitException 
	 */
    @Override
	public void transfers(Account origin, Account destination, double value) throws NoLimitException{
		
		if(balance.checkLimit(origin, value)){
			
			TransferEvent event = new TransferEvent(origin, destination, value);
			producer.produce(event);
			
			Transaction transaction = new Transaction(event);
			transactioner.register(transaction);
		} else {
			throw new NoLimitException();
		}
	}

	/**
	 * This method responsibility is to pay a bill. Instead of calling the microservice
	 * directly, it will queue a payment event.
	 *
	 * @param Account origin
	 * @param Bill bill
	 * 
	 * @return void
	 * @throws NoLimitException 
	 */
    @Override
	public void payments(Account account, Bill bill) throws NoLimitException{

    	logger.info("New payment");
    	
		if(balance.checkLimit(account, bill.getPrice())){
			
			PaymentEvent event = new PaymentEvent(account, bill);
			
			Transaction transaction = new Transaction(event);
			transactioner.register(transaction);
			
			producer.produce(event);
		} else {
			throw new NoLimitException();
		}
	}

	/**
	 * This method responsibility is to return the balance of a account.
	 *
	 * @param Account origin
	 * 
	 * @return Balance
	 * @throws Exception 
	 */
    @Override
	public Balance checkBalances(Account account) throws Exception{
		
		return balance.getBalance(account);
	}
}
