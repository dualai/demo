package com.zailing.jg_db_downld;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zailing.jg_db_downld.http.Volley;
import com.zailing.jg_db_downld.http.download.DownFileManager;
import com.zailing.jg_db_downld.http.interfaces.IDataListener;

public class HttpActivity extends Activity {
//    public  static  final String url="http://v.juhe.cn/toutiao/index?type=top&key=29da5e8be9ba88b932394b7261092f71";
//    public  static  final String url="https://raw.githubusercontent.com/dualai/pig_demos/master/user.json";
//    public  static  final String url="http://192.168.0.118:8080/user.txt";
    public  static  final String url="http://10.0.2.2:8080/user.txt";
//    public  static  final String url="http://127.0.0.1:8080/user.txt";
    private static final String TAG = "dongnao";
    Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readPermission = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            //检测是否有权限，如果没有权限，就需要申请
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(PERMISSIONS_STORAGE, 1001);
            }else{

            }
        } else {

        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                login();
                download();
            }
        },100);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"count,"+count);
            }
        },2000);
    }


    private void download(){
        DownFileManager downFileService=new DownFileManager();
        downFileService.down("http://gdown.baidu.com/data/wisegame/8be18d2c0dc8a9c9/WPSOffice_177.apk");
    }












    private int count;
    private void login(){
        User user = new User();
        user.setName("xuluming");
        user.setPassword("21232332");

        for(int i = 0;i<30;i++) {

            Volley.sendRequest(user, url, User.class, new IDataListener<User>() {
                @Override
                public void onSuccess(User loginRespense) {
                    count++;
                    /***
                     * 不知道为什么，打印次数总是小于总次数，但是count的数值是对的
                     */
                    Log.i(TAG,"result:"+loginRespense.toString());
                }

                @Override
                public void onErro() {
                    Log.i(TAG,"onErro...");
                }
            });
        }
    }



}
