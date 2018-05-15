package com.ums.extend.dialect;

public interface SQLdailect {
	
    public String getTotalSQL(String sql);

	public String getPageSQL(String sql,int offset,int limit);
}
