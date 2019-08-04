package com.zailing.jg_db_downld.http.download;

import android.util.Log;
import android.widget.ListView;

import com.zailing.jg_db_downld.http.interfaces.IHttpListener;
import com.zailing.jg_db_downld.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FilDownHttpService implements IHttpService {

//    URLConnection urlConnection;
private static final String TAG = "dongnao";
    /**
     * 即将添加到请求头的信息
     */
    private Map<String ,String> headerMap= Collections.synchronizedMap(new HashMap<String ,String>());
    /**
     * 含有请求处理的 接口
     */
    private IHttpListener httpListener;

    private HttpClient httpClient=new DefaultHttpClient();
    private HttpGet httpGet;
    private String url;

    private byte[] requestDate;
    /**
     * httpClient获取网络的回调
     */
    private  HttpRespnceHandler httpRespnceHandler=new HttpRespnceHandler();
    /**
     * 增加方法
     */
    private AtomicBoolean pause=new AtomicBoolean(false);

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {

        httpGet = new HttpGet(url);
        constructHeader();
        try {
            httpClient.execute(httpGet,httpRespnceHandler);
        } catch (IOException e) {
            httpListener.onFail();
        }
    }

    private void constructHeader() {
        Iterator iterator = headerMap.keySet().iterator();
        while (iterator.hasNext()){
            String key = (String)iterator.next();
            String value = headerMap.get(key);
            Log.i(TAG," 请求头信息,key "+key+" value "+value);
            httpGet.addHeader(key,value);
        }
    }

    @Override
    public Map<String, String> getHttpHeadMap() {
        return headerMap;
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestDate = requestData;
    }

    @Override
    public void pause() {

    }



    @Override
    public boolean cancle() {
        return false;
    }

    @Override
    public boolean isCancle() {
        return false;
    }

    @Override
    public boolean isPause() {
        return false;
    }

    private class HttpRespnceHandler extends BasicResponseHandler
    {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            //响应吗
            int code=response.getStatusLine().getStatusCode();
            if(code==200||206==code)
            {
                httpListener.onSuccess(response.getEntity());
            }else
            {
                ListView list;
                httpListener.onFail();
            }
            return null;
        }
    }
}
