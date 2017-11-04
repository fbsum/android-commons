package com.fbsum.android.experiment.util;

import android.graphics.Paint;
import android.widget.TextView;

/**
 * Created by xin on 10/24/17.
 */

public final class TextViewUtil {

    private TextViewUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * 加下划线
     */
    public static void addButtomLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * 添加中划线
     */
    public static void addMiddleLine(TextView textView, boolean isAntiAlias) {
        if (isAntiAlias) {
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    /**
     * 清除划线
     */
    public static void clearLine(TextView textView) {
        textView.getPaint().setFlags(0);
    }

    /**
     * 抗锯齿
     */
    private void setAntiAlias(TextView textView, boolean isAntiAlias) {
        textView.getPaint().setAntiAlias(isAntiAlias);
    }
}
