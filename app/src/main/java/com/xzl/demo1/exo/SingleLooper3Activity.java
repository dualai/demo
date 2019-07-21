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
import android.widget.Toast;

import com.xzl.demo1.R;

import java.io.File;

public class SingleLooper3Activity extends AppCompatActivity {

    private ZlExoPlayer mExoPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleloop3);

        initView();

        if (requestPermission()) {
            initPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mExoPlayer != null){
            mExoPlayer.release();
        }
    }

    private void initView() {
        String video0 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_103528.mp4";
        String video1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_103513.mp4";
        String video2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_170746.mp4";

        View playBtn = findViewById(R.id.btn_player);
        View stopBtn = findViewById(R.id.btn_stop);
        View releaseBtn = findViewById(R.id.btn_release);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExoPlayer.release();
                mExoPlayer.start(video0);
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExoPlayer.stop();
            }
        });

        releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExoPlayer.release();
            }
        });
    }

    private void initPlayer() {
        if (mExoPlayer != null) return;
        mExoPlayer = new ZlExoPlayer(this, findViewById(R.id.view_player),null);
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
