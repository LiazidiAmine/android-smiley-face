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
import android.graphics.RectF;
import android.util.Log;

import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import com.seedup.amine.smileyemotion.camera.GraphicOverlay;

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
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;

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

    private volatile Face mFace;

    Paint facePaint;
    Paint mePaint;


    float radius;
    float adjust;

    float mouthLeftX, mouthRightX, mouthTopY, mouthBottomY;
    RectF mouthRectF;
    Path mouthPath;

    RectF eyeLeftRectF, eyeRightRectF;
    float eyeLeftX, eyeRightx, eyeTopY, eyeBottomY;

    public FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        radius = overlay.getRadius();
        Log.d("RADIUS", radius+"");

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



        adjust = radius / 3.2f;


        // Left Eye
        eyeLeftX = radius-(radius*0.43f);
        eyeRightx = eyeLeftX+ (radius*0.3f);
        eyeTopY = radius-(radius*0.5f);
        eyeBottomY = eyeTopY + (radius*0.4f);

        eyeLeftRectF = new RectF(eyeLeftX+adjust,eyeTopY+adjust,eyeRightx+adjust,eyeBottomY+adjust);

        // Right Eye
        eyeLeftX = eyeRightx + (radius*0.3f);
        eyeRightx = eyeLeftX + (radius*0.3f);

        eyeRightRectF = new RectF(eyeLeftX+adjust,eyeTopY+adjust,eyeRightx+adjust,eyeBottomY+adjust);


        // Smiley Mouth
        mouthLeftX = radius-(radius/2.0f);
        mouthRightX = mouthLeftX+ radius;
        mouthTopY = radius - (radius*0.2f);
        mouthBottomY = mouthTopY + (radius*0.5f);

        mouthRectF = new RectF(mouthLeftX+adjust,mouthTopY+adjust,mouthRightX+adjust,mouthBottomY+adjust);
        mouthPath = new Path();

        mouthPath.arcTo(mouthRectF, 30, 120, true);

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

        canvas.drawRoundRect(new RectF(left, top, right, bottom),right,left,mBoxPaint);

        drawFaceAnnotations(canvas);
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
        List<Point> landmarks = new ArrayList<>();

        for (Landmark landmark : mFace.getLandmarks()) {
            int cx = (int) translateX(landmark.getPosition().x);
            int cy = (int) translateY(landmark.getPosition().y);
            landmarks.add(new Point(cx, cy));
        }

        for(Point p : landmarks){
            canvas.drawPoint(p.x,p.y,paint);
        }

        // 1. draw face
        canvas.drawCircle(radius+adjust, radius+adjust, radius, facePaint);

        // 2. draw mouth
        mePaint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mouthPath, mePaint);

        // 3. draw eyes
        mePaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(eyeLeftRectF, 0, 360, true, mePaint);
        canvas.drawArc(eyeRightRectF, 0, 360, true, mePaint);
    }

}
