package com.fbsum.android.commons.util;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.CheckResult;
import android.support.v4.app.FragmentActivity;

/**
 * Created by xin on 9/21/17.
 */

public class LifecycleUtils {

    private LifecycleUtils() {
    }

    @CheckResult
    public static boolean isNotResume(FragmentActivity activity) {
        return !isResume(activity);
    }

    @CheckResult
    public static boolean isResume(FragmentActivity activity) {
        if (activity == null) {
            return false;
        }
        return activity.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED);
    }
}
