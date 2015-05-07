package  com.ly.bm.service;

import com.ly.bm.vo.Borrower;
import org.nutz.dao.Condition;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.nutz.dao.Cnd;
import com.ly.comm.Page;

import java.util.List;


@IocBean(fields = { "dao" })
public class BorrowerService extends IdEntityService<Borrower> {

	public static String CACHE_NAME = "borrower";
    public static String CACHE_COUNT_KEY = "borrower_count";

    public List<Borrower> queryCache(Condition c,Page p)
    {
        List<Borrower> list_borrower = null;
        String cacheKey = "borrower_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_borrower = this.query(c, p);
            cache.put(new Element(cacheKey, list_borrower));
        }else{
            list_borrower = (List<Borrower>)cache.get(cacheKey).getObjectValue();
        }
        return list_borrower;
    }

    public int listCount(Condition c)
    {
        Long num = 0L;
        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(CACHE_COUNT_KEY) == null)
        {
            num = Long.valueOf(this.count(c));
            cache.put(new Element(CACHE_COUNT_KEY, num));
        }else{
            num = (Long)cache.get(CACHE_COUNT_KEY).getObjectValue();
        }
        return num.intValue();
    }



}


