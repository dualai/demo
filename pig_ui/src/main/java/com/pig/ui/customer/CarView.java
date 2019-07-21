package com.pig.ui.customer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.pig.ui.R;

public class CarView extends View {

    private Bitmap carBitmap;
    private Path path;
    private PathMeasure pathMeasure;
    private float distanceRatio = 0;
    private Paint circlePaint; //画圆圈的画笔
    private Paint carPaint; //画小车的画笔
    private Matrix carMatrix; //针对car bitmap图片操作的矩阵
    public CarView(Context context) {
        this(context,null);
    }

    public CarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /***
     * PathMeasure https://www.jianshu.com/p/3efa5341abcc
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        carBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_car);
        path = new Path();
        /**
         * cw 顺时针
         */
        path.addCircle(0,0,200,Path.Direction.CW);
        /**
         * 这个参数——forceClosed，简单的说，就是Path最终是否需要闭合，如果为True的话，则不管关联的Path是否是闭合的，都会被闭合。
         * 但是这个参数对Path和PathMeasure的影响是需要解释下的：
         * forceClosed参数对绑定的Path不会产生任何影响，例如一个折线段的Path，本身是没有闭合的，forceClosed设置为True的时候，PathMeasure计算的Path是闭合的，但Path本身绘制出来是不会闭合的。
         * forceClosed参数对PathMeasure的测量结果有影响，还是例如前面说的一个折线段的Path，本身没有闭合，forceClosed设置为True，PathMeasure的计算就会包含最后一段闭合的路径，与原来的Path不同。
         */
        pathMeasure = new PathMeasure(path,false);

        circlePaint = new Paint();
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true); //抗锯齿
        circlePaint.setColor(Color.BLACK);

        carPaint = new Paint();
        carPaint.setColor(Color.DKGRAY);
        carPaint.setStrokeWidth(2);
        carPaint.setStyle(Paint.Style.STROKE);

        carMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE); //背景置空

        int width = getWidth();
        int height = getHeight();

        //移动canvas坐标系到中点
        canvas.translate(width / 2,height / 2);

        //每次重置
        carMatrix.reset();

        distanceRatio += 0.006; //当前进度百分比

        /**
         * 也可以用自定义属性动画来赋值
         */
        if(distanceRatio >= 1){
            distanceRatio = 0;
        }

        //        0 - 1
        float[] pos = new float[2]; //记录位置
        float[] tan = new float[2]; //记录切点值xy
//        Log.d("test","pathMeasure.getLength():"+pathMeasure.getLength());
        /***
         * pathMeasure.getLength() 当前为圆的总长度
         */
        float distance = pathMeasure.getLength() * distanceRatio;
        pathMeasure.getPosTan(distance, pos, tan);
//        tan0代表 cos tan[1] sin
        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI); //计算小车本身要旋转的角度
        Log.d("test","degree:"+degree);

        /***
         *          假设这里的width和height分别为图片的width和height
         *          matrix.postRotate(30);//绕原点旋转30度
         * //		matrix.postRotate(30, width/2, heigth/2);//绕某个点旋转30度，这里选择的原点是图片的中心点
         * //		matrix.postScale(2, 1);//两个参数为缩放比例。按比例缩放，宽为原来的2倍，1为正常所以高不变，但参考点事坐标原点
         * //		matrix.postTranslate(240-width/2, 400-heigth/2);//参考点为坐标原点（0，0）移动到（240-width/2,400-heigth/2）
         * //		matrix.postScale(2, 2, 240-width/2,400-heigth/2);//以 （240-width/2,400-heigth/2）为缩放中心
         * //		matrix.postSkew(0.2f, 0.2f);//参数为x,y轴倾斜角度
         */

        /***
         * 弧度：radian
         *
         * 角度：degress
         *
         * Math.atan2(y,x) 得到弧度；
         *
         * 每个弧度有多少角度 = 180 / Math.PI(3.14);
         *
         * 角度 = 弧度 * (180 / Math.PI);
         *
         * Matrix矩阵认角度
         */
        carMatrix.postRotate(degree, carBitmap.getWidth() / 2, carBitmap.getHeight() / 2); //设置旋转角度和旋转中心
        //这里要将设置到小车的中心点
        carMatrix.postTranslate(pos[0] - carBitmap.getWidth() / 2, pos[1] - carBitmap.getHeight() / 2);
//        carMatrix.postTranslate(pos[0], pos[1] );
        canvas.drawPath(path, circlePaint);
        canvas.drawBitmap(carBitmap, carMatrix, carPaint);
        invalidate();

    }
}
