package com.xzl.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuyuanteng on 2018/8/15.
 * wuyuanteng@zailingtech.com
 */

public class ListUtil {
    public interface Filterable<T> {
        boolean filter(T t);
    }

    static public <T> List<T> filter(final List<T> srcList, Filterable<T> cb) {
        List<T> dstMap = new ArrayList<>();
        if (srcList == null) {
            return dstMap;
        }
        for (T v : srcList) {
            if (v == null) {
                continue;
            }
            if (!cb.filter(v)) {
                continue;
            }
            dstMap.add(v);
        }
        return dstMap;
    }

    static public <T> List<T> safeFilter(final List<T> srcList, Filterable<T> cb) {
        List<T> dstMap = new ArrayList<>();
        if (srcList == null) {
            return dstMap;
        }
        for (T v : srcList) {
            try {
                if (v == null) {
                    continue;
                }
                if (!cb.filter(v)) {
                    continue;
                }
                dstMap.add(v);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dstMap;
    }

    public interface Foreachable<T> {
        void foreach(T t); //true:继续；false:退出
    }

    static public <T> void foreach(final List<T> srcList,Foreachable<T> cb) {
        if (srcList == null) {
            return;
        }
        for (T v : srcList) {
            if (v == null) {
                continue;
            }
            cb.foreach(v);
        }
    }

    static public <T> void safeForeach(final List<T> srcList, final Foreachable<T> cb) {
        if (srcList == null) {
            return;
        }
        for (T v : srcList) {
            try {
                if (v == null) {
                    continue;
                }
                cb.foreach(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //短路式遍历
    public interface ShotForeachable<T> {
        boolean foreach(T t); //true:继续；false:退出
    }

    static public <T> boolean shotForeach(final List<T> srcList, ShotForeachable<T> cb) {
        if (srcList == null) {
            return true;
        }
        for (T v : srcList) {
            if (v == null) {
                continue;
            }
            if (!cb.foreach(v)) {
                return false;
            }
        }
        return true;
    }

    static public <T> boolean safeShotForeach(final List<T> srcList, ShotForeachable<T> cb) {
        if (srcList == null) {
            return true;
        }
        for (T v : srcList) {
            try {
                if (v == null) {
                    continue;
                }
                if (!cb.foreach(v)) {
                    return false;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return true;
    }

    public interface ForeachableWithIndex<T> {
        void foreach(T t, int index); //true:继续；false:退出
    }

    static public <T> void foreach(final List<T> srcList, ForeachableWithIndex<T> cb) {
        if (srcList == null) {
            return;
        }
        int index = 0;
        for (T v : srcList) {
            if (v == null) {
                index++;
                continue;
            }
            cb.foreach(v, index++);
        }
    }

    static public <T> void safeForeach(final List<T> srcList, ForeachableWithIndex<T> cb) {
        if (srcList == null) {
            return;
        }
        int index = 0;
        for (T v : srcList) {
            try {
                if (v == null) {
                    index++;
                    continue;
                }
                cb.foreach(v, index++);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
