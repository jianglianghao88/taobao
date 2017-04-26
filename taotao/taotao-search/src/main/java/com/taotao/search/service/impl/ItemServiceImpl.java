package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ItemMapper itemMapper;
	
	@Override
	public TaotaoResult importItem() throws Exception {
		// TODO Auto-generated method stub
		List<Item> list = itemMapper.getItemList();
		
		for (Item item : list) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSell_point());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImages());
			doc.addField("item_category_name", item.getCategory_name());
			doc.addField("item_desc", item.getItem_desc());
			
			solrServer.add(doc);
		}
		solrServer.commit();
		
		return TaotaoResult.ok();
	}

}
