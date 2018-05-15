package com.ums.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ums.common.cache.Syscache;
import com.ums.dto.BaseDTO;

@Controller
public class FileController  {
	    /**
	     * 预览pdf文件
	     */
	    @RequestMapping(value="main/sys/previewPdfFile")
	    @ResponseBody
	    public BaseDTO previwePdfFile(HttpServletRequest request, HttpServletResponse response) {
	    	if(Syscache.getUserInfo("user") == null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	    	File file = new File("D:/user/80002200/桌面/新建文件夹/TEMPLATE/dataBase.pdf");
	    	BufferedInputStream bis = null;
	    	ServletOutputStream sop = null;
	    	   try {
				bis = new BufferedInputStream(new FileInputStream(file));
				response.reset();
	            response.setContentType("application/pdf;charset=utf-8");
				sop = response.getOutputStream(); 
				 response.setHeader("Content-Disposition", "attachment;filename=" + 
		                    new String(("指引手册.pdf").getBytes(), "iso-8859-1"));
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
					if (bis != null) {
						bis.close();
					}
					if (sop != null) {
						sop.close();
					} } catch (IOException e) {
						e.printStackTrace();
					}
			}
	    	   return null;
	    }
}
