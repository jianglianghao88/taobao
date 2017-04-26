package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows)
			throws Exception {
		SolrQuery query = new SolrQuery();
		query.setQuery(queryString);
		query.setStart((page - 1) * rows);
		query.setRows(rows);
		
		query.set("df", "item_title");
		
		query.setHighlight(true);
		
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font class=\"skcolor_ljg\">");
		query.setHighlightSimplePost("</font>");
		
		SearchResult result = searchDao.search(query);
		
		Long recordCount = result.getRecordCount();
		
		int totalPage = (int) (recordCount / rows) ;
		
		if(recordCount % rows > 0){
			totalPage++ ;
		}
		
		result.setCurrentPage(page);
		result.setTotalPage(totalPage);
		
		return result;
	}

}
