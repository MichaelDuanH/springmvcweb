package com.ums.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ums.biz.IModelBiz;
import com.ums.dto.BaseDTO;
import com.ums.entity.Module;
@Controller
public class ModelController {
	
	@Autowired
	public IModelBiz modelBiz;
    //pModule就是那个顶级的父moduleId
	private void loadChildrens(Module pModule,List<Module> moduleAll){
		List<Module> childrens= new ArrayList<Module>();
		for (Module module : moduleAll) {//当moduleAll全部遍历完成之后，执行setChildren方法
			//当已经找到了第一个moduleId的时候，再根据这个moduleId与parentId相等，找到下面一个moduleId
			if (module.getParentId() == pModule.getModuleId()) {
				pModule.setState("closed");
				childrens.add(module);
				//注意这里的pModule 改变成了module，继续递归
				loadChildrens(module,moduleAll);
			}
		}
		pModule.setChildren(childrens);
		
	}
	
    @RequestMapping(value="main/module/getModelTree",method=RequestMethod.GET)
    @ResponseBody
    public BaseDTO getModelTree(HttpServletRequest request,
    		HttpServletResponse response,Integer parentId) throws IOException {
    	if(parentId==null) {
    		parentId = 2;
    	}
    	List<Module> moduleAll = this.modelBiz.getModuleList(parentId);
    	List<Module> rootModules = new ArrayList<Module>();
    	//首先根据parentId为空或者为0的条件 找出moduleId
    	for (Module module : moduleAll) {
    		if(module.getParentId() == 0) {
    			module.setState("closed");
    			rootModules.add(module);
    		}
		}
    	//rootModules 里面只有一个元素
    	for (Module module : rootModules) {
    		loadChildrens(module,moduleAll);
		}
    	
    	BaseDTO base = new BaseDTO();
    	base.setRows(JSON.toJSONString(rootModules));
        base.setCode("200");
        base.setTotal(rootModules.size());
		return base;
    }
}
