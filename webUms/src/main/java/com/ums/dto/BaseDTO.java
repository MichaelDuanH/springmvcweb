package com.ums.dto;

import java.io.Serializable;

public class BaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code;
	public Object rows;
	public int total;
	public BaseDTO() {
		
	}
	public BaseDTO(Object rows){
		this.rows = rows;
	}
	
	public BaseDTO(Object rows,String code) {
		this.rows = rows;
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
