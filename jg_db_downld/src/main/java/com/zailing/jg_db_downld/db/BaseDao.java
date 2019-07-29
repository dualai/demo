package com.zailing.jg_db_downld.db;

import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDao<M> implements IBaseDao<M> {

    private SQLiteDatabase database; //持有操作数据库的引用
    private boolean isInit = false; //保证实例化一次；
    private Class<M> entityClass; // 操作数据库表对应的java类型；

    //
    protected synchronized boolean init(Class<M> entity, SQLiteDatabase sqLiteDatabase){
        if(!isInit){
            database = sqLiteDatabase;

        }
    }

    @Override
    public abstract Long insert(M entity);

    @Override
    public abstract Long update(M entity, M where);
}
