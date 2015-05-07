package  com.ly.bm.service;

import com.ly.bm.vo.Borrowbook;
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
public class BorrowbookService extends IdEntityService<Borrowbook> {

	public static String CACHE_NAME = "borrowbook";
    public static String CACHE_COUNT_KEY = "borrowbook_count";

    public List<Borrowbook> queryCache(Condition c,Page p)
    {
        List<Borrowbook> list_borrowbook = null;
        String cacheKey = "borrowbook_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_borrowbook = this.query(c, p);
            cache.put(new Element(cacheKey, list_borrowbook));
        }else{
            list_borrowbook = (List<Borrowbook>)cache.get(cacheKey).getObjectValue();
        }
        return list_borrowbook;
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


