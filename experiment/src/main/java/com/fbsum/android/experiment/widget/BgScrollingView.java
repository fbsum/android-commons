package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xin on 8/21/17.
 */

public class BgScrollingView extends View {

    private static int DIRECTION_RIGHT = 1;
    private static int DIRECTION_LEFT = -1;
    private static int DELAY_MILLISECOND = 48;

    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;

    private boolean isStarted;
    private float speed = 1;
    private int dstWidth;
    private int offset;
    private int direction = DIRECTION_RIGHT;
    private Rect srcRect;
    private Rect dstRect;

    public BgScrollingView(Context context) {
        super(context);
    }

    public BgScrollingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BgScrollingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();

        dstWidth = (int) (bitmapWidth * 0.5f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            if (dstRect == null) {
                dstRect = new Rect(0, 0, getWidth(), getHeight());
            } else {
                dstRect.right = getWidth();
                dstRect.bottom = getHeight();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null || bitmap == null || getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if (direction == DIRECTION_RIGHT) {
            if (offset + dstWidth > bitmapWidth) {
                offset = bitmapWidth - dstWidth;
            } else if (offset + dstWidth == bitmapWidth) {
                direction = DIRECTION_LEFT;
            }
        } else {
            if (offset < 0) {
                offset = 0;
            } else if (offset == 0) {
                direction = DIRECTION_RIGHT;
            }
        }

        if (srcRect == null) {
            srcRect = new Rect();
        }
        srcRect.left = offset;
        srcRect.right = offset + dstWidth;
        srcRect.top = 0;
        srcRect.bottom = bitmapHeight;
        if (dstRect == null) {
            dstRect = new Rect(0, 0, getWidth(), getHeight());
        }
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);

        if (isStarted && speed != 0) {
            offset += speed * direction;
            postInvalidateDelayed(DELAY_MILLISECOND);
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        if (isStarted) {
            postInvalidateDelayed(DELAY_MILLISECOND);
        }
    }


    public void start() {
        if (!isStarted) {
            isStarted = true;
            postInvalidateDelayed(DELAY_MILLISECOND);
        }
    }

    public void stop() {
        if (isStarted) {
            isStarted = false;
            invalidate();
        }
    }

}
