package com.fbsum.android.experiment.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by hujiang on 2017/4/13.
 */

public final class ServiceUtil {

    private ServiceUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
        if (TextUtils.isEmpty(serviceName)) {
            return true;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService
                = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (TextUtils.equals(runningService.get(i).service.getClassName(), serviceName)) {
                return true;
            }
        }
        return false;
    }
}
