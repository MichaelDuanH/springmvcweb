package com.ums.entity;

import java.io.Serializable;
import java.util.List;

public class Module implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int moduleId;
	private int parentId;
	private String text;
	private String state;
	private String actionUrl;
	private List<Module> children;
	
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public List<Module> getChildren() {
		return children;
	}
	public void setChildren(List<Module> children) {
		this.children = children;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
}
