package com.fbsum.android.experiment.util;

import android.graphics.Color;

/**
 * Created by xin on 5/12/17.
 */

public final class ColorUtil {
    private ColorUtil() {
    }

    /**
     * @param color     颜色值
     * @param luminance 亮度值（正／负）
     * @return 改变亮度后的颜色值
     */
    public static int setLuminance(int color, int luminance) {
        int r = Color.red(color) + luminance;
        if (r < 0) {
            r = 0;
        }
        if (r > 255) {
            r = 255;
        }
        int g = (int) (Color.green(color) + luminance * 0.78);
        if (g < 0) {
            g = 0;
        }
        if (g > 255) {
            g = 255;
        }
        int b = (int) (Color.blue(color) + luminance * 0.2);
        if (b < 0) {
            b = 0;
        }
        if (b > 255) {
            b = 255;
        }
        return Color.argb(255, r, g, b);
    }
}
