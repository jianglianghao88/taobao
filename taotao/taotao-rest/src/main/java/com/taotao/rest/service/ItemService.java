package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {

	public TbItem getItemById(Long itemId);
	
	public TbItemDesc getItemDescById(Long itemId);
}
