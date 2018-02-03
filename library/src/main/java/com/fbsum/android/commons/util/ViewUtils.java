package com.fbsum.android.commons.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by xin on 10/28/17.
 */

public final class ViewUtils {

    private ViewUtils() {
    }

    @CheckResult
    public static int getCenterX(@NonNull View view) {
        return (view.getLeft() + view.getRight()) / 2;
    }

    @CheckResult
    public static int getCenterY(@NonNull View view) {
        return (view.getTop() + view.getBottom()) / 2;
    }

    @CheckResult
    public static int[] getCenter(@NonNull View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        return new int[]{cx, cy};
    }

    @CheckResult
    public static int getInnerCircleRadius(@NonNull View view) {
        int wr = view.getWidth() / 2;
        int hr = view.getHeight() / 2;
        return Math.min(wr, hr);
    }

    @CheckResult
    public static int getOuterCircleRadius(@NonNull View view) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;
        int dx = Math.max(cx, view.getWidth() - cx);
        int dy = Math.max(cy, view.getHeight() - cy);
        return (int) Math.hypot(dx, dy);
    }
}
