package com.ums.biz;

import java.util.List;

import com.ums.entity.Module;

public interface IModelBiz {
	
	List<Module> getModuleList(int parentId);
}
