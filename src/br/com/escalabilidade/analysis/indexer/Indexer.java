package br.com.escalabilidade.analysis.indexer;

import java.util.Set;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import br.com.escalabilidade.transaction.Transaction;

public interface Indexer extends ElasticsearchRepository<Transaction, String> {

	public void save(Set<Transaction> all);
}
