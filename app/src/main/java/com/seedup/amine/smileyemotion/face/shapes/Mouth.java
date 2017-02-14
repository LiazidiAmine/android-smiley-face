package com.seedup.amine.smileyemotion.face.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by amine on 14/02/17.
 */

public class Mouth {
    private int leftX;
    private int rightX;
    private int topY;
    private int bottomY;
    private int startAngle;
    private int sweepAngle;
    private RectF mouthRectF;
    private Path mouthPath;

    public Mouth(int leftX, int rightX, int topY, int bottomY, int startAngle, int sweepAngle, RectF mouthRectF) {
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.bottomY = bottomY;
        this.mouthRectF = mouthRectF;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
    }

    public int getLeftX() {
        return leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public int getTopY() {
        return topY;
    }

    public int getBottomY() {
        return bottomY;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public void setBottomY(int bottomY) {
        this.bottomY = bottomY;
    }

    public void draw(Canvas canvas, Paint paint){
        mouthPath = new Path();
        mouthPath.arcTo(mouthRectF,startAngle,sweepAngle,true);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mouthPath, paint);
    }
}
