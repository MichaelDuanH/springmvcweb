package com.ums.extend;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.ums.extend.page.RowBonds;


public class Page<T>  implements Serializable {

	/**
	 * 序列化版本标示
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前页码数
	 */
    protected int page;
    
    /**
     * 每页最大显示条数
     */
    protected int pageSize;
    
    /**
     * 总页数
     */
    protected int pageCount;
    
    /**
     * 总条数
     */
    protected int total;
    
    /**
     * 查询条件
     */
    protected T queryObj;
    
    /**
     * 结果集
     */
    protected List<T> data;

    /**
     * 获取分页对象
     * @return
     */
    public RowBonds getRowBounds(){
    	
    	return (RowBonds)new RowBonds((page-1)*pageSize, pageSize);
    }
    


	public Page(int page, int pageSize, T queryObj) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.queryObj = queryObj;
	}



	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}


	public T getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(T queryObj) {
		this.queryObj = queryObj;
	}

	public List<T> getData() {
		return data;
	}

	public int getPageCount() {
		return pageCount;
	}



	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}



	public void setTotal(RowBounds rowBounds) {
		this.total = ((RowBonds)rowBounds).getTotal();
		this.pageCount = (this.total % this.pageSize == 0) ? (this.total/this.pageSize) : ((this.total/this.pageSize)+1);
	}
	
	public void setData(List<T> data,RowBounds rowBounds) {
		this.data = data;
		this.total = ((RowBonds)rowBounds).getTotal();
		this.pageCount = (this.total % this.pageSize == 0) ? (this.total/this.pageSize) : ((this.total/this.pageSize)+1);
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
}
