package com.ums.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ums.dao.IPicDao;
import com.ums.dto.BaseDTO;
import com.ums.entity.Picture;

@Controller
public class PicsController  {
	
	    @Autowired
	    private IPicDao picDao;
	    
	    @RequestMapping(value="main/pic/showPic",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO showPic(HttpServletRequest request,
	    		HttpServletResponse response) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	List<Picture> pics = this.picDao.getPic();
	    	Picture pic = pics.get(pics.size()-1);
	    	BaseDTO base = new BaseDTO();
	    	base.setRows(pic.getPicName());
	    	base.setCode(pic.getUrl());
	    	base.setTotal(200);
	        return base;
	    }
	    
	    @RequestMapping(value="main/pic/showDoc",method=RequestMethod.GET)
	    @ResponseBody
	    public BaseDTO showDoc(HttpServletRequest request,
	    		HttpServletResponse response) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	File file = new File("F:/系统操作指引.doc");
	    	BufferedInputStream bis = null;
	    	ServletOutputStream sop = null;
	    	   try {
				bis = new BufferedInputStream(new FileInputStream(file));
				response.reset();
	            response.setContentType("application/MS-word;charset=utf-8");
				sop = response.getOutputStream(); 
				 response.setHeader("Content-Disposition", "attachment;filename=" + 
		                    new String(("系统操作指引.doc").getBytes(), "iso-8859-1"));
				byte[] b = new byte[1024];
				int i = 0;
					while ((i = bis.read(b)) != -1) {
					    sop.write(b, 0, i);
					}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
				if(bis != null) {
					bis.close();
				}
				if(sop != null) {
					sop.close();
				} } catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
	    }
}
