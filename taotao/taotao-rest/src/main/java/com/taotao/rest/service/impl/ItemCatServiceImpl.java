package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCat() {
		// TODO Auto-generated method stub
		List list = getItemCatList(0l);
		
		ItemCatResult result = new ItemCatResult();
		
		result.setData(list);
		return result;
	}
	
	public List getItemCatList(Long parentId){
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList  = new ArrayList<>();
		int count = 0;
		
		for (TbItemCat tbItemCat : list ) {
			if(count >= 14){
				break;
			}
			if(tbItemCat.getIsParent()){
				CatNode catNode = new CatNode();
				//取出一级节点
				catNode.setUrl("/products/"+tbItemCat.getId()+".html");
				if(tbItemCat.getParentId() == 0){
					catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
					count++;
				}else{
					catNode.setName(tbItemCat.getName());
				}
				
				catNode.setItems(getItemCatList(tbItemCat.getId()));
				resultList.add(catNode);
			}else{
				//如果是叶子节点
				String item = "/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName();
				resultList.add(item);
				
			}
			
		}
		return resultList;
	}

}
