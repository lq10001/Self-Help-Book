package com.ly;

import com.alibaba.fastjson.JSON;
import com.ly.bm.service.BookService;
import com.ly.bm.service.BorrowbookService;
import com.ly.bm.service.BorrowerService;
import com.ly.bm.service.TestService;
import com.ly.bm.vo.Book;
import com.ly.bm.vo.Borrowbook;
import com.ly.bm.vo.Borrower;
import com.ly.bm.vo.Test;
import com.ly.comm.Page;
import com.ly.comm.Page2;
import com.ly.comm.ParseObj;
import com.ly.sys.service.InfoService;
import com.ly.sys.vo.User;
import com.ly.util.EnDeCode;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IocBean
@At("/data")
@Fail("json")
public class DataAction {
	
	private static final Log log = Logs.getLog(DataAction.class);

    @Inject
    private BorrowbookService borrowbookService;

    @Inject
    private BookService bookService;

    @Inject
    private BorrowerService borrowerService;

    @Inject
    private TestService testService;

    @At("/")
    @Ok("json")
    public Map bookList(@Param("..")Page p,
                      @Param("..")Test test,
                      HttpServletRequest request){
        Cnd c = new ParseObj(test).getCnd();
        List<Test> list_m = testService.query(c, p);
        p.setRecordCount(testService.count(c));

        Page2 p2 = new Page2();
        p2.setTotal(100);
        p2.setCurrent(1);

        Map map = new HashMap();
        map.put("data", list_m);
        map.put("page",p2);

        map.put("success",true);
        return map;

    }


    @At
    @Ok("json")
    public Long borrow(@Param("barcode")String barcode,
                      @Param("qrcode")String qrcode)
    {
        Book book = bookService.fetch(Cnd.where("barcode","=",barcode));
        if (book == null){  //没有这本书
            return 0L;
        }

        if (book.getNum() == book.getBorrownum())
        {
            return 2L;  //这本书已经被借
        }


        Borrower borrower = borrowerService.fetch(Cnd.where("qrcode","=",qrcode));
        if (borrower == null) //没有这个用户
        {
            return 1L;
        }

        /*
        Borrowbook borrowbook = borrowbookService.fetch(Cnd.where("barcode","=",barcode).and("state","=","1"));
        if (borrowbook != null)     //这本书已经被借
        {
            return 2L;
        }
        */

        List<Borrowbook> borrowbookList = borrowbookService.query(Cnd.where("qrcode","=",qrcode).and("state","=","1"),null);
        if (borrowbookList.size() > 0)  //只能借一本
        {
            return 3L;
        }

        Borrowbook bb = new Borrowbook();
        bb.setQrcode(qrcode);
        bb.setBarcode(barcode);
        bb.setState(1L);
        bb.setDate1(new Date());
        borrowbookService.dao().insert(bb);

        book.setBorrownum(book.getBorrownum() + 1);
        bookService.dao().update(book);

        return 10L;
    }

    @At
    @Ok("json")
    public Long returnBook(@Param("barcode")String barcode,
                           @Param("qrcode")String qrcode)
    {
        Book book = bookService.fetch(Cnd.where("barcode","=",barcode));
        if (book == null){  //没有书
            return 0L;
        }

        Borrower borrower = borrowerService.fetch(Cnd.where("qrcode","=",qrcode));
        if (borrower == null) //没有这个用户
        {
            return 2L;
        }

        Borrowbook borrowbook = borrowbookService.fetch(Cnd.where("barcode","=",barcode).and("qrcode","=",qrcode).and("state","=","1") );
        if (borrowbook == null)    //书没有借
        {
            return 1L;
        }

        borrowbook.setDate2(new Date());
        borrowbook.setState(2L);
        borrowbookService.dao().update(borrowbook);

        book.setBorrownum(book.getBorrownum() - 1);
        bookService.dao().update(book);

        return 10L;
    }


    @At
    @Ok("json")
    public String getBookName(@Param("barcode")String barcode)
    {
        Book book = bookService.fetch(Cnd.where("barcode","=",barcode));
        if (book == null){
            return "0";
        }
        return book.getName();
    }

}
