package com.zailing.jg_db_downld;

import com.zailing.jg_db_downld.db.annotation.DbField;
import com.zailing.jg_db_downld.db.annotation.DbTable;

@DbTable("tb_user")
public class User {

    public int user_Id=0;

    public Integer getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Integer user_Id) {
        this.user_Id = user_Id;
    }

    public User(Integer id, String name, String password) {
        user_Id= id;
        this.name = name;
        this.password = password;
    }
    public User( ) {
    }

    @DbField("name")
    public String name;

    @DbField("password")
    public String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "name  "+name+"  password "+password;
    }
}
