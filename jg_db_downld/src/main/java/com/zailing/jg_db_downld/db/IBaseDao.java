package com.zailing.jg_db_downld.db;

import java.util.List;

public interface IBaseDao<M> {
    /**
     * 插入数据
     * @param entity
     * @return
     */
    Long insert(M entity);

    /**
     *
     * @param entity
     * @param where
     * @return
     */
    int update(M entity,M where);

    /**
     * 删除数据
     * @param where
     * @return
     */
    int  delete(M where);

    /**
     * 查询数据
     */
    List<M> query(M where);


    List<M> query(M where,String orderBy,Integer startIndex,Integer limit);


    List<M> query(String sql);

}
