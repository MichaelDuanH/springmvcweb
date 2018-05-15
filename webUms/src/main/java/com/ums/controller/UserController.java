package com.ums.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ums.biz.IUserBiz;
import com.ums.common.util.CyptoUtils;
import com.ums.common.util.DateUtil;
import com.ums.common.util.POIUtils;
import com.ums.common.util.StringUtil;
import com.ums.common.util.SysConfigProperties;
import com.ums.dto.BaseDTO;
import com.ums.entity.User;
import com.ums.extend.Page;
@Controller
public class UserController  {
	
	    @Autowired
	    private IUserBiz userBiz;
	    
	    @RequestMapping(value="main/mainPage",method=RequestMethod.GET)
	    @ResponseBody	
	    public  ModelAndView inital(HttpServletRequest request,
	    		HttpServletResponse response,
	    		Integer total) throws IOException {
	    	HttpSession session = request.getSession();
	    	ModelAndView model = new ModelAndView();
	    	if(session.getAttribute("user")==null) {
	    		response.sendRedirect(request.getContextPath());
	    		model.addObject("user"," ");
	    	};
	    	model.addObject("user",session.getAttribute("user"));
	    	model.addObject("timeout",total);
	        return model;
	    }
	    String pageNum;
	    String rows;
	    public String getPageNum() {
			return pageNum;
		}
		public void setPageNum(String pageNum) {
			this.pageNum = pageNum;
		}
		public String getRows() {
			return rows;
		}
		public void setRows(String rows) {
			this.rows = rows;
		}
		@RequestMapping(value="main/user/queryUserPage",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO queryUserPage(HttpServletRequest request,
	    		HttpServletResponse response,
	    	    User user) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	pageNum = request.getParameter("page");
	    	rows = request.getParameter("rows");
	    	Page<User> page = new Page<User>(Integer.valueOf(pageNum), Integer.valueOf(rows), user);
	    	this.userBiz.allUsersPage(page);
	    	BaseDTO base = new BaseDTO();
	    	base.setRows(page.getData());
	    	base.setTotal(page.getTotal());
	    	base.setCode("200");
	        return base;
	    }
	    //更新用户密码
	    @RequestMapping(value="main/user/updateUserPass",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO updatePass(HttpServletRequest request,
	    		HttpServletResponse response,
	    	    User user) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	//检查新密码的长度
	    	if(user.getNewPass().length() <8) {
	    		return new BaseDTO("密码长度必须8位","500");
	    	}
	    	user.setNewPass(CyptoUtils.encode(CyptoUtils.ALGORITHM_DES, user.getNewPass()));
	    	this.userBiz.updateUser(user);
	        return new BaseDTO("密码更新成功,下次登录请使用新密码登录系统","200");
	    }
	    
