package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.redis.JedisClient;
import com.taotao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	
	@Override
	public List<TbContent> getContentList(Long cid) {
		// TODO Auto-generated method stub
		//查询redis
		try {
			String json = jedisClient.hget(REDIS_CONTENT_KEY, cid+"");
			if(!StringUtils.isBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
			
				return list;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		
		List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example );
		
		try {
			
			jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(contentList));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return contentList;
	}


	@Override
	public TaotaoResult synContent(Long cid) {
		// TODO Auto-generated method stub
		jedisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}

}
