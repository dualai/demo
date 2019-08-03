package com.zailing.jg_db_downld.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.zailing.jg_db_downld.db.annotation.DbField;
import com.zailing.jg_db_downld.db.annotation.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseDao<M> implements IBaseDao<M> {

    private SQLiteDatabase database; //持有操作数据库的引用
    private boolean isInit = false; //保证实例化一次；
    private Class<M> entityClass; // 操作数据库表对应的java类型；

    //
    private HashMap<String, Field> cacheMap;
    private String tableName;


    protected synchronized boolean init(Class<M> entity, SQLiteDatabase sqLiteDatabase) {
        if (entity == null) return false;
        if (!isInit) {
            entityClass = entity;
            database = sqLiteDatabase;
            if (entity.getAnnotation(DbTable.class) == null) {
                tableName = entityClass.getSimpleName();
            } else {
                tableName = entityClass.getAnnotation(DbTable.class).value();
            }

            if (!database.isOpen()) {
                return false;
            }

            if (!TextUtils.isEmpty(createTable())) {
                database.execSQL(createTable());
            }
            cacheMap = new HashMap<>();
            /***
             * initCacheMap之前，表已经创建完成;
             */
            initCacheMap();
            isInit = true;
        }
        return true;
    }

    private void initCacheMap() {
        String sql = "select * from " + this.tableName + " limit 1 , 0";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, null);
            /**
             * 表的列名数据
             */
            String[] columnNames = cursor.getColumnNames();
            /**
             *拿到Field属性数组
             */
            Field[] columnFields = entityClass.getFields();
//            for (Field filed : columnFields) {
//                filed.setAccessible(true);
//            }
            /**
             * 开始找对应关系
             */
            /**
             * columnName 是必然存在的，field却不一定都是数据库属性名
             */
            for (String columnName : columnNames) {

                Field columnField = null;

                for (Field field : columnFields) {
                    field.setAccessible(true);
                    String fieldName = null;
                    if (field.getAnnotation(DbField.class) != null) {
                        fieldName = field.getAnnotation(DbField.class).value();
                    } else {
                        fieldName = field.getName();
                    }

                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }

                if (columnField != null) {
                    cacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }
    }



    @Override
    public Long insert(M entity){
        Map<String,String> map = getValues(entity);
        ContentValues values = getContentValues(map);
        Long result = database.insert(tableName,null,values);
        return result;
    }


    protected ContentValues getContentValues(Map<String, String> map){
        ContentValues contentValues=new ContentValues();
        Set keys=map.keySet();
        Iterator<String> iterator=keys.iterator();
        while (iterator.hasNext())
        {
            String key=iterator.next();
            String value=map.get(key);
            if(value!=null)
            {
                contentValues.put(key,value);
            }
        }
        return contentValues;
    }


    private List<M> getResult(Cursor cursor, M where) {
        ArrayList list=new ArrayList();

        Object item;
        while (cursor.moveToNext())
        {
            try {
                item=where.getClass().newInstance();
                /**
                 * 列名  name
                 * 成员变量名  Filed;
                 */
                Iterator iterator=cacheMap.entrySet().iterator();
                while (iterator.hasNext())
                {
                    Map.Entry entry= (Map.Entry) iterator.next();
                    /**
                     * 得到列名
                     */
                    String colomunName= (String) entry.getKey();
                    /**
                     * 然后以列名拿到  列名在游标的位子
                     */
                    Integer colmunIndex=cursor.getColumnIndex(colomunName);

                    Field field= (Field) entry.getValue();

                    Class type=field.getType();
                    if(colmunIndex!=-1)
                    {
                        if(type==String.class)
                        {
                            //反射方式赋值
                            field.set(item,cursor.getString(colmunIndex));
                        }else if(type==Double.class)
                        {
                            field.set(item,cursor.getDouble(colmunIndex));
                        }else  if(type==Integer.class)
                        {
                            field.set(item,cursor.getInt(colmunIndex));
                        }else if(type==Long.class)
                        {
                            field.set(item,cursor.getLong(colmunIndex));
                        }else  if(type==byte[].class)
                        {
                            field.set(item,cursor.getBlob(colmunIndex));
                            /*
                            不支持的类型
                             */
                        }else {
                            continue;
                        }
                    }

                }
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return list;
    }

    @Override
    public int delete(M where) {
        Map map=getValues(where);

        Condition condition=new Condition(map);
        /**
         * id=1 数据
         * id=?      new String[]{String.value(1)}
         */
        int reslut=database.delete(tableName,condition.getWhereClause(),condition.getWhereArgs());
        return reslut;
    }

    /**
     * 将对象拥有的成员变量
     * 转换成  表的列名  ---》成员变量的值
     * 如  tb_name  ----> "张三"
     * 这样的map集合
     * User
     * name  "zhangsn"
     * @param entity
     * @return
     */
    protected  Map<String, String> getValues(M entity){
        HashMap<String,String> result=new HashMap<>();
        Iterator<Field> fieldsIterator =cacheMap.values().iterator();

        while (fieldsIterator.hasNext()){
            Field field = fieldsIterator.next();
            String key = null; // 表的列名
            String value = null; // 值
            if(field.getAnnotation(DbField.class) != null){
                key = field.getAnnotation(DbField.class).value();
            }else{
                key = field.getName();
            }
            try {
                Object valueObj = field.get(entity);
                if(valueObj == null){ // 表示该属性没赋值;
                    continue;
                }
                value = valueObj.toString();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            result.put(key,value);
        }
        return result;
    }



    @Override
    public List<M> query(M where, String orderBy, Integer startIndex, Integer limit) {
        Map map=getValues(where);

        String limitString=null;
        if(startIndex!=null&&limit!=null)
        {
            limitString=startIndex+" , "+limit;
        }

        Condition condition=new Condition(map);
        Cursor cursor=database.query(tableName,null,condition.getWhereClause()
                ,condition.getWhereArgs(),null,null,orderBy,limitString);
        List<M> result=getResult(cursor,where);
        cursor.close();
        return result;
    }

    @Override
    public List<M> query(M where) {
        return query(where,null,null,null);
    }


    @Override
    public int update(M entity, M where) {
        int reslut=-1;
        Map values=getValues(entity);
        /**
         * 将条件对象 转换map
         */
        Map whereClause=getValues(where);

        Condition condition=new Condition(whereClause);
        ContentValues contentValues=getContentValues(values);
        reslut=database.update(tableName,contentValues,condition.getWhereClause(),condition.getWhereArgs());
        return reslut;
    }

    /**
     * 封装修改语句
     *
     */
    class Condition
    {
        /**
         * 查询条件
         * name=? && password =?
         */
        private String whereClause;

        private  String[] whereArgs;
        public Condition(Map<String ,String> whereClause) {
            ArrayList list=new ArrayList();
            StringBuilder stringBuilder=new StringBuilder();

            stringBuilder.append(" 1=1 ");
            Set keys=whereClause.keySet();
            Iterator iterator=keys.iterator();
            while (iterator.hasNext())
            {
                String key= (String) iterator.next();
                String value=whereClause.get(key);

                if (value!=null)
                {
                    /*
                    拼接条件查询语句
                    1=1 and name =? and password=?
                     */
                    stringBuilder.append(" and "+key+" =?");
                    /**
                     * ？----》value
                     */
                    list.add(value);
                }
            }
            this.whereClause=stringBuilder.toString();
            this.whereArgs= (String[]) list.toArray(new String[list.size()]);

        }

        public String[] getWhereArgs() {
            return whereArgs;
        }

        public String getWhereClause() {
            return whereClause;
        }
    }

    /**
     * 创建表
     *
     * @return
     */
    protected abstract String createTable();

}
