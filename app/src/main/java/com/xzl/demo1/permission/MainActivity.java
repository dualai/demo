package com.xzl.demo1.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xzl.demo1.LLog;
import com.xzl.demo1.R;

/**
 * Created by xuluming on 2019/5/8
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private Button setBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        setBtn = findViewById(R.id.btn_set);
        setBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        doAction();
    }

    private void doAction() {
        int flg = checkout();
        if (flg != State_Permission_OK) {
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
                Toast.makeText(this,"qing 给权限...",Toast.LENGTH_SHORT).show();
                return;
            }
            requestPermission();
            return;
        }

        LLog.d("开始定位》。。");
    }


    public static final int State_Permission_OK = 0;
    public static final int State_Permission_Rationale = 1;
    public static final int State_Permission_NO = 2;

    private int checkout() {
        try{
            if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
                int locationMode = 0;
                try {
                    locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                    return State_Permission_NO;
                }
                return locationMode != Settings.Secure.LOCATION_MODE_OFF ? State_Permission_OK : State_Permission_NO;
            }else{
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        return State_Permission_Rationale;
                    } else {
                        return State_Permission_NO;
                    }
                }
                return State_Permission_OK;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return State_Permission_NO;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                2001);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 2001: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LLog.d("已经开启定位...");
                } else {
                    Toast.makeText(this, "请开启定位权限2", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
