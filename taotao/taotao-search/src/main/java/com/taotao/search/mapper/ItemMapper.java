package com.taotao.search.mapper;

import java.util.List;

import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

public interface ItemMapper {

	public List<Item> getItemList();
	
	public Item getItemById(Long itemId);
}
