package com.fbsum.android.experiment.util;

import android.support.annotation.CheckResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xin on 9/21/17.
 */

public class DebounceUtil {

    private static final String DEFAULT_TAG = "DEBOUNCE_DEFAULT_TAG";
    private static final Map<String, Long> timeMap = new HashMap<>();

    private DebounceUtil() {
        throw new UnsupportedOperationException();
    }

    @CheckResult
    public static boolean debounce(long debounceTime) {
        return debounce(DEFAULT_TAG, debounceTime);
    }

    @CheckResult
    public static boolean debounce(String tag, long debounceTime) {
        Long savedTime = timeMap.get(tag);
        if (savedTime == null) {
            return false;
        }
        long crrTime = System.currentTimeMillis();
        long timeD = crrTime - savedTime;
        if (0 <= timeD && timeD < debounceTime) {
            return true;
        } else {
            timeMap.put(tag, crrTime);
            return false;
        }
    }
}
