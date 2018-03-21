package com.fbsum.android.experiment.util;

import android.os.Environment;

/**
 * Created by xin on 3/1/18.
 */

public class StorageUtils {

    // 读/写检查
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    // 只读检查
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state)
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