	    @RequestMapping(value="main/user/saveUser",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO saveUser(HttpServletRequest request,
	    		HttpServletResponse response,
	    	    User user) {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	    	User u = userBiz.searchByUserCode(user.getUserCode());
	    	if(u != null) {
	    		return new BaseDTO("数据已经存在");
	    	} else {
	    		validateData(user);
		    	this.userBiz.saveUser(user);
	    	}
	    	
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("用户保存成功");
	    	base.setCode("200");
	        return base;
	    }
	    
	  //重新计算接口
	    @RequestMapping(value="main/user/reCalc",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO reCalc(HttpServletRequest request,
	    		HttpServletResponse response,
	    		@RequestParam("calc") String params) {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	   
		    this.userBiz.reCalc();
	    	
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("重算开始");
	    	base.setCode("200");
	        return base;
	    }
	    
	    @RequestMapping(value="main/user/updateUser",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO updateUser(HttpServletRequest request,
	    		HttpServletResponse response,
	    	    User user) {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	    
	    	User u = userBiz.searchByUserCode(user.getUserCode());
	    	if ( u == null) {
	    		return new BaseDTO("数据不存在");
	    	}
	    	//validateData(user);
	    	user.setUserName(u.getUserName());
	    	user.setPersonTypeCode(u.getPersonTypeCode()==null?StringUtil.getPersonTypeCode(u.getUserCode()):u.getPersonTypeCode());
	    	if(!StringUtil.isEmpty(user.getCommisDateStr())) {
	    		user.setCommisDate(DateUtil.parse(user.getCommisDateStr(), "yyyy-MM-dd"));
	    	}
	    	this.userBiz.updateUser(user);
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("用户更新成功");
	    	base.setCode("200");
	        return base;
	    }
	    
	    public  BaseDTO updateUnique(HttpServletRequest request,
	    		HttpServletResponse response,
	    	    User user) {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	    	/*if(this.userBiz.validateRepeat(user)) {
	    		return new BaseDTO("数据已经存在，请检查");
	    	}*/
	    	//validateData(user);
	    	user.setDeptCode(user.getDeptCode().toUpperCase());
	    	user.setCommisDate(DateUtil.parse(user.getCommisDateStr(), "yyyy-MM-dd"));
	    	this.userBiz.updateUnique(user);
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("用户更新成功");
	    	base.setCode("200");
	        return base;
	    }
	    
	    public void validateData(User user) {
	    	user.setDeptCode(user.getDeptCode().toUpperCase());
	    	user.setUserName(StringUtil.getEmpName(user.getUserCode()));
	    	user.setPersonTypeCode(StringUtil.getPersonTypeCode(user.getUserCode()));
	    }
	    
	    @RequestMapping(value="main/user/delUser",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO delUser(HttpServletRequest request,
	    		HttpServletResponse response,
	    		@RequestParam("id[]")String[] ids ) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user")==null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	for(int i = 0; i<ids.length;i++){
	    		this.userBiz.delUser(String.valueOf(ids[i]));
	    	}
	    	
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("用户删除成功");
	    	base.setCode("200");
	        return base;
	    }
	    
	    
	    @RequestMapping(value="main/user/batchDelUser",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO batchDelUser(HttpServletRequest request, HttpServletResponse response,
	    		@RequestParam("batch") String batch) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user") == null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	if(!StringUtil.isEmpty(batch)) {
	    		this.userBiz.deleteAll();
	    	}
	    	
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("数据清空成功");
	    	base.setCode("200");
	        return base;
	    }
	    
	    
	    
	    @RequestMapping(value="main/user/onProduceOrgData",method=RequestMethod.POST)
	    @ResponseBody
	    public  BaseDTO onProduceOrgData(HttpServletRequest request, HttpServletResponse response, @RequestParam("produce") String produce) throws IOException {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user") == null){
	    		response.sendRedirect(request.getContextPath());
	    	};
	    	List<User> us = new ArrayList<User>();
	    	if(!StringUtil.isEmpty(produce)) {
	    		for(int i = 0; i<100; i++) {
					User u = new User();
					us.add(u.createUser(u));
				}
	    		this.userBiz.addOrgBatch(us);
	    	}
	    	
	    	BaseDTO base = new BaseDTO();
	    	base.setRows("100条数据生成成功");
	    	base.setCode("200");
	        return base;
	    }
	    
	    
	    @RequestMapping(value="main/toLogout",method=RequestMethod.POST)
	    @ResponseBody
	    public BaseDTO toLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    	    User u = (User) request.getSession().getAttribute("user");
	    	    User us = this.userBiz.selectUserByName(u.getUserName());
	    	    userBiz.updateByUserName("",null,us.getUserName());
			    BaseDTO base = new BaseDTO();
			    base.setCode("exit");
			    return base;
	    }
	    
	    @RequestMapping(value="main/doLogout",method=RequestMethod.GET)
	    @ResponseBody
	    public BaseDTO doLogout(HttpServletRequest request,
	    		HttpServletResponse response) throws IOException {
	    		request.getSession().invalidate(); 
			    response.sendRedirect(request.getContextPath());
			    BaseDTO base = new BaseDTO();
			    base.setCode("exit");
			    base.setRows("退出登录");
			    return base;
	    }
	    
	    /**
	     * 导入用户模板
	     */
	    @RequestMapping(value="main/user/downLoadExcelTemplate")
	    @ResponseBody
	    public BaseDTO downLoadExcelTemplate(HttpServletRequest request, HttpServletResponse response) {
	    	HttpSession session = request.getSession();
	    	if(session.getAttribute("user") == null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	    	File file = new File("d:/user/TEMPLATE/UserTemplate.xls");
	    	BufferedInputStream bis = null;
	    	ServletOutputStream sop = null;
	    	   try {
				bis = new BufferedInputStream(new FileInputStream(file));
				response.reset();
	            response.setContentType("application/vnd.ms-excel;charset=utf-8");
				sop = response.getOutputStream(); 
				 response.setHeader("Content-Disposition", "attachment;filename=" + 
		                    new String(("用户信息导入模板.xls").getBytes(), "iso-8859-1"));
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
	    
	    
	    

	    
	    
	    @RequestMapping(value="main/user/importExcel")
	    @ResponseBody
	    public BaseDTO importExcel(@RequestParam("uploadExcelName") CommonsMultipartFile uploadExcel,  
	            HttpServletRequest request, HttpServletResponse response) {
	     	HttpSession session = request.getSession();
	    	if(session.getAttribute("user") == null){
	    		try {
					response.sendRedirect(request.getContextPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	};
	    	   // 第一个参数是文件名，第二个参数是提示信息m
	           Object[] strs = new Object[2];
	           List<User> errorList = new ArrayList<User>();
	    	   // 设置读取列数
	           int lastCellNum = 7;
	           // 读取上传文件
	            DiskFileItem fi = (DiskFileItem)uploadExcel.getFileItem();
	            POIUtils poi = new POIUtils(fi.getStoreLocation(),lastCellNum);
	           // 最后一行
	            int lastRowNum = poi.getLastRowNum();
	            if (lastRowNum < 1) {
	            	return new BaseDTO(SysConfigProperties.EXCEL_IMORT_FILE_NULL_DATA);
	            }
	            if (lastRowNum > SysConfigProperties.EXCEL_IMPORT_MAX_COUNT + 2) {
	            	return new BaseDTO("一次最多导入 5000 条记录！");
	            }
	   

	            // 记录错误的个数
	            int error = 0;
	            int success = 0;
	            
	            // 重复数据标示
	            Map<String, Integer> checkExistMap = new HashMap<String, Integer>((int) (lastRowNum * 1.5));
	            for (int i = 1; i <= lastRowNum; i++) {
	                Object[] objects = poi.getNewRowAt(i);
	                // 员工号码
	                String empCode = StringUtil.getExcelString(objects[0]);
	                if (!StringUtil.isEmpty(empCode)) {
	                    // 获得重复唯一标示
	                    String key = empCode;
	                    // 判断是否有重复的 ，如果有设置为2 没有则设置记录
	                    if (checkExistMap.containsKey(key)) {
	                        checkExistMap.put(key, 2);
	                    } else {
	                        checkExistMap.put(key, 1);
	                    }
	                }
	            }
	            
	            List<User> userList = new ArrayList<User>();
	            User u = null;
	            
	            // 添加数据
	            for (int i = 1; i <= lastRowNum; i++) {
	                Object[] objects = poi.getNewRowAt(i);
	                try {
	                    u = new User();
	                    // 网点
	                    u.setDeptCode(StringUtil.getExcelString(objects[0]));
	                    // 工号
	                    u.setUserCode(StringUtil.getExcelString(objects[1]));
	                    //姓名
	                    u.setUserName(StringUtil.getExcelString(objects[2]));
	                    // 是否转计提
	                    u.setIsCommis("是".equals(StringUtil.getExcelString(objects[3]))?"1":"否".equals(StringUtil.getExcelString(objects[3]))?"0":"1");
	                    //转计提日期
	                    u.setCommisDate(DateUtil.parse(StringUtil.getExcelString(objects[4]), "yyyy-MM-dd"));
	                    //人员类型
	                    u.setPersonTypeCode("类型1".equals(StringUtil.getExcelString(objects[5]))?"A":"类型2".equals(StringUtil.getExcelString(objects[5]))?"W":"A");
	                    u.setRemark(StringUtil.getExcelString(objects[6]));
	                    if (u.getDeptCode() == null) {
	                    	 throw new RuntimeException("网点不能为空");
	                    }
	                    if(u.getUserCode() == null) {
	                    	 throw new RuntimeException("工号不能为空");
	                    }
	                    
	                    if (u.getUserName() == null) {
	                    	 throw new RuntimeException("用户名不能为空");
	                    }
	                    
	                    if(u.getIsCommis() == null) {
	                    	 throw new RuntimeException("是否转计提不能为空");
	                    }
	                    String key = u.getUserCode();
	                    Integer checkValue = checkExistMap.get(key);
	                    if (checkValue != null && checkValue == 2) {
	                    	throw new RuntimeException("导入列表中，工号为" + u.getUserCode() + "数据重复");
	                    }
	                  
	                    if(u.getPersonTypeCode() == null) {
	                    	throw new RuntimeException("人员类型不能为空");
	                    }
	                    //检查是否重复
	                    User entity = userBiz.searchByUserCode(u.getUserCode());
	                    if(entity != null) {
	                    	u.setUserId(entity.getUserId());
	                    }
	                    userList.add(u);
	                   
	                    // 记录成功的条数
	                    success++;
	                } catch (Exception e) {
	                    // 复制数据，记录错误
	                    u.setErrorMsg(e.getMessage());
	                    errorList.add(u);
	                    error++;
	                }
	            }
	            
	            // 错误大于0输出错误文件
	            if (error > 0) {
	                strs[0] = errorList;
	                //strs[1]= StringUtil.connectsObject("导入失败：有", error, "条数据出现错误，请检查修改后全部重新导入！");
	            } else {
	                for (int i = 0; i < userList.size(); i++) {
	                    if (userList.get(i).getUserId() == null) {
	                    	this.userBiz.saveUser(userList.get(i));
	                    } else {
	                    	updateUnique(request,response,userList.get(i));
	                    }
	                }
	                strs[1] = StringUtil.connectsObject("导入成功，成功导入", success, "条。");
	            }
	            BaseDTO bd = new BaseDTO();
	            bd.setRows(strs[0] == null?strs[1]:strs[0]);
				return bd;
	    }
	    
	    @RequestMapping(value="main/user/exportUser")
	    @ResponseBody
	    public BaseDTO exportUser(HttpServletResponse response,HttpServletRequest request,@RequestParam("userName")String userName,@RequestParam("userCode")String userCode) throws IOException {
	     	 HttpSession session = request.getSession();
		    	 if(session.getAttribute("user") == null){
		    		try {
						response.sendRedirect(request.getContextPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	 };
	    	     List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
	    	     Map<String,Object> tempMap = new HashMap<String, Object>();
	             tempMap.put("sheetName", "员工基本资料");
	             listmap.add(tempMap);
//	             pageNum = request.getParameter("page");
//	             rows = request.getParameter("rows");
	             User u = new User();
	             u.setUserCode(userCode);
	             u.setUserName(userName);
	 	    	 Page<User> page = new Page<User>(Integer.valueOf(1), (userBiz.getTotalCount()==0)?1:userBiz.getTotalCount(), u);
	 	    	 this.userBiz.allUsersPage(page);
	 	    	 List<User> users = page.getData();
	 	    	 User us = null;
	 	        for (int j = 0; j < users.size(); j++) {
	 	        	us = users.get(j);
                    Map<String, Object> mapValue = new HashMap<String, Object>();
                    mapValue.put("deptCode", us.getDeptCode());
                    mapValue.put("userCode", us.getUserCode());
                    mapValue.put("userName", us.getUserName());
                    mapValue.put("isCommis", "0".equals(us.getIsCommis())?"否":"1".equals(us.getIsCommis())?"是":"否");
                    mapValue.put("commisDateStr", us.getCommisDateStr());
                    mapValue.put("personTypeCode", "A".equals(us.getPersonTypeCode())?"类型1":"类型2");
                    mapValue.put("remark",us.getRemark());
                    listmap.add(mapValue);
            }
	 	    // 列名
            String[] columnNames = {"网点代码", "工号", "姓名", 
                      "是否转计提", "转计提日期", "人员类型", "备注"};
            String[] keys = {"deptCode", "userCode", "userName", "isCommis",
                    "commisDateStr", "personTypeCode", "remark"};
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            StringUtil.createWorkBook(listmap, keys, columnNames).write(os);;
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try{
            	response.setHeader("Content-Disposition", "attachment;filename=" +
                new String(("员工基本资料.xls").getBytes(), "iso-8859-1"));
                 ServletOutputStream out = response.getOutputStream();
                 bis = new BufferedInputStream(is);
                 bos = new BufferedOutputStream(out);
                 byte[] buff = new byte[2048];
                 int bytesRead;
                 // Simple read/write loop.
                 while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                         bos.write(buff, 0, bytesRead);
                 }
            }catch(Exception e) {
            	e.printStackTrace();
            }finally{
            	 if (bis != null) {
                     try {
                             bis.close();
                     } 
                     catch (IOException e) {
                             e.printStackTrace();
                     }
             }
             if (bos != null) {
                     try {
                             bos.close();
                     } 
                     catch (IOException e) {
                             e.printStackTrace();
                     }
                }
            }
			return new BaseDTO("导出成功","200");
	  }
}
