package com.ly.bm.action;

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


import com.ly.bm.vo.Booktype;
import com.ly.bm.service.BooktypeService;


@IocBean
@At("/booktype")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BooktypeAction {

	private static final Log log = Logs.getLog(BooktypeAction.class);
	
	@Inject
	private BooktypeService booktypeService;

    @At("/")
    @Ok("beetl:/WEB-INF/bm/booktype_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Booktype booktype,
                      HttpServletRequest request){

        Cnd c = new ParseObj(booktype).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(booktypeService.listCount(c));
            request.setAttribute("list_obj", booktypeService.queryCache(c,p));
        }else{
            p.setRecordCount(booktypeService.count(c));
            request.setAttribute("list_obj", booktypeService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("booktype", booktype);
    }

    @At
    @Ok("beetl:/WEB-INF/bm/booktype.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("booktype", null);
        }else{

            Booktype booktype = booktypeService.fetch(id);
            if (action == 3)
            {
                //booktype.setName(null);
            }
            request.setAttribute("booktype", booktype);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Booktype booktype){
        Object rtnObject;
        if (booktype.getId() == null || booktype.getId() == 0) {
            rtnObject = booktypeService.dao().insert(booktype);
        }else{
            if (action == 3) {
                booktype.setId(null);
                rtnObject = booktypeService.dao().insert(booktype);
            }else{
                rtnObject = booktypeService.dao().updateIgnoreNull(booktype);
            }
        }
        CacheManager.getInstance().getCache(BooktypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_booktype", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  booktypeService.delete(id);
        CacheManager.getInstance().getCache(BooktypeService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_booktype",false);
    }

}
