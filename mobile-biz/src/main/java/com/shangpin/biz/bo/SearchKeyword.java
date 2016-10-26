package com.shangpin.biz.bo;

import java.io.Serializable;
import java.util.List;

public class SearchKeyword implements Serializable{

	/**
	 * 搜索热词
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private List<RefContent> list;//促销消息，现在走通用规则
	public List<RefContent> getList() {
		return list;
	}
	public void setList(List<RefContent> list) {
		this.list = list;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	 
  
	
}
