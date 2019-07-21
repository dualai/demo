package com.xzl.demo1.exo;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.VideoListener;
import com.xzl.demo1.LLog;
import com.xzl.demo1.R;

import java.net.URL;

public class ZlExoPlayer {
    private final Context mContext;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    public interface ISinglePlayerListener extends VideoListener,Player.EventListener {

    }
    private ISinglePlayerListener playerListener;

    private DefaultTrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private final static String TAG = "VideoDebug";


    public ZlExoPlayer(Context context, PlayerView mPlayerView,ISinglePlayerListener listener) {
        this.mContext = context;
        this.mPlayerView = mPlayerView;

        dataSourceFactory = new FileDataSourceFactory(null);
        trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
        this.playerListener = listener;
        Log.setLogLevel(Log.LOG_LEVEL_ALL);
        initSettings();
    }

    private void initSettings() {
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
    }


    private String mediaUrl;
    public void start(String url) {
        release();
        if (TextUtils.isEmpty(url)) return;
        mediaUrl = url;
        try {
            MediaSource mediaSource = buildMediaSource(Uri.parse(url), C.TYPE_OTHER);
            TrackSelection.Factory trackSelectionFactory = new RandomTrackSelection.Factory();
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(mContext);
            renderersFactory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);
            trackSelector = new DefaultTrackSelector(trackSelectionFactory);
            trackSelector.setParameters(trackSelectorParameters);
            mPlayer = ExoPlayerFactory.newSimpleInstance(mContext, renderersFactory, trackSelector);
            mPlayer.setPlayWhenReady(false);
            mPlayer.addListener(playerListener);
            mPlayer.addVideoListener(playerListener);
            mPlayer.prepare(mediaSource);
            mPlayerView.setPlayer(mPlayer);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void play(){
        LLog.d("url:"+mediaUrl);
        mPlayer.setPlayWhenReady(true);
    }

    public void pause(){
        mPlayer.setPlayWhenReady(false);
    }

    public void stop(){
        mPlayer.stop(true);
    }

    public void release() {
        if (mPlayer != null) {
            mPlayer.stop(true);
            mPlayer.release();
            mPlayer = null;
            trackSelector = null;
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
}
