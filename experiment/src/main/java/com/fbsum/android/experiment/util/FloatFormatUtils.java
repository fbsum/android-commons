package com.fbsum.android.experiment.util;

import java.text.DecimalFormat;

/**
 * Created by xin on 11/16/17.
 */

public class FloatFormatUtils {

    private static final DecimalFormat floatFormat;

    static {
        floatFormat = new DecimalFormat("##0.00");
        floatFormat.setMinimumFractionDigits(0);
    }

    public static String format(float f) {
        return floatFormat.format(f);
    }
}
