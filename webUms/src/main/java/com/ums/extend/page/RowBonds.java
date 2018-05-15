package com.ums.extend.page;

import org.apache.ibatis.session.RowBounds;

public class RowBonds extends RowBounds {
protected int total;

	public RowBonds(int offset, int limit) {
		super(offset,limit);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
