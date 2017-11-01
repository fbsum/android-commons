package com.fbsum.android.experiment.util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * 获得屏幕尺寸相关的工具类
 */
public final class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return 获取屏幕密度
     */
    public static DisplayMetrics getDensity(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        Display display = wm.getDefaultDisplay();
        display.getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * @return 获取屏幕真实密度
     */
    public static PointF getExactDensity(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        PointF point = new PointF();
        point.x = outMetrics.xdpi;
        point.y = outMetrics.ydpi;
        return point;
    }

    /**
     * @return 获取屏幕分辨率
     */
    public static Point getResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        Display display = wm.getDefaultDisplay();
        display.getMetrics(outMetrics);

        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else {
            point.x = outMetrics.widthPixels;
            point.y = outMetrics.heightPixels;
        }
        return point;
    }

    /**
     * @return 获取屏幕真实分辨率
     */
    public static Point getAvailableResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        Point point = new Point();
        point.x = outMetrics.widthPixels;
        point.y = outMetrics.heightPixels;
        return point;
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取加上状态栏的屏幕高度
     */
    public static int getScreenHeightWithStatusBar(Context context) {
        return getScreenHeight(context) + getStatusHeight(context);
    }

    /**
     * 通过反射，获取包含虚拟键的屏幕高度
     */
    public static int getHeightWithVirtualKeyboard(Context context) {
        int dpi = 0;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

}