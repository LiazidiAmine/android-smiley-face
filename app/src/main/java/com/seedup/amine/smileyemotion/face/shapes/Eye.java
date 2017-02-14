package com.seedup.amine.smileyemotion.face.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by amine on 14/02/17.
 */

public class Eye {
    private int top;
    private int bottom;
    private int left;
    private int right;
    private RectF rect;

    public Eye(int x, int x1, int y, int y1, RectF rect){
        this.top = y;
        this.bottom = y1;
        this.left = x;
        this.right = x1;
        this.rect = rect;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public RectF getRect(){ return rect;}

    public void setTop(int top) {
        this.top = top;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setRect(RectF rect){ this.rect = rect;}

    public void draw(Canvas canvas, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rect, 0, 360, true, paint);
    }
}
