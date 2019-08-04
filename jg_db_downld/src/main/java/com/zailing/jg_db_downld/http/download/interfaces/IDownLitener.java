package com.zailing.jg_db_downld.http.download.interfaces;


import com.zailing.jg_db_downld.http.interfaces.IHttpListener;
import com.zailing.jg_db_downld.http.interfaces.IHttpService;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public interface IDownLitener  extends IHttpListener {

    void setHttpServive(IHttpService httpServive);


    void  setCancleCalle();


    void  setPuaseCallble();

}
