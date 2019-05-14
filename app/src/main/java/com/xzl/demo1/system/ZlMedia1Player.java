package com.xzl.demo1.system;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;

public class ZlMedia1Player implements SurfaceHolder.Callback {
    private final Context mContext;
    private SurfaceView mPlayerView;
    private MediaPlayer mMediaPlayer;

    private int mVideoWidth;
    private int mVideoHeight;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private AudioManager mAudioManager;
    private int mAudioFocusType = AudioManager.AUDIOFOCUS_GAIN; // legacy focus gain
    private AudioAttributes mAudioAttributes;

    public ZlMedia1Player(Context mContext, SurfaceView mPlayerView) {
        this.mContext = mContext;
        this.mPlayerView = mPlayerView;
        initSettings();
    }

    private void initSettings() {
        mVideoWidth = 0;
        mVideoHeight = 0;

        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mPlayerView.getHolder().addCallback(this);
        mPlayerView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


    private String mediaSource;

    public void start(String url){
        if(TextUtils.isEmpty(url))return;
        mediaSource = url;

        mMediaPlayer = new MediaPlayer();
        // TODO: create SubtitleController in MediaPlayer, but we need
        // a context for the subtitle renderers

//        mMediaPlayer.setOnPreparedListener(mPreparedListener);
//        mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
//        mMediaPlayer.setOnCompletionListener(mCompletionListener);
//        mMediaPlayer.setOnErrorListener(mErrorListener);
//        mMediaPlayer.setOnInfoListener(mInfoListener);
//        mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
//        mCurrentBufferPercentage = 0;
//        mMediaPlayer.setDataSource(mContext, mUri, mHeaders);
//        mMediaPlayer.setDisplay(mSurfaceHolder);
//        mMediaPlayer.setScreenOnWhilePlaying(true);
//        mMediaPlayer.prepareAsync();


    }

    private void release(boolean cleartargetstate) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            if (mAudioFocusType != AudioManager.AUDIOFOCUS_NONE) {
                mAudioManager.abandonAudioFocus(null);
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format,
                               int w, int h)
    {
        mSurfaceWidth = w;
        mSurfaceHeight = h;
    }

    public void surfaceCreated(SurfaceHolder holder)
    {

    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }
}
