package com.xzl.demo1.exo;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.view.SurfaceView;
import android.view.View;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.xzl.demo1.LLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试结果：
 * Exoplayer: 有直接混合的模式(ConcatenatingMediaSource,setRepeatMode)，但是因为期间播放完成没有事件抛出来，所以，如果期间有播放图片的话，不能控制
 *            如果是SurfaceView重叠，那么有问题
 *            TextureView重叠,可以
 *
 *            SurfaceView:尝试使用Visibile模式,其他模式在探索 https://blog.csdn.net/email_jade/article/details/82895335
 *            https://www.jianshu.com/p/d558a4c9c868
 *
 *            https://blog.csdn.net/gfg156196/article/details/72899287
 *
 *            https://blog.csdn.net/xuedaqian123/article/details/77878781
 *
 *            https://blog.csdn.net/smileorcryps/article/details/52614631
 *
 *            getChildDrawIndex
 */

public class ZlCombPlayer {
    private ZlExoPlayer mFrontPlayer;
    private ZlExoPlayer mPlayer0;
    private ZlExoPlayer mPlayer1;

    private PlayerView mFrontPlayerView;
    private PlayerView mPlayerView0;
    private PlayerView mPlayerView1;

    private final Context mContext;

    private List<String> mVideoList;
    private final static String TAG = "VideoDebug";


    public ZlCombPlayer(Context context, PlayerView playerView0, PlayerView playerView1) {
        this.mContext = context;
        this.mPlayerView0 = playerView0;
        this.mPlayerView1 = playerView1;
//        mPlayerView0.setVisibility(PlayerView.VISIBLE);
//        mPlayerView1.setVisibility(PlayerView.INVISIBLE);
        mPlayer0 = new ZlExoPlayer(mContext, mPlayerView0, player0Listener);
        mPlayer1 = new ZlExoPlayer(mContext, mPlayerView1, player1Listener);
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
            //初始化，直接让player0开始播放
            mFrontPlayer = mPlayer0;
            mFrontPlayerView = mPlayerView0;

            mFrontPlayerView.bringToFront();

            mFrontPlayer.start(getCurrentUrl());
            mFrontPlayer.play();

            //preload player1
            mPlayer1.start(getCurrentUrl());
        }
    }

    private void swapPlayer() {
        if (mFrontPlayer == null) return;
        mFrontPlayerView = mFrontPlayerView == mPlayerView0 ? mPlayerView1 : mPlayerView0;
        mFrontPlayer = mFrontPlayer == mPlayer0 ? mPlayer1 : mPlayer0;
//        mFrontPlayerView.setVisibility(PlayerView.VISIBLE);
        mFrontPlayerView.bringToFront();
        mFrontPlayer.play();
        preload();
    }


    private void preload() {
        if (mFrontPlayer == null) return;
        PlayerView preloadPlayerView = mFrontPlayerView == mPlayerView0 ? mPlayerView1 : mPlayerView0;

        ZlExoPlayer preloadPlayer = mFrontPlayer == mPlayer0 ? mPlayer1 : mPlayer0;
        preloadPlayer.start(getCurrentUrl());
    }

    private int mediaSourceIndex = -1;
    private String getCurrentUrl() {
        mediaSourceIndex = mediaSourceIndex >= mVideoList.size() - 1 ? 0 : ++mediaSourceIndex;
        return mVideoList.get(mediaSourceIndex);
    }

    private final ZlExoPlayer.ISinglePlayerListener player0Listener = new ZlExoPlayer.ISinglePlayerListener() {
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

    private final ZlExoPlayer.ISinglePlayerListener player1Listener = new ZlExoPlayer.ISinglePlayerListener() {
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
