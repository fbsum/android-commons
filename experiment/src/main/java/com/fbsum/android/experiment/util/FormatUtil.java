package com.fbsum.android.experiment.util;

import android.support.annotation.ColorInt;

/**
 * Created by xin on 7/27/17.
 */

public final class FormatUtil {

    private FormatUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Convert a color int to hex string
     *
     * @param color colorInt
     * @return color hex string
     */
    public static String convertColorToHex(@ColorInt final int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
