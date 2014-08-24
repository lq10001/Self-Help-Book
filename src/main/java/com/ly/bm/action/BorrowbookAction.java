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

import com.ly.bm.vo.Borrowbook;
import com.ly.bm.service.BorrowbookService;


@IocBean
@At("/borrowbook")
@Fail("json")
@Filters(@By(type=CheckSession.class, args={"username", "/WEB-INF/login.html"}))
public class BorrowbookAction {

	private static final Log log = Logs.getLog(BorrowbookAction.class);
	
	@Inject
	private BorrowbookService borrowbookService;

    @At("/")
    @Ok("beetl:/WEB-INF/bm/borrowbook_list.html")
    public void index(@Param("..")Page p,
                      @Param("..")Borrowbook borrowbook,
                      HttpServletRequest request){
        Cnd c = new ParseObj(borrowbook).getCnd();
        List<Borrowbook> list_m = borrowbookService.query(c, p);
        p.setRecordCount(borrowbookService.count(c));

        request.setAttribute("list_obj", list_m);
        request.setAttribute("page", p);
        request.setAttribute("borrowbook", borrowbook);
    }

    @At
    @Ok("beetl:/WEB-INF/bm/borrowbook.html")
    public void edit(@Param("id")Long id,
                      HttpServletRequest request){
        if(id == null || id == 0){
            request.setAttribute("borrowbook", null);
        }else{
            request.setAttribute("borrowbook", borrowbookService.fetch(id));
        }
    }

    @At
    @Ok("json")
    public Map<String,String> save( @Param("..")Borrowbook borrowbook){
        Object rtnObject;
        if (borrowbook.getId() == null || borrowbook.getId() == 0) {
            rtnObject = borrowbookService.dao().insert(borrowbook);
        }else{
            rtnObject = borrowbookService.dao().updateIgnoreNull(borrowbook);
        }
        return Dwz.rtnMap((rtnObject == null) ? false : true, "borrowbook", "closeCurrent");
    }

    @At
    @Ok("json")
    public Map<String,String> del(@Param("id")Long id)
    {
        int num =  borrowbookService.delete(id);
        return Dwz.rtnMap((num > 0) ? true : false , "borrowbook", "");
    }

}
