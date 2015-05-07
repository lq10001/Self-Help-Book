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


import com.ly.bm.vo.Borrower;
import com.ly.bm.service.BorrowerService;


@IocBean
@At("/borrower")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BorrowerAction {

	private static final Log log = Logs.getLog(BorrowerAction.class);
	
	@Inject
	private BorrowerService borrowerService;

    @At("/")
    @Ok("beetl:/WEB-INF/bm/borrower_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Borrower borrower,
                      HttpServletRequest request){

        Cnd c = new ParseObj(borrower).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(borrowerService.listCount(c));
            request.setAttribute("list_obj", borrowerService.queryCache(c,p));
        }else{
            p.setRecordCount(borrowerService.count(c));
            request.setAttribute("list_obj", borrowerService.query(c,p));
        }

        request.setAttribute("page", p);
        request.setAttribute("borrower", borrower);
    }

    @At
    @Ok("beetl:/WEB-INF/bm/borrower.html")
    public void edit(@Param("action")int action,
                     @Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("borrower", null);
        }else{

            Borrower borrower = borrowerService.fetch(id);
            request.setAttribute("borrower", borrower);
        }
        request.setAttribute("action", action);
    }

    @At
    @Ok("json")
    public Map<String,String> save(@Param("action")int action,
                                @Param("..")Borrower borrower){
        Object rtnObject;
        if (borrower.getId() == null || borrower.getId() == 0) {
            rtnObject = borrowerService.dao().insert(borrower);
        }else{
            if (action == 3) {
                borrower.setId(null);
                rtnObject = borrowerService.dao().insert(borrower);
            }else{
                rtnObject = borrowerService.dao().updateIgnoreNull(borrower);
            }
        }
        CacheManager.getInstance().getCache(BorrowerService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((rtnObject == null) ? false : true, "tab_borrower", true);

    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  borrowerService.delete(id);
        CacheManager.getInstance().getCache(BorrowerService.CACHE_NAME).removeAll();
        return Bjui.rtnMap((num > 0) ? true : false , "tab_borrower",false);
    }

}
