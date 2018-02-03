package com.fbsum.android.commons.util;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public final class ProcessUtils {

    private ProcessUtils() {
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 当前进程名
     */
    @Nullable
    @CheckResult
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取上下文对应的进程名
     *
     * @return 当前进程名
     */
    @Nullable
    @CheckResult
    public static String getProcessName(@NonNull Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoApp = am.getRunningAppProcesses();
        if (infoApp == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo proInfo : infoApp) {
            if (proInfo.pid == Process.myPid()) {
                if (proInfo.processName != null) {
                    return proInfo.processName;
                }
            }
        }
        return null;
    }
}