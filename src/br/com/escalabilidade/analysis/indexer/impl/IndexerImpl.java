package br.com.escalabilidade.analysis.indexer.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import br.com.escalabilidade.analysis.indexer.Indexer;
import br.com.escalabilidade.transaction.Transaction;

@Component
public class IndexerImpl implements Indexer {

	final static Logger logger = Logger.getLogger(IndexerImpl.class);

	private ElasticsearchTemplate template;

	@Autowired
	public IndexerImpl(ElasticsearchTemplate template) throws UnknownHostException {
		this.template = template;
	}

	@Override
	public void save(Set<Transaction> all) {

		if (all.size() > 0) {
			
			List<IndexQuery> indexQueries = new ArrayList<IndexQuery>();

			for (Transaction transaction : all) {

				IndexQuery indexQuery1 = new IndexQueryBuilder()
						.withId(transaction.getId())
						.withObject(transaction)
						.build();
				indexQueries.add(indexQuery1);
			}

			template.bulkIndex(indexQueries);
		}
	}

	@Override
	public <S extends Transaction> S index(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Transaction> search(QueryBuilder arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FacetedPage<Transaction> search(SearchQuery arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FacetedPage<Transaction> search(QueryBuilder arg0, Pageable arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Transaction> searchSimilar(Transaction arg0, String[] arg1, Pageable arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Transaction> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Transaction> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Transaction arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Transaction> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Transaction> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Transaction> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Transaction> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Transaction> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
