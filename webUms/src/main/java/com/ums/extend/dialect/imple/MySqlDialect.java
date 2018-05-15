package com.ums.extend.dialect.imple;

import com.ums.common.util.StringUtil;
import com.ums.extend.dialect.SQLdailect;

public class MySqlDialect implements SQLdailect {
public String getTotalSQL(final String sql) {
		
		return StringUtil.append("SELECT COUNT(1) AS TOTAL FROM (",sql,") T");
	}

	public String getPageSQL(final String sql,int offset,int limit) {
		
		return StringUtil.append(sql , " LIMIT " , offset , "," , limit);
	}
}
