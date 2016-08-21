package com.ly.comm;

import org.nutz.dao.pager.Pager;

public class Page2 extends Pager {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 232233332233333L;


	private int total;
	
	private int current;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}
}
