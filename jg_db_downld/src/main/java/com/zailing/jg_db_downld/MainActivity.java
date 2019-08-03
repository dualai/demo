package com.zailing.jg_db_downld;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zailing.jg_db_downld.db.BaseDaoFactory;
import com.zailing.jg_db_downld.db.IBaseDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "dongnao";
    IBaseDao<User> baseDao;
    IBaseDao<DownFile> fileDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initDb();
    }

    private void initDb(){
        baseDao= BaseDaoFactory.getInstance().getDataHelper(UserDao.class,User.class);
        fileDao=BaseDaoFactory.getInstance().getDataHelper(DownDao.class,DownFile.class);
        findViewById(R.id.deleteUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readPermission = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int writePermission = this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            //检测是否有权限，如果没有权限，就需要申请
            if (readPermission != PackageManager.PERMISSION_GRANTED || writePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(PERMISSIONS_STORAGE, 1001);
                return false;
            }
            return true;
        }
        return true;
    }


    public void save(View view)
    {
//        for (int i=0;i<20;i++)
//        {
//            DownFile user=new DownFile("2016-1-11","123456");
//            fileDao.insert(user);
//        }
//        for (int i=0;i<20;i++)
//        {
//            User user=new User(i,"teacher","123456");
//            baseDao.insert(user);
//        }



    }
    public void deleteUser()
    {
        User user=new User();
        user.setName("David");
        baseDao.delete(user);
    }
    public  void  update(View view)
    {
        User where=new User();
        where.setName("teacher");

//        User user=new User(1,"David","123456789");
//        baseDao.update(user,where);


    }
    public void queryList(View view)
    {
//         DownFile downFile=new DownFile();
//        downFile.setTime("2016-1-11");
//        List<DownFile> list=fileDao.query(downFile);
//        for (DownFile down:list)
//        {
//            Log.i(TAG,down.getPath()+"  time  "+down.getTime());
//        }


//        User where=new User();
//        where.setName("teacher");
//        where.setUser_Id(5);
//        List<User> list=baseDao.query(where);
//        Log.i(TAG,"查询到  "+list.size()+"  条数据");

    }
}
