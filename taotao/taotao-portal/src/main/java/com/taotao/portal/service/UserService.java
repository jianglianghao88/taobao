package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.pojo.TbUser;

public interface UserService {

	public TbUser getUserByToken(HttpServletRequest request , HttpServletResponse response);
}
