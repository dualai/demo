package com.zailing.jg_db_downld.db;

public interface IBaseDao<M> {
    Long insert(M entity);

    Long update(M entity,M where);

}
