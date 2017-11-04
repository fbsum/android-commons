package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.fbsum.android.experiment.R;


/**
 * Created by xin on 9/21/17.
 */

public class MultiImageView extends AppCompatImageView {

    private int[] imageIds;
    private int position = -1;

    public MultiImageView(Context context) {
        this(context, null, 0);
    }

    public MultiImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiImageView);
            int imagesResId = a.getResourceId(R.styleable.MultiImageView_miv_images, 0);
            position = a.getInteger(R.styleable.MultiImageView_miv_position, -1);
            a.recycle();

            TypedArray b = getResources().obtainTypedArray(imagesResId);
            int length = b.length();
            if (length > 0) {
                imageIds = new int[length];
                for (int i = 0; i < length; i++) {
                    imageIds[i] = b.getResourceId(i, -1);
                }
            }
            b.recycle();
        }
        if (imageIds != null && position >= 0 && position < imageIds.length) {
            setImageResource(imageIds[position]);
        }
    }

    public void setImagePosition(int position) {
        if (imageIds == null || position < 0 || position >= imageIds.length) {
            return;
        }
        if (this.position == position) {
            return;
        }
        this.position = position;
        setImageResource(imageIds[position]);
    }

    public int getImagePosition() {
        return position;
    }
}
