package com.ly;


import com.ly.comm.Page;

public class CBData {

    private static CBData uniqueInstance = null;

//    private List<Os> osList;


    private CBData() {
    }

    public static CBData getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CBData();
        }
        return uniqueInstance;
    }

    public void init()
    {
        Page p = new Page();

//        setOsList(AppContext.ioc.get(OsService.class).queryCache(null, p));


    }

}
