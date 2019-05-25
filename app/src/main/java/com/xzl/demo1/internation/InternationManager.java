package com.xzl.demo1.internation;

import android.content.Context;

import com.blankj.utilcode.util.ObjectUtils;
import com.xzl.demo1.LLog;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by xuluming on 2019/5/5
 */
public class InternationManager {
    private final static String Config_SpKey_Language = "Config_SpKey_Language";
    private final static String Config_SpKey_TimeZone = "Config_SpKey_TimeZone";
    /**
     * 如果匹配不到uiLocalLanguages数组，那么默认用zh-cn,或者用en？
     */
    private final static String uiLocalDefaultLanguage = "zh-cn";
    private final static List<String> uiLocalLanguages = Arrays.asList("zh-cn", "zh-tw", "en");
    private final static Locale defaultLocale = Locale.getDefault();

    //application的context
    private Context mContext;
    private String mLanguage;

    public String getLanguage() {
        if (ObjectUtils.isEmpty(mLanguage)) {
            initLanguage();
        }
        return mLanguage;
    }

    /**
     * 获取初始化的语言和时区
     */
    public void start(Context context) {
        mContext = context;
        initLanguage();
    }

    /**
     * 中文简体
     * 中国：systemLanguage zh,systemCountry CN
     * <p>
     * 中文繁体
     * 台湾：systemLanguage zh,systemCountry TW
     * 香港繁体：systemLanguage zh,systemCountry HK
     * 澳门：systemLanguage zh,systemCountry MO
     * <p>
     * 英文
     * 英文：systemLanguage en,systemCountry xxx
     */
    private void initLanguage() {
        String lang = ConfigManager.getInstance().config_sharePre.getString(Config_SpKey_Language, "");
        if (ObjectUtils.isEmpty(lang)) {
            String systemLanguage = defaultLocale.getLanguage();
            String systemCountry = defaultLocale.getCountry();
            LLog.d("systemLanguage:" + systemLanguage);
            LLog.d("systemCountry:" + systemCountry);
            if (systemLanguage.equals("en") && uiLocalLanguages.contains("en")) {
                lang = "en";
            } else if (systemLanguage.equals("zh") && systemCountry.equals("CN") && uiLocalLanguages.contains("zh-cn")) {
                lang = "zh-cn";
            } else if (systemLanguage.equals("zh") && (systemCountry.equals("TW") || systemCountry.equals("HK") || systemCountry.equals("MO"))
                    && uiLocalLanguages.contains("zh-tw")) {
                lang = "zh-tw";
            }
        }
        mLanguage = uiLocalLanguages.indexOf(lang) >= 0 ? lang : uiLocalDefaultLanguage;
    }


    private void initTimeZone() {

    }

    /**
     * 根据当前的语言化环境获取asset目录中的数据,当前主要是json
     */
    public void getAssetsDataByCurrentLanguage() {

    }

    /**
     * 根据业务来看，是否需要setlanguage，暂时可能不需要，或者是程序第一次启动的时候，
     * 根据服务器返回的数据来确定当前是什么国家屏幕，设置进去，还是怎么搞？
     *
     * @param language
     */
    public void setLanguage(String language) {
        if (ObjectUtils.isEmpty(language)) return;
        ConfigManager.getInstance().config_sharePre.put(Config_SpKey_Language, language, true);
    }

    public String getLanguageDesc(String lang) {
        if (lang.equals("en"))
            return "English";
        else if (lang.equals("zh-cn"))
            return "简体中文";
        else if (lang.equals("zh-tw"))
            return "繁體中文";
        return "";
    }

    private static class SingletonHolder {
        private static InternationManager singletonClass = new InternationManager();
    }

    private InternationManager() {
    }

    public static InternationManager getInstance() {
        return SingletonHolder.singletonClass;
    }
}
