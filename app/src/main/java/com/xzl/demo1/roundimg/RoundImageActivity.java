package com.xzl.demo1.roundimg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.xzl.demo1.R;

public class RoundImageActivity extends Activity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundimg);

        imageView = findViewById(R.id.img);

        imageView.setImageDrawable(getMusicItemDrawable(getApplicationContext()));
    }


    public Drawable getMusicItemDrawable(Context context) {
//        外面的圆盘
        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R
                .drawable.ic_disc), 813, 813, false);

//        Bitmap bitmapMusicPic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_music2), 560, 560, false);

        Bitmap bitmapMusicPic = getMusicPicBitmap(getApplicationContext(),533,R.drawable.ic_music2);

        BitmapDrawable discDrawable = new BitmapDrawable(bitmapDisc);
        RoundedBitmapDrawable roundMusicDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmapMusicPic);

        discDrawable.setAntiAlias(true);
        roundMusicDrawable.setAntiAlias(true);


        Drawable[] drawables = new Drawable[2];
        drawables[0] = roundMusicDrawable;
        drawables[1] = discDrawable;

        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        int musicPicMargin = (int) ((720-560)/2);
        //调整专辑图片的四周边距，让其显示在正中
        layerDrawable.setLayerInset(0, musicPicMargin, musicPicMargin, musicPicMargin,
                musicPicMargin);
        return layerDrawable;
    }


    public Bitmap getMusicPicBitmap(Context context,int musicPicSize, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(context.getResources(),resId,options);
        int imageWidth = options.outWidth;

        int sample = imageWidth / musicPicSize;
        int dstSample = 1;
        if (sample > dstSample) {
            dstSample = sample;
        }
        options.inJustDecodeBounds = false;
        //设置图片采样率
        options.inSampleSize = dstSample;
        //设置图片解码格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),
                resId, options), musicPicSize, musicPicSize, true);
    }
}
