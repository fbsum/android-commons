package com.fbsum.android.commons.util;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.CheckResult;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xin on 9/21/17.
 */

public class LifecycleUtils {

    private LifecycleUtils() {
        throw new UnsupportedOperationException();
    }

    @CheckResult
    public static boolean isNotResume(AppCompatActivity activity) {
        return !isAtLeastResume(activity);
    }

    @CheckResult
    public static boolean isAtLeastResume(AppCompatActivity activity) {
        return activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }
}
