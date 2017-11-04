package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.fbsum.android.experiment.R;

/**
 * Created by sum on 10/18/17.
 */

public class RatioFrameLayout extends FrameLayout {

    private static final float NON_RATIO = 0.0f;
    private float ratio = NON_RATIO;

    public RatioFrameLayout(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
            ratio = a.getFloat(R.styleable.RatioLayout_rfl_ratio, NON_RATIO);
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (ratio == NON_RATIO) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            int newWidth;
            int newHeight;
            if (width / ratio < height) {
                newWidth = width;
                newHeight = Math.round(width / ratio);
            } else {
                newWidth = Math.round(height * ratio);
                newHeight = height;
            }

            int newWidthSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
            int newHeightSpec = MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY);
            super.onMeasure(newWidthSpec, newHeightSpec);
        }
    }
}
