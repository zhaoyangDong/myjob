package cn.ssm.dto;

import java.util.List;

public class page {
	private int thispage;
	private int rowperpage;
	private int countrow;
	private int countpage;
	private int firstpage;
	private int lastpage;
	private int prepage;
	private int nextpage;
	private List<Film> list;
	private List<customer> customerlist;
	
	public List<customer> getCustomerlist() {
		return customerlist;
	}
	public void setCustomerlist(List<customer> customerlist) {
		this.customerlist = customerlist;
	}
	public int getThispage() {
		return thispage;
	}
	public void setThispage(int thispage) {
		this.thispage = thispage;
	}
	public int getRowperpage() {
		return rowperpage;
	}
	public void setRowperpage(int rowperpage) {
		this.rowperpage = rowperpage;
	}
	public int getCountrow() {
		return countrow;
	}
	public void setCountrow(int countrow) {
		this.countrow = countrow;
	}
	public int getCountpage() {
		return countpage;
	}
	public void setCountpage(int countpage) {
		this.countpage = countpage;
	}
	public int getFirstpage() {
		return firstpage;
	}
	public void setFirstpage(int firstpage) {
		this.firstpage = firstpage;
	}
	public int getLastpage() {
		return lastpage;
	}
	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}
	public int getPrepage() {
		return prepage;
	}
	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}
	public int getNextpage() {
		return nextpage;
	}
	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}
	public List<Film> getList() {
		return list;
	}
	public void setList(List<Film> list) {
		this.list = list;
	}
	
	

}
