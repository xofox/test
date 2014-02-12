package org.nbrc.mobile.helper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchResult<T> implements Serializable {
	private static final long serialVersionUID = 4721725531770086775L;
	
	private List<T> items;
	
	private int page;
	private int pagesize;
	private int total;
	private int totalpage;
	
	private Map<String,List<GroupInfo>>groups;
	
	public SearchResult(int page,int pagesize){
		this.page = page;this.pagesize=pagesize;
		//this.items = new ArrayList<T>();
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalpage() {
		if(this.pagesize==0){
			return 0;
		}
		this.totalpage = this.total / this.pagesize;
		if(this.total % this.pagesize>0)
			this.totalpage++;
		return this.totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	
	public int getStart(){
		return this.page * this.pagesize - this.pagesize;
	}
	/**
	 * @return the groups
	 */
	public Map<String,List<GroupInfo>> getGroups() {
		return groups;
	} 
	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Map<String,List<GroupInfo>> groups) {
		this.groups = groups;
	}
	

}
