package com.zailing.jg_db_downld.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class BaseDaoFactory {
    private String sqliteDatabasePath;

    private SQLiteDatabase sqLiteDatabase;

    private static  BaseDaoFactory instance=new BaseDaoFactory();
    public BaseDaoFactory()
    {
        sqliteDatabasePath= Environment.getExternalStorageDirectory().getAbsolutePath()+"/teacher.db";
        openDatabase();
    }
    private void openDatabase() {
        this.sqLiteDatabase=SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath,null);
    }

    public synchronized <T extends BaseDao<M>,M> T getDataHelper(Class<T> clazz,Class<M> entityClass){

        BaseDao baseDao = null;
        try{
            baseDao = clazz.newInstance();
            baseDao.init(entityClass,sqLiteDatabase);
        }catch (Exception ex){
            Log.e("test",ex.toString());
        }
        return (T)baseDao;
    }

    public  static  BaseDaoFactory getInstance()
    {
        return instance;
    }
}
