package com.seedup.amine.smileyemotion.face.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by amine on 14/02/17.
 */

public class Circle {

    private int radius;
    private Point point;

    public Circle() {
        point = new Point(0, 0);
        radius = 1;
    }

    public Circle(int x, int y, int r) {
        point = new Point(x, y);
        radius = r;
    }

    public int getRadius() {
        return radius;
    }

    public double getArea() {
        return Math.PI * radius * radius;
    }

    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    public void draw(Canvas canvas, Paint p){
        canvas.drawCircle(point.x, point.y, radius, p);
    }
}
