package com.taotao.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;

public class ItemAddMessageListener implements MessageListener {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			Long itemId = Long.parseLong(text);
			Thread.sleep(1000);
			
			Item item = itemMapper.getItemById(itemId);
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", item.getId());
			doc.addField("item_title", item.getTitle());
			doc.addField("item_sell_point", item.getSell_point());
			doc.addField("item_price", item.getPrice());
			doc.addField("item_image", item.getImages());
			doc.addField("item_category_name", item.getCategory_name());
			doc.addField("item_desc", item.getItem_desc());
			
			solrServer.add(doc);
			solrServer.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
