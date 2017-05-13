package com.seedup.amine.smileyemotion.face;

/**
 * Created by amine on 31/01/17.
 */

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.seedup.amine.smileyemotion.camera.GraphicOverlay;
import com.seedup.amine.smileyemotion.face.shapes.Circle;
import com.seedup.amine.smileyemotion.face.shapes.MyLandmark;
import com.seedup.amine.smileyemotion.face.shapes.SmileyFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
public class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private float mFaceHappiness;
    private float mEyeLeftOpen;
    private float mEyeRightOpen;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private SmileyFace smiley;

    private volatile Face mFace;

    float radius;

    public FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        radius = overlay.getRadius();

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);

    }

    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    public void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);

        // Happiness
        mFaceHappiness = face.getIsSmilingProbability();

        //Eyes
        mEyeLeftOpen = mFace.getIsLeftEyeOpenProbability();
        mEyeRightOpen = mFace.getIsRightEyeOpenProbability();

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;

//        canvas.drawText("happiness: " + String.format("%.2f", mFaceHappiness), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("right eye: " + String.format("%.2f", mEyeRightOpen), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("left eye: " + String.format("%.2f", mEyeLeftOpen), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);

        RectF r = new RectF(left,top,right,bottom);
        Point center = new Point((int)((r.left + r.right)/2), (int)((r.top + r.bottom)/2));
        Circle circle = new Circle(center.x, center.y, (int)face.getWidth());
        circle.draw(canvas, mBoxPaint);
        int radius = circle.getRadius();
        smiley = new SmileyFace(face.getWidth(), center);
        drawFaceAnnotations(canvas);
        smiley.draw(canvas);
    }

    /**
     * Draws a small circle for each detected landmark, centered at the detected landmark position.
     * <p>
     *
     * Note that eye landmarks are defined to be the midpoint between the detected eye corner
     * positions, which tends to place the eye landmarks at the lower eyelid rather than at the
     * pupil position.
     */
    private void drawFaceAnnotations(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        List<MyLandmark> landmarks = new ArrayList<>();

        for (Landmark landmark : mFace.getLandmarks()) {
            int cx = (int) translateX(landmark.getPosition().x);
            int cy = (int) translateY(landmark.getPosition().y);
            landmarks.add(new MyLandmark(landmark.getType(), cx, cy));
        }

        for(MyLandmark p : landmarks){
            //p.draw(canvas,paint);
        }
    }
}
