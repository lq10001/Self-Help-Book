package com.ly.bm.vo;

import java.util.Date;

import org.nutz.dao.entity.annotation.*;

@Table("book")
public class Book{

	@Id
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private String memo;

	@Column
	private String barcode;

	@Column
	private Date adddate;


	@Column
	private Long borrownum;

	@Column
	private Long num;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public Long getBorrownum() {
		return borrownum;
	}

	public void setBorrownum(Long borrownum) {
		this.borrownum = borrownum;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}


	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
}
