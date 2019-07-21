package com.xzl.demo1.exo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ContentFrameLayout;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.orhanobut.logger.AndroidLogAdapter;

import com.orhanobut.logger.Logger;
import com.xzl.demo1.R;

import java.io.File;
import java.security.Permission;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.exoplayer2.DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS;
import static com.google.android.exoplayer2.DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS;

public class FileActivity extends AppCompatActivity {

    private Button playBtn;
    private Button stopBtn;
    private PlayerView playerView;


    private PlayerComponentListener componentListener;

    private SimpleExoPlayer mInternalPlayer;

    private int mVideoWidth;
    private int mVideoHeight;

    private String mDataSource;
    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private String userAgent;
    private static final int mediaSourceType = C.TYPE_OTHER;
    private final static String UserAgent = "ExoPlayerWrapper";
    private List<String> videoList = new ArrayList<>();
    private final static String TAG = "VideoDebug";
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

        String video0 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_103528.mp4";
        String video1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "VID_20190419_103513.mp4";
        String video2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "trailer.mp4";
        String video3 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "oceans.mp4";
        String video4 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "big_buck_bunny.mp4";

        File file0 = new File(video0);
        File file1 = new File(video1);
        File file2 = new File(video2);
        File file3 = new File(video3);
        File file4 = new File(video4);

        if(!file0.exists() || !file1.exists() || !file2.exists()|| !file3.exists() || !file4.exists()){
            Toast.makeText(this,"文件不存在...",Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"文件都存在...",Toast.LENGTH_SHORT).show();

        Logger.t(TAG).d(video0);
        videoList.add(video0);
        videoList.add(video1);
        videoList.add(video2);
        videoList.add(video3);
        videoList.add(video4);

        playBtn = findViewById(R.id.btn_player);
        stopBtn = findViewById(R.id.btn_stop);
        playerView = findViewById(R.id.view_player);

        userAgent = Util.getUserAgent(this, UserAgent);
        dataSourceFactory = new FileDataSourceFactory(transferListener);
        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();

        com.google.android.exoplayer2.util.Log.setLogLevel(com.google.android.exoplayer2.util.Log.LOG_LEVEL_ALL);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(videoList.get(0));
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

    }

    private final TransferListener transferListener = new TransferListener() {
        @Override
        public void onTransferInitializing(DataSource source, DataSpec dataSpec, boolean isNetwork) {
//            Logger.t(TAG).d("onTransferInitializing");
        }

        @Override
        public void onTransferStart(DataSource source, DataSpec dataSpec, boolean isNetwork) {
//            Logger.t(TAG).d("onTransferStart");
        }

        @Override
        public void onBytesTransferred(DataSource source, DataSpec dataSpec, boolean isNetwork, int bytesTransferred) {
//            Logger.t(TAG).d("onBytesTransferred");
        }

        @Override
        public void onTransferEnd(DataSource source, DataSpec dataSpec, boolean isNetwork) {
//            Logger.t(TAG).d("onTransferEnd");
        }
    };

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


    public void start(String url) {
        if (TextUtils.isEmpty(url)) return;
        if (this.mInternalPlayer != null) {
            stop();
        }

        try {
            mDataSource = url;
            if (TextUtils.isEmpty(mDataSource)) return;
            mediaSource = buildMediaSource(Uri.parse(url), this.mediaSourceType);

            TrackSelection.Factory trackSelectionFactory = new RandomTrackSelection.Factory();
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this);
            renderersFactory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);

            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
//            DefaultLoadControl.Builder loadControl = new DefaultLoadControl.Builder();
//            if (mVideoType == PlayerView.VideoType.Live || mVideoType == PlayerView.VideoType.Shift) {
//                loadControl.setBufferDurationsMs(10 * 1000, 20 * 1000, DEFAULT_BUFFER_FOR_PLAYBACK_MS, DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS);
//            } else {
//                loadControl.setBufferDurationsMs(20 * 1000, 50 * 1000, DEFAULT_BUFFER_FOR_PLAYBACK_MS, 4000);
//            }
//            mInternalPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl.createDefaultLoadControl());
            componentListener = new PlayerComponentListener();
            mInternalPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector);
            mInternalPlayer.setPlayWhenReady(true);
            mInternalPlayer.addListener(componentListener);
            mInternalPlayer.prepare(mediaSource);
            playerView.setPlayer(mInternalPlayer);
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
            mDataSource = null;
            mediaSource = null;
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

                    break;
                case Player.STATE_BUFFERING:

                    break;
                case Player.STATE_READY:
                    break;
                case Player.STATE_ENDED:

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

}
