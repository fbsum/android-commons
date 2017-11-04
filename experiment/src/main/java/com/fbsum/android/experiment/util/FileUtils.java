package com.fbsum.android.experiment.util;

import android.text.TextUtils;

import java.io.File;

/**
 * Created by xin on 9/28/17.
 */

public final class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException();
    }

    public String getParentPath(String filePath) {
        int index = filePath.lastIndexOf(File.pathSeparator);
        return filePath.substring(0, index);
    }

    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }
}
