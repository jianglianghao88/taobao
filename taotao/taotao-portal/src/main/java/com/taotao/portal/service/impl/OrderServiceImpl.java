package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.OrderInfo;
import com.taotao.portal.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService{
	
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	
	@Override
	public String createOrder(OrderInfo orderInfo) {

		String json = JsonUtils.objectToJson(orderInfo);
		
		String doPostJson = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, json);
	
		TaotaoResult result = TaotaoResult.format(doPostJson);
		
		String orderId = result.getData().toString();
		
		return orderId;
	}

}
