package br.com.escalabilidade;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.escalabilidade.entity.Account;
import br.com.escalabilidade.entity.Balance;
import br.com.escalabilidade.microservices.exceptions.NoLimitException;
import br.com.escalabilidade.queue.impl.ExampleConsumer;
import br.com.escalabilidade.requests.PaymentRequest;
import br.com.escalabilidade.requests.TransferRequest;
import br.com.escalabilidade.services.BankServices;

@RestController("/example")
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class Server {

	final static Logger logger = Logger.getLogger(Server.class);
	
    @Autowired
	private BankServices services;

    public Server() {
    	
    }

    /*
    {
    "account": {"id":1},
    "bill": {"biller":"walmart","price":"100.0"}
    }
     */
    @RequestMapping(value = "/pay", method=RequestMethod.POST)
    public void pay(
    		@RequestBody PaymentRequest request
    		) throws NoLimitException {
    	
    	logger.info("A new request for payment");
    	
    	services.payments(request.getAccount(), request.getBill());
    }

    /*
    {
    "origin": {"id":1},
    "destination": {"id":2},
    "value": "100.0"
    }    
     */
    @ResponseBody
    @RequestMapping(value = "/transfer", method=RequestMethod.POST)
    public void transfer(
    		@RequestBody TransferRequest request
    		) throws NoLimitException {
    	
    	logger.info("A new request for transfer");
    	
    	services.transfers(
    			request.getOrigin(),
    			request.getDestination(), 
    			request.getValue()
    		);
    }

    /*
     {"id":1, "user":{ "name":"luiz"}}
     */
    @RequestMapping(value = "/checkBalances", method=RequestMethod.POST)
    public @ResponseBody Balance checkBalances(
    		@RequestBody Account account
    		) throws Exception {
    	
    	logger.info("A new request for checking balance");
    	
    	return services.checkBalances(account);
    }

    public static void main(String[] args) throws Exception {
        //SpringApplication.run(Server.class, args);
        SpringApplication.run(Server.class, args);
        SpringApplication.run(ExampleConsumer.class, args);
    }
}
