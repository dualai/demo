package com.xzl.demo1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readPermission = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            //检测是否有权限，如果没有权限，就需要申请
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(PERMISSIONS_STORAGE, 1001);
            }else{
                start();
            }
        } else {
            start();
        }
    }

    private void start(){
        Intent intent = new Intent(this, com.xzl.demo1.presentation.MainActivity.class);
        startActivity(intent);
        finish();
    }
}
