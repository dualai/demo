package com.zailing.jg_db_downld.http.download;

import android.os.Environment;
import android.util.Log;

import com.zailing.jg_db_downld.http.HttpTask;
import com.zailing.jg_db_downld.http.RequestHodler;
import com.zailing.jg_db_downld.http.ThreadPoolManager;
import com.zailing.jg_db_downld.http.download.interfaces.IDownloadServiceCallable;
import com.zailing.jg_db_downld.http.interfaces.IHttpListener;
import com.zailing.jg_db_downld.http.interfaces.IHttpService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class DownFileManager implements IDownloadServiceCallable {

    private static final String TAG ="dongnao" ;
    private byte[] lock =new byte[0];


    public void down(String url){

        String[] preFix=url.split("/");
        String displayName=preFix[preFix.length-1];
        File file = new File(Environment.getExternalStorageDirectory(),displayName);

        //实例化downloadItem
        DownloadItemInfo downloadItemInfo = new DownloadItemInfo(url,file.getAbsolutePath());
        downloadItemInfo.setUrl(url);

        synchronized (lock){

            RequestHodler requestHodler = new RequestHodler();
            //设置下载的策略
            IHttpService httpService = new FilDownHttpService();

            //得到请求头的参数 map
            Map<String,String> map = httpService.getHttpHeadMap();

            /**
             * 处理结果的策略;
             */
            IHttpListener httpListener = new DownLoadListener(downloadItemInfo,this,httpService);

            requestHodler.setHttpListener(httpListener);
            requestHodler.setHttpService(httpService);
            /**
             *  bug  url
             */
            requestHodler.setUrl(downloadItemInfo.getUrl());
            HttpTask httpTask=new HttpTask(requestHodler);


            downloadItemInfo.setHttpTask(httpTask);
            /**
             * 添加
             */
//            downloadFileTaskList.add(downloadItemInfo);
//            httpTask.start();
            try {
                ThreadPoolManager.getInstance().execte(new FutureTask<Object>(httpTask,null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onDownloadStatusChanged(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onTotalLengthReceived(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onCurrentSizeChanged(DownloadItemInfo downloadItemInfo, double downLenth, long speed) {
        Log.i(TAG,"下载速度："+ speed/1000 +"k/s");
        Log.i(TAG,"-----路径  "+ downloadItemInfo.getFilePath()+"  下载长度  "+downLenth+"   速度  "+speed);
    }

    @Override
    public void onDownloadSuccess(DownloadItemInfo downloadItemInfo) {
        Log.i(TAG,"下载成功    路劲  "+ downloadItemInfo.getFilePath()+"  url "+ downloadItemInfo.getUrl());



    }

    @Override
    public void onDownloadPause(DownloadItemInfo downloadItemInfo) {



    }

    @Override
    public void onDownloadError(DownloadItemInfo downloadItemInfo, int var2, String var3) {

    }
}