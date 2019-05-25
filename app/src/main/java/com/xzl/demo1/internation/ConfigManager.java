package com.xzl.demo1.internation;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by wuyuanteng on 2018/8/9.
 * wuyuanteng@zailingtech.com
 */


public class ConfigManager {
    private static final ConfigManager     inst              = new ConfigManager();
    private final        Gson              gson              = new Gson();
    private SharedPreferences sharedPreferences = null;
    public               SPUtils           config_sharePre   = SPUtils.getInstance("appConfig");
    public               SPUtils           wxb_sharePre      = SPUtils.getInstance("appWxb");
    public               SPUtils           main_sharePre     = SPUtils.getInstance("lift-main-v2");

    private ConfigManager() {

    }

    public static ConfigManager getInstance() {
        return inst;
    }

    public void start(Context context) {
        sharedPreferences = context.getSharedPreferences("appConfig", Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            throw new IllegalArgumentException("sharedPrefrences创建失败");
        }
    }

    public void stop() {

    }

    public synchronized void set(String key, Object obj) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (edit == null) {
            throw new NullPointerException("edit为null");
        }
        String json = gson.toJson(obj);
        edit.putString(key, json);
        edit.apply();
    }

    public synchronized void remove(String key) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (edit == null) {
            throw new NullPointerException("edit为null");
        }
        edit.remove(key);
        edit.apply();
    }

    public synchronized <T> T get(String key, Class<T> clazz) {
        if (sharedPreferences == null) {
            throw new NullPointerException("sharedPrefrences 为空!");
        }
        String value = sharedPreferences.getString(key, "");
        if (value.isEmpty()) {
            return null;
        }
        return gson.fromJson(value, clazz);
    }

    public synchronized <T> T get(String key, Type typeOfT) {
        if (sharedPreferences == null) {
            throw new NullPointerException("sharedPrefrences 为空!");
        }
        String value = sharedPreferences.getString(key, "");
        if (value.isEmpty()) {
            return null;
        }
        return gson.fromJson(value, typeOfT);
    }

    public synchronized <T> T get(String key, TypeToken<T> clazz) {
        if (sharedPreferences == null) {
            throw new NullPointerException("sharedPrefrences 为空!");
        }
        String value = sharedPreferences.getString(key, "");
        if (value.isEmpty()) {
            return null;
        }
        return gson.fromJson(value, clazz.getType());
    }
}