package com.xzl.demo1.exo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.orhanobut.logger.Logger;
import com.xzl.demo1.LLog;
import com.xzl.demo1.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoopActivity extends AppCompatActivity {

    private Button playBtn;
    private Button stopBtn;
    private PlayerView playerView;
    private PlayerComponentListener componentListener;

    private SimpleExoPlayer mInternalPlayer;
    private DefaultTrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private String userAgent;
    private static final int mediaSourceType = C.TYPE_OTHER;
    private final static String UserAgent = "ExoPlayerWrapper";
    private List<String> videoList = new ArrayList<>();
    private final static String TAG = "VideoDebug";

//    private LoopingMediaSource compositeSource;
    ConcatenatingMediaSource concatenatedSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化数据
        boolean hasPermission = storatgePermission();
        if(!hasPermission){
            Toast.makeText(this,"请先获取权限...",Toast.LENGTH_SHORT).show();
            return;
        }

//        videoList.add(video0);
//        videoList.add(video1);
//        videoList.add(video2);
//        videoList.add(video3);
//        videoList.add(video4);

        playBtn = findViewById(R.id.btn_player);
        stopBtn = findViewById(R.id.btn_stop);
        playerView = findViewById(R.id.view_player);
        userAgent = Util.getUserAgent(this, UserAgent);
        dataSourceFactory = new FileDataSourceFactory(transferListener);
        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();

        String video0 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_103528.mp4";
        String video1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_103513.mp4";
        String video2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "trailer.mp4";
        String video3 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "oceans.mp4";
        String video4 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "big_buck_bunny.mp4";

        MediaSource video1Source = buildMediaSource(Uri.parse(video0), this.mediaSourceType);
        MediaSource video2Source = buildMediaSource(Uri.parse(video1), this.mediaSourceType);
        concatenatedSource = new ConcatenatingMediaSource(video1Source, video2Source);

//        compositeSource = new LoopingMediaSource(concatenatedSource);

        com.google.android.exoplayer2.util.Log.setLogLevel(com.google.android.exoplayer2.util.Log.LOG_LEVEL_ALL);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

    }

    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private boolean storatgePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int readPermission = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            //检测是否有权限，如果没有权限，就需要申请
            if (readPermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(PERMISSIONS_STORAGE,1001);
                return false;
            }
            return true;
        }
        return true;
    }


    public void start() {
        LLog.d("exo start111...");
        if (this.mInternalPlayer != null) {
            stop();
        }
        LLog.d("exo start222...");
        try {
//            mDataSource = url;
//            if (TextUtils.isEmpty(mDataSource)) return;
//            mediaSource = buildMediaSource(Uri.parse(url), this.mediaSourceType);
            TrackSelection.Factory trackSelectionFactory = new RandomTrackSelection.Factory();
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);
            renderersFactory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);

            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
            componentListener = new PlayerComponentListener();
            mInternalPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector);
            mInternalPlayer.setPlayWhenReady(true);
            mInternalPlayer.addListener(componentListener);
            mInternalPlayer.prepare(concatenatedSource);
            mInternalPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
            playerView.setPlayer(mInternalPlayer);
            LLog.d("exo start333...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private MediaSource buildMediaSource(Uri uri, @C.ContentType int type) {
        switch (type) {
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private void stop() {
        if (mInternalPlayer != null) {
            mInternalPlayer.removeListener(componentListener);
            componentListener = null;
            mInternalPlayer.release();
            mInternalPlayer = null;
            trackSelector = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    private final class PlayerComponentListener implements Player.EventListener, VideoListener {
        private PlayerComponentListener() {

        }

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

        }

        @Override
        public void onRenderedFirstFrame() {

        }


        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE:
                    LLog.d("exo state_idle...");
                    break;
                case Player.STATE_BUFFERING:

                    break;
                case Player.STATE_READY:
                    LLog.d("exo state_ready...");
                    break;
                case Player.STATE_ENDED:
                    LLog.d("exo state_ended...");
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {

        }
    }



    private final TransferListener transferListener = new TransferListener() {
        @Override
        public void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean isNetwork) {
//            LLog.d("onTransferInitializing");
        }

        @Override
        public void onTransferStart(DataSource source, DataSpec dataSpec, boolean isNetwork) {
//            LLog.d("onTransferStart");
        }

        @Override
        public void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean isNetwork, int bytesTransferred) {
//            LLog.d("onBytesTransferred");
        }

        @Override
        public void onTransferEnd(DataSource source, DataSpec dataSpec, boolean isNetwork) {
//            LLog.d("onTransferEnd");
        }
    };


}
