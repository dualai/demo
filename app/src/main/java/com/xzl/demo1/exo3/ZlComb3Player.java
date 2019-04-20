package com.xzl.demo1.exo3;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.exoplayer2.Player;
import com.xzl.demo1.LLog;

import java.util.ArrayList;
import java.util.List;

public class ZlComb3Player {
    private ZlExo3Player mFrontPlayer;
    private ZlExo3Player mPlayer0;
    private ZlExo3Player mPlayer1;

    private SurfaceView mFrontPlayerView;
    private SurfaceView mPlayerView0;
    private SurfaceView mPlayerView1;

    private final Context mContext;

    private List<String> mVideoList;
    private final static String TAG = "VideoDebug";


    public ZlComb3Player(Context context, SurfaceView playerView0, SurfaceView playerView1) {
        this.mContext = context;
        this.mPlayerView0 = playerView0;
        this.mPlayerView1 = playerView1;

        this.mPlayerView0.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                LLog.d("playerView0 surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LLog.d("playerView0 surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LLog.d("playerView0 surfaceDestroyed");
            }
        });


        this.mPlayerView1.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                LLog.d("playerView1 surfaceCreated");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LLog.d("playerView1 surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LLog.d("playerView1 surfaceDestroyed");
            }
        });

        this.mPlayerView0.setVisibility(View.INVISIBLE);
        this.mPlayerView1.setVisibility(View.INVISIBLE);

        mPlayer0 = new ZlExo3Player(mContext, mPlayerView0, player0Listener);
        mPlayer1 = new ZlExo3Player(mContext, mPlayerView1, player1Listener);
        mVideoList = new ArrayList<>();
    }

    public void addUrl(String url) {
        if (!mVideoList.contains(url)) {
            mVideoList.add(url);
        }
    }

    public void removeUrl(String url) {
        if (mVideoList.contains(url)) {
            mVideoList.remove(url);
        }
    }

    public void start() {
        if (mVideoList.size() == 0) return;
        if (mFrontPlayer == null) {
            //playCurrent
            mFrontPlayer = mPlayer0;
            mFrontPlayerView = mPlayerView0;
            mFrontPlayerView.setVisibility(View.VISIBLE);
            mFrontPlayer.start(getCurrentUrl());
            mFrontPlayer.play();

            //preloadNext
            mPlayerView1.setVisibility(View.INVISIBLE);
            mPlayer1.start(getCurrentUrl());
        }
    }

    private void swapPlayer() {
        if (mFrontPlayer == null) return;
        //playCurrent
        mFrontPlayerView = mFrontPlayerView == mPlayerView0 ? mPlayerView1 : mPlayerView0;
        mFrontPlayerView.setVisibility(View.VISIBLE);

        mFrontPlayer = mFrontPlayer == mPlayer0 ? mPlayer1 : mPlayer0;
        mFrontPlayer.play();
        //preloadNext
        preload();
    }


    private void preload() {
        if (mFrontPlayer == null) return;
        SurfaceView preloadPlayerView = mFrontPlayerView == mPlayerView0 ? mPlayerView1 : mPlayerView0;
        preloadPlayerView.setVisibility(View.INVISIBLE);

        ZlExo3Player preloadPlayer = mFrontPlayer == mPlayer0 ? mPlayer1 : mPlayer0;
        preloadPlayer.start(getCurrentUrl());
    }

    private int mediaSourceIndex = -1;

    private String getCurrentUrl() {
        mediaSourceIndex = mediaSourceIndex >= mVideoList.size() - 1 ? 0 : ++mediaSourceIndex;
        return mVideoList.get(mediaSourceIndex);
    }

    private final ZlExo3Player.ISinglePlayerListener player0Listener = new ZlExo3Player.ISinglePlayerListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE:
                    LLog.d("player0 state_idle...");
                    break;
                case Player.STATE_BUFFERING:

                    break;
                case Player.STATE_READY:
                    LLog.d("player0 state_ready...");
                    break;
                case Player.STATE_ENDED:
                    LLog.d("player0 state_ended...");
                    swapPlayer();
                    break;
                default:
                    break;
            }
        }
    };

    private final ZlExo3Player.ISinglePlayerListener player1Listener = new ZlExo3Player.ISinglePlayerListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_IDLE:
                    LLog.d("player1 state_idle...");
                    break;
                case Player.STATE_BUFFERING:

                    break;
                case Player.STATE_READY:
                    LLog.d("player1 state_ready...");
                    break;
                case Player.STATE_ENDED:
                    LLog.d("player1 state_ended...");
                    swapPlayer();
                    break;
                default:
                    break;
            }
        }
    };

    public void release() {
        mPlayer0.release();
        mPlayer1.release();
        mFrontPlayer = null;
    }
}
