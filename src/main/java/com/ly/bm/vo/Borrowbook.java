package com.ly.bm.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("borrowbook")
public class Borrowbook{

	@Id
	@Column
	private Long id;

	@Column
	private Long bookid;

	@Column
	private Long borrowerid;

	@Column
	private Date date1;

	@Column
	private Date date2;

	@Column
	private Long state;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookid() {
		return bookid;
	}

	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}

	public Long getBorrowerid() {
		return borrowerid;
	}

	public void setBorrowerid(Long borrowerid) {
		this.borrowerid = borrowerid;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public Date getDate2() {
		return date2;
	}

	public void setDate2(Date date2) {
		this.date2 = date2;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}
}
