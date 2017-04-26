package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private ItemService itemService;
	@Value("${COOKIE_EXPIRE}")
	private Integer COOKIE_EXPIRE;

	@Override
	public TaotaoResult addCart(Long itemId, Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> list = getItemList(request);
		
		boolean flag = false;
		for (CartItem cartItem : list) {
			if(cartItem.getId().longValue() == itemId){
				cartItem.setNum(num + cartItem.getNum());
				flag = true;
				break;
			}
		}
		if(!flag){
			TbItem tbItem = itemService.getItemById(itemId);
			CartItem cartItem = new CartItem();
			cartItem.setId(itemId);
			cartItem.setNum(num);
			cartItem.setPrice(tbItem.getPrice());
			cartItem.setTitle(tbItem.getTitle());
			String image = tbItem.getImage();
			
			if(StringUtils.isNotBlank(image)){
				
				String[] strings = image.split(",");
				cartItem.setImages(strings[0]);
			}
			
			list.add(cartItem);
			
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), COOKIE_EXPIRE, true);
		
		return TaotaoResult.ok();
	}
	
	private List<CartItem> getItemList(HttpServletRequest request){
		
		try {
			String json = CookieUtils.getCookieValue(request, "TT_CART", true);
			
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
		
			return list == null ? new ArrayList<CartItem>():list;
		} catch (Exception e) {
			return new ArrayList<CartItem>();
		}
	}

	@Override
	public List<CartItem> getItems(HttpServletRequest request) {
		
		List<CartItem> list = getItemList(request);
		
		return list;
	}

	@Override
	public TaotaoResult updateCartItem(long itemId, Integer num,
			HttpServletRequest request, HttpServletResponse response) {

		List<CartItem> list = getItemList(request);
		
		for (CartItem cartItem : list) {
			if(cartItem.getId() == itemId){
				cartItem.setNum(num);
				break;
			}
			
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), COOKIE_EXPIRE, true);
		
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request,
			HttpServletResponse response) {

		List<CartItem> list = getItemList(request);
		
		for (CartItem cartItem : list) {
			if(cartItem.getId() == itemId){

				list.remove(cartItem);
				break;
			}
			
		}
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list), COOKIE_EXPIRE, true);
		
		
		return TaotaoResult.ok();
	}
	

}
