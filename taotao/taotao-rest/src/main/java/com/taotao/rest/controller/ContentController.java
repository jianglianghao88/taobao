package com.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;

@Controller
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/content/list/{cid}")
	@ResponseBody
	public TaotaoResult getContentList(@PathVariable Long cid){
		
		try {
			List<TbContent> contentList = contentService.getContentList(cid);
			return TaotaoResult.ok(contentList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/sync/content/{cid}")
	@ResponseBody
	public TaotaoResult syncContentList(@PathVariable Long cid){
		try {
			TaotaoResult result = contentService.synContent(cid);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
