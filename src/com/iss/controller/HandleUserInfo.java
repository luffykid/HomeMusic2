package com.iss.controller;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iss.dao.UserDaoI;

@Controller
public class HandleUserInfo {
	
	@Autowired
	private UserDaoI dao;
	
	@RequestMapping("checkuserstatus")
	void checkUserStatus(HttpServletRequest req, HttpServletResponse res){
		HttpSession ses = req.getSession();
		
		Map<String, Object> user = (Map<String, Object>)ses.getAttribute("user");
		String rs = user == null? "0" : "1";
		System.out.println(rs);
		try {
			res.getWriter().append(rs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("login")
	void doLogin(HttpServletRequest req, HttpServletResponse res){
		String uname = req.getParameter("username");
		String pwd = req.getParameter("password");
		
		System.out.println(uname+ pwd);
		HttpSession ses = req.getSession();
		List<Map<String, Object>> rs = dao.doVerify(uname, pwd);
		System.out.println(rs);
		if(rs.size() == 1){
			ses.setAttribute("user", rs.get(0));
			try {
				res.getWriter().append("1");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				res.getWriter().append("0");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}