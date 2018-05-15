package com.ums.controller;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ums.biz.IUserBiz;
import com.ums.common.cache.Syscache;
import com.ums.common.util.CyptoUtils;
import com.ums.common.util.DateUtil;
import com.ums.common.util.StringUtil;
import com.ums.dto.BaseDTO;
import com.ums.entity.User;
@Controller
public class LoginController {
	@Autowired
	public IUserBiz userBiz;
	
	
    @RequestMapping(value="login/doLogin",method=RequestMethod.POST,consumes = "application/json; charset=utf-8")
    @ResponseBody
    public  BaseDTO doLogin(@RequestBody User param, 
    		                             HttpServletRequest request,
    		                             HttpServletResponse response) {
	       
		  if (StringUtil.isEmpty(param.getUserName())) {
			  return new BaseDTO("用户名不能为空","emptyUserName");
		  }
		  
		  if (StringUtil.isEmpty(param.getPassWord())) {
			  return new BaseDTO("密码不能为空","emptyPwd");
		  }
		  User user = null;
		  try {
			  user = userBiz.selectUserByName(param.getUserName());
		  } catch (Exception e){
			  e.printStackTrace();
			  return new BaseDTO("sql查询错误","serverErr");
		  }
		
		  if (user == null) {
			  return new BaseDTO("用户不存在","userNotExist"); 
		  }
		  user.setPas(CyptoUtils.decode(CyptoUtils.ALGORITHM_DES, user.getPas()));
		  if (!param.getPassWord().equals(user.getPas())) {
			  return new BaseDTO("密码错误","pwdErr"); 
		  }
		  
		
		  if (!param.getInputVerCode().equalsIgnoreCase((String)Syscache.getValidCode("imageCode"))){
			  return new BaseDTO("验证码输入错误","validErr"); 
		  }
		  user.setCreateTm(DateUtil.dateToTimeStam(new Date()));
		  if (param.getLoginId()!=null) {
			  user.setLoginId(param.getLoginId());
		  }
		  //session
		  request.getSession().setAttribute("user", user);
		  //用于在其他controller获取用户信息
		  Syscache.setUserInfo("user", user);
	      HttpSession session = request.getSession();
	      int timeout = session.getMaxInactiveInterval();
	      //记录登陆次数
	      user.setLogTimes(user.getLogTimes().intValue() + 1);
	      userBiz.updateByUserName(param.getLoginId(),user.getLogTimes(),user.getUserName());
		  BaseDTO base = new BaseDTO();
		  base.setTotal(timeout);
		  base.setRows("登陆成功");
		  base.setCode("200");
		  return base;
    }
    //检查登录
    @RequestMapping(value="login/check/doCheckLogin",method=RequestMethod.POST)
    @ResponseBody
    public  BaseDTO checkLogin(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("checkLogin") String checkLogin 
    		) {
    	// && request.getSession(false) == null
    	BaseDTO base = new BaseDTO();
    	request.getSession().setAttribute("user",null);
     	if("true".equals(checkLogin)) {
     		base.setRows("0");
     	}
		return base;
    }
    
    //检查单点
    @RequestMapping(value="login/check/doOneLogin",method=RequestMethod.POST)
    @ResponseBody
    public  BaseDTO checkOneLogin(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam("checkOneLogin") String checkOneLogin) {
    	User u = (User) request.getSession().getAttribute("user");
    	User us = this.userBiz.selectUserByName(u == null ?"":u.getUserName());
    	BaseDTO base = new BaseDTO();
    	if(u !=null && us != null 
    			&& "true".equals(checkOneLogin) 
    			&& !StringUtil.isEmpty(u.getLoginId()) 
    			&& !StringUtil.isEmpty(us.getLoginId()) 
    			&& !u.getLoginId().equals(us.getLoginId())) {
    		base.setRows("0");
    	}
		return base;
    }
}
