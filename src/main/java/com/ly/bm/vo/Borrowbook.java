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
    private String bookname;

    @Column
    @Readonly
    private String name;



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


    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


