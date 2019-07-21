package com.pig.svg;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public class ProviceItem {
    private Path path;

    /**
     * 绘制颜色
     */
    private  int drawColor;
    public ProviceItem(Path path) {
        this.path = path;
    }

    public void setDrawColor(int drawColor) {
        this.drawColor = drawColor;
    }
    void drawItem(Canvas canvas, Paint paint, boolean isSelect) {
        if(isSelect){
//            绘制内部的颜色
            paint.clearShadowLayer();
            paint.setStrokeWidth(5);

            paint.setColor(Color.parseColor("#ff0000"));
//            paint.setColor(0xff0000);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);

//            绘制边界

            paint.setColor(Color.parseColor("#0000ff"));
//            paint.setColor(0x0000ff);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(path, paint);
        }else {

            //            绘制边界
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
//            paint.setShadowLayer(8,10,10,0x00ff00);
            paint.setStrokeWidth(2);
            canvas.drawPath(path,paint);

            paint.clearShadowLayer();
            paint.setColor(drawColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);
        }


    }

    public boolean isTouch(float x, float y) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return region.contains((int)x, (int)y);
    }
}
