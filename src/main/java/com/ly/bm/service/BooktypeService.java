package  com.ly.bm.service;

import com.ly.bm.vo.Booktype;
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
public class BooktypeService extends IdEntityService<Booktype> {

	public static String CACHE_NAME = "booktype";
    public static String CACHE_COUNT_KEY = "booktype_count";

    public List<Booktype> queryCache(Condition c,Page p)
    {
        List<Booktype> list_booktype = null;
        String cacheKey = "booktype_list_" + p.getPageCurrent();

        Cache cache = CacheManager.getInstance().getCache(CACHE_NAME);
        if(cache.get(cacheKey) == null)
        {
            list_booktype = this.query(c, p);
            cache.put(new Element(cacheKey, list_booktype));
        }else{
            list_booktype = (List<Booktype>)cache.get(cacheKey).getObjectValue();
        }
        return list_booktype;
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


