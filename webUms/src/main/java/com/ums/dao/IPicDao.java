package com.ums.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ums.entity.Picture;

public interface IPicDao {
	public List<Picture> getPic();
	public void insert(@Param("pic")Picture pic);
}
