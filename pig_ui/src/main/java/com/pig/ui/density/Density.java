package com.pig.ui.density;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.pig.ui.util.LLog;

public class Density {

    private static final float  WIDTH = 360;//参考设备的宽，单位是dp 320 / 2 = 160

    private static float appDensity;//表示屏幕密度
    private static float appScaleDensity; //字体缩放比例，默认appDensity

    public static void setDensity(final Application application, Activity activity){

        DisplayMetrics dm33 = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm33);
        int screenWidth = dm33.widthPixels;
        int screenHeight = dm33.heightPixels;
        LLog.d(" app级别，方法1,screenWidth "+screenWidth+ " hegiht:"+screenHeight);

        //获取当前app的屏幕显示信息
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();

        LLog.d(" app级别的,widthPixels:"+displayMetrics.widthPixels+" heightP:"+displayMetrics.heightPixels+" D:"
                +displayMetrics.density+" densityDpi:"+displayMetrics.densityDpi
                +" xdpi:"+displayMetrics.xdpi + " ydpi:"+displayMetrics.ydpi);

        if (appDensity == 0){
            //初始化赋值操作
            appDensity = displayMetrics.density;
            appScaleDensity = displayMetrics.scaledDensity;

            //添加字体变化监听回调
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体发生更改，重新对scaleDensity进行赋值
                    if (newConfig != null && newConfig.fontScale > 0){
                        appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        //计算目标值density, scaleDensity, densityDpi
        float targetDensity = displayMetrics.widthPixels / WIDTH; // 1080 / 360 = 3.0
        float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = (int) (targetDensity * 160);

        /***
         * xdpi,ydpi是否需要一起改掉？
         */
        //替换Activity的density, scaleDensity, densityDpi
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();

        LLog.d(" activiy,修改以前,widthPixels:"+dm.widthPixels+" heightP:"+dm.heightPixels+" D:"
                +dm.density+" densityDpi:"+dm.densityDpi
                +" xdpi:"+dm.xdpi + " ydpi:"+dm.ydpi + " width dp:"+(float)(dm.widthPixels*1.0f / dm.density));

        dm.density = targetDensity;
        dm.scaledDensity = targetScaleDensity;
        dm.densityDpi = targetDensityDpi;


        DisplayMetrics dm2Trans = activity.getResources().getDisplayMetrics();
        LLog.d(" activiy 修改以后,widthPixels:"+dm2Trans.widthPixels+" heightP:"+dm2Trans.heightPixels+" D:"
                +dm2Trans.density+" densityDpi:"+dm2Trans.densityDpi
        +" xdpi:"+dm2Trans.xdpi + " ydpi:"+dm2Trans.ydpi + " width dp:"+(int)(dm2Trans.widthPixels / dm2Trans.density));

    }

}
