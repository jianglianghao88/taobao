package com.taotao.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public Object getItemCat(String callback){
		ItemCatResult result = itemCatService.getItemCat();
		
		if(StringUtils.isEmpty(callback)){
			return result;
		}
		
		MappingJacksonValue jacksonValue = new MappingJacksonValue(result);
		jacksonValue.setJsonpFunction(callback);
		
		return jacksonValue;
	}
	
}
