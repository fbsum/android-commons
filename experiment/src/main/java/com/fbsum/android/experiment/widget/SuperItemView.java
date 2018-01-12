package com.fbsum.android.experiment.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
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

public class SuperItemView extends FrameLayout
        implements CompoundButton.OnCheckedChangeListener {

    private int imageRes;
    private String text;
    private int secondImageRes;
    private String secondText;
    private boolean checked;

    private ImageView imageView;
    private TextView textView;
    private ImageView secondImageView;
    private TextView secondTextView;
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
            imageRes = ta.getResourceId(R.styleable.SuperItemView_siv_image, 0);
            text = ta.getString(R.styleable.SuperItemView_siv_text);
            secondImageRes = ta.getResourceId(R.styleable.SuperItemView_siv_second_icon, 0);
            secondText = ta.getString(R.styleable.SuperItemView_siv_second_text);
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

        imageView = (ImageView) findViewById(R.id.siv_image_view);
        textView = (TextView) findViewById(R.id.siv_text_view);
        secondImageView = (ImageView) findViewById(R.id.siv_second_image_view);
        secondTextView = (TextView) findViewById(R.id.siv_second_text_view);
        switchCompat = (SwitchCompat) findViewById(R.id.siv_switch);

        setImageResource(imageRes);
        setSecondImageResource(secondImageRes);
        setText(text);
        setSecondText(secondText);
        setChecked(checked);
        // 监听 SwitchCompat
        if (switchCompat != null) {
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
        ss.imageRes = imageRes;
        ss.text = text;
        ss.secondImageRes = secondImageRes;
        ss.secondText = secondText;
        ss.checked = checked;
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setImageResource(ss.imageRes);
        setText(ss.text);
        setSecondImageResource(ss.secondImageRes);
        setSecondText(ss.secondText);
        setChecked(ss.checked);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChange(this, b);
        }
    }

    public void setDrawable(Drawable drawable) {
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public void setImageResource(@DrawableRes int imageRes) {
        if (imageView != null && imageRes != 0) {
            this.imageRes = imageRes;
            imageView.setImageResource(this.imageRes);
        }
    }

    public void setSecondImageResource(@DrawableRes int imageRes) {
        if (secondImageView != null && imageRes != 0) {
            secondImageRes = imageRes;
            secondImageView.setImageResource(secondImageRes);
        }
    }

    public void setText(String text) {
        if (textView != null && text != null) {
            this.text = text;
            textView.setText(this.text);
        }
    }

    public void setHint(String hint) {
        if (textView != null && hint != null) {
            textView.setHint(hint);
        }
    }

    public void setSecondText(String text) {
        if (secondTextView != null && text != null) {
            this.secondText = text;
            secondTextView.setText(this.secondText);
        }
    }

    public void setSecondHint(String hint) {
        if (secondTextView != null && hint != null) {
            secondTextView.setHint(hint);
        }
    }

    public void setChecked(boolean checked) {
        if (switchCompat != null) {
            this.checked = checked;
            switchCompat.setChecked(checked);
        }
    }

    @Nullable
    public ImageView getImageView() {
        return imageView;
    }

    @Nullable
    public ImageView getSecondImageView() {
        return secondImageView;
    }

    @Nullable
    public TextView getTextView() {
        return textView;
    }

    @Nullable
    public TextView getSecondTextView() {
        return secondTextView;
    }

    @Nullable
    public SwitchCompat getSwitchCompat() {
        return switchCompat;
    }

    @DrawableRes
    public int getImageResource() {
        return imageRes;
    }

    @DrawableRes
    public int getSecondImageResource() {
        return secondImageRes;
    }

    public String getText() {
        return text;
    }

    public String getSecondText() {
        return secondText;
    }

    public boolean isChecked() {
        if (switchCompat != null) {
            return switchCompat.isChecked();
        }
        return false;
    }

    /**
     * 回调 SwitchCompat 的状态改变状态
     */
    public interface OnCheckedChangeListener {

        void onCheckedChange(SuperItemView itemView, boolean check);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }

    /**
     * 数据保存 State
     */
    private static class SavedState extends BaseSavedState {

        int imageRes;
        String text;
        int secondImageRes;
        String secondText;
        boolean checked;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            imageRes = in.readInt();
            text = in.readString();
            secondImageRes = in.readInt();
            secondText = in.readString();
            checked = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(imageRes);
            out.writeString(text);
            out.writeInt(secondImageRes);
            out.writeString(secondText);
            out.writeInt(checked ? 1 : 0);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
