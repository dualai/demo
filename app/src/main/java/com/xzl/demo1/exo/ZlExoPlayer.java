package com.xzl.demo1.exo;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

public class ZlExoPlayer {
    private SimpleExoPlayer mFrontPlayer;
    private SimpleExoPlayer mBbackPlayer;
    private PlayerView mPlayerView;

    public ZlExoPlayer(PlayerView mPlayerView) {
        this.mPlayerView = mPlayerView;
    }

    
}
