package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String keyword, int page, int rows) {
		Map<String, String> param = new HashMap<>();
		param.put("keyword", keyword);
		param.put("page", page+"");
		param.put("rows", rows+"");
		
		String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
		
		TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
		
		SearchResult searchResult = (SearchResult) result.getData();
		
		return searchResult;
	}

}
