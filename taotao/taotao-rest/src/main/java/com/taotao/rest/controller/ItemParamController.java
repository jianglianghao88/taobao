package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.rest.service.ItemParamService;

@Controller
public class ItemParamController {
	
	@Autowired
	ItemParamService itemParamService;

	@RequestMapping("/item/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamById(@PathVariable Long itemId) {
		try {
			TbItemParamItem itemParamItem = itemParamService.getItemParamById(itemId);
			return TaotaoResult.ok(itemParamItem);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
