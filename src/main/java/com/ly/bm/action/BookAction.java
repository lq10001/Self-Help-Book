package com.ly.bm.action;

import com.ly.bm.service.BooktypeService;
import com.ly.comm.Bjui;
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

import net.sf.ehcache.CacheManager;


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

    @Inject
    private BooktypeService booktypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/bm/book_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Book book,
                      HttpServletRequest request){

        Cnd c = new ParseObj(book).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(bookService.listCount(c));
            request.setAttribute("list_obj", bookService.queryCache(c,p));
        }else{
            p.setRecordCount(bookService.count(c));
            request.setAttribute("list_obj", bookService.query(c,p));
        }

        request.setAttribute("booktypeList",booktypeService.queryCache(null,new Page()));
        request.setAttribute("page", p);
        request.setAttribute("book", book);
    }

    @At
    @Ok("beetl:/WEB-INF/bm/book.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("book", null);
        }else{

            Book book = bookService.fetch(id);
            request.setAttribute("book", book);
        }
        request.setAttribute("booktypeList",booktypeService.queryCache(null,new Page()));
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Book book){
        Object rtnObject;
        if (book.getId() == null || book.getId() == 0) {
            rtnObject = bookService.dao().insert(book);
        }else{
            if (action == 3) {
                book.setId(null);
                rtnObject = bookService.dao().insert(book);
            }else{
                rtnObject = bookService.dao().updateIgnoreNull(book);
            }
        }
        CacheManager.getInstance().getCache(BookService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_book", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  bookService.delete(id);
        CacheManager.getInstance().getCache(BookService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_book",false);
    }

}
