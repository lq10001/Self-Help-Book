package  com.ly.bm.service;

import com.ly.bm.vo.Borrower;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;

import java.util.List;


@IocBean(fields = { "dao" })
public class BorrowerService extends IdEntityService<Borrower> {
}


