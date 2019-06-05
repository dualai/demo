package com.xzl.demo1.autotext.contentviewpagewithimg;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.xzl.demo1.R;

public class MainActivity extends Activity implements AutoVScrollTextViewCtSingle.ScrollStatusListener {
    AutoVScrollTextViewCtSingle textViewCtSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autov_main);
        textViewCtSingle = findViewById(R.id.left_tv);
        textViewCtSingle.setText("绝对是咖啡就开始打发范德萨范德萨范\n德萨范德萨范德萨发士大夫if是个积分兑换更合适的"+
                "\n份经过几个房间号国家机关的"
        );
        textViewCtSingle.setScrollStatusListener(MainActivity.this);
        textViewCtSingle.stopAndReset();
        textViewCtSingle.reset();
    }

    @Override
    public void onScrollPrepare() {

    }

    @Override
    public void onScrollStart() {

    }

    @Override
    public void onScrollStop(AutoVScrollTextViewCtSingle viewCtSingle) {
        viewCtSingle.reset();
    }
}
