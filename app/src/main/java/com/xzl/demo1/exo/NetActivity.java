package com.xzl.demo1.exo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xzl.demo1.R;

import java.util.ArrayList;
import java.util.List;

public class NetActivity extends AppCompatActivity {

    private Button playBtn;
    private Button stopBtn;
    private PlayerView playerView;


    private PlayerComponentListener componentListener;

    private SimpleExoPlayer mInternalPlayer;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
        setContentView(R.layout.activity_main);

        //初始化数据
        boolean hasPermission = storatgePermission();
        if(!hasPermission){
            Toast.makeText(this,"请先获取权限...",Toast.LENGTH_SHORT).show();
            return;
        }

        String video0 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        String video0 = "https://media.w3.org/2010/05/sintel/trailer.mp4";
//        String video0 = "http://www.w3school.com.cn/example/html5/mov_bbb.mp4";
//        String video0 = "https://www.w3schools.com/html/movie.mp4";
        Logger.t(TAG).d(video0);

        videoList.add(video0);

        playBtn = findViewById(R.id.btn_player);
        stopBtn = findViewById(R.id.btn_stop);
        playerView = findViewById(R.id.view_player);

        userAgent = Util.getUserAgent(this, UserAgent);
        dataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
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

    private void initPlayer() {

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
//        KLog.d("buildMediaSource:"+type);
        switch (type) {
//            case C.TYPE_SS:
//                return new SsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//            case C.TYPE_HLS:
//                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
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
