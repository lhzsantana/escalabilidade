package br.com.escalabilidade.analysis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.escalabilidade.analysis.indexer.Indexer;
import br.com.escalabilidade.transaction.impl.Database;

@Component
public class ReportGenerator {
	
	final static Logger logger = Logger.getLogger(ReportGenerator.class);

	private final Indexer indexer;
	private final Database database;
    
    @Autowired
    public ReportGenerator(Indexer indexer, Database database){
    	this.indexer=indexer;
    	this.database=database;    	
    }

    @Scheduled(fixedRate = 500000)
	public void generateReports(){

    	logger.info("Creating report");
        
		indexer.save(database.getAll());
	}
}
