package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.pojo.AdNode;
import com.taotao.portal.service.AdService;

@Service
public class AdServiceImpl implements AdService{

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;
	@Value("${AD_CID}")
	private String AD_CID;
	
	@Override
	public String getAdList() {
		// TODO Auto-generated method stub
		String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + AD_CID);

		TaotaoResult result = TaotaoResult.formatToList(json, TbContent.class);
		
		List<TbContent> contentList = (List<TbContent>) result.getData();
		List<AdNode> adList = new ArrayList<>();
		
		for (TbContent content : contentList) {
			AdNode node = new AdNode();
			
			node.setHeight(240);
			node.setWidth(670);
			node.setSrc(content.getPic());
			
			node.setHeightB(240);
			node.setWidthB(550);
			node.setSrcB(content.getPic2());
			
			node.setAlt(content.getSubTitle());
			node.setHref(content.getUrl());
			
			adList.add(node);
		}
		
		return JsonUtils.objectToJson(adList);
	}

}
