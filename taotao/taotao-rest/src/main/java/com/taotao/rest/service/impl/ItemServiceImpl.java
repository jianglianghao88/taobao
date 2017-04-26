package com.taotao.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.rest.redis.JedisClient;
import com.taotao.rest.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Override
	public TbItem getItemById(Long itemId) {
		
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId);
			if(StringUtils.isNotBlank(json)){
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		
		try {
			jedisClient.set(REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId, JsonUtils.objectToJson(tbItem));
			jedisClient.expire(REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId, ITEM_EXPIRE_SECOND);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		//查询缓存
		//查询缓存，如果有缓存，直接返回
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY);
			//判断数据是否存在
			if (StringUtils.isNotBlank(json)) {
				// 把json数据转换成java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return itemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//根据商品id查询商品详情
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//添加缓存
		try {
			//向redis中添加缓存
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY
					, JsonUtils.objectToJson(itemDesc));
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":" + ITEM_DESC_KEY, ITEM_EXPIRE_SECOND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
