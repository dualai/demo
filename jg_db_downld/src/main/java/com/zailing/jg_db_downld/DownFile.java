package com.zailing.jg_db_downld;

import com.zailing.jg_db_downld.db.annotation.DbField;
import com.zailing.jg_db_downld.db.annotation.DbTable;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
@DbTable("tb_down")
public class DownFile {
    public DownFile(String time, String path) {
        this.time = time;
        this.path = path;
    }

    public DownFile( ) {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     *
     */
    @DbField("tb_time")
    public String time;

    @DbField("tb_path")
    public String path;
}
