package org.nbrc.mobile.helper;

import java.io.Serializable;
import java.util.List;

public class Pagenation<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int total;
	private List<T> rows;
	private int page;
	private int tp;
	private int pagesize;
	
	public Pagenation(int page,int limit){
		this.page = page;
		this.pagesize = limit;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTp() {
		if(this.pagesize<=0) return 0;
		this.tp = this.total/this.pagesize;
		if(this.total % this.pagesize>0) this.tp++;
		return this.tp;
	}
	public void setTp(int tp) {
		this.tp = tp;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int limit) {
		this.pagesize = limit;
	}
	
	

}
