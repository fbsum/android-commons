package com.fbsum.android.experiment.util;

import android.support.annotation.CheckResult;

import java.lang.reflect.Field;

/**
 * Created by xin on 1/29/18.
 */

public final class ResourcesUtils {

    private ResourcesUtils() {
    }

    @CheckResult
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
