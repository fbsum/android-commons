package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by sum on 7/18/17.
 */

public class ScrollingImageView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private boolean isReleased;

    private int width;
    private int height;

    private Bitmap bitmap;
    private Rect bitmapRect;

    private Paint paint;
    private Rect firstRect;
    private Rect secondRect;
    private int xOffset;

    private int speed = 2;

    public ScrollingImageView(Context context) {
        this(context, null, 0);
    }

    public ScrollingImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollingImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                isReleased = false;
                drawBackground();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
                width = w;
                height = h;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                isReleased = true;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (width == 0 && height == 0) {
            width = getWidth();
            height = getHeight();
            firstRect = new Rect(0, 0, width, height);
            secondRect = new Rect(0, 0, width, height);
        }
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.bitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * PS：回调 surfaceDestroyed() 方法更改 isRelease 的值，与实例调用 drawBackground() 方法，存在线程并发问题。
     * 在 Activity 等组件 onPause() 方法调用后，禁止调用本方法。
     */
    public void drawBackground() {
        if (isReleased) {
            return;
        }
        if (bitmap == null || width == 0 || height == 0) {
            return;
        }

        firstRect.left = xOffset - width;
        firstRect.right = xOffset;
        secondRect.left = xOffset;
        secondRect.right = secondRect.left + width;

        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawBitmap(bitmap, bitmapRect, firstRect, paint);
            canvas.drawBitmap(bitmap, bitmapRect, secondRect, paint);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);

        xOffset += speed;
        xOffset = xOffset % width;
    }
}
