package com.fbsum.android.commons.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fbsum.android.commons.R;


/**
 * Created by sum on 16-1-5.
 */
public class DividerView extends View {

    private static final int COLOR_TRANSPARENT = -1;

    private RectF rect;
    private Paint paint;
    private int foregroundColor = COLOR_TRANSPARENT;
    private float leftForegroundMargin;
    private float rightForegroundMargin;

    public DividerView(Context context) {
        this(context, null, 0);
    }

    public DividerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DividerView);
            leftForegroundMargin = ta.getDimension(R.styleable.DividerView_divider_foreground_margin_left, 0);
            rightForegroundMargin = ta.getDimension(R.styleable.DividerView_divider_foreground_margin_right, 0);
            foregroundColor = ta.getColor(R.styleable.DividerView_divider_foreground_color, COLOR_TRANSPARENT);
            ta.recycle();
        }

        paint = new Paint();
        paint.setColor(foregroundColor);

        rect = new RectF();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            rect.set(leftForegroundMargin, 0, width - rightForegroundMargin, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (foregroundColor != COLOR_TRANSPARENT) {
            canvas.drawRect(rect, paint);
        }
    }
}

