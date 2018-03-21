package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

import com.fbsum.android.experiment.R;

/**
 * Created by xin on 3/7/18.
 */

public class ShadowFrameLayout extends FrameLayout {

    public ShadowFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private static final String DEFAULT_SHADOW_COLOR = "#8D8D8D";
    private static final int DEFAULT_SHADOW_OFFSET = 1;

    private Paint shadowPaint;
    private RectF shadowRect;
    private int shadowColor;
    private int shadowDepth;
    private int shadowRound;
    private int shadowLeftOffset;
    private int shadowTopOffset;
    private int shadowRightOffset;
    private int shadowBottomOffset;

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowFrameLayout);
            shadowColor = typedArray.getColor(R.styleable.ShadowFrameLayout_sfl_shadow_color, Color.parseColor(DEFAULT_SHADOW_COLOR));
            shadowDepth = (int) typedArray.getDimension(R.styleable.ShadowFrameLayout_sfl_shadow_depth, dip2px(8));
            shadowRound = (int) typedArray.getDimension(R.styleable.ShadowFrameLayout_sfl_shadow_round, dip2px(0));
            shadowLeftOffset = (int) typedArray.getDimension(R.styleable.ShadowFrameLayout_sfl_shadow_left_offset, DEFAULT_SHADOW_OFFSET);
            shadowTopOffset = (int) typedArray.getDimension(R.styleable.ShadowFrameLayout_sfl_shadow_top_offset, DEFAULT_SHADOW_OFFSET);
            shadowRightOffset = (int) typedArray.getDimension(R.styleable.ShadowFrameLayout_sfl_shadow_right_offset, DEFAULT_SHADOW_OFFSET);
            shadowBottomOffset = (int) typedArray.getDimension(R.styleable.ShadowFrameLayout_sfl_shadow_bottom_offset, DEFAULT_SHADOW_OFFSET);
            typedArray.recycle();
        } else {
            shadowColor = Color.parseColor(DEFAULT_SHADOW_COLOR);
            shadowDepth = (int) dip2px(8);
            shadowRound = 0;
            shadowLeftOffset = DEFAULT_SHADOW_OFFSET;
            shadowTopOffset = DEFAULT_SHADOW_OFFSET;
            shadowRightOffset = DEFAULT_SHADOW_OFFSET;
            shadowBottomOffset = DEFAULT_SHADOW_OFFSET;
        }

        shadowColor = getDarkerColor(shadowColor);

        setLayerType(LAYER_TYPE_SOFTWARE, null);
        shadowPaint = new Paint();
        shadowPaint.setColor(Color.WHITE);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setShadowLayer(shadowDepth, 0, 0, shadowColor);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (shadowRect == null) {
            shadowRect = new RectF();
            int width = getWidth();
            int height = getHeight();
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();

            shadowRect.left = paddingLeft + shadowLeftOffset;
            shadowRect.top = paddingTop + shadowTopOffset;
            shadowRect.right = width - paddingRight - shadowRightOffset;
            shadowRect.bottom = height - paddingBottom - shadowBottomOffset;
        }
        canvas.drawRoundRect(shadowRect, shadowRound, shadowRound, shadowPaint);
        super.dispatchDraw(canvas);
    }

    private float dip2px(float dpValue) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        float scale = dm.density;
        return (int) (dpValue * scale + 0.5F);
    }

    private static int getDarkerColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[1] = hsv[1] + 0.1f;
        hsv[2] = hsv[2] - 0.1f;
        return Color.HSVToColor(hsv);
    }

    public void setShadowColor(@ColorInt int color) {
        this.shadowColor = color;
        shadowPaint.setShadowLayer(shadowDepth, 0, 0, shadowColor);
        invalidate();
    }
}
