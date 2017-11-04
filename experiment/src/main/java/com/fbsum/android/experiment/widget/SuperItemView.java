package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbsum.android.experiment.R;


/**
 * Created by xin on 7/28/17.
 */

public class SuperItemView extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    public int leftIconRes;
    public String leftText;
    public int rightIconRes;
    public String rightText;
    public boolean checked;

    private ImageView leftImageView;
    private TextView leftTextView;
    private ImageView rightImageView;
    private TextView rightTextView;
    private SwitchCompat switchCompat;
    private OnCheckedChangeListener onCheckedChangeListener;

    public SuperItemView(Context context) {
        this(context, null, 0);
    }

    public SuperItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SuperItemView);
            leftIconRes = ta.getResourceId(R.styleable.SuperItemView_siv_left_icon, 0);
            leftText = ta.getString(R.styleable.SuperItemView_siv_left_text);
            rightIconRes = ta.getResourceId(R.styleable.SuperItemView_siv_right_icon, 0);
            rightText = ta.getString(R.styleable.SuperItemView_siv_right_text);
            checked = ta.getBoolean(R.styleable.SuperItemView_siv_checked, false);
            int childLayout = ta.getResourceId(R.styleable.SuperItemView_siv_layout, 0);
            ta.recycle();
            if (childLayout != 0) {
                View.inflate(context, childLayout, this);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        leftImageView = (ImageView) findViewById(R.id.super_item_left_imageview);
        leftTextView = (TextView) findViewById(R.id.super_item_left_textview);
        rightImageView = (ImageView) findViewById(R.id.super_item_right_imageview);
        rightTextView = (TextView) findViewById(R.id.super_item_right_textview);
        switchCompat = (SwitchCompat) findViewById(R.id.super_item_switch);
        if (leftImageView != null && leftIconRes != 0) {
            leftImageView.setImageResource(leftIconRes);
        }
        if (leftTextView != null && !TextUtils.isEmpty(leftText)) {
            leftTextView.setText(leftText);
        }
        if (rightImageView != null && rightIconRes != 0) {
            rightImageView.setImageResource(rightIconRes);
        }
        if (rightTextView != null && !TextUtils.isEmpty(rightText)) {
            rightTextView.setText(rightText);
        }
        if (switchCompat != null) {
            switchCompat.setChecked(checked);
            switchCompat.setOnCheckedChangeListener(this);
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchCompat.setChecked(!switchCompat.isChecked());
                }
            });
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.leftIconRes = leftIconRes;
        ss.leftText = leftText;
        ss.rightIconRes = rightIconRes;
        ss.rightText = rightText;
        ss.checked = checked;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setLeftIconRes(ss.leftIconRes);
        setLeftText(ss.leftText);
        setRightIconRes(ss.rightIconRes);
        setRightText(ss.rightText);
        setChecked(ss.checked);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChange(this, b);
        }
    }

    public void setLeftIconRes(int iconRes) {
        if (leftImageView != null && iconRes != 0) {
            leftIconRes = iconRes;
            leftImageView.setImageResource(leftIconRes);
        }
    }

    public void setRightIconRes(int iconRes) {
        if (rightImageView != null && iconRes != 0) {
            rightIconRes = iconRes;
            rightImageView.setImageResource(rightIconRes);
        }
    }

    public void setLeftText(String text) {
        if (leftTextView != null && text != null) {
            leftText = text;
            leftTextView.setText(leftText);
        }
    }

    public void setRightText(String text) {
        if (rightTextView != null && text != null) {
            rightText = text;
            rightTextView.setText(rightText);
        }
    }

    public void setChecked(boolean checked) {
        if (switchCompat != null) {
            this.checked = checked;
            switchCompat.setChecked(checked);
        }
    }

    public ImageView getLeftImageView() {
        return leftImageView;
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public String getLeftText() {
        return leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public boolean isChecked() {
        return checked;
    }

    /**
     *
     */
    public interface OnCheckedChangeListener {

        void onCheckedChange(SuperItemView itemView, boolean check);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    private static class SavedState extends BaseSavedState {

        int leftIconRes;
        String leftText;
        int rightIconRes;
        String rightText;
        boolean checked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            leftIconRes = in.readInt();
            leftText = in.readString();
            rightIconRes = in.readInt();
            rightText = in.readString();
            checked = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(leftIconRes);
            out.writeString(leftText);
            out.writeInt(rightIconRes);
            out.writeString(rightText);
            out.writeInt(checked ? 1 : 0);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
