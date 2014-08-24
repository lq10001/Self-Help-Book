package com.ly.bm.action;

import com.ly.comm.Dwz;
import com.ly.comm.Page;
import com.ly.comm.ParseObj;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.filter.CheckSession;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import com.ly.bm.vo.Book;
import com.ly.bm.service.BookService;


@IocBean
@At("/book")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BookAction {

	private static final Log log = Logs.getLog(BookAction.class);
	
	@Inject
	private BookService bookService;

    @At("/")
    @Ok("beetl:/WEB-INF/bm/book_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Book book,
                      HttpServletRequest request){
        Cnd c = new ParseObj(book).getCnd();
        List<Book> list_m = bookService.query(c, p);
        p.setRecordCount(bookService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("book", book);
    }

    @At
    @Ok("beetl:/WEB-INF/bm/book.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("book", null);
        }else{
            request.setAttribute("book", bookService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Book book){
        Object rtnObject;
        if (book.getId() == null || book.getId() == 0) {
            rtnObject = bookService.dao().insert(book);
        }else{
            rtnObject = bookService.dao().updateIgnoreNull(book);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "book", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  bookService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "book", "");
    }

}
