package com.ly.bm.service;

import com.ly.bm.vo.Book;
import com.ly.bm.vo.Test;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.service.IdEntityService;


@IocBean(fields = { "dao" })
public class TestService extends IdEntityService<Test> {
}


