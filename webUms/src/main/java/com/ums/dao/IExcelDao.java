package com.ums.dao;

import java.util.List;

import com.ums.entity.ExcelEntityTest;

public interface IExcelDao {

	int saveFatherData(List<ExcelEntityTest> datas);
	
	int saveChildData(List<ExcelEntityTest> datas);
	
	int saveChildDataPhone(List<ExcelEntityTest> datas);


	
}
