package com.ly.bm.vo;

import java.util.Date;

import org.nutz.dao.entity.annotation.*;

@Table("borrowbook")
@View("borrowview")
public class Borrowbook{

	@Id
	@Column
	private Long id;

	@Column
	private String barcode;

	@Column
	private String qrcode;

	@Column
	private Date date1;

	@Column
	private Date date2;

	@Column
	private Long state;

    //----------view -------
    @Column
    @Readonly
    private String name;

    @Column
    @Readonly
    private String borrowername;

	@Column
	@Readonly
	private Long num;

	@Column
	@Readonly
	private Long borrownum;

	@Column
	@Readonly
	private Long booktypeid;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
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

	public String getBorrowername() {
		return borrowername;
	}

	public void setBorrowername(String borrowername) {
		this.borrowername = borrowername;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Long getBorrownum() {
		return borrownum;
	}

	public void setBorrownum(Long borrownum) {
		this.borrownum = borrownum;
	}

	public Long getBooktypeid() {
		return booktypeid;
	}

	public void setBooktypeid(Long booktypeid) {
		this.booktypeid = booktypeid;
	}
}


