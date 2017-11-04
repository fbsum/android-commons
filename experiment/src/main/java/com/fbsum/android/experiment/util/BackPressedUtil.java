package com.fbsum.android.experiment.util;

import android.content.Context;
import android.view.KeyEvent;

import com.fbsum.android.commons.util.ToastUtils;

/**
 * Created by xin on 4/14/17.
 */

public final class BackPressedUtil {

    private static long lastBackPressedTime = 0;

    private BackPressedUtil() {

    }

    public static boolean canExit(Context context, int keyCode, String toast) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            long timeD = currentTime - lastBackPressedTime;
            if (timeD >= 3000) {
                lastBackPressedTime = currentTime;
                ToastUtils.showShort(context, toast);
                return false;
            }
        }
        return true;
    }

}
