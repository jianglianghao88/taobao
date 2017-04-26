package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		QueryResponse response = solrServer.query(query);
		
		SolrDocumentList docs = response.getResults();
		List<Item> itemList = new ArrayList<>();
		
		for (SolrDocument solrDocument : docs) {
			Item item = new Item();
			
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			item.setImages((String) solrDocument.get("item_image"));
			item.setPrice((Long) solrDocument.get("item_price"));
			item.setSell_point((String)solrDocument.get("item_sell_point"));
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String content = "";
			if(list != null && list.size() > 0){
				content = list.get(0);
			}else{
				content = (String) solrDocument.get("item_title");
			}
			
			item.setTitle(content);
			
			itemList.add(item);
		}
		
		SearchResult result = new SearchResult();
		result.setItemList(itemList);
		result.setRecordCount(docs.getNumFound());
		
		return result;
	}

}
