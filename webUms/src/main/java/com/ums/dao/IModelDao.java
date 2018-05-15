package com.ums.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ums.entity.Module;

public interface IModelDao {
	List<Module> getModuleList(@Param("parentId") int parentId);
}
