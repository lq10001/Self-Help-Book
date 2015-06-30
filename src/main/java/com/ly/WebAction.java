package com.ly;

import com.ly.bm.service.BookService;
import com.ly.bm.service.BooktypeService;
import com.ly.bm.service.BorrowbookService;
import com.ly.bm.vo.Borrowbook;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import com.ly.sys.service.InfoService;
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
@At("/web")
@Fail("json")
public class WebAction {
	
	private static final Log log = Logs.getLog(WebAction.class);

    @Inject
    private InfoService infoService;

    @Inject
    private BorrowbookService borrowbookService;

    @Inject
    private BookService bookService;

    @Inject
    private BooktypeService booktypeService;


    @At("/")
    @Ok("beetl:/WEB-INF/web/search.html")
    public void index(@Param("..")Page p,
                      @Param("..")Borrowbook borrowbook,
                      HttpServletRequest request){
        if (borrowbook.getName() != null && borrowbook.getName().trim() == "")
        {
            borrowbook.setName(null);
        }

        Cnd c = new ParseObj(borrowbook).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(bookService.listCount(c));
            request.setAttribute("list_obj", bookService.queryCache(Cnd.NEW().desc("adddate"),p));
        }else{
            p.setRecordCount(bookService.count(c));
            request.setAttribute("list_obj", bookService.query(c.desc("adddate"), p));
        }
//            p.setRecordCount(borrowbookService.count(c));
//            request.setAttribute("list_obj", borrowbookService.query(c.desc("date1"),p));

        request.setAttribute("booktypeList",booktypeService.queryCache(null,new Page()));
        request.setAttribute("page", p);
        request.setAttribute("borrowbook", borrowbook);
    }

    @At
    @Ok("beetl:/WEB-INF/web/borrower.html")
    public void borrower(@Param("..")Borrowbook borrowbook,
                      HttpServletRequest request){
        Cnd c = new ParseObj(borrowbook).getCnd();
        if (c == null || c.equals(""))
        {
            request.setAttribute("list_obj", null);
        }else{
            request.setAttribute("list_obj", borrowbookService.query(c.desc("date1"), null));
        }

    }


}
