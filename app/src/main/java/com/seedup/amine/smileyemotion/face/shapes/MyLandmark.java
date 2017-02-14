package com.seedup.amine.smileyemotion.face.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.google.android.gms.vision.face.Landmark;

/**
 * Created by amine on 14/02/17.
 */

public class MyLandmark {

    public static final int BOTTOM_MOUTH = 0;
    public static final int LEFT_CHEEK = 1;
    public static final int LEFT_EAR = 3;
    public static final int LEFT_EAR_TIP = 2;
    public static final int LEFT_EYE = 4;
    public static final int LEFT_MOUTH = 5;
    public static final int NOSE_BASE = 6;
    public static final int RIGHT_CHEEK = 7;
    public static final int RIGHT_EAR = 9;
    public static final int RIGHT_EAR_TIP = 8;
    public static final int RIGHT_EYE = 10;
    public static final int RIGHT_MOUTH = 11;

    private final float x;
    private final float y;
    private final int type;

    public MyLandmark(int type, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Canvas canvas, Paint p){
        canvas.drawPoint(x, y, p);
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public String getTitle(){
        switch (type) {
            case BOTTOM_MOUTH:
                return "BOTTOM_MOUTH";
            case NOSE_BASE:
                return "NOSE_BASE";
            case LEFT_CHEEK:
                return "LEFT_CHEEK";
            case LEFT_EAR:
                return "LEFT_EAR";
            case LEFT_EAR_TIP:
                return "LEFT_EAR_TIP";
            case LEFT_EYE:
                return "LEFT_EYE";
            case LEFT_MOUTH:
                return "LEFT_MOUTH";
            case RIGHT_CHEEK:
                return "RIGHT_CHEEK";
            case RIGHT_EAR:
                return "RIGHT_EAR";
            case RIGHT_EAR_TIP:
                return "RIGHT_EAR_TIP";
            case RIGHT_EYE:
                return "RIGHT_EYE";
            case RIGHT_MOUTH:
                return "RIGHT_MOUTH";
            default:
                throw new IllegalStateException("Wrong landmark type");
        }
    }
}
