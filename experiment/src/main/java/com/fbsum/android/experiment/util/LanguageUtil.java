package com.fbsum.android.experiment.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;

import java.util.Locale;

/**
 * 语言工具
 * <p/>
 * Created by sum on 7/5/16.
 */
public final class LanguageUtil {

    private LanguageUtil() {
        throw new UnsupportedOperationException();
    }

    public static Locale getCurrentLocale(Context context) {
        return context.getApplicationContext().getResources().getConfiguration().locale;
    }

    public static void changeLanguageConfiguration(Context context, Locale locale) {
        Resources resources = context.getApplicationContext().getResources();

        Configuration config = resources.getConfiguration();
        config.locale = locale;

        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static boolean isDisplayLanguageEquals(Locale l, Locale r) {
        return l.getDisplayLanguage().equals(r.getDisplayLanguage());
    }

    public static boolean isZh(Context context) {
        Locale locale = getCurrentLocale(context);
        return TextUtils.equals(locale.getDisplayLanguage(), "Chinese");
    }
}