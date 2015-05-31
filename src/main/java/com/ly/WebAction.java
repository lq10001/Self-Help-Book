package com.ly;

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


    @At("/")
    @Ok("beetl:/WEB-INF/web/search.html")
    public void index(@Param("..")Page p,
                      @Param("..")Borrowbook borrowbook,
                      HttpServletRequest request){

        Cnd c = new ParseObj(borrowbook).getCnd();
        if (c == null || c.equals(""))
        {
            p.setRecordCount(borrowbookService.listCount(c));
            request.setAttribute("list_obj", borrowbookService.queryCache(Cnd.NEW().desc("date1"),p));
        }else{
            p.setRecordCount(borrowbookService.count(c));
            request.setAttribute("list_obj", borrowbookService.query(c.desc("date1"),p));
        }

        request.setAttribute("page", p);
        request.setAttribute("borrowbook", borrowbook);
    }
}
