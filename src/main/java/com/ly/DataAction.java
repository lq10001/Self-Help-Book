package com.ly;

import com.ly.bm.service.BookService;
import com.ly.bm.service.BorrowbookService;
import com.ly.bm.service.BorrowerService;
import com.ly.bm.vo.Book;
import com.ly.bm.vo.Borrowbook;
import com.ly.bm.vo.Borrower;
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


    @At
    @Ok("json")
    public Long borrow(@Param("barcode")String barcode,
                      @Param("qrcode")String qrcode)
    {
        Book book = bookService.fetch(Cnd.where("barcode","=",barcode));
        if (book == null){
            return 0L;
        }

        Borrower borrower = borrowerService.fetch(Cnd.where("qrcode","=",qrcode));
        if (borrower == null)
        {
            return 0L;
        }

        Borrowbook bb = new Borrowbook();
        bb.setBookid(book.getId());
        bb.setBorrowerid(borrower.getId());
        borrowbookService.dao().insert(bb);

        return 1L;
    }
}
