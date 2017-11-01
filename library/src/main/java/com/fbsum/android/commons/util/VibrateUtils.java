package com.fbsum.android.commons.util;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;

/**
 * 需要权限：<code><uses-permission android:name="android.permission.VIBRATE" /></code>
 * Created by xin on 11/1/17.
 */

public final class VibrateUtils {

    private VibrateUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * 震动 milliseconds 毫秒
     */
    public static void vibrate(final Activity activity, long milliseconds) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }

    /**
     * 以 pattern[] 方式震动
     */
    public static void vibrate(final Activity activity, long[] pattern, int repeat) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(pattern, repeat);
    }

    /**
     * 取消震动
     */
    public static void virateCancle(final Activity activity) {
        Vibrator vib = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        vib.cancel();
    }

}
