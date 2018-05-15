package com.ums.biz.imp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ums.biz.IModelBiz;
import com.ums.dao.IModelDao;
import com.ums.entity.Module;
@Service
public class ModelBiz implements IModelBiz {

	@Autowired
	IModelDao modelDao;
	
	@Override
	public List<Module> getModuleList(int parentId) {
		// TODO Auto-generated method stub
		return modelDao.getModuleList(parentId);
	}	
   
}
