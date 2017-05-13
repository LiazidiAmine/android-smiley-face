package com.seedup.amine.smileyemotion.face.shapes;

/**
 * Created by amine on 13/02/17.
 */

import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

public class SmileyFace {

    private Paint facePaint;
    private Paint mePaint;

    private Point center;

    private float radius;
    private float adjustX;
    private float adjustY;

    public Mouth mouth;
    public Eye leftEye, rightEye;
    public Circle circle;

    public SmileyFace(float radius, Point center) {
        this.radius= radius;
        this.center = center;

        facePaint = new Paint();
        facePaint.setColor(0xfffed325); // yellow
        facePaint.setDither(true);
        facePaint.setStrokeJoin(Paint.Join.ROUND);
        facePaint.setStrokeCap(Paint.Cap.ROUND);
        facePaint.setPathEffect(new CornerPathEffect(10) );
        facePaint.setAntiAlias(true);
        facePaint.setShadowLayer(4, 2, 2, 0x80000000);

        mePaint = new Paint();
        mePaint.setColor(0xff2a2a2a);
        mePaint.setDither(true);
        mePaint.setStyle(Paint.Style.STROKE);
        mePaint.setStrokeJoin(Paint.Join.ROUND);
        mePaint.setStrokeCap(Paint.Cap.ROUND);
        mePaint.setPathEffect(new CornerPathEffect(10) );
        mePaint.setAntiAlias(true);
        mePaint.setStrokeWidth(radius / 14.0f);

        adjustX = center.x - radius;
        adjustY = center.y - radius;

        // Left Eye
        int eyeLeftX = (int)(radius-(radius*0.43f));
        int eyeRightX = (int)(eyeLeftX+ (radius*0.3f));
        int eyeTopY = (int)(radius-(radius*0.5f));
        int eyeBottomY = (int)(eyeTopY + (radius*0.4f));
        RectF eyeLeftRectF = new RectF(eyeLeftX + adjustX,eyeTopY+ adjustY,eyeRightX+ adjustX,eyeBottomY+ adjustY);

        leftEye = new Eye(eyeLeftX, eyeRightX, eyeTopY, eyeBottomY, eyeLeftRectF);

        // Right Eye
        eyeLeftX = (int)(eyeRightX + (radius*0.3f));
        eyeRightX = (int)(eyeLeftX + (radius*0.3f));
        RectF eyeRightRectF = new RectF(eyeLeftX+ adjustX,eyeTopY+ adjustY,eyeRightX+ adjustX,eyeBottomY+ adjustY);

        rightEye = new Eye(eyeLeftX, eyeRightX, eyeTopY, eyeBottomY, eyeRightRectF);

        // Smiley Mouth
        int mouthLeftX = (int)(radius-(radius/2.0f));
        int mouthRightX = (int)(mouthLeftX+ radius);
        int mouthTopY = (int)(radius - (radius*0.2f));
        int mouthBottomY = (int)(mouthTopY + (radius*0.5f));
        RectF mouthRectF = new RectF(mouthLeftX+ adjustX,mouthTopY+ adjustY,mouthRightX+ adjustX,mouthBottomY+ adjustY);

        mouth = new Mouth(mouthLeftX, mouthRightX, mouthTopY, mouthBottomY,30, 120, mouthRectF);

        // Smiley Face
        circle = new Circle((int)(radius + adjustX), (int)(radius + adjustY), (int)radius);
        Log.d("SMILEY ","RADIUS :::" + radius + " ");
        Log.d("SMILEY ","ADJUST X :::" + adjustX + " ");
        Log.d("SMILEY ","X :::" + adjustX + radius + " ");
        Log.d("SMILEY ","CENTER X" + center.x + " ");

    }

    public void draw(Canvas canvas) {

        // 1. draw face
        circle.draw(canvas, facePaint);

        // 2. draw mouth
        mouth.draw(canvas, mePaint);

        // 3. draw eyes
        leftEye.draw(canvas, mePaint);
        rightEye.draw(canvas, mePaint);

    }

    private void clear(Canvas canvas) {
        canvas.drawColor(0xfffed325);
    }

    public void update(Canvas canvas) {
        clear(canvas);
        draw(canvas);
    }

}
