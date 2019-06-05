package com.xzl.demo1.real_time_log;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.xzl.demo1.FutureUtil;
import com.xzl.demo1.LLog;

import java.io.File;

public class MainActivity extends Activity {
    FutureUtil futureUtil = new FutureUtil();
    private Process logcatProc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LogcatHelper.getInstance(this).start();
        String PATH_LOGCAT = null;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            PATH_LOGCAT = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "demo";
        }
        LLog.d("PATH_LOGCAT:"+PATH_LOGCAT);
        File file = new File(PATH_LOGCAT);
        boolean mkdirSuc = false;
        if (!file.exists()) {
            mkdirSuc = file.mkdirs();
        }
        LLog.d("mkdirSuc:"+mkdirSuc);

        String cmds = "logcat -v time -f /storage/emulated/0/demo/realtime_2.log";
        try {
            logcatProc = Runtime.getRuntime().exec(cmds);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LogcatHelper.getInstance(this).stop();
//        futureUtil.clear();
        logcatProc.destroy();
    }
}
