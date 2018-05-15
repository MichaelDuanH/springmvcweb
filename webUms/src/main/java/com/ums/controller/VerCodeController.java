package com.ums.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ums.biz.IUserBiz;
import com.ums.common.cache.Syscache;
import com.ums.common.util.ImageUtil;
import com.ums.common.util.StringUtil;
import com.ums.dto.BaseDTO;
import com.ums.entity.User;
@Controller
@RequestMapping("/")
public class VerCodeController {
	
	@Autowired
	public IUserBiz userBiz;

	@RequestMapping(value ="index", method=RequestMethod.GET)
    @ResponseBody
    public ModelAndView index(ModelAndView model,HttpServletRequest request,
    	   HttpServletResponse response) {
		   User u = (User) request.getSession().getAttribute("user");
		   if(u != null) {
			   userBiz.updateByUserName("", null,u.getUserName());  
		   }
    	 return model;
    }
	

    
    @RequestMapping(value="imageCode",method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public void getImageCode(String verCode,HttpServletRequest request,HttpServletResponse response) throws IOException {
    	    Map<String, BufferedImage> map = 
    			ImageUtil.createImage();
    		String code = map.keySet().iterator().next();
    		//request.getSession().setAttribute("imageCode", code);
    		Syscache.setValidCode("imageCode", code);
    		BufferedImage image = map.get(code);
    		ImageIO.write(image , "jpg", response.getOutputStream());	
    }
    
    
    
    //校验验证码
    @RequestMapping(value ="validateCode", method=RequestMethod.POST)
    @ResponseBody
    public  BaseDTO validateCode(@RequestBody User param,HttpServletRequest request,
    		HttpServletResponse response) {
//    	if(StringUtils.isEmpty(param.getCode())) {
//    		return new BaseDTO();
//    	}
    	//获取session 验证码
    	String imageCode = (String) request.getSession().getAttribute("imageCode");
    	
//    	if(!imageCode.equalsIgnoreCase(param.getCode())) {
//    		return new BaseDTO("验证码错误","validaError");
//    	}
    	return new BaseDTO("验证正确","validate200");
    }
    
//    //校验手机号码
//    @RequestMapping(value ="validateCellCode", method=RequestMethod.POST)
//    @ResponseBody
//    public  BaseDTO validateCellCode(@RequestBody UserDTO param,HttpServletRequest request,
//    		HttpServletResponse response) {
////    	if(StringUtils.isEmpty(param.getCellCode())) {
////    		return new BaseDTO();
////    	}
//    	
//    	return new BaseDTO("验证正确","validate200");
//    }
    
    // 获取验证码字符串接口
    @RequestMapping(value ="verCode", method=RequestMethod.GET)
    @ResponseBody
    public String verCode(HttpServletRequest request,
    		HttpServletResponse response) {
    	Map<String, BufferedImage> map = 
    			ImageUtil.createImage();
    		String code = map.keySet().iterator().next();
    		return code;
    }
    
 // 获取ip地址
    @RequestMapping(value ="getIpAdr", method=RequestMethod.GET)
    @ResponseBody
    public String getIpAdr(HttpServletRequest request,
    		HttpServletResponse response) {
    		return StringUtil.getIpAdress();
    }
    
}
