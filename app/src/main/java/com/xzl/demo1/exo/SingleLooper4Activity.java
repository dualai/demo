package com.xzl.demo1.exo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xzl.demo1.R;

import java.io.File;

public class SingleLooper4Activity extends AppCompatActivity {

    private ZlCombPlayer zlCombPlayer;

    private Button swapBtn;
    private ViewGroup playRootView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleloop4);

        initView();

        if (requestPermission()) {
            initPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(zlCombPlayer != null){
            zlCombPlayer.release();
        }
    }

    private void initView() {
        View playBtn = findViewById(R.id.btn_player);
        View swapBtn = findViewById(R.id.btn_swapview);
        View releaseBtn = findViewById(R.id.btn_release);
        playRootView = findViewById(R.id.view_playroot);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zlCombPlayer.start();
            }
        });

        releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zlCombPlayer.release();
            }
        });

        ViewGroup testGroup = findViewById(R.id.view_testswap);
        final View view0 = findViewById(R.id.test1);
        final View view1 = findViewById(R.id.test2);

        swapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isView0Front) {
                    view0.bringToFront();
                }else{
                    view1.bringToFront();
                }

                isView0Front = !isView0Front;
            }
        });
    }

    private boolean isView0Front = false;

    private void initPlayer() {
        if (zlCombPlayer != null) return;
        zlCombPlayer = new ZlCombPlayer(this,findViewById(R.id.view_player0),findViewById(R.id.view_player1));

        String video0 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190420_155850.mp4";
        String video1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190420_155901.mp4";
        String video2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190420_155909.mp4";
        zlCombPlayer.addUrl(video0);
        zlCombPlayer.addUrl(video1);
        zlCombPlayer.addUrl(video2);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                initPlayer();
            } else {
                Toast.makeText(this, "请赋予读写磁盘权限", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
